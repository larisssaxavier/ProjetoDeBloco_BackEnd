package br.com.suporteCFTV.main;

import br.com.suporteCFTV.model.*;
import br.com.suporteCFTV.repository.*;
import br.com.suporteCFTV.service.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String basePath = "src/main/resources/data/";

        ChamadoRepository chamadoRepository = new ChamadoCSVRepository(basePath + "chamados.csv");
        TecnicoRepository tecnicoRepository = new TecnicoCSVRepository(basePath + "tecnicos.csv");
        AtendenteRepository atendenteRepository = new AtendenteCSVRepository(basePath + "atendentes.csv");
        ClienteRepository clienteRepository = new ClienteCSVRepository(basePath + "clientes.csv");
        GerenteRepository gerenteRepository = new GerenteCSVRepository(basePath + "gerentes.csv");
        ServicoRepository servicoRepository = new ServicoCSVRepository(basePath + "servicos.csv");
        NotificacaoRepository notificacaoRepository = new NotificacaoCSVRepository(basePath + "notificacoes.csv");
        EnderecoRepository enderecoRepository = new EnderecoCSVRepository(basePath + "enderecos.csv");
        SLARepository slaRepository = new SlaCSVRepository(basePath + "slas.csv");

        NotificacaoService notificacaoService = new NotificacaoServiceImpl();
        ChamadoService chamadoService = new ChamadoServiceImpl(chamadoRepository, tecnicoRepository, notificacaoService);
        ClienteService clienteService = new ClienteServiceImpl(enderecoRepository);
        RelatorioService relatorioService = new RelatorioServiceImpl(chamadoRepository, tecnicoRepository);

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        criarNovoChamado(scanner, chamadoService);
                        break;
                    case 2:
                        listarChamadosAtivos(chamadoService);
                        break;
                    case 3:
                        atribuirTecnico(scanner, chamadoService);
                        break;
                    case 4:
                        iniciarAtendimento(scanner, chamadoService);
                        break;
                    case 5:
                        encerrarChamado(scanner, chamadoService);
                        break;
                    case 6:
                        gerarRelatorioSLA(scanner, relatorioService);
                        break;
                    case 7:
                        gerarRelatorioEquipe(scanner, relatorioService);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro: Entrada inválida. Por favor, digite um número.");
                opcao = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro na operação: " + e.getMessage());
            }

            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=============================================");
        System.out.println("   Sistema de Gestão de Chamados CFTV");
        System.out.println("=============================================");
        System.out.println("1. Criar Novo Chamado");
        System.out.println("2. Listar Chamados Ativos");
        System.out.println("3. Atribuir Técnico a um Chamado");
        System.out.println("4. Iniciar Atendimento de um Chamado");
        System.out.println("5. Encerrar um Chamado");
        System.out.println("6. Gerar Relatório de SLA");
        System.out.println("7. Gerar Relatório de Desempenho da Equipe");
        System.out.println("0. Sair");
        System.out.println("---------------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarNovoChamado(Scanner scanner, ChamadoService chamadoService) {
        System.out.println("\n--- Criar Novo Chamado ---");
        System.out.print("Digite o ID do Cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Descrição do Problema: ");
        String descricao = scanner.nextLine();

        Chamado novoChamado = new Chamado();
        novoChamado.setIdCliente(idCliente);
        novoChamado.setDescricaoProblema(descricao);

        novoChamado.setPrioridade(EnumPrioridadeChamado.NORMAL);

        Chamado chamadoCriado = chamadoService.criarChamado(novoChamado);
        System.out.println("\nSUCESSO: Chamado criado!");
        System.out.println("Protocolo: " + chamadoCriado.getNumeroProtocolo());
    }

    private static void listarChamadosAtivos(ChamadoService chamadoService) {
        System.out.println("\n--- Chamados Ativos ---");
        List<Chamado> chamados = chamadoService.buscarChamadosAtivos();
        if (chamados.isEmpty()) {
            System.out.println("Nenhum chamado ativo no momento.");
        } else {
            chamados.forEach(System.out::println);
        }
    }

    private static void atribuirTecnico(Scanner scanner, ChamadoService chamadoService) {
        System.out.println("\n--- Atribuir Técnico a Chamado ---");
        System.out.print("Digite o ID do Chamado: ");
        int idChamado = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o ID do Técnico: ");
        int idTecnico = Integer.parseInt(scanner.nextLine());

        chamadoService.atribuirTecnico(idChamado, idTecnico);
        System.out.println("\nSUCESSO: Técnico atribuído ao chamado.");
    }

    private static void iniciarAtendimento(Scanner scanner, ChamadoService chamadoService) {
        System.out.println("\n--- Iniciar Atendimento de Chamado ---");
        System.out.print("Digite o ID do Chamado: ");
        int idChamado = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o ID do Técnico que está iniciando: ");
        int idTecnico = Integer.parseInt(scanner.nextLine());


        chamadoService.iniciarAtendimento(idChamado, idTecnico);
        System.out.println("\nSUCESSO: Atendimento do chamado iniciado.");
    }

    private static void encerrarChamado(Scanner scanner, ChamadoService chamadoService) {
        System.out.println("\n--- Encerrar Chamado ---");
        System.out.print("Digite o ID do Chamado: ");
        int idChamado = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o ID do Técnico que está encerrando: ");
        int idTecnico = Integer.parseInt(scanner.nextLine());
        System.out.print("Descrição da Solução Aplicada: ");
        String solucao = scanner.nextLine();

        chamadoService.encerrarChamado(idChamado, idTecnico, solucao);
        System.out.println("\nSUCESSO: Chamado encerrado.");
    }

    private static void gerarRelatorioSLA(Scanner scanner, RelatorioService relatorioService) {
        System.out.println("\n--- Gerar Relatório de SLA ---");
        LocalDate inicio = LocalDate.now().minusMonths(1);
        LocalDate fim = LocalDate.now();

        String relatorio = relatorioService.gerarRelatorioSLA(inicio, fim);
        System.out.println(relatorio);
    }

    private static void gerarRelatorioEquipe(Scanner scanner, RelatorioService relatorioService) {
        System.out.println("\n--- Gerar Relatório de Desempenho da Equipe ---");
        LocalDate inicio = LocalDate.now().minusMonths(1);
        LocalDate fim = LocalDate.now();

        String relatorio = relatorioService.gerarRelatorioDesempenhoTecnicos(inicio, fim);
        System.out.println(relatorio);
    }
}
