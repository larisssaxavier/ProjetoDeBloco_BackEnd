package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Endereco;
import br.com.suporteCFTV.model.SLA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnderecoCSVRepository implements EnderecoRepository{
    private final String caminhoArquivo;
    private final String DELIMITER = ";";
    private int proximoId = 1;

    public EnderecoCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }

    private static final String CSV_HEADER = "idEndereco;idCliente;numero;bairro;cidade;estado;cep";

    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(Endereco::getIdEndereco)
                .max()
                .orElse(0) + 1;
    }

    private void salvarTodosNoArquivo(List<Endereco> enderecos) {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write(CSV_HEADER + "\n");
            for (Endereco e : enderecos) {
                bw.write(e.toCsvString(DELIMITER) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar endereços no CSV: " + e.getMessage());
        }
    }

    private List<Endereco> buscarTodos() {
        List<Endereco> enderecos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return enderecos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(DELIMITER, -1);

                if (campos.length >= 7) {
                    Endereco endereco = new Endereco();
                    endereco.setIdEndereco(Integer.parseInt(campos[0]));
                    endereco.setIdCliente(Integer.parseInt(campos[1]));
                    endereco.setNumero(campos[2]);
                    endereco.setBairro(campos[3]);
                    endereco.setCidade(campos[4]);
                    endereco.setEstado(campos[5]);
                    endereco.setCep(campos[6]);
                    enderecos.add(endereco);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler arquivo de endereços: " + e.getMessage());
        }
        return enderecos;
    }

    @Override
    public Optional<Endereco> buscarPorId(int idEndereco) {
        return buscarTodos().stream()
                .filter(e -> e.getIdEndereco() == idEndereco)
                .findFirst();
    }

    @Override
    public List<Endereco> buscarPorIdCliente(int idCliente) {
        return buscarTodos().stream()
                .filter(e -> e.getIdCliente() == idCliente)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Endereco> buscarPorIdEClienteId(int idEndereco, int idCliente) {
        return buscarTodos().stream()
                .filter(e -> e.getIdEndereco() == idEndereco && e.getIdCliente() == idCliente)
                .findFirst();
    }

    @Override
    public Endereco salvar(Endereco endereco) {
        List<Endereco> enderecos = new ArrayList<>(buscarTodos());
        if (endereco.getIdEndereco() == 0) {
            endereco.setIdEndereco(proximoId++);
            enderecos.add(endereco);
        } else {
            enderecos.removeIf(e -> e.getIdEndereco() == endereco.getIdEndereco());
            enderecos.add(endereco);
        }
        salvarTodosNoArquivo(enderecos);
        return endereco;
    }

    @Override
    public void deletarPorId(int idEndereco) {
        List<Endereco> enderecos = new ArrayList<>(buscarTodos());
        boolean removido = enderecos.removeIf(e -> e.getIdEndereco() == idEndereco);
        if (removido) {
            salvarTodosNoArquivo(enderecos);
        }
    }
}
