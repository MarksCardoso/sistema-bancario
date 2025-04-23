
import java.util.Scanner;

public class App {

    public static void showMenu(boolean acc, String msg) {
        System.out.print("\033\143");
        System.out.println("========== MENU ==========");
        if (msg != null) {
            System.out.println("\n" + msg);
        }
        if (!acc) {
            System.out.println("\n1. Abrir conta");
        }
        if (acc) {
            System.out.println("\n2. Depositar");
            System.out.println("3. Sacar");
            System.out.println("4. Aplicar juros");
            System.out.println("5. Simular empréstimo");
            System.out.println("6. Extrato");
            System.out.println("7. Integrantes");
        }
        System.out.println("8. Sair");
    }

    public static void simularEmprestimo(Scanner input) {
        double capital = 0.0d;
        double taxa = 0.0d;
        int parcelas = 0;
        double totalJuros = 0.0d;
        double totalParcelas = 0.0d;

        while (taxa <= 0.0d || capital <= 0.0d || parcelas <= 0) {
            System.out.print("\033\143");
            System.out.println("========== SIMULAR EMPRÉSTIMO ==========\n");
            if (capital <= 0.0d) {
                System.out.print("Digite o valor do empréstimo: ");
                capital = input.nextDouble();
            }

            if (taxa <= 0.0d) {
                System.out.print("Digite a taxa de juros mensal: ");
                taxa = input.nextDouble();
            }

            if (parcelas <= 0) {
                System.out.print("Digite a quantidade de parcelas: ");
                parcelas = input.nextInt();
            }
        }

        System.out.print("\033\143");
        System.out.println("========== SIMULAR EMPRÉSTIMO ==========\n");
        double juros = 0.0d;
        double montante = 0.0d;

        for (int i = 0; i < parcelas; i++) {
            juros = capital * taxa * (i + 1);
            montante = capital + juros;
            System.out.printf("Parcela %d: R$ %.2f\n", (i + 1), montante);
            totalJuros += juros;
            totalParcelas += montante;
        }

        System.out.printf("Total de juros: R$ %.2f\n", totalJuros);
        System.out.printf("Total de parcelas: R$ %.2f\n", totalParcelas);
        System.out.print("\nPressione ENTER para voltar ao menu...");
        input.nextLine();
        input.nextLine();
    }

