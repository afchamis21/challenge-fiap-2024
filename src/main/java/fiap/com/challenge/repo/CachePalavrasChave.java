package fiap.com.challenge.repo;

import fiap.com.challenge.services.ArquivoService;
import fiap.com.challenge.services.PalavraService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CachePalavrasChave {
    private static final Logger log = LoggerFactory.getLogger(CachePalavrasChave.class);
    private final Map<String, Map<String, Long>> palavrasCache = new ConcurrentHashMap<>();
    private final ArquivoService arquivoService;
    private final PalavraService palavraService;

    public CachePalavrasChave(ArquivoService arquivoService, PalavraService palavraService) {
        this.arquivoService = arquivoService;
        this.palavraService = palavraService;
    }

    @PostConstruct
    void inicializarCache() {
        log.info("Inicializando cache...");
        try {
            File[] arquivos = arquivoService.pegarTodosOsModelos();
            for (File arquivo : arquivos) {
                String nomeArquivo = arquivo.getName();
                ConcurrentHashMap<String, Long> palavrasArquivo = new ConcurrentHashMap<>();
                List<String> linhas = Files.readAllLines(arquivo.toPath()).stream().map(String::toLowerCase).toList();
                for (String linha : linhas) {
                    String[] palavras = linha.split(" ");
                    checarPalavrasChave(palavras, palavrasArquivo);
                }

                palavrasCache.put(nomeArquivo, palavrasArquivo);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Erro ao manipular arquivo", e);
            throw new RuntimeException(e);
        }
        log.info("Cache inicializado com {} arquivos", palavrasCache.size());
    }

    private void checarPalavrasChave(String[] palavras, ConcurrentHashMap<String, Long> palavrasArquivo) {
        for (String palavra : palavras) {
            if (!palavraService.isPalavraChave(palavra)) {
                continue;
            }

            if (palavra.contains("/")) {
                checarPalavrasChave(palavra.split("/"), palavrasArquivo);
                continue;
            }

            palavrasArquivo.put(palavra, palavrasArquivo.getOrDefault(palavra, 0L) + 1);
        }
    }

    public Map<String, Map<String, Long>> getPalavrasCache() {
        return palavrasCache;
    }
}