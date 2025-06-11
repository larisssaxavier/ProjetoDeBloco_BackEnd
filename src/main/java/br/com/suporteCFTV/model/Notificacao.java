package br.com.suporteCFTV.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificacao {
    private int idNotificacao;
    private int idChamadoAssociado;
    private LocalDateTime dataHoraEnvio;
    private String destinatarioEmail;
    private String assunto;
    private EnumStatusNotificacao statusEnvio;
    private EnumTipoNotificacao tipoNotificacao;

    public Notificacao(int idChamadoAssociado, String destinatarioEmail, String assunto, EnumTipoNotificacao tipo) {
        this.idChamadoAssociado = idChamadoAssociado;
        this.destinatarioEmail = destinatarioEmail;
        this.assunto = assunto;
        this.tipoNotificacao = tipo;
        this.dataHoraEnvio = LocalDateTime.now(); // Data de envio é agora
        this.statusEnvio = EnumStatusNotificacao.ENVIADA_COM_SUCESSO; // Assume sucesso por padrão
    }

    public int getIdNotificacao() { return idNotificacao; }
    public void setIdNotificacao(int idNotificacao) { this.idNotificacao = idNotificacao; }
    public int getIdChamadoAssociado() { return idChamadoAssociado; }
    public void setIdChamadoAssociado(int idChamadoAssociado) { this.idChamadoAssociado = idChamadoAssociado; }
    public LocalDateTime getDataHoraEnvio() { return dataHoraEnvio; }
    public void setDataHoraEnvio(LocalDateTime dataHoraEnvio) { this.dataHoraEnvio = dataHoraEnvio; }
    public String getDestinatarioEmail() { return destinatarioEmail; }
    public void setDestinatarioEmail(String destinatarioEmail) { this.destinatarioEmail = destinatarioEmail; }
    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }
    public EnumStatusNotificacao getStatusEnvio() { return statusEnvio; }
    public void setStatusEnvio(EnumStatusNotificacao statusEnvio) { this.statusEnvio = statusEnvio; }
    public EnumTipoNotificacao getTipoNotificacao() { return tipoNotificacao; }
    public void setTipoNotificacao(EnumTipoNotificacao tipoNotificacao) { this.tipoNotificacao = tipoNotificacao; }

    public String toCsvString(String delimiter) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return String.join(delimiter,
                String.valueOf(this.idNotificacao),
                String.valueOf(this.idChamadoAssociado),
                this.dataHoraEnvio.format(formatter),
                this.destinatarioEmail,
                this.assunto,
                this.statusEnvio.name(),
                this.tipoNotificacao.name()
        );
    }
}