    public static double[] sacar(Scanner input, double saldoAtual, int qtySaques, boolean valorValidacao,
            double totalSaques, double saldoMin) {

        int valorSaque;
        int[] notas = { 100, 50, 20, 10, 5, 2 };

        while (!valorValidacao) {
            System.out.print("\033\143");
            System.out.println("========== REALIZAR SAQUE ==========\n");
            System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);
            System.out.print("\nDigite o valor a ser sacado: ");

            if (input.hasNextInt()) {
                valorSaque = input.nextInt();
                input.nextLine();

                if (valorSaque <= 0) {
                    System.out.println("Digite um valor maior que zero!");

                } else if (valorSaque > saldoAtual) {
                    System.out.println("Saldo insuficiente!");

                } else {
                    int testeNotas = valorSaque;

                    for (int i = 0; i < notas.length; i++) {
                        testeNotas = testeNotas % notas[i];
                    }

                    if (testeNotas != 0) {
                        System.out.print("\033\143");
                        System.out.println("========== REALIZAR SAQUE ==========\n");
                        System.out.println("Valor inválido para as notas disponíveis.");
                        System.out.print("\nPressione ENTER para tentar novamente...");

                        input.nextLine();

                    } else {
                        saldoAtual -= valorSaque;
                        qtySaques++;
                        totalSaques += valorSaque;

                        if (saldoAtual < saldoMin) {
                            saldoMin = saldoAtual;
                        }

                        int[] qtdNotas = new int[notas.length];
                        int restante = valorSaque;

                        for (int i = 0; i < notas.length; i++) {
                            qtdNotas[i] = restante / notas[i];
                            restante %= notas[i];
                        }

                        System.out.print("\033\143");
                        System.out.println("========== REALIZAR SAQUE ==========\n");
                        System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);
                        System.out.println("\nNotas entregues:\n");
                        for (int i = 0; i < notas.length; i++) {
                            if (qtdNotas[i] > 0) {
                                System.out.printf("R$ %d: %d nota(s)\n", notas[i], qtdNotas[i]);
                            }
                        }

                        System.out.printf("\nSaque de R$ %d,00 realizado com sucesso!\n", valorSaque);
                        valorValidacao = true;
                    }
                }
            } else {
                System.out.println("Erro: digite um número válido.");
                input.nextLine(); // limpa buffer
            }
        }

        System.out.print("\nPressione ENTER para voltar ao menu...");
        input.nextLine();

        return new double[] { saldoAtual, totalSaques, qtySaques, saldoMin };

    }

    public static double[] depositar(Scanner input, double saldoAtual, double totalDeposito, int qtyDeposito,
            double saldoMax) {
        System.out.print("\033\143");
        System.out.println("========== DEPÓSITO ==========\n");
        System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);
        double valorDeposito = 0.0d;

        while (valorDeposito <= 0) {
            System.out.print("\nDigite um valor a ser depositado: ");
            valorDeposito = input.nextDouble();
        }

        saldoAtual += valorDeposito;
        totalDeposito += valorDeposito;
        qtyDeposito++;

        if (saldoAtual > saldoMax) {
            saldoMax = saldoAtual;
        }

        System.out.print("\033\143");
        System.out.println("========== DEPÓSITO ==========\n");
        System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);
        System.out.print("\nPressione ENTER para voltar ao menu...");
        input.nextLine();
        input.nextLine();

        return new double[] { saldoAtual, totalDeposito, qtyDeposito, saldoMax };
    }

    public static double[] aplicarJuros(Scanner input, double saldoAtual, double totalJuros, double saldoMax) {
        System.out.print("\033\143");
        System.out.println("========== APLICAR JUROS ==========\n");
        System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);
        System.out.print("\nDigite a taxa de juros (%): ");
        double taxaJuros = input.nextDouble();

        while (taxaJuros <= 0) {
            System.out.print("Taxa está invalida. Digite uma taxa com valor positivo: ");
            taxaJuros = input.nextDouble();
        }

        double valorJuros = saldoAtual * (taxaJuros / 100);
        saldoAtual += valorJuros;
        totalJuros += valorJuros;

        if (saldoAtual > saldoMax) {
            saldoMax = saldoAtual;
        }

        System.out.print("\033\143");
        System.out.println("========== APLICAR JUROS ==========\n");
        System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);
        System.out.printf("Total de juros: R$ %.2f\n", totalJuros);
        System.out.print("\nPressione ENTER para voltar ao menu...");
        input.nextLine();
        input.nextLine();

        return new double[] { saldoAtual, totalJuros, saldoMax };
    }

    public static void extrato(String nome, double saldoInicial, Scanner input, double saldoAtual, int qtyDepositos,
            double totalDeposito, int qtySaques, double totalSaques, double totalJuros, double saldoMax,
            double saldoMin) {
        System.out.print("\033\143"); // Limpar tela
        System.out.println("========== EXTRATO ==========\n");

        System.out.format("Titular: %s\n", nome);
        System.out.format("Saldo inicial: R$ %.2f\n", saldoInicial);
        System.out.format("Saldo atual: R$ %.2f\n", saldoAtual);
        System.out.format("Quantidade total de depositos: %d\n", qtyDepositos);
        System.out.format("Valor total de depositos: R$ %.2f\n", totalDeposito);
        System.out.format("Quantidade total de saques: %d\n", qtySaques);
        System.out.format("Valor total de saques: R$ %.2f\n", totalSaques);
        System.out.format("Valor total de juros recebidos: R$ %.2f\n", totalJuros);
        System.out.format("Saldo máximo da conta: R$ %.2f\n", saldoMax);
        System.out.format("Saldo mínimo da conta: R$ %.2f\n", saldoMin);

        System.out.printf("\nPressione ENTER para voltar ao menu...");
        input.nextLine();
    }

    public static void showIntegrantes(Scanner input) {
        System.out.print("\033\143");
        System.out.println("========== INTEGRANTES ==========\n");
        System.out.println("Marks Cardoso, Henrique Joaquim e Nícolas Lisbôa.\n");
        System.out.print("Pressione ENTER para voltar ao menu...");
        input.nextLine();
    }

    public static Object[] abrirConta(Scanner input) {
        System.out.print("\033\143");
        System.out.println("========== ABRIR CONTA ==========\n");
        System.out.print("Digite o nome do titular da nova conta: ");
        String nome = input.nextLine();
        System.out.print("Saldo inicial da nova conta: ");
        double saldoInicial = input.nextDouble();
        double saldoAtual = saldoInicial;
        double saldoMin = saldoInicial;
        double saldoMax = saldoInicial;
        input.nextLine();

        boolean contaExists = false;

        if (saldoInicial != 0.0d && saldoInicial > 0 && saldoAtual == saldoInicial) {
            contaExists = true;
            System.out.print("\033\143");
            System.out.println("========== ABRIR CONTA ==========\n");
            System.out.println("Conta cadastrada com sucesso!\n");
            System.out.printf("Saldo inicial de R$ %.2f\n", saldoInicial);
        }

        System.out.println("\nPressione ENTER para voltar ao menu...");
        input.nextLine();

        return new Object[] { nome, saldoInicial, saldoAtual, saldoMin, saldoMax, contaExists };
    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        boolean contaExists = false;
        String nome = null;
        String msg = null;

        double saldoInicial = 0.0d;
        double saldoAtual = 0.0d;
        double totalDeposito = 0.0d;
        double totalSaques = 0.0d;
        double totalJuros = 0.0d;
        double saldoMin = 0.0d;
        double saldoMax = 0.0d;
        int qtyDeposito = 0;
        int qtySaques = 0;
        boolean valorValidacao = false;

        int option = 0;

        while (option != 8) {
            showMenu(contaExists, msg);
            option = input.nextInt();
            input.nextLine();
            msg = null;

            if (contaExists) {
                switch (option) {
                    case 2:
                        double[] resultadoDeposito = depositar(input, saldoAtual, totalDeposito, qtyDeposito,
                                saldoMax);
                        saldoAtual = resultadoDeposito[0];
                        totalDeposito = resultadoDeposito[1];
                        qtyDeposito = (int) resultadoDeposito[2];
                        saldoMax = resultadoDeposito[3];

                        break;

                    case 3:
                        double[] resultadoSaque = sacar(input, saldoAtual, qtySaques, valorValidacao, totalSaques,
                                saldoMin);
                        saldoAtual = resultadoSaque[0];
                        totalSaques = resultadoSaque[1];
                        qtySaques = (int) resultadoSaque[2];
                        saldoMin = resultadoSaque[3];

                        break;

                    case 4:
                        double[] resultadoAplicarJuros = aplicarJuros(input, saldoAtual, totalJuros, saldoMax);

                        saldoAtual = resultadoAplicarJuros[0];
                        totalJuros = resultadoAplicarJuros[1];
                        saldoMax = resultadoAplicarJuros[2];

                        break;

                    case 5:
                        simularEmprestimo(input);

                        break;
                    case 6:
                        extrato(nome, saldoInicial, input, saldoAtual, qtyDeposito, totalDeposito, qtySaques,
                                totalSaques, totalJuros, saldoMax, saldoMin);
                        break;

                    case 7:
                        showIntegrantes(input);

                        break;

                    case 8:
                        msg = "Fechando programa";

                        break;

                    default:
                        msg = "ERRO: Opção indisponível";

                        break;
                }
            } else {
                switch (option) {
                    case 1:
                        Object[] conta = abrirConta(input);
                        nome = (String) conta[0];
                        saldoInicial = (double) conta[1];
                        saldoAtual = (double) conta[2];
                        saldoMin = (double) conta[3];
                        saldoMax = (double) conta[4];
                        contaExists = (boolean) conta[5];

                        break;

                    case 8:
                        msg = "Fechando programa";

                        break;

                    default:
                        msg = "ERRO: Opção indisponível";

                        break;
                }
            }
        }

        showMenu(contaExists, msg);

        input.close();
    }
}
