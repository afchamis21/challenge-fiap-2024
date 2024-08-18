package fiap.com.challenge.services;

import fiap.com.challenge.model.RecomendacaoArquivo;
import fiap.com.challenge.repo.CachePalavrasChave;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BuscadorDePalavraChaveService {
    private final double porcentagemMinimaParaMatch = 0.6;
    private final PalavraService palavraService;
    private final CachePalavrasChave cachePalavrasChave;

    public BuscadorDePalavraChaveService(PalavraService palavraService, CachePalavrasChave cachePalavrasChave) {
        this.palavraService = palavraService;
        this.cachePalavrasChave = cachePalavrasChave;
    }

    public List<RecomendacaoArquivo> procurarMelhoresModelos(String frase) {
        List<RecomendacaoArquivo> res = new ArrayList<>();
        List<String> palavrasChave = palavraService.pegarPalavrasChave(frase);
        Map<String, Map<String, Long>> cache = cachePalavrasChave.getPalavrasCache();
        for (String arquivo : cache.keySet()) {
            RecomendacaoArquivo recomendacaoArquivo = new RecomendacaoArquivo(arquivo);

            Map<String, Long> map = cache.get(arquivo);

            for (String palavra : palavrasChave) {
                for (String palavraDoCache: map.keySet()) {
                    if (palavra.equals(palavraDoCache)
                            || palavraService.calcularSimilaridadeEntrePalavras(palavra, palavraDoCache) > porcentagemMinimaParaMatch) {
                        recomendacaoArquivo.adicionarOcorrencia(map.get(palavraDoCache));
                    }
                }
            }

            res.add(recomendacaoArquivo);
        }

        return res.stream()
                .filter(recomendacaoArquivo -> recomendacaoArquivo.getOcorrenciasDePalavrasChave() > 0)
                .sorted(Comparator.comparingLong(RecomendacaoArquivo::getOcorrenciasDePalavrasChave).reversed())
                .limit(5)
                .toList();
    }
}
