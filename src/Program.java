/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * Referências:
 * Uso de null com int: https://stackoverflow.com/questions/13747859/how-to-check-if-an-int-is-a-null
 * Substring: https://www.w3schools.com/java/ref_string_substring.asp
 * Retornar multiplos valores em Java: https://www.geeksforgeeks.org/returning-multiple-values-in-java/
 * BufferedReader: https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
 *
 * Conversas com IA:
 * Finalizando código antes da main: https://chatgpt.com/share/67322c05-e6c0-800b-880c-15446a709ba9
 * BufferedReader, BufferedWriter e ternárias: https://chatgpt.com/share/67322ce9-1fac-800b-950d-846bf2a72ce3
 * Parâmetros opcionais: https://chatgpt.com/share/67322d82-28f4-800b-aa84-2dd5ea394b5a
 */

import java.util.Scanner;

/*
** Realiza a interação direta com o usuário, formatando, validando e
** avaliando a entrada chamando a classe Repl
*/
public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input; // Entrada do usuário
		Repl repl = new Repl();
		boolean hasExited = false; // Verifica se o usuário executou o comando EXIT

		// O REPL pede a entrada do usuário até ele inserir o comando de saída
		while (!hasExited) {
			System.out.print("> ");

			// Formata a entrada antes de avaliar
			input = repl.formatInput(scanner.nextLine());

			/*
			 * Realiza uma validação preliminar da entrada, verificando erros simples antes
			 * de processá-la mais profundamente.
			 * Se a validação for bem-sucedida, a entrada é avaliada e verificada
			 * novamente por outros métodos.
			 */
			if (repl.validateInput(input)) {
				hasExited = repl.evaluateInput(input);
			}
		}

		scanner.close();
	}

}
