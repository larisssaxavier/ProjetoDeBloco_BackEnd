package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Notificacao;
import java.util.List;

public interface NotificacaoRepository {
    void salvar(Notificacao notificacao);
    List<Notificacao> buscarPorIdChamado(int idChamado);
}
