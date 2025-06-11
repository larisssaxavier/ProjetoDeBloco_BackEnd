package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Chamado;
import br.com.suporteCFTV.service.NotificacaoService;
public class NotificacaoServiceImpl implements NotificacaoService {

    @Override
    public void enviarNotificacaoNovoChamado(Chamado chamado) {
        System.out.println("--- NOTIFICAÇÃO ---");
        System.out.println("Para: Cliente (ID " + chamado.getIdCliente() + ")");
        System.out.println("Assunto: Chamado Aberto - Protocolo " + chamado.getNumeroProtocolo());
        System.out.println("Seu chamado foi registrado com sucesso!");
        System.out.println("--------------------");
    }

    @Override
    public void enviarNotificacaoTecnicoAtribuido(Chamado chamado) {
        System.out.println("--- NOTIFICAÇÃO ---");
        System.out.println("Para: Técnico (ID " + chamado.getIdTecnicoAtribuido() + ")");
        System.out.println("Assunto: Novo Chamado Atribuído - " + chamado.getNumeroProtocolo());
        System.out.println("Você foi atribuído ao chamado " + chamado.getIdChamado() + ".");
        System.out.println("--------------------");
    }

    @Override
    public void enviarNotificacaoStatusAtualizado(Chamado chamado) {

    }

    @Override
    public void enviarNotificacaoChamadoConcluido(Chamado chamado) {

    }

    @Override
    public void enviarAlertaSLAPrestesAVencer(Chamado chamado) {

    }

    @Override
    public void enviarNotificacaoChamado(Chamado chamadoSalvo) {

    }
}