package br.com.suporteCFTV.model;

public class Servico {
    private int idServico;
    private String nomeServico;
    private String descricao;

    public Servico(int idServico, String nomeServico, String descricao) {
        this.idServico = idServico;
        this.nomeServico = nomeServico;
        this.descricao = descricao;
    }

    public int getIdServico() { return idServico; }
    public void setIdServico(int idServico) { this.idServico = idServico; }

    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return idServico + ";" + nomeServico + ";" + descricao;
    }
}
