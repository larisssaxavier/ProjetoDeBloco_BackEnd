package br.com.suporteCFTV.model;

public class Gerente {
    private int idGerente;
    private String nomeCompleto;
    private String login;
    private String senhaHash;
    private String emailEmpresa;
    private boolean ativo;

    public Gerente(int idGerente, String nomeCompleto, String login, String senhaHash, String emailEmpresa, boolean ativo) {
        this.idGerente = idGerente;
        this.nomeCompleto = nomeCompleto;
        this.login = login;
        this.senhaHash = senhaHash;
        this.emailEmpresa = emailEmpresa;
        this.ativo = ativo;
    }

    public int getIdGerente() {return idGerente;}
    public void setIdGerente(int idGerente) {this.idGerente = idGerente;}
    public String getNomeCompleto() {return nomeCompleto;}
    public void setNomeCompleto(String nomeCompleto) {this.nomeCompleto = nomeCompleto;}
    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}
    public String getSenhaHash() {return senhaHash;}
    public void setSenhaHash(String senhaHash) {this.senhaHash = senhaHash;}
    public String getEmailEmpresa() {return emailEmpresa;}
    public void setEmailEmpresa(String emailEmpresa) {this.emailEmpresa = emailEmpresa;}
    public boolean isAtivo() {return ativo;}
    public void setAtivo(boolean ativo) {this.ativo = ativo;}

    @Override
    public String toString() {
        return idGerente +  ";" + nomeCompleto + ";" + login + ";" + senhaHash + ";" + emailEmpresa + ";" + ativo;
    }
}
