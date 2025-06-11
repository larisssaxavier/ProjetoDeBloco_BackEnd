package br.com.suporteCFTV.model;

public class Endereco {
    private int idEndereco;
    private int idCliente;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(int idEndereco, String numero, String bairro, String cidade, String estado, String cep) {
        this.idEndereco = idEndereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Endereco() {
    }

    public int getIdEndereco() { return idEndereco; }
    public void setIdEndereco(int idEndereco) { this.idEndereco = idEndereco; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }


    public String toString(String delimiter) {
        return String.join(delimiter,
                String.valueOf(this.idEndereco),
                String.valueOf(this.idCliente),
                this.numero,
                this.bairro,
                this.cidade,
                this.estado,
                this.cep);
    }
    @Override
    public String toString() {
        return "Endereco{" +
                "idEndereco=" + idEndereco +
                ", idCliente=" + idCliente +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }

    public String toCsvString(String delimiter) {
        return String.join(delimiter,
                String.valueOf(this.idEndereco),
                String.valueOf(this.idCliente),
                this.numero,
                this.bairro,
                this.cidade,
                this.estado,
                this.cep);
    }
}
