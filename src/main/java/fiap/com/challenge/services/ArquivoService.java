package fiap.com.challenge.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class ArquivoService {
    private final String pastaDeModelos = "modelos";

    public File[] pegarTodosOsModelos() throws URISyntaxException, FileNotFoundException {
        URL urlPasta = ClassLoader.getSystemClassLoader().getResource(pastaDeModelos);
        if (urlPasta == null) {
            throw new FileNotFoundException("Pasta de modelos não encontrada dentro da pasta resources. Nome esperado : " + pastaDeModelos);
        }
        File folder = new File(urlPasta.toURI());
        return folder.listFiles();
    }
}
