package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Optional<Cliente> buscarPorId(int id);
    List<Cliente> buscarTodos();
    Cliente salvar(Cliente cliente);
    void salvarTodos(List<Cliente> clientes);
}
