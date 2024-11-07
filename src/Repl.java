/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// TODO descrição dessa classe
public class Repl {

    private Commands commands;
    // private Instructions instructions = new Instructions();
    private Buffer buffer;

    public Repl() {
        this.buffer = new Buffer();
        this.commands = new Commands();
    }

    /**
     * Converte a entrada inicial para maíusculo e remove todos os espaços
     * desnecessários
     *
     * @param input entrada não formatada como "10 MoV A 5 "
     * @return entrada formatada tipo "10 mov a 5"
     */
    public String formatInput(String input) {
        return input.toLowerCase().replaceAll("\\s+", " ").trim();
    }

    // TODO mudar isso pra retornar a string de erro
    /**
     * Verifica se a entrada do usuário é válida para ser avaliada pelo REPL,
     * isso não quer dizer que ele não cause erros dentro da funcionalidade interna
     * dos comandos, essa é apenas uma verificação mínima, os erros de cada
     * instrução/comando são tratados em seus métodos.
     *
     * @param input entrada formatada
     * @return true se o comando for válido e false se for inválido
     */
    public boolean validateInput(String input) {

        // Verifica se o comando está vazio
        if (input.isEmpty()) {
            displayError("entrada está vazia.");
            return false;
        }
        String[] splitInput = input.split(" ");

        String potentialCommand = splitInput[0];

        // Verifica se o primeiro termo é um comando válido
        if (!commands.isCommand(potentialCommand)) {
            displayError("O comando fornecido não é válido.");
            return false;
        }

        // Verifica se existe um comando para verificar
        if (splitInput.length < 2) {
            displayError("entrada não contém parâmetros do comando.");
            return false;
        }

        return true;
    }

    public void evaluateInput(String input) {
        // REVIEW precisa ser assim?

        String[] splitInput = input.split(" ", 2);
        String command = splitInput[0];

        if (command.equals("ins") && splitInput.length > 1) {
            try {
                // Exemplo de entrada: "ins 71 out a"
                String[] args = splitInput[1].split(" ", 3);

                int lineNumber = Integer.parseInt(args[0]);
                String instruction = args[1];
                String parameters = args.length > 2 ? args[2] : "";

                // Chama o comando ins no buffer
                String result = commands.insert(buffer, lineNumber, instruction, parameters);

                if (result == null) {
                    System.out.println("Linha inserida:\n" + lineNumber + " " + instruction + " " + parameters);
                } else {
                    System.out.println(result); // Mensagem de erro
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: formato de linha inválido.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Erro: parâmetros insuficientes para o comando INS.");
            }
        }
        else if (command.equals("run") && splitInput.length == 1) {
            // VERIFICAÇÕES AQUI

            // execução
            String result = command.run(buffer);
            
                if (result == null) {
                    System.out.println("Linha inserida:\n" + lineNumber + " " + instruction + " " + parameters);
                } else {
                    System.out.println(result); // Mensagem de erro
                }
        }
    }

    public void displayError(String errorMessage) {
        System.out.println("Erro: " + errorMessage);
    }

}
