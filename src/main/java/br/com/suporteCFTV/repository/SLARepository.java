package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.SLA;
import java.util.List;
import java.util.Optional;

public interface SLARepository {
    Optional<SLA> buscarPorId(int idSLA);
    List<SLA> buscarTodos();
    SLA salvar(SLA sla);
}
