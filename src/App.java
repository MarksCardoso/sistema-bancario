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

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        boolean contaExists = false;
        String nome = null;
        String msg = null;

        double saldoInicial = 0.0d;
        double saldoAtual = 0.0d;
        double totalDeposito = 0.0d;
        int qtyDeposito = 0;

        int option = 0;

        while (option != 8) {
            showMenu(contaExists, msg);
            option = input.nextInt();
            input.nextLine();
            msg = null;

            if (contaExists) {
                switch (option) {
                    case 2:
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
                        
                        msg = String.format("Saldo atual: R$ %.2f", saldoAtual);

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

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
                        System.out.println("========== ABRIR CONTA ==========\n");
                        System.out.print("Digite o nome do titular da nova conta: ");
                        nome = input.nextLine();
                        System.out.print("Saldo inicial da nova conta: ");
                        saldoInicial = input.nextFloat();
                        saldoAtual = saldoInicial;

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
