package com.algaworks.algafood.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@ResponseBody O restController já tem isso implementado
@RestController
@RequestMapping("/cidades")
public class CidadeController {

//    @Autowired
//    CidadeRepository cidadeRepository;

//    @GetMapping
//    public List<Cidade> listar() {
//        return cidadeRepository.findAll();
//    }
}
