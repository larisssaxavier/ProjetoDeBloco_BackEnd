package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Servico;
import br.com.suporteCFTV.repository.ServicoRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicoCSVRepository implements ServicoRepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private int proximoId = 1;

    private static final String CSV_HEADER = "idServico;nomeServico;descricao;ativo";

    public ServicoCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(Servico::getIdServico)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Optional<Servico> buscarPorId(int idServico) {
        return buscarTodos().stream()
                .filter(s -> s.getIdServico() == idServico)
                .findFirst();
    }

    @Override
    public List<Servico> buscarTodos() {
        List<Servico> servicos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) return servicos;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); // Pular cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);

                Servico servico = new Servico(
                        Integer.parseInt(campos[0]),
                        campos[1], // nomeServico
                        campos[2]  // descricao
                );
                servicos.add(servico);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler arquivo de serviços: " + e.getMessage());
        }
        return servicos;
    }

    @Override
    public Servico salvar(Servico servico) {
        List<Servico> servicos = new ArrayList<>(buscarTodos());
        if (servico.getIdServico() == 0) {
            servico.setIdServico(proximoId++);
            servicos.add(servico);
        } else {
            servicos.removeIf(s -> s.getIdServico() == servico.getIdServico());
            servicos.add(servico);
        }
        salvarTodosNoArquivo(servicos);
        return servico;
    }

    private void salvarTodosNoArquivo(List<Servico> servicos) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write(CSV_HEADER + "\n");
            for (Servico s : servicos) {
                // Supondo que a classe Servico tenha um método toCsvString()
                bw.write(s.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar serviços no CSV: " + e.getMessage());
        }
    }
}
