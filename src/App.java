
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

    public static double[] depositar(Scanner input, double saldoAtual, double totalDeposito, int qtyDeposito) {
        System.out.print("\033\143");
        System.out.println("========== DEPÓSITO ==========\n");
        System.out.printf("Saldo atual: R$ %.2f", saldoAtual);
        double valorDeposito = 0.0d;

        while (valorDeposito <= 0) {
            System.out.print("\nDigite um valor a ser depositado: ");
            valorDeposito = input.nextDouble();
        }

        saldoAtual += valorDeposito;
        totalDeposito += valorDeposito;
        qtyDeposito++;

        return new double[]{saldoAtual, totalDeposito, qtyDeposito};

    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        boolean contaExists = false;
        String nome = null;
        String msg = null;

        double saldoInicial = 0.0d;
        double saldoAtual = 0.0d;
        double totalDeposito = 0.0d;
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

                        double[] resultadoDeposito = depositar(input, saldoAtual, totalDeposito, qtyDeposito);
                        saldoAtual = resultadoDeposito[0];
                        totalDeposito = resultadoDeposito[1];
                        qtyDeposito = (int) resultadoDeposito[2];
                        msg = String.format("Saldo atual: R$ %.2f", saldoAtual);

                        break;

                    case 3:
                        System.out.print("\033\143");
                        System.out.println("========== REALIZAR SAQUE ==========\n");
                        System.out.printf("Saldo atual: R$ %.2f\n", saldoAtual);

                        int valorSaque = 0;
                        int[] notas = {100, 50, 20, 10, 5, 2};

                        while (!valorValidacao) {
                            System.out.println("\nDigite o valor a ser sacado: ");

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
                                        System.out.println("Valor inválido para as notas disponíveis.");

                                    } else {
                                        valorValidacao = true;
                                        saldoAtual -= valorSaque;
                                        qtySaques++;

                                        int[] qtdNotas = new int[notas.length];
                                        int restante = valorSaque;

                                        for (int i = 0; i < notas.length; i++) {
                                            qtdNotas[i] = restante / notas[i];
                                            restante %= notas[i];
                                        }

                                        System.out.println("Notas entregues:");
                                        for (int i = 0; i < notas.length; i++) {
                                            if (qtdNotas[i] > 0) {
                                                System.out.printf("R$ %d: %d nota(s)\n", notas[i], qtdNotas[i]);
                                            }
                                        }

                                        System.out.printf("Saque de R$%d realizado com sucesso!\n", valorSaque);
                                        System.out.printf("Saldo atual: R$%.2f\n", saldoAtual);
                                    }
                                }
                            } else {
                                System.out.println("Erro: digite um número válido.");
                                input.nextLine(); // limpa buffer
                            }
                        }

                        valorValidacao = false;
                        System.out.println("Pressione ENTER para voltar ao menu...");
                        input.nextLine();
                        break;

                    case 4:

                        break;
                    case 5:
                        double capital = 0.0d;
                        double taxa = 0.0d;
                        int parcelas = 0;
                        double totalJuros = 0.0d;
                        double totalParcelas = 0.0d;
                        msg = "";

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

                        double juros = 0.0d;
                        double montante = 0.0d;

                        for (int i = 0; i < parcelas; i++) {
                            juros = capital * taxa * (i + 1);
                            montante = capital + juros;
                            msg += String.format("Parcela %d: R$ %.2f\n", (i + 1), montante);
                            totalJuros += juros;
                            totalParcelas += montante;
                        }

                        msg += String.format("Total de juros: R$ %.2f\n", totalJuros);
                        msg += String.format("Total de parcelas: R$ %.2f", totalParcelas);

                        break;
                    case 6:

                        break;
                    case 7:

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
                        System.out.print("\033\143");
                        System.out.print("Digite o nome do titular da nova conta: ");
                        nome = input.nextLine();
                        System.out.print("Saldo inicial da nova conta: ");
                        saldoInicial = input.nextFloat();
                        saldoAtual = saldoInicial;
                        input.nextLine();

                        System.out.println("Conta cadastrada com sucesso!");
                        System.out.format("Saldo inicial de R$%.2f", saldoInicial);
                        input.nextLine();

                        System.out.print("\033\143");
                        System.out.println("Pressione ENTER para voltar ao menu...");
                        input.nextLine();

                        if (saldoInicial != 0.0d && saldoInicial > 0 && saldoAtual == saldoInicial) {
                            contaExists = true;
                        }

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
