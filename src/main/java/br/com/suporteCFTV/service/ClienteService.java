package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Endereco;

public interface ClienteService{
    Endereco atualizarEndereco(int idCliente, int idEndereco, Endereco novosDadosEndereco);
}
