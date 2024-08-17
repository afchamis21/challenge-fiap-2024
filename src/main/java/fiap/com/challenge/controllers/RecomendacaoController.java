package fiap.com.challenge.controllers;

import fiap.com.challenge.model.RecomendacaoArquivo;
import fiap.com.challenge.services.BuscadorDePalavraChaveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class RecomendacaoController {
    private final BuscadorDePalavraChaveService buscadorDePalavraChaveService;

    public RecomendacaoController(BuscadorDePalavraChaveService buscadorDePalavraChaveService) {
        this.buscadorDePalavraChaveService = buscadorDePalavraChaveService;
    }

    @GetMapping("modelo")
    public List<RecomendacaoArquivo> pesquisarMelhoresModelos(@RequestParam String frase) {
        return buscadorDePalavraChaveService.procurarMelhoresModelos(frase);
    }
}
