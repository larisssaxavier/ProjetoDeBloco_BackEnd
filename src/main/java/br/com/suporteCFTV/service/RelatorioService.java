package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Chamado;

import java.time.LocalDate;

public interface RelatorioService {
    String gerarRelatorioSLA(LocalDate inicio, LocalDate fim);
    String gerarRelatorioDesempenhoTecnicos(LocalDate inicio, LocalDate fim);
    boolean isDentroDoPrazo(Chamado chamado);
}
