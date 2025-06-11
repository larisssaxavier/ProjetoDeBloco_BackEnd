package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Chamado;

public interface NotificacaoService {
    void enviarNotificacaoNovoChamado(Chamado chamado);
    void enviarNotificacaoTecnicoAtribuido(Chamado chamado);
    void enviarNotificacaoStatusAtualizado(Chamado chamado);
    void enviarNotificacaoChamadoConcluido(Chamado chamado);
    void enviarAlertaSLAPrestesAVencer(Chamado chamado);
    void enviarNotificacaoChamado(Chamado chamadoSalvo);
}
