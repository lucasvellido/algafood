package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
