package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Tecnico;
import br.com.suporteCFTV.repository.TecnicoRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TecnicoCSVRepository implements TecnicoRepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private int proximoId = 1;

    public TecnicoCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(Tecnico::getIdTecnico)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public List<Tecnico> buscarTodos() {
        List<Tecnico> tecnicos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) return tecnicos;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);

                Tecnico tecnico = new Tecnico(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        campos[2],
                        campos[3],
                        campos[4],
                        Boolean.parseBoolean(campos[5])
                );
                tecnicos.add(tecnico);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler arquivo de técnicos: " + e.getMessage());
        }
        return tecnicos;
    }

    @Override
    public Optional<Tecnico> buscarPorId(int idTecnico) {
        return buscarTodos().stream()
                .filter(t -> t.getIdTecnico() == idTecnico)
                .findFirst();
    }

    @Override
    public Tecnico salvar(Tecnico tecnico) {
        List<Tecnico> tecnicos = new ArrayList<>(buscarTodos());
        if (tecnico.getIdTecnico() == 0) {
            tecnico.setIdTecnico(proximoId++);
            tecnicos.add(tecnico);
        } else {
            tecnicos.removeIf(t -> t.getIdTecnico() == tecnico.getIdTecnico());
            tecnicos.add(tecnico);
        }
        salvarTodosNoArquivo(tecnicos);
        return tecnico;
    }

    private void salvarTodosNoArquivo(List<Tecnico> tecnicos) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("idTecnico;nomeCompleto;login;senhaHash;emailEmpresa;ativo\n");
            for (Tecnico t : tecnicos) {
                // Supondo que a classe Tecnico tenha um método toCsvString()
                bw.write(t.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar técnicos no CSV: " + e.getMessage());
        }
    }
}
