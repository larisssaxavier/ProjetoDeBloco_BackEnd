package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Gerente;
import br.com.suporteCFTV.repository.GerenteRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GerenteCSVRepository implements GerenteRepository {
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private int proximoId = 1;

    public GerenteCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    @Override
    public Optional<Gerente> buscarPorId(int idGerente) {
        return buscarTodos().stream()
                .filter(g -> g.getIdGerente() == idGerente)
                .findFirst();
    }

    @Override
    public List<Gerente> buscarTodos() {
        List<Gerente> gerentes = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) return gerentes;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);


                Gerente gerente = new Gerente(
                        Integer.parseInt(campos[0]),
                        campos[1],
                        campos[2],
                        campos[3],
                        campos[4],
                        Boolean.parseBoolean(campos[5])
                );
                gerentes.add(gerente);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler arquivo de gerentes: " + e.getMessage());
        }
        return gerentes;
    }

    @Override
    public Gerente salvar(Gerente gerente) {
        List<Gerente> gerentes = new ArrayList<>(buscarTodos());
        if (gerente.getIdGerente() == 0) {
            gerente.setIdGerente(proximoId++);
            gerentes.add(gerente);
        } else {
            gerentes.removeIf(a -> a.getIdGerente() == gerente.getIdGerente());
            gerentes.add(gerente);
        }
        salvarTodosNoArquivo(gerentes);
        return gerente;
    }

    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(Gerente::getIdGerente)
                .max()
                .orElse(0) + 1;
    }

    private void salvarTodosNoArquivo(List<Gerente> gerentes) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("idGerente;nomeCompleto;login;senhaHash;emailEmpresa;ativo\n");
            for (Gerente a : gerentes) {
                bw.write(a.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar atendentes no CSV: " + e.getMessage());
        }
    }
}
