package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Notificacao;
import br.com.suporteCFTV.repository.NotificacaoRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotificacaoCSVRepository implements NotificacaoRepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";

    public NotificacaoCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(Notificacao notificacao) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            File arquivo = new File(caminhoArquivo);
            if (arquivo.length() == 0) {
                bw.write("idNotificacao;idChamadoAssociado;dataHoraEnvio;destinatarioEmail;assunto;statusEnvio;tipoNotificacao\n");
            }
            // Supondo que Notificacao tenha um método toCsvString()
            bw.write(notificacao.toCsvString(DELIMITER) + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar log de notificação: " + e.getMessage());
        }
    }

    @Override
    public List<Notificacao> buscarPorIdChamado(int idChamado) {
        List<Notificacao> todas = new ArrayList<>();
        return todas.stream()
                .filter(n -> n.getIdChamadoAssociado() == idChamado)
                .collect(Collectors.toList());
    }
}
