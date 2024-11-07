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

		// TODO adicionar condição de saída melhor
		// O REPL pede a entrada do usuário até ele inserir o comando de saída
		while (true) {
			System.out.print("> ");

			// Entrada é formatada antes de ser avaliada
			input = repl.formatInput(scanner.nextLine());

			/*
			 * Realiza uma validação preliminar da entrada, verificando erros simples antes
			 * de processá-la mais profundamente.
			 * Se a validação for bem-sucedida, a entrada é avaliada
			 */
			if (repl.validateInput(input)) {
				repl.evaluateInput(input);
			} else {
				break;
			}
		}

		scanner.close();
	}

}
