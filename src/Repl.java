/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * Implementa a lógica de um REPL que processa os comandos inseridos pelo usuário e
 * os executa se comunicando com o buffer do código carregado.
 */

import java.util.Scanner;

public class Repl {

    private Commands commands; // Comandos do REPL
    private Buffer buffer; // Buffer do código atual
    private String currentBufferFileName; // Nome do arquivo carregado no buffer
    private boolean bufferHasChanged = false; // Verifica se houve alguma alteração não salva no buffer

    public Repl() {
        buffer = new Buffer();
        commands = new Commands();
        currentBufferFileName = "";
    }

    /**
     * Converte a entrada inicial para maíusculo e remove todos os espaços
     * desnecessários.
     *
     * @param input a entrada não formatada como "10 MoV A 5 "
     * @return entrada formatada tipo "10 mov a 5"
     */
    public String formatInput(String input) {
        // Não altera o nome do arquivo carregado
        if (input.toLowerCase().startsWith("load ")) {
            return "load " + input.substring(5).replaceAll("\\s+", " ").trim();
        }
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
        if (command.equals("exit") && splitInput.length == 1) {
            String message = commands.exit(buffer, currentBufferFileName, bufferHasChanged);

            if (message != null) {
                // Comando retornou com erro
                displayMessage(message, 2);
            } else {
                return true; // hasExited é verdadeiro, finalizando a execução do REPL
            }
        }
        // Insere uma instrução em uma linha específica
        else if (command.equals("ins") && splitInput.length > 1 && splitInput.length < 6) {

            // Isola o número da linha, a instrução e os argumentos
            String[] insertArguments = splitInput[1].split(" ", 3);

            // Verifica se o número de linha é um Integer ou se a instrução está completa
            int lineNumber = 0;
            try {
                lineNumber = Integer.parseInt(insertArguments[0]); // Número da linha da instrução

                String instruction = insertArguments[1]; // Instrução

                // Arguments são strings vazias se não forem preenchidas
                String arguments = insertArguments.length > 2 ? insertArguments[2] : "";

                // Chama o comando insert no buffer e recebe qualquer mensagem retornada
                String message = commands.insert(buffer, lineNumber, instruction, arguments);

                if (message == null) {
                    // Linha foi inserida
                    displayMessage("Linha inserida:\n" + lineNumber + " " + instruction + " " + arguments, 0);
                    bufferHasChanged = true;
                } else if (message.startsWith("Linha:")) {
                    // Linha foi atualizada
                    displayMessage(message, 0);
                    bufferHasChanged = true;
                } else {
                    // Comando falhou
                    displayMessage(message, 2);
                }

            }
            // Verifica se o número de linha é válido
            catch (NumberFormatException e) {
                displayMessage(
                        "código na memória ou entrada do usuário para o comando " + command.toUpperCase()
                                + " contém número de linha inválido.",
                        2);
            }
            // Verifica se a instrução possui o tamanho correto
            catch (ArrayIndexOutOfBoundsException e) {
                displayMessage("instrução incompleta para a linha inserida no comando "
                        + command.toUpperCase() + ".", 2);
            }

            // Executa as instruções armazenadas no buffer
        } else if (command.equals("run") && splitInput.length == 1) {

            // Chama o comando run no buffer e recebe qualquer mensagem retornada
            String message = commands.run(buffer);

            if (message != null) {
                // Comando retornou erro
                displayMessage(message, 2);
            }
        }
        // Loop para listar blocos de 20 linhas até que menos de 20 sejam retornadas
        else if (command.equals("list") && splitInput.length == 1) {

            // Verifica se o erro de list já foi exibido
            boolean bufferHasError = false;

            while (true) {
                // Chama o comando list no buffer e recebe qualquer mensagem retornada
                String message = commands.list(buffer);

                // Verifica se a mensagem é um erro
                if (message != null && message.startsWith("nenhum")) {
                    // Exibe mensagem de erro e sai do loop
                    displayMessage(message, 2);
                    break;
                } else if (message != null) {
                    // Exibe o bloco de 20 linhas no console
                    System.out.print(message);

                    // Verifica quantas linhas foram retornadas
                    String[] messageLines = message.split("\n");
                    int messageLineCount = messageLines.length;

                    try {

                        // Obtém o número da última linha da mensagem
                        String lastLine = messageLines[messageLines.length - 1];
                        String[] lastLineParts = lastLine.split(" ", 2);

                        // Obtém o número da última linha do buffer
                        String[] tailParts = buffer.getCommandBuffer().getTail().getValue().split(" ", 2);

                        int lastLineNumber = Integer.parseInt(lastLineParts[0]);
                        int tailLineNumber = Integer.parseInt(tailParts[0]);

                        // Verifica se existem linhas inválidas no buffer usando a exceção
                        for (String line : messageLines) {
                            String[] lineParts = line.split(" ", 2);
                            Integer.parseInt(lineParts[0]);
                        }

                        // Sai do loop se o número da última linha for igual ao número da linha da tail
                        // do buffer
                        if (messageLineCount < 20) {
                            break;
                        } else if (lastLineNumber == tailLineNumber) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        bufferHasError = true;
                        // Tenta continuar listando o arquivo mesmo se ele contém erros
                        if (messageLineCount < 20) {
                            break;
                        } else if (!messageLines[messageLineCount - 1]
                                .equals(buffer.getCommandBuffer().getTail().getValue())) {
                            continue;
                        }
                    }
                }

            }

            // Avisa o usuário se o código listado contém algum número de linha inválido
            if (bufferHasError) {
                displayMessage(
                        "código na memória contém pelo menos um número de linha inválido.",
                        1);
            }
        }

        // Remove uma ou mais linhas do buffer
        else if (command.equals("del") && splitInput.length > 1 && splitInput.length < 4) {

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

                if (message.startsWith("Linha")) {
                    // Comando operou corretamente
                    displayMessage(message, 0);
                    bufferHasChanged = true; // Buffer foi alterado
                } else if (message != null) {
                    // Comando retornou erro
                    displayMessage(message, 2);
                }

            } catch (NumberFormatException e) {
                // Alerta o usuário caso o comando detecte alguma linha com número de linha
                // inválida
                displayMessage(
                        "código na memória ou entrada do usuário para o comando " + command.toUpperCase()
                                + " contém número de linha inválido.",
                        2);
            }

        }
        // Subsitui o conteúdo do buffer atual com o de um arquivo .ed1
        else if (command.equals("load") && splitInput.length > 1 && splitInput[1].split(" ").length < 2) {

            String loadedFileName = splitInput[1]; // Caminho do arquivo salvo

            // Chama o comando load no buffer e recebe qualquer mensagem retornada
            LoadResult result = commands.load(buffer, loadedFileName, currentBufferFileName, bufferHasChanged);

            // Arquivo foi carregado com erros
            if (result.error.startsWith("Erros de")) {
                displayMessage(result.error, 1);
                // Buffer novo ainda intacto
                bufferHasChanged = false;
                // Atualiza o nome do arquivo atual
                currentBufferFileName = result.fileName;
            }
            // Arquivo foi carregado sem erros
            else if (result.error.startsWith("Arquivo carregado")) {
                // Mensagem não é erro nesse caso
                displayMessage(result.error, 0);
                // Buffer novo ainda intacto
                bufferHasChanged = false;
                // Atualiza o nome do arquivo atual
                currentBufferFileName = result.fileName;

            } else {
                // Comando retornou erro e arquivo não foi carregado
                displayMessage(result.error, 2);
            }

        }
        // Insere o conteúdo do buffer atual em um arquivo .ed1
        else if (command.equals("save") && (splitInput.length > 1 ? splitInput[1].split(" ").length : 0) < 2) {
            String savedFilePath; // Caminho do arquivo salvo

            // Verifica se o usuário especificou o arquivo para salvar
            if (splitInput.length > 1) {
                savedFilePath = splitInput[1];

                // Verifica a extensão do arquivo
                if (!savedFilePath.endsWith(".ed1")) {
                    displayMessage("extensão do arquivo não termina com extensão .ed1.", 2);
                    return false;
                }
            } else {

                if (!currentBufferFileName.isEmpty()) {
                    savedFilePath = currentBufferFileName; // Arquivo atual é o arquivo salvo
                } else {
                    Scanner saveScanner = new Scanner(System.in);
                    String saveInput = "";

                    // Loop até o usuário fornecer uma resposta válida
                    while (saveInput.isEmpty()) {
                        System.out.print("Arquivo atual não possui nome, insira um nome:\n> ");
                        saveInput = saveScanner.nextLine();

                        // Verifica a extensão do arquivo
                        if (!saveInput.endsWith(".ed1")) {
                            System.out.println("Erro: extensão de arquivo inválida, use .ed1");
                            saveInput = "";
                            continue;
                        }
                        System.out.println("Tentando salvar arquivo " + input + "...");
                    }
                    savedFilePath = saveInput; // Arquivo salvo se torna a entrada
                }
            }

            // Chama o comando save no buffer e recebe qualquer mensagem retornada
            String message = commands.save(buffer, savedFilePath, currentBufferFileName, false);

            if (message != null) {
                // Comando retornou erro
                displayMessage(message, 2);
            } else {
                bufferHasChanged = false; // Arquivo foi salvo então há alterações não salvas
                displayMessage("Arquivo salvo com sucesso.", 0);
                currentBufferFileName = savedFilePath; // Arquivo atual é o arquivo salvo
            }
        } else {
            // Alerta que o comando inserido possui número de argumentos inválido
            displayMessage("argumentos para o comando " + command.toUpperCase() + " insuficientes ou excessivos.", 2);
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
