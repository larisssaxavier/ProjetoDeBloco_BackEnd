package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Chamado;
import br.com.suporteCFTV.model.EnumPrioridadeChamado;
import br.com.suporteCFTV.model.EnumStatusChamado;
import br.com.suporteCFTV.repository.ChamadoRepository;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChamadoCSVRepository implements ChamadoRepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private int proximoId = 1;

    public ChamadoCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    private int carregarProximoId() {
        List<Chamado> chamados = buscarTodos();
        return chamados.stream()
                .mapToInt(Chamado::getIdChamado)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Chamado salvar(Chamado chamado) {
        List<Chamado> chamados = new ArrayList<>(buscarTodos());
        if (chamado.getIdChamado() == 0) {
            chamado.setIdChamado(proximoId++);
            chamados.add(chamado);
        } else {
            chamados.removeIf(c -> c.getIdChamado() == chamado.getIdChamado());
            chamados.add(chamado);
        }
        salvarTodosNoArquivo(chamados);
        return chamado;
    }

    private void salvarTodosNoArquivo(List<Chamado> chamados) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("idChamado;numeroProtocolo;dataAbertura;dataEncerramento;descricaoProblema;solucaoAplicada;status;prioridade;idCliente;idAtendenteRegistro;idTecnicoAtribuido;idServico;idEnderecoOcorrencia;idSLA;observacoesInternas\n");
            for (Chamado c : chamados) {
                bw.write(c.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar chamados no CSV: " + e.getMessage());
        }
    }

    @Override
    public Optional<Chamado> buscarPorID(int id) {
        return buscarTodos().stream()
                .filter(c -> c.getIdChamado() == id)
                .findFirst();
    }
    @Override
    public List<Chamado> buscarTodos() {
        List<Chamado> chamados = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return chamados;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); // Pular cabeçalho
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);
                Chamado chamado = new Chamado();
                chamado.setIdChamado(Integer.parseInt(campos[0]));
                chamado.setNumeroProtocolo(campos[1]);
                if (!campos[2].isEmpty()) chamado.setDataAbertura(LocalDateTime.parse(campos[2], FORMATTER));
                if (!campos[3].isEmpty()) chamado.setDataEncerramento(LocalDateTime.parse(campos[3], FORMATTER));
                chamado.setDescricaoProblema(campos[4]);
                chamado.setSolucaoAplicada(campos[5]);
                if (!campos[6].isEmpty()) chamado.setStatus(EnumStatusChamado.valueOf(campos[6]));
                if (!campos[7].isEmpty()) chamado.setPrioridade(EnumPrioridadeChamado.valueOf(campos[7]));
                chamado.setIdCliente(Integer.parseInt(campos[8]));
                chamado.setIdAtendenteRegistro(Integer.parseInt(campos[9]));
                if (!campos[10].isEmpty()) chamado.setIdTecnicoAtribuido(Integer.parseInt(campos[10]));
                chamado.setIdServico(Integer.parseInt(campos[11]));
                chamado.setIdEnderecoOcorrencia(Integer.parseInt(campos[12]));
                chamado.setIdSLA(Integer.parseInt(campos[13]));
                chamado.setObservacoesInternas(campos[14]);
                chamados.add(chamado);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de chamados: " + e.getMessage());
        }
        return chamados;
    }

    @Override
    public List<Chamado> buscarPorStatus(EnumStatusChamado status) {
        return buscarTodos().stream()
                .filter(c -> c.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(int id) {
        List<Chamado> chamados = new ArrayList<>(buscarTodos());
        boolean removido = chamados.removeIf(c -> c.getIdChamado() == id);
        if (removido) {
            salvarTodosNoArquivo(chamados);
        }
    }
}