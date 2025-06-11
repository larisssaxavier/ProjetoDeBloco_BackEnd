package br.com.suporteCFTV.model;

public class SLA {
    private int idSLA;
    private String nomeRegra;
    private Integer tempoRespostaHoras;
    private Integer tempoSolucaoHoras;
    private Integer tempoSolucaoDiasUteis;
    private String descricaoCondicoes;

    public SLA(int idSLA, String nomeRegra, Integer tempoRespostaHoras, Integer tempoSolucaoHoras, Integer tempoSolucaoDiasUteis, String descricaoCondicoes) {
        this.idSLA = idSLA;
        this.nomeRegra = nomeRegra;
        this.tempoRespostaHoras = tempoRespostaHoras;
        this.tempoSolucaoHoras = tempoSolucaoHoras;
        this.tempoSolucaoDiasUteis = tempoSolucaoDiasUteis;
        this.descricaoCondicoes = descricaoCondicoes;
    }

    public int getIdSLA() { return idSLA; }
    public void setIdSLA(int idSLA) { this.idSLA = idSLA; }
    public String getNomeRegra() { return nomeRegra; }
    public void setNomeRegra(String nomeRegra) { this.nomeRegra = nomeRegra; }
    public Integer getTempoRespostaHoras() { return tempoRespostaHoras; }
    public void setTempoRespostaHoras(Integer tempoRespostaHoras) { this.tempoRespostaHoras = tempoRespostaHoras; }
    public Integer getTempoSolucaoHoras() { return tempoSolucaoHoras; }
    public void setTempoSolucaoHoras(Integer tempoSolucaoHoras) { this.tempoSolucaoHoras = tempoSolucaoHoras; }
    public Integer getTempoSolucaoDiasUteis() { return tempoSolucaoDiasUteis; }
    public void setTempoSolucaoDiasUteis(Integer tempoSolucaoDiasUteis) { this.tempoSolucaoDiasUteis = tempoSolucaoDiasUteis; }
    public String getDescricaoCondicoes() { return descricaoCondicoes; }
    public void setDescricaoCondicoes(String descricaoCondicoes) { this.descricaoCondicoes = descricaoCondicoes; }

    @Override
    public String toString() {
        return idSLA + ";" + nomeRegra + ";" + tempoRespostaHoras + ";" + tempoSolucaoHoras + ";" + tempoSolucaoDiasUteis + ";" + descricaoCondicoes;
    }

    public String toCsvString(String delimiter) {
        return idSLA + ";" + nomeRegra + ";" +
                tempoRespostaHoras + ";" + tempoSolucaoHoras + ";" + tempoSolucaoDiasUteis;
    }
}
