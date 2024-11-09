/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * Implementa a lógica de um REPL que processa os comandos inseridos pelo usuário e
 * os executa se comunicando com o buffer do código carregado.
 */

public class Repl {

    private Commands commands; // Comandos do REPL
    private Buffer buffer; // Buffer do código atual
    private String fileName; // Nome do arquivo carregado no buffer

    public Repl() {
        buffer = new Buffer();
        commands = new Commands();
        fileName = "";
    }

    /**
     * Converte a entrada inicial para maíusculo e remove todos os espaços
     * desnecessários.
     *
     * @param input a entrada não formatada como "10 MoV A 5 "
     * @return entrada formatada tipo "10 mov a 5"
     */
    public String formatInput(String input) {
        return input.toLowerCase().replaceAll("\\s+", " ").trim();
    }

    /**
     * Verifica se a entrada do usuário é válida para ser avaliada pelo REPL,
     * mas ela ainda pode retornar erros dentro dos métodos das outras classes.
     *
     * @param input a entrada formatada
     * @return true se o comando for válido e false se for inválido
     */
    public boolean validateInput(String input) {

        // Verifica se o comando está vazio
        if (input.isEmpty()) {
            displayMessage("entrada está vazia.", 2);
            return false;
        }

        // Verifica se o primeiro termo é um comando válido
        String[] splitInput = input.split(" ");
        String potentialCommand = splitInput[0];

        if (!commands.isCommand(potentialCommand)) {
            displayMessage("o comando fornecido não é válido.", 2);
            return false;
        }

        return true;
    }

    /**
     * Verifica qual comando o usuário vai executar e
     * usa a classe Commands para executar o comando.
     * 
     * @param input o comando do usuário
     * @return true se o usuário executar o comando EXIT, e
     *         false para todos os outros
     */
    public boolean evaluateInput(String input) {

        // Isola o comando do resto da entrada
        String[] splitInput = input.split(" ", 2);
        String command = splitInput[0];

        // Finaliza a execução do REPL
        if (command.equals("exit")) {
            // Verificações...

            String message = commands.exit(buffer, fileName);

            if (message != null) {
                displayMessage(message, 2);
            } else {
                return true;
            }
        }
        // Insere uma instrução em uma linha específica
        else if (command.equals("ins") && splitInput.length > 1 && splitInput.length < 6) {

            // Isola o número da linha, a instrução e os argumentos
            String[] insertArguments = splitInput[1].split(" ", 3);

            // Verifica se o número de linha é um Integer ou se a instrução está completa
            try {
                int lineNumber = Integer.parseInt(insertArguments[0]); // Número da linha da instrução

                String instruction = insertArguments[1]; // Instrução

                // Arguments são strings vazias se não forem preenchidas
                String arguments = insertArguments.length > 2 ? insertArguments[2] : "";

                // Chama o comando insert no buffer e recebe qualquer mensagem retornada
                String message = commands.insert(buffer, lineNumber, instruction, arguments);

                if (message == null) {
                    // Linha foi inserida
                    displayMessage("Linha inserida:\n" + lineNumber + " " + instruction + " " + arguments, 0);
                } else if (message.startsWith("Linha:")) {
                    // Linha foi atualizada
                    displayMessage(message, 0);
                } else {
                    // Comando falhou
                    displayMessage(message, 2);
                }

            } catch (NumberFormatException e) {
                displayMessage("número de linha para o comando " + command.toUpperCase() + " inválido.", 2);
            } catch (ArrayIndexOutOfBoundsException e) {
                displayMessage("instrução incompleta para a linha inserida no comando "
                        + command.toUpperCase() + ".", 2);
            }

            // Executa as instruções armazenadas no buffer
        } else if (command.equals("run") && splitInput.length == 1) {
            // VERIFICAÇÕES AQUI

            // Chama o comando run no buffer e recebe qualquer mensagem retornada
            String message = commands.run(buffer);

            if (message != null) {
                // Comando retornou erro
                displayMessage(message, 2);
            }
        } else if (command.equals("list") && splitInput.length == 1) {
            // VERIFICAÇÕES AQUI

            // Chama o comando list no buffer e recebe qualquer mensagem retornada
            String message = commands.list(buffer);

            // Verifica se a mensagem começa com um número
            if (message.matches("^[0-9]")) {
                displayMessage(message, 0);

            } else if (message != null) {
                // Comando retornou erro
                displayMessage(message, 2);
            }

            // Remove uma ou mais linhas do buffer
        } else if (command.equals("del") && splitInput.length > 1 && splitInput.length < 4) {
            // VERIFICAÇÕES AQUI

            // Isola o número da linha ou intervalo de linhas
            String[] numberStrings = splitInput[1].split(" ");

            // Verifica se o número de linha é um Integer
            try {
                Integer[] lineNumbers = new Integer[numberStrings.length];
                for (int i = 0; i < numberStrings.length; i++) {
                    lineNumbers[i] = Integer.parseInt(numberStrings[i]); // Converte cada número de linha para Integer
                }

                // Chama o comando delete no buffer e recebe qualquer mensagem retornada
                String message = commands.delete(buffer, lineNumbers);

                // Expressão regular para começar com: "Linha(s opcional) removida(s opcional)?"
                if (message.matches("^Linha(s)? removida(s)?$")) {
                    displayMessage(message, 0);
                } else if (message != null) {
                    // Comando retornou erro
                    displayMessage(message, 2);
                }

            } catch (NumberFormatException e) {
                displayMessage("número de linha para o comando " + command.toUpperCase() + " inválido.", 2);
            } 

            // Subsitui o conteúdo do buffer atual com o de um arquivo .ed1
        } else if (command.equals("load") && splitInput.length > 1) {
            // Verificações aqui...

            String filePath = splitInput[1]; // Caminho do arquivo salvo

            // Chama o comando load no buffer e recebe qualquer mensagem retornada
            String message = commands.load(buffer, filePath);

            if (message != null) {
                // Comando retornou erro
                displayMessage(message, 2);
            }

            // Insere o conteúdo do buffer atual em um arquivo .ed1
        } else if (command.equals("save")) {
            String savedFilePath; // Caminho do arquivo salvo

            // Verifica se o usuário especificou o arquivo para salvar
            if (splitInput[1] != null) {
                savedFilePath = splitInput[1];
            } else {
                savedFilePath = fileName;
            }

            // Chama o comando save no buffer e recebe qualquer mensagem retornada
            String message = commands.save(buffer, savedFilePath);

            if (message != null) {
                // Comando retornou erro
                displayMessage(message, 2);
            }
        } else {
            displayMessage("argumentos para o comando " + command.toUpperCase() + " insuficientes", 2);
        }

        // Usuário não executou o comando EXIT
        return false;
    }

    /**
     * Exibe alguma mensagem para o usuário, podendo ser um erro,
     * aviso ou informação.
     * 
     * @param message mensagem de um método
     * @param code    tipo da mensagem a ser exibida:
     *                0 para informação,
     *                1 para aviso,
     *                2 para erro
     */
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
