package src;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static List<Funcionario> funcionarios = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
    private static DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

    public static void main(String[] args) {
        inicializarFuncionarios();

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> listarFuncionarios();
                case 2 -> adicionarFuncionario();
                case 3 -> removerFuncionario();
                case 4 -> aplicarAumento();
                case 5 -> agruparPorFuncao();
                case 6 -> aniversariantesMes();
                case 7 -> funcionarioMaisVelho();
                case 8 -> ordenarAlfabeticamente();
                case 9 -> totalSalarios();
                case 10 -> salariosEmRelacaoAoMinimo();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menu de Funcionários ---");
        System.out.println("1 - Listar funcionários");
        System.out.println("2 - Adicionar funcionário");
        System.out.println("3 - Remover funcionário");
        System.out.println("4 - Aplicar aumento de 10%");
        System.out.println("5 - Agrupar por função");
        System.out.println("6 - Listar aniversariantes em Outubro e Dezembro");
        System.out.println("7 - Funcionário mais velho");
        System.out.println("8 - Ordenar funcionários alfabeticamente");
        System.out.println("9 - Total dos salários");
        System.out.println("10 - Salários em relação ao mínimo");
        System.out.println("0 - Sair");
    }

    private static void inicializarFuncionarios() {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remover João conforme requisito
        funcionarios.removeIf(f -> f.getNome().equals("João"));
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- Lista de Funcionários ---");
        funcionarios.forEach(f -> System.out.println(f.getNome() + " | " +
                f.getDataNascimento().format(formatter) + " | " +
                decimalFormat.format(f.getSalario()) + " | " +
                f.getFuncao()));
    }

    private static void adicionarFuncionario() {
        System.out.println("\n--- Adicionar Funcionário ---");
        String nome = lerString("Nome: ");
        int dia = lerInt("Dia de nascimento: ");
        int mes = lerInt("Mês de nascimento: ");
        int ano = lerInt("Ano de nascimento: ");
        BigDecimal salario = lerBigDecimal("Salário: ");
        String funcao = lerString("Função: ");

        funcionarios.add(new Funcionario(nome, LocalDate.of(ano, mes, dia), salario, funcao));
        System.out.println("Funcionário adicionado!");
    }

    private static void removerFuncionario() {
        String nome = lerString("Digite o nome do funcionário a remover: ");
        boolean removed = funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
        System.out.println(removed ? "Funcionário removido!" : "Funcionário não encontrado.");
    }

    private static void aplicarAumento() {
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(BigDecimal.valueOf(1.10))));
        System.out.println("Aumento de 10% aplicado a todos os funcionários.");
    }

    private static void agruparPorFuncao() {
        Map<String, List<Funcionario>> agrupados = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
        System.out.println("\n--- Funcionários por Função ---");
        agrupados.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println("   - " + f.getNome()));
        });
    }

    private static void aniversariantesMes() {
        System.out.println("\n--- Aniversariantes em Outubro e Dezembro ---");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento().format(formatter)));
    }

    private static void funcionarioMaisVelho() {
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\n--- Funcionário mais velho ---");
            System.out.println(maisVelho.getNome() + " - " + idade + " anos");
        }
    }

    private static void ordenarAlfabeticamente() {
        System.out.println("\n--- Funcionários em Ordem Alfabética ---");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));
    }

    private static void totalSalarios() {
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n--- Total dos Salários ---");
        System.out.println(decimalFormat.format(total));
    }

    private static void salariosEmRelacaoAoMinimo() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n--- Salários em relação ao salário mínimo ---");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " - " + qtdSalarios + " salários mínimos");
        });
    }

    // Métodos auxiliares para leitura
    private static String lerString(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private static int lerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static BigDecimal lerBigDecimal(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextBigDecimal()) {
            System.out.print("Digite um valor válido: ");
            scanner.next();
        }
        BigDecimal valor = scanner.nextBigDecimal();
        scanner.nextLine();
        return valor;
    }
}
