package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Endereco;
import br.com.suporteCFTV.repository.EnderecoRepository;
import br.com.suporteCFTV.service.ClienteService;

public class ClienteServiceImpl implements ClienteService {
    private final EnderecoRepository enderecoRepository;

    public ClienteServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Endereco atualizarEndereco(int idCliente, int idEndereco, Endereco novosDadosEndereco) {
        Endereco enderecoExistente = enderecoRepository.buscarPorIdEClienteId(idEndereco, idCliente)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado ou não pertence ao cliente."));

        enderecoExistente.setNumero(novosDadosEndereco.getNumero());

        return enderecoRepository.salvar(enderecoExistente);
    }
}
