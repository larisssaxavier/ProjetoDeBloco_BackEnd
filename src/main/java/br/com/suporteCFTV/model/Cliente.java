package br.com.suporteCFTV.model;

public class Cliente {
    private int idCliente;
    private String nomeContato;
    private String nomeEmpresa;
    private String documento;
    private String telefone;
    private String email;
    private String dataCadastro;

    public Cliente(int idCliente, String nomeContato, String nomeEmpresa, String documento, String telefone, String email, String dataCadastro) {
        this.idCliente = idCliente;
        this.nomeContato = nomeContato;
        this.nomeEmpresa = nomeEmpresa;
        this.documento = documento;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public String toCsvString() {
        return String.join(";",
                String.valueOf(this.idCliente),
                this.nomeContato != null ? this.nomeContato : "",
                this.nomeEmpresa != null ? this.nomeEmpresa : "",
                this.documento != null ? this.documento : "",
                this.telefone != null ? this.telefone : "",
                this.email != null ? this.email : "",
                this.dataCadastro != null ? this.dataCadastro : ""
        );
    }


    public int getIdCliente() {return idCliente;}
    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}
    public String getNomeContato() {return nomeContato;}
    public void setNomeContato(String nomeContato) {this.nomeContato = nomeContato;}
    public String getNomeEmpresa() {return nomeEmpresa;}
    public void setNomeEmpresa(String nomeEmpresa) {this.nomeEmpresa = nomeEmpresa;}
    public String getDocumento() {return documento;}
    public void setDocumento(String documento) {this.documento = documento;}
    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getDataCadastro() {return dataCadastro;}

    @Override
    public String toString() {
        return idCliente + ";" + nomeContato + ";" + nomeEmpresa + ";" + documento + ";" + telefone + ";" + email + ";" + dataCadastro;
    }
}
