package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Chamado;
import br.com.suporteCFTV.model.EnumStatusChamado;
import java.util.List;

public interface ChamadoService {
    Chamado criarChamado(Chamado dadosNovoChamado);
    Chamado atribuirTecnico(int idChamado, int idTecnico);
    Chamado alterarStatus(int idChamado, EnumStatusChamado novoStatus);
    List<Chamado> buscarChamadosAtivos();;
    Chamado iniciarAtendimento(int idChamado, int idTecnico);
    Chamado encerrarChamado(int idChamado, int idTecnico, String solucao);
}
