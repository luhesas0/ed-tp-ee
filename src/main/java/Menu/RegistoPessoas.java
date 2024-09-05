package Menu;

import Pessoas.Pessoa;
import Pessoas.TipoPessoa;
import Program.Program;

import java.util.Scanner;

public class RegistoPessoas {
    Program program = new Program();

    public RegistoPessoas() {};

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Registo de novo utilizador ---");
        System.out.println("1 - Registar Médico");
        System.out.println("2 - Registar Enfermeiro");
        System.out.println("3 - Registar Auxiliar");
        System.out.println("4 - Registar Administrativo");
        System.out.println("5 - Registar Utente");
        System.out.println("6 - Registar Visitante");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                registarPessoa(TipoPessoa.MEDICO, scanner);
                break;
            case 2:
                registarPessoa(TipoPessoa.ENFERMEIRO, scanner);
                break;
            case 3:
                registarPessoa(TipoPessoa.AUXILIAR, scanner);
                break;
            case 4:
                registarPessoa(TipoPessoa.ADMINISTRATIVO, scanner);
                break;
            case 5:
                registarPessoa(TipoPessoa.UTENTE, scanner);
                break;
            case 6:
                registarPessoa(TipoPessoa.VISITANTE, scanner);
                break;
            case 0:
                System.out.println("A sair...");
                break;
            default:
                System.out.println("Opção inválida");
        }

    }

    private void registarPessoa(TipoPessoa tipoPessoa, Scanner scanner) {
        System.out.println("Insira o ID desejado: ");
        String id = scanner.nextLine();

        System.out.println("Insira o nome desejado: ");
        String nome = scanner.nextLine();

        Pessoa pessoa = new Pessoa(id, nome, tipoPessoa);
        System.out.println("Pessoa registada com sucesso: " + pessoa);
        program.addPessoa(pessoa);
    }

    public static void main(String[] args) {
        RegistoPessoas registoPessoas = new RegistoPessoas();
        registoPessoas.exibirMenu();
    }

}
