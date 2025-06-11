package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Gerente;
import java.util.List;
import java.util.Optional;

public interface GerenteRepository {
    Optional<Gerente> buscarPorId(int idGerente);
    List<Gerente> buscarTodos();
    Gerente salvar(Gerente gerente);
}
