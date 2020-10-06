package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.execeptionhandler.Problema;
import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.CozinhasXmlWrapper;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import com.algaworks.algafood.api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
//@ResponseBody O restController já tem isso implementado
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    CadastroCozinhaService cadastroCozinha;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
    }

    //    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
        return cadastroCozinha.buscarOuFalhar(id);
/*
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        }

        return ResponseEntity.notFound().build();
*/

        /*O trecho de código a cima é a mesma coisa que esse*/
//        return cozinha.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        /*Exemplo de como mudar o status*/
//        return ResponseEntity.status(HttpStatus.OK).body(cozinha);
//        return ResponseEntity.ok(cozinha);

        /*Exemplo de redirecionamento de URL*/
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .headers(headers)
//                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody  Cozinha cozinha) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cadastroCozinha.salvar(cozinhaAtual);

/*
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

        if (cozinhaAtual.isPresent()) {
            //        cozinhaAtual.setNome(cozinha.getNome());
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");

            Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());

            return ResponseEntity.ok(cozinhaSalva);

        }

        return ResponseEntity.notFound().build();
*/
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long cozinhaId) {
        try {
            cadastroCozinha.excluir(cozinhaId);

            return ResponseEntity.noContent().build();
            /*Colo  cando a anotação de classe da exception, não preciso */
//        } catch (EntidadeNaoEncontradaException e) {
//            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

//    @ExceptionHandler({EntidadeNaoEncontradaException.class})
//    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
//        Problema problema = Problema.builder()
//                .dateHora(LocalDateTime.now())
//                .mensagem(e.getMessage())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
//    }
}
