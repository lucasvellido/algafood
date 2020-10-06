package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import com.algaworks.algafood.api.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.api.secutity.AlgaSecurity;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
//@ResponseBody O restController já tem isso implementado
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;

    @Autowired
    AlgaSecurity algaSecurity;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurante> listar() {
        System.out.println("algaSecurity = " + algaSecurity.getUsuarioId());
        return restauranteRepository.findAll();
    }

    //    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        return restaurante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        /*Exemplo de como mudar o status*/
//        return ResponseEntity.status(HttpStatus.OK).body(restaurante);
//        return ResponseEntity.ok(restaurante);

        /*Exemplo de redirecionamento de URL*/
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://localhost:8080/restaurantes");
//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .headers(headers)
//                .build();
    }

    @GetMapping("/por-nome")
    public List<Restaurante> buscarPorNome(String nome, Long cozinhaId) {
        return restauranteRepository.consultaPorNome(nome, cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/primeiro")
    public Optional<Restaurante> restaurantePrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }


    @GetMapping("/por-nome/between")
    public List<Restaurante> buscarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        try {
            if (restauranteAtual.isPresent()) {
                BeanUtils.copyProperties(restaurante, restauranteAtual.get(),
                        "id", "formaPagamentos", "endereco", "dataCadastro");

                Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
                return ResponseEntity.ok(restauranteSalvo);
            }
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable("restauranteId") Long restauranteId,
                                              @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

        if (restauranteAtual.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual.get(), request);

        try {
        } catch (EntidadeNaoEncontradaException e) {
        }

        return atualizar(restauranteId, restauranteAtual.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        }catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> remover(@PathVariable("restauranteId") Long restauranteId) {
        try {
            cadastroRestaurante.excluir(restauranteId);

            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
