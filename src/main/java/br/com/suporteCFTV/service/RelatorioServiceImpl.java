package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Chamado;
import br.com.suporteCFTV.model.EnumStatusChamado;
import br.com.suporteCFTV.repository.ChamadoRepository;
import br.com.suporteCFTV.repository.TecnicoRepository;
import br.com.suporteCFTV.service.RelatorioService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioServiceImpl implements RelatorioService{
    private final ChamadoRepository chamadoRepository;
    private final TecnicoRepository tecnicoRepository;

    public RelatorioServiceImpl(ChamadoRepository chamadoRepository, TecnicoRepository tecnicoRepository) {
        this.chamadoRepository = chamadoRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public boolean isDentroDoPrazo(Chamado chamado) {
        if (chamado.getStatus() != EnumStatusChamado.CONCLUIDO || chamado.getDataEncerramento() == null) {
            return false;
        }
        long prazoEmHoras = 48;

        LocalDateTime prazoFinal = chamado.getDataAbertura().plusHours(prazoEmHoras);

        return !chamado.getDataEncerramento().isAfter(prazoFinal);
    }
    public String gerarRelatorioSLA(LocalDate inicio, LocalDate fim) {
        List<Chamado> chamadosNoPeriodo = chamadoRepository.buscarTodos().stream()
                .filter(c -> !c.getDataAbertura().toLocalDate().isBefore(inicio) && !c.getDataAbertura().toLocalDate().isAfter(fim))
                .collect(Collectors.toList());

        long totalChamados = chamadosNoPeriodo.size();
        long dentroDoPrazo = chamadosNoPeriodo.stream().filter(chamado -> isDentroDoPrazo(chamado)).count();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("--- RELATÓRIO DE SLA ---\n");
        relatorio.append("Período: ").append(inicio).append(" a ").append(fim).append("\n");
        relatorio.append("Total de Chamados: ").append(totalChamados).append("\n");
        relatorio.append("Chamados Dentro do Prazo: ").append(dentroDoPrazo).append("\n");
        relatorio.append("Chamados Fora do Prazo: ").append(totalChamados - dentroDoPrazo).append("\n");

        return relatorio.toString();
    }

    @Override
    public String gerarRelatorioDesempenhoTecnicos(LocalDate inicio, LocalDate fim) {
        List<Chamado> chamadosNoPeriodo = chamadoRepository.buscarTodos().stream()
                .filter(c -> c.getDataAbertura().toLocalDate().isBefore(inicio) && !c.getDataAbertura().toLocalDate().isAfter(fim))
                .collect(Collectors.toList());

        Map<Integer, Long> chamadosPorTecnico = chamadosNoPeriodo.stream()
                .filter(c -> c.getIdTecnicoAtribuido() != null)
                .collect(Collectors.groupingBy(Chamado::getIdTecnicoAtribuido, Collectors.counting()));

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("--- RELATÓRIO DE DESEMPENHO DOS TÉCNICOS ---\n");
        relatorio.append("Período: ").append(inicio).append(" a ").append(fim).append("\n");

        chamadosPorTecnico.forEach((idTecnico, quantidade) -> {
            relatorio.append("Técnico ID ").append(idTecnico).append(": ").append(quantidade).append(" chamados atendidos.\n");
        });

        return relatorio.toString();
    }
}
