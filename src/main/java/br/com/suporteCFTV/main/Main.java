package br.com.suporteCFTV.main;

import br.com.suporteCFTV.model.Cliente;
import br.com.suporteCFTV.repository.ClienteRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClienteRepository repo = new ClienteRepository();
        List<Cliente> clientes = repo.lerClientes();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Gestão de Clientes ---");
            System.out.println("1 - Listar Clientes");
            System.out.println("2 - Adicionar Cliente");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    clientes.forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Nome do contato: ");
                    String nome = sc.nextLine();
                    System.out.print("Empresa (opcional): ");
                    String empresa = sc.nextLine();
                    System.out.print("Documento: ");
                    String doc = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Data cadastro (DD-MM-AAAA): ");
                    String data = sc.nextLine();

                    int novoId = clientes.size() + 1;
                    Cliente novo = new Cliente(novoId, nome, empresa, doc, tel, email, data);
                    clientes.add(novo);
                    repo.salvarClientes(clientes);
                    System.out.println("Cliente cadastrado com sucesso!");
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
