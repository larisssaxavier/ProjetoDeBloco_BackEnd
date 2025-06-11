package br.com.suporteCFTV.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Function;

public class Chamado {
    private int idChamado;
    private String numeroProtocolo;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataEncerramento;
    private String descricaoProblema;
    private String solucaoAplicada; // Pode ser nulo.
    private EnumStatusChamado status;
    private EnumPrioridadeChamado prioridade;
    private String observacoesInternas; // Pode ser nulo.
    private int idCliente;
    private int idAtendenteRegistro;
    private Integer idTecnicoAtribuido;
    private int idServico;
    private int idEnderecoOcorrencia;
    private int idSLA;

    public Chamado(int idCliente, int idAtendenteRegistro, int idServico, int idEnderecoOcorrencia, int idSLA, String descricaoProblema, EnumPrioridadeChamado prioridade) {
        this.idCliente = idCliente;
        this.idAtendenteRegistro = idAtendenteRegistro;
        this.idServico = idServico;
        this.idEnderecoOcorrencia = idEnderecoOcorrencia;
        this.idSLA = idSLA;
        this.descricaoProblema = descricaoProblema;
        this.prioridade = prioridade;
    }

    public Chamado() {
    }

    public int getIdChamado() {
        return idChamado;
    }
    public void setIdChamado(int idChamado) {
        this.idChamado = idChamado;
    }
    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }
    public void setNumeroProtocolo(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }
    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }
    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }
    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
    public String getDescricaoProblema() {
        return descricaoProblema;
    }
    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }
    public String getSolucaoAplicada() {
        return solucaoAplicada;
    }
    public void setSolucaoAplicada(String solucaoAplicada) {
        this.solucaoAplicada = solucaoAplicada;
    }
    public EnumStatusChamado getStatus() {
        return status;
    }
    public void setStatus(EnumStatusChamado status) {
        this.status = status;
    }
    public EnumPrioridadeChamado getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(EnumPrioridadeChamado prioridade) {
        this.prioridade = prioridade;
    }
    public String getObservacoesInternas() {
        return observacoesInternas;
    }
    public void setObservacoesInternas(String observacoesInternas) {
        this.observacoesInternas = observacoesInternas;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public int getIdAtendenteRegistro() {
        return idAtendenteRegistro;
    }
    public void setIdAtendenteRegistro(int idAtendenteRegistro) {
        this.idAtendenteRegistro = idAtendenteRegistro;
    }
    public Integer getIdTecnicoAtribuido() {
        return idTecnicoAtribuido;
    }
    public void setIdTecnicoAtribuido(Integer idTecnicoAtribuido) {
        this.idTecnicoAtribuido = idTecnicoAtribuido;
    }
    public int getIdServico() {
        return idServico;
    }
    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }
    public int getIdEnderecoOcorrencia() {
        return idEnderecoOcorrencia;
    }
    public void setIdEnderecoOcorrencia(int idEnderecoOcorrencia) {
        this.idEnderecoOcorrencia = idEnderecoOcorrencia;
    }
    public int getIdSLA() {
        return idSLA;
    }
    public void setIdSLA(int idSLA) {
        this.idSLA = idSLA;
    }

    public String toCsvString(String delimiter) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        final Function<String, String> escapeCsv = (String field) -> {
            if (field == null)return "";
            if (field.contains(delimiter) || field.contains("\"") || field.contains("\n")) {
                return "\"" + field.replace("\"", "\"\"") + "\"";
            }
            return field;
        };
        return String.join(delimiter,
                String.valueOf(this.idChamado),
                escapeCsv.apply(this.numeroProtocolo),
                this.dataAbertura != null ? this.dataAbertura.format(formatter) : "",
                this.dataEncerramento != null ? this.dataEncerramento.format(formatter) : "",
                escapeCsv.apply(this.descricaoProblema),
                escapeCsv.apply(this.solucaoAplicada),
                this.status != null ? this.status.name() : "",
                this.prioridade != null ? this.prioridade.name() : "",
                String.valueOf(this.idCliente),
                String.valueOf(this.idAtendenteRegistro),
                this.idTecnicoAtribuido != null ? String.valueOf(this.idTecnicoAtribuido) : "",
                String.valueOf(this.idServico),
                String.valueOf(this.idEnderecoOcorrencia),
                String.valueOf(this.idSLA),
                escapeCsv.apply(this.observacoesInternas)
        );
    }
    @Override
    public String toString() {
        return "Chamado{" + "idChamado=" + idChamado + ", numeroProtocolo='" + numeroProtocolo + '\'' + ", status=" + status +
                ", prioridade=" + prioridade + ", idCliente=" + idCliente + ", idTecnicoAtribuido=" + idTecnicoAtribuido + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chamado chamado = (Chamado) o;
        return idChamado == chamado.idChamado && idChamado != 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(idChamado);
    }

    public String geDescricaoproblema() {
        return this.descricaoProblema;
    }

    public Integer getIdTecnicoatribuido() {
        return this.idTecnicoAtribuido;
    }
}

