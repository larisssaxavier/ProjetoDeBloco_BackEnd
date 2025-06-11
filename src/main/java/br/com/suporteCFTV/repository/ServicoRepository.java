package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Servico;
import java.util.List;
import java.util.Optional;

public interface ServicoRepository {
    Optional<Servico> buscarPorId(int idServico);
    List<Servico> buscarTodos();
    Servico salvar(Servico servico);
}
