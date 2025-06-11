package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Endereco;
import java.util.List;
import java.util.Optional;

public interface EnderecoRepository {
    Optional<Endereco> buscarPorId(int idEndereco);
    List<Endereco> buscarPorIdCliente(int idCliente);
    Optional<Endereco> buscarPorIdEClienteId(int idEndereco, int idCliente);
    Endereco salvar(Endereco endereco);
    void deletarPorId(int idEndereco);
}
