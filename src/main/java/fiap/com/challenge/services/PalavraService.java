package fiap.com.challenge.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PalavraService {
    private final int tamanhoMinimoPalavraChave = 5;

    /**
     * Arquivo para achar palavras com inícios similares, para aumentar acurácia da busca por palavras-chave
     * Ex: Ativar/Ativação, Desativar/Desativação
     * */
    public double calcularSimilaridadeEntrePalavras(String palavra1, String palavra2) {
        String menorPalavra;
        String maiorPalavra;

        if (palavra1.length() > palavra2.length()) {
            maiorPalavra = palavra1;
            menorPalavra = palavra2;
        } else {
            maiorPalavra = palavra2;
            menorPalavra = palavra1;
        }

        int contador = 0;
        for (int i = 0; i < menorPalavra.length(); i++) {
            if (menorPalavra.charAt(i) == maiorPalavra.charAt(i)) {
                contador++;
            }
        }

        return (double) contador / maiorPalavra.length();
    }

    /**
     * Receba uma frase e analisa quais palavras se classificam como palavra-chave
     *
     * @return Uma lista com as palavras-chave
     * */
    public List<String> pegarPalavrasChave(String frase) {
        return Arrays.stream(frase.split(" ")).filter(this::isPalavraChave).map(String::toLowerCase).toList();
    }

    /**
     * Define se uma palavra é uma palavra-chave ou não.
     * Para ser uma palavra-chave a palavra deve:
     * <ul>
     *     <li>Ter tamanho >= a 5</li>
     *     <li>Não ter nenhuma sequência de 3 ou mais letras repetidas. Queremos filtrar palavras como xxxxxxx ou http</li>
     * </ul>
     *
     * @return {@code True} se for palavra-chave, senão {@code False}
     * */
    public boolean isPalavraChave(String palavra) {
        return palavra.length() >= tamanhoMinimoPalavraChave && contarMaiorSequencia(palavra) <= 1;
    }

    /**
     * Conta a maior sequência de letras repetidas em uma palavra
     * */
    private int contarMaiorSequencia(String palavra) {
        if (palavra == null || palavra.isEmpty()) {
            return 0;
        }

        int maxSeq = 1;
        int currentSeq = 1;

        for (int i = 1; i < palavra.length(); i++) {
            if (palavra.charAt(i) == palavra.charAt(i - 1)) {
                currentSeq++;
            } else {
                maxSeq = Math.max(maxSeq, currentSeq);
                currentSeq = 1;
            }
        }

        maxSeq = Math.max(maxSeq, currentSeq);

        return maxSeq;
    }
}
