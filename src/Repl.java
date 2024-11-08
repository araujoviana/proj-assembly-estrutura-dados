/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * Implementa a lógica de um REPL que processa os comandos inseridos pelo usuário e
 * os executa se comunicando com o buffer do código.
 */
public class Repl {

    private Commands commands; // Lógica dos comandos do REPL
    private Buffer buffer; // Buffer do código atual

    public Repl() {
        buffer = new Buffer();
        commands = new Commands();
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
            displayMessage("entrada está vazia.", 2);
            return false;
        }
        String[] splitInput = input.split(" ");

        String potentialCommand = splitInput[0];

        // Verifica se o primeiro termo é um comando válido
        if (!commands.isCommand(potentialCommand)) {
            displayMessage("O comando fornecido não é válido.", 2);
            return false;
        }

        return true;
    }

    /**
     * Verifica qual comando o usuário vai executar, verifica se existem erros
     * iniciais e usa a classe Commands para avaliar o comando
     * 
     * @param input comando do usuário
     */
    public void evaluateInput(String input) {

        // Isola o comando do resto da entrada
        String[] splitInput = input.split(" ", 2);
        String command = splitInput[0];

        // Comando insert
        if (command.equals("ins") && splitInput.length > 1) {
            try {
                // Isola o número da linha, a instrução e os parâmetros
                String[] args = splitInput[1].split(" ", 3);

                int lineNumber = Integer.parseInt(args[0]);
                String instruction = args[1];

                // Parâmetros são Strings vazias se não forem preenchidos
                String parameters = args.length > 2 ? args[2] : "";

                // Chama o comando ins no buffer
                String result = commands.insert(buffer, lineNumber, instruction, parameters);

                if (result == null) {
                    // Comando foi bem-sucedido
                    displayMessage("Linha inserida:\n" + lineNumber + " " + instruction + " " + parameters, 0);
                } else if (result != "atualizado"){
                    // Comando retornou erro
                    displayMessage(result, 2); 
                }

            // REVIEW Isso é necessário?
            } catch (NumberFormatException e) {
                displayMessage("formato de linha inválido.", 2);
            } catch (ArrayIndexOutOfBoundsException e) {
                displayMessage("parâmetros insuficientes para o comando INS.", 2);
            }
        } else if (command.equals("run") && splitInput.length == 1) {
            // VERIFICAÇÕES AQUI

            // execução
            String result = commands.run(buffer);

            if (result == null) {
                displayMessage("RUN PLACEHOLDER", 0);
            } else {
                displayMessage(result, 2);
            }
        }
    }

    // Exibe alguma mensagem para o usuário, podendo ser um erro, aviso ou informação
    public void displayMessage(String message, int code) {
        switch (code) {
            case 0: // Informação
                System.out.println(message);
                break;
            case 1: // Aviso
                System.out.println("Aviso: " + message);
                break;
            case 2: // Erro
                System.out.println("Erro: " + message);
                break;
        }
    }


}
