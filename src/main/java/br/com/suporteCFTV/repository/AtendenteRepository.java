package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Atendente;
import java.util.List;
import java.util.Optional;

public interface AtendenteRepository {
    Optional<Atendente> buscarPorId(int idAtendente);
    List<Atendente> buscarTodos();
    Atendente salvar(Atendente atendente);
}
