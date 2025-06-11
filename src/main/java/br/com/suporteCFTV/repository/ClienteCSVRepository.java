package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Cliente;
import br.com.suporteCFTV.model.SLA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteCSVRepository implements ClienteRepository {

    private static String caminhoArquivo = "src/main/resources/data/clientes.csv";
    private int proximoId = 1;

    public ClienteCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.proximoId = carregarProximoId();
    }
    private int carregarProximoId() {
        return buscarTodos().stream()
                .mapToInt(Cliente::getIdCliente)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo de clientes não encontrado, retornando lista vazia.");
            return clientes;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";", -1);

                if (dados.length >= 7) {
                    Cliente cliente = new Cliente(
                            Integer.parseInt(dados[0]),
                            dados[1],
                            dados[2],
                            dados[3],
                            dados[4],
                            dados[5],
                            dados[6]
                    );
                    clientes.add(cliente);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler clientes do CSV: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public void salvarTodos(List<Cliente> clientes) {
        File arquivo = new File(caminhoArquivo);
        File diretorioPai = arquivo.getParentFile();
        if (diretorioPai != null && !diretorioPai.exists()) {
            diretorioPai.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("idCliente;nomeContato;nomeEmpresa;documento;telefone;email;dataCadastro\n");
            for (Cliente c : clientes) {
                bw.write(c.toCsvString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes no CSV: " + e.getMessage());
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        return buscarTodos().stream()
                .filter(c -> c.getIdCliente() == id)
                .findFirst();
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        List<Cliente> clientes = new ArrayList<>(buscarTodos());
        clientes.removeIf(c -> c.getIdCliente() == cliente.getIdCliente());
        clientes.add(cliente);
        salvarTodos(clientes);
        return cliente;
    }
}
