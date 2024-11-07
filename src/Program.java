/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

import java.util.Scanner;

public class Program {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		String input = "";

		Repl repl = new Repl();

		while (true) {
			System.out.print("> ");

			// Entrada é formatada antes de ser avaliada
			input = repl.formatInput(scanner.nextLine());

			// Interpreta e avalia o tipo de entrada específica,
			// imprimindo o resultado
			repl.readFormattedInput(input);

		}

		scanner.close();
	}

}
