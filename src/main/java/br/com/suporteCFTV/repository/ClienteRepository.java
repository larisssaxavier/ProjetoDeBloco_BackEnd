package br.com.suporteCFTV.repository;

import br.com.suporteCFTV.model.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private static final String FILE_PATH = "src/main/resources/data/clientes.csv";

    public List<Cliente> lerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Cliente cliente = new Cliente(
                        Integer.parseInt(dados[0]),
                        dados[1],
                        dados[2].isEmpty() ? null : dados[2],
                        dados[3],
                        dados[4],
                        dados[5],
                        dados[6]
                );
                clientes.add(cliente);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void salvarClientes(List<Cliente> clientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("idCliente;nomeContato;nomeEmpresa;documento;telefone;email;dataCadastro\n");
            for (Cliente c : clientes) {
                bw.write(c.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }
}
