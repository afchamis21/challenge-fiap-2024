package fiap.com.challenge.model;

import java.io.Serializable;

public class RecomendacaoArquivo implements Comparable<RecomendacaoArquivo>, Serializable {
    private final String nomeArquivo;
    private long ocorrenciasDePalavrasChave = 0;

    public RecomendacaoArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public void adicionarOcorrencia(long ocorrencias) {
        ocorrenciasDePalavrasChave += ocorrencias;
    }

    @Override
    public int compareTo(RecomendacaoArquivo o) {
        return Long.compare(o.ocorrenciasDePalavrasChave, this.ocorrenciasDePalavrasChave);
    }

    @Override
    public String toString() {
        return "RecomendacaoArquivo{" +
                "nomeArquivo='" + nomeArquivo + '\'' +
                ", ocorrenciasDePalavrasChave=" + ocorrenciasDePalavrasChave +
                '}';
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public long getOcorrenciasDePalavrasChave() {
        return ocorrenciasDePalavrasChave;
    }

    public void setOcorrenciasDePalavrasChave(long ocorrenciasDePalavrasChave) {
        this.ocorrenciasDePalavrasChave = ocorrenciasDePalavrasChave;
    }
}
