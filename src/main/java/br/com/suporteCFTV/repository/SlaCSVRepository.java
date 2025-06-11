package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.SLA;
import br.com.suporteCFTV.repository.SLARepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlaCSVRepository implements SLARepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private int proximoId = 1;

    private static final String CSV_HEADER = "idSLA;nomeRegra;tempoRespostaHoras;tempoSolucaoHoras;tempoSolucaoDiasUteis;descricaoCondicoes;ativo";

    public SlaCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(SLA::getIdSLA)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Optional<SLA> buscarPorId(int idSLA) {
        return buscarTodos().stream()
                .filter(s -> s.getIdSLA() == idSLA)
                .findFirst();
    }

    @Override
    public List<SLA> buscarTodos() {
        List<SLA> slas = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) return slas;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);

                SLA sla = new SLA(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        campos[2].isEmpty() ? null : Integer.parseInt(campos[2]),
                        campos[3].isEmpty() ? null : Integer.parseInt(campos[3]),
                        campos[4].isEmpty() ? null : Integer.parseInt(campos[4]),
                        campos[5]
                );
                slas.add(sla);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler arquivo de SLAs: " + e.getMessage());
        }
        return slas;
    }

    @Override
    public SLA salvar(SLA sla) {
        List<SLA> slas = new ArrayList<>(buscarTodos());
        if (sla.getIdSLA() == 0) {
            sla.setIdSLA(proximoId++);
            slas.add(sla);
        } else {
            slas.removeIf(s -> s.getIdSLA() == sla.getIdSLA());
            slas.add(sla);
        }
        salvarTodosNoArquivo(slas);
        return sla;
    }

    private void salvarTodosNoArquivo(List<SLA> slas) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write(CSV_HEADER + "\n");
            for (SLA s : slas) {
                // Supondo que a classe SLA tenha um método toCsvString()
                bw.write(s.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar SLAs no CSV: " + e.getMessage());
        }
    }
}
