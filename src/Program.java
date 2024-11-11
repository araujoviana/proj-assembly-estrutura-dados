/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * REFERÊNCIAS:
 * TODO
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
