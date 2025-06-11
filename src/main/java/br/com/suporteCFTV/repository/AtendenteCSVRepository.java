package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Atendente;
import br.com.suporteCFTV.repository.AtendenteRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtendenteCSVRepository implements AtendenteRepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private int proximoId = 1;

    public AtendenteCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    @Override
    public Optional<Atendente> buscarPorId(int idAtendente) {
        return buscarTodos().stream()
                .filter(a -> a.getIdAtendente() == idAtendente)
                .findFirst();
    }

    @Override
    public List<Atendente> buscarTodos() {
        List<Atendente> atendentes = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) return atendentes;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);


                Atendente atendente = new Atendente(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        campos[2],
                        campos[3],
                        campos[4],
                        Boolean.parseBoolean(campos[5])
                );
                atendentes.add(atendente);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler arquivo de atendentes: " + e.getMessage());
        }
        return atendentes;
    }

    @Override
    public Atendente salvar(Atendente atendente) {
        List<Atendente> atendentes = new ArrayList<>(buscarTodos());
        if (atendente.getIdAtendente() == 0) {
            atendente.setIdAtendente(proximoId++);
            atendentes.add(atendente);
        } else {
            atendentes.removeIf(a -> a.getIdAtendente() == atendente.getIdAtendente());
            atendentes.add(atendente);
        }
        salvarTodosNoArquivo(atendentes);
        return atendente;
    }

    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(Atendente::getIdAtendente)
                .max()
                .orElse(0) + 1;
    }

    private void salvarTodosNoArquivo(List<Atendente> atendentes) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("idAtendente;nomeCompleto;login;senhaHash;emailEmpresa;ativo\n");
            for (Atendente a : atendentes) {
                bw.write(a.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar atendentes no CSV: " + e.getMessage());
        }
    }
}
