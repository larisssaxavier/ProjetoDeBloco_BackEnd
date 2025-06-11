package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Tecnico;
import java.util.List;
import java.util.Optional;

public interface TecnicoRepository {
    Optional<Tecnico> buscarPorId(int idTecnico);
    List<Tecnico> buscarTodos();
    Tecnico salvar(Tecnico tecnico);
}
