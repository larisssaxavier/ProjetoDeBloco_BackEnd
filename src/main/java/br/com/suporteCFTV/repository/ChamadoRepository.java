package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Chamado;
import java.util.List;
import java.util.Optional;
import br.com.suporteCFTV.model.EnumStatusChamado;

public interface ChamadoRepository {
    Chamado salvar(Chamado chamado);
    Optional<Chamado> buscarPorID(int id);
    List<Chamado> buscarTodos();
    public List<Chamado> buscarPorStatus(EnumStatusChamado status);
    void deletarPorId(int id);
}
