package br.com.suporteCFTV.service;

import br.com.suporteCFTV.model.Chamado;
import br.com.suporteCFTV.model.EnumStatusChamado;
import br.com.suporteCFTV.repository.ChamadoRepository;
import br.com.suporteCFTV.repository.TecnicoRepository;
import br.com.suporteCFTV.service.NotificacaoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChamadoServiceImpl implements ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final NotificacaoService notificacaoService;

    public ChamadoServiceImpl(ChamadoRepository chamadoRepository, TecnicoRepository tecnicoRepository, NotificacaoService notificacaoService) {
        this.chamadoRepository = chamadoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.notificacaoService = notificacaoService;
    }

    @Override
    public Chamado criarChamado(Chamado dadosNovoChamado) {
        if (dadosNovoChamado.getDescricaoProblema() == null || dadosNovoChamado.geDescricaoproblema().trim().isEmpty()){
            throw new IllegalArgumentException("A descrição do problema não pode ser vazia.");
        }

        dadosNovoChamado.setDataAbertura(LocalDateTime.now());
        dadosNovoChamado.setStatus(EnumStatusChamado.ABERTO);

        Chamado chamadoSalvo = chamadoRepository.salvar(dadosNovoChamado);
        notificacaoService.enviarNotificacaoChamado(chamadoSalvo);

        return chamadoSalvo;
    }
    @Override
    public Chamado atribuirTecnico(int idChamado, int idTecnico){
        tecnicoRepository.buscarPorId(idTecnico).orElseThrow(() -> new RuntimeException("Técnico com ID " + idTecnico + " não encontrado."));
        Chamado chamado = chamadoRepository.buscarPorID(idChamado).orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        if (chamado.getStatus() != EnumStatusChamado.ABERTO && chamado.getStatus() != EnumStatusChamado.EM_ANALISE){
            throw  new IllegalStateException("Não é possível atribuir um técnico a um chamado  com status " + chamado.getStatus());
        }

        chamado.setIdTecnicoAtribuido(idTecnico);
        chamado.setStatus(EnumStatusChamado.ATRIBUIDO_AO_TECNICO);

        Chamado chamadoAtualizado = chamadoRepository.salvar(chamado);
        notificacaoService.enviarNotificacaoChamado(chamadoAtualizado);

        return chamadoAtualizado;
    }
    @Override
    public Chamado alterarStatus(int idChamado, EnumStatusChamado novoStatus) {
        Chamado chamado = chamadoRepository.buscarPorID(idChamado).orElseThrow(()-> new RuntimeException("Chamado não encontrado!"));
        chamado.setStatus(novoStatus);

        return chamadoRepository.salvar(chamado);
    }
    @Override
    public Chamado iniciarAtendimento (int idChamado, int idTecnico){
        Chamado chamado = chamadoRepository.buscarPorID(idChamado).orElseThrow(()-> new RuntimeException("Chamado não encontrado!"));

        if(chamado.getIdTecnicoatribuido() == null ||  chamado.getIdTecnicoAtribuido() != idTecnico){
            throw new IllegalStateException("Tecnico não autorizado a iniciar este chamado.");
        }
        if(chamado.getStatus() != EnumStatusChamado.ATRIBUIDO_AO_TECNICO){
            throw new IllegalStateException("Chamado não está no status 'Atribuido ao tecico' para ser iniciado.");
        }

        chamado.setStatus(EnumStatusChamado.EM_ATENDIMENTO);

        Chamado chamadoAtualizado = chamadoRepository.salvar(chamado);
        notificacaoService.enviarNotificacaoChamado(chamadoAtualizado);

        return chamadoAtualizado;
    }
    @Override
    public Chamado encerrarChamado(int idChamado, int idTecnico, String solucao) {
        Chamado chamado = chamadoRepository.buscarPorID(idChamado).orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        if (chamado.getIdTecnicoatribuido() == null  || chamado.getIdTecnicoAtribuido() != idTecnico) {
            throw new SecurityException("Técnico não autorizado a encerrar este chamado.");
        }
        if (chamado.getStatus() != EnumStatusChamado.EM_ATENDIMENTO){
            throw new IllegalStateException("Apenas chamados em atendimento podem ser encerrados.");
        }
        if (solucao == null || solucao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da solução é obrigatória para encerrar o chamado.");
        }

        chamado.setSolucaoAplicada(solucao);
        chamado.setDataEncerramento(LocalDateTime.now());
        chamado.setStatus(EnumStatusChamado.CONCLUIDO);

        Chamado chamadoAtualizado = chamadoRepository.salvar(chamado);
        notificacaoService.enviarNotificacaoChamado(chamadoAtualizado);

        return chamadoAtualizado;
    }
    @Override
    public List<Chamado> buscarChamadosAtivos(){
        return chamadoRepository.buscarTodos().stream()
                .filter(c->c.getStatus() != EnumStatusChamado.CONCLUIDO &&
                        c.getStatus() != EnumStatusChamado.CANCELADO).collect(Collectors.toList());
    }
}
