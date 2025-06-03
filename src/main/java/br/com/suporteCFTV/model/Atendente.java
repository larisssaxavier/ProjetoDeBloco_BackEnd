package model;

public class Atendente {
    private int idAtendente;
    private String nomeCompleto;
    private String login;
    private String senhaHash;
    private String emailEmpresa;
    private boolean ativo;

    public Atendente(int idAtendente, String nomeCompleto, String login, String senhaHash, String emailEmpresa, boolean isAtivo) {
        this.idAtendente = idAtendente;
        this.nomeCompleto = nomeCompleto;
        this.login = login;
        this.senhaHash = senhaHash;
        this.emailEmpresa = emailEmpresa;
        this.ativo = isAtivo;
    }

    public int getIdAtendente() {
        return idAtendente;
    }
    public void setIdAtendente(int idAtendente) {this.idAtendente = idAtendente;}
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
        return idAtendente +  ";" + nomeCompleto + ";" + login + ";" + senhaHash + ";" + emailEmpresa;
    }
}
