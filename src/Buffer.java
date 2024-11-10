/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

import java.io.*;
import java.util.Scanner;

/**
 * Gerencia o buffer contendo o código carregado no REPL. Utiliza uma lista
 * encadeada para armazenar as linhas de código.
 */
public class Buffer {

    // Armazena todos os comandos carregados em uma lista encadeada
    private LinkedList<String> commandBuffer;
    // Armazena todos os registradores carregados
    private Registers registers;

    public Buffer() {
        commandBuffer = new LinkedList<>();
        registers = new Registers();
    }

    /**
     * Insere uma linha de código no buffer na posição especificada, com a instrução
     * e os argumentos fornecidos. Se a linha já existir, ela será atualizada.
     * Caso o número da linha seja maior do que as linhas existentes, a linha será
     * inserida no final.
     * 
     * @param lineNumber  o número da linha onde a instrução deve ser inserida ou
     *                    atualizada
     * @param instruction a instrução a ser inserida
     * @param arguments   os argumentos associados à instrução
     * @return uma mensagem indicando se a operação foi bem-sucedida ou não
     */
    public String insertLine(int lineNumber, String instruction, String arguments) {
        // Constrói a linha completa a partir dos argumentos
        String line = lineNumber + " " + instruction + " " + arguments;

        Node<String> current = commandBuffer.getHead();

        // Verifica se a lista está vazia e insere diretamente
        if (current == null) {
            commandBuffer.append(line);
            return null;
        }

        // Percorre a lista para encontrar a posição correta de inserção de acordo com o
        // número da linha inserida
        do {
            String[] parts = current.getValue().split(" ", 2);
            int existingLineNumber = Integer.parseInt(parts[0]);

            // Verifica se a linha já existe, se sim, ela é substituida
            if (existingLineNumber == lineNumber) {
                String originalLine = current.getValue();

                current.setValue(line);

                return "Linha:\n" + originalLine + "\nAtualizada para:\n" + line;
            }

            // Insere antes de um número de linha maior, mantendo a ordem
            if (existingLineNumber > lineNumber) {
                commandBuffer.insertBefore(current, line);
                return null;
            }

            current = current.getNext();
        } while (current != commandBuffer.getHead());

        // Caso nenhuma posição de linha maior seja encontrada, insere no final
        commandBuffer.append(line);
        return null;
    }

    /**
     * Remove a linha de código especificada do buffer.
     * 
     * @param lineNumber o número da linha a ser removida
     * @return uma mensagem indicando se a operação foi bem-sucedida ou não
     */
    public String removeLine(int lineNumber) {

        Node<String> current = commandBuffer.getHead();

        // Verifica se a lista está vazia
        if (current == null) {
            return "a memória está vazia.";
        }

        do {
            // Divide o valor da linha atual para obter o número da linha
            String[] parts = current.getValue().split(" ", 2);
            int existingLineNumber = Integer.parseInt(parts[0]);

            // Verifica se esta é a linha que queremos remover
            if (existingLineNumber == lineNumber) {
                String lineToRemove = current.getValue();

                // Remove o nó e verifica se a remoção foi bem-sucedida
                boolean removed = commandBuffer.removeNode(lineToRemove);

                if (removed) {
                    return "Linha removida:\n" + lineToRemove;
                } else {
                    return "não foi possível encontrar a linha " + lineToRemove + ".";
                }
            }

            current = current.getNext();
        } while (current != commandBuffer.getHead());

        return "número de linha " + lineNumber + " inexistente.";
    }

    /**
     * Remove um intervalo de linhas do buffer. As linhas a serem removidas devem
     * estar dentro do intervalo especificado.
     * 
     * @param lineStart o número da linha inicial do intervalo
     * @param lineEnd   o número da linha final do intervalo
     * @return uma mensagem indicando se a operação foi bem-sucedida ou não
     */
    public String removeInterval(int lineStart, int lineEnd) {

        // Verifica se o buffer está vazio
        if (commandBuffer.isEmpty()) {
            return "não há nenhum código carregado na memória";
        }
        // Verifica se os parâmetros do intervalo são válidos
        if (lineStart > lineEnd) {
            return "inicio do intervalo maior que o final.";
        } else if (lineStart == lineEnd) {
            return "intervalos iguais.";
        }

        // Verifica se o intervalo está dentro do código
        try {
            String[] headParts = commandBuffer.getHead().getValue().split(" ", 2);
            int headLineNumber = Integer.parseInt(headParts[0]);

            String[] tailParts = commandBuffer.getTail().getValue().split(" ", 2);
            int tailLineNumber = Integer.parseInt(tailParts[0]);

            if (lineStart < headLineNumber) {
                return "intervalo inicia antes da linha do começo.";
            } else if (lineEnd < headLineNumber) {
                return "intervalo termina antes da linha do começo.";
            } else if (lineStart > tailLineNumber) {
                return "intervalo inicia depois da linha do final.";
            } else if (lineEnd > tailLineNumber) {
                return "intervalo termina depois da linha do final.";
            }

        } catch (NumberFormatException e) {
            return "Arquivo começa ou termina com número de linha vazio ou inválido, verifique se existem linhas vazias no arquivo.";
        }

        Node<String> current = commandBuffer.getHead();
        boolean removedAny = false;
        StringBuilder removedLines = new StringBuilder("Linhas removidas:\n");

        // Verifica se a lista está vazia
        if (current == null) {
            return "a lista está vazia.";
        }

        Node<String> nextNode = current.getNext();

        // Verifica se o head está no intervalo de remoção
        boolean headInInterval = false;
        String[] currentParts = current.getValue().split(" ", 2);
        int existingLineNumber = Integer.parseInt(currentParts[0]);
        if (existingLineNumber >= lineStart && existingLineNumber <= lineEnd && current == commandBuffer.getHead()) {
            // Se sim, pula a remoção dele, até o final, para não atrapalhar o loop de
            // remoção
            headInInterval = true;
            current = nextNode;
            nextNode = current.getNext();

            // Adiciona head ao stringbuilder
            removedLines.append(commandBuffer.getHead().getValue()).append("\n");
        }

        do {
            // Divide o valor da linha atual para obter o número da linha
            currentParts = current.getValue().split(" ", 2);
            existingLineNumber = Integer.parseInt(currentParts[0]);

            // Verifica se a linha está no intervalo
            if (existingLineNumber >= lineStart && existingLineNumber <= lineEnd) {
                String lineToRemove = current.getValue();

                // Tenta remover o nó
                boolean removed = commandBuffer.removeNode(lineToRemove);
                if (removed) {
                    removedAny = true;
                    removedLines.append(lineToRemove).append("\n");

                    // Após a remoção, ajusta o próximo nó corretamente
                    if (current == commandBuffer.getHead()) {
                        // Se removemos o nó inicial, atualiza o head
                        current = commandBuffer.getHead();
                    } else {
                        current = nextNode;
                    }
                } else {
                    current = nextNode;
                }
            } else {
                current = nextNode;
            }

            // Atualiza o próximo nó
            if (current != commandBuffer.getHead()) {
                nextNode = current.getNext();
            }

        } while (current != commandBuffer.getHead());

        // Remove a head por último se ela estiver no intervalo
        if (headInInterval) {
            commandBuffer.removeHead();
            removedAny = true;
        }

        // Retorna as linhas removidas ou uma mensagem caso nenhuma linha tenha sido
        // encontrada
        if (removedAny) {
            return removedLines.toString();
        } else {
            return "nenhuma linha encontrada no intervalo especificado.";
        }
    }

    /**
     * Avalia e executa cada linha no buffer de comandos conforme a lógica das
     * instruções definidas.
     * 
     * @return uma mensagem de erro em caso de instrução inválida ou erro de
     *         sintaxe, ou retorna null caso todas as instruções sejam válidas e a
     *         execução ocorra sem problemas.
     */
    public String evaluateBuffer() {

        Node<String> current = commandBuffer.getHead();

        // Armazena quais registradores já foram definidos
        LinkedList<String> definedRegisters = new LinkedList<>();

        boolean jnzJumped = false; // Verifica se jnz fez algum pulo

        do {

            // Divide a linha em partes (número da linha, instrução e argumentos)
            String[] parts = current.getValue().split(" ", 3);
            String lineNumber = parts[0]; // Número da linha (não utilizado diretamente aqui)

            // Verifica se a linha começa com um inteiro
            try {
                Integer.parseInt(lineNumber);

                // Verifica se existem linhas duplicadas ou na ordem errada
                if (Integer.parseInt(lineNumber) <= Integer.parseInt(current.getPrev().getValue().split(" ", 3)[0])
                        && current != commandBuffer.getHead()) {
                    return "número de linha " + lineNumber + " igual ou menor que pelo menos uma linha anterior";

                }
            } catch (NumberFormatException e) {
                return "número de linha inválido: " + lineNumber + ".";
            }

            String instruction = parts[1]; // Instrução (ex: 'mov', 'add')
            String arguments = parts[2]; // Argumentos da instrução

            // Verifica se a instrução é válida
            if (!Instructions.isInstruction(instruction)) {
                return "instrução inválida:" + instruction;
            }
            // Valida a sintaxe dos argumentos da instrução
            String validationError = Instructions.validateSyntax(instruction, arguments);
            if (validationError != null) {
                return validationError;
            }

            // Verifica se a operação envolve um registrador indefinido
            String[] verifyParts = arguments.split(" ");
            if (!instruction.equals("mov")) {
                if (Instructions.isValidRegister(verifyParts[0])) {
                    if (definedRegisters.getNode(verifyParts[0]) == null) {
                        return "registrador " + verifyParts[0] + " indefinido.";
                    }
                }
            }
            if (verifyParts.length > 1 && Instructions.isValidRegister(verifyParts[1])) {
                if (definedRegisters.getNode(verifyParts[1]) == null) {
                    return "registrador " + verifyParts[1] + " indefinido.";
                }
            }

            // Mensagem retornada pelas instruções
            String message;

            // Avalia comando respectivo
            switch (instruction) {
                // Adiciona dois registradores
                case "add":

                    message = Instructions.add(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }
                    break;

                // Subtrai dois registradores
                case "sub":

                    message = Instructions.sub(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }
                    break;

                // Multiplica dois registradores
                case "mul":

                    message = Instructions.mul(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }
                    break;

                // Divide dois registradores
                case "div":

                    message = Instructions.div(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }
                    break;

                // Incrementa um registrador
                case "inc":

                    message = Instructions.inc(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }
                    break;

                // Decrementa um registrador
                case "dec":

                    message = Instructions.dec(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }
                    break;

                // Define um valor para um registrador
                case "mov":

                    message = Instructions.mov(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }

                    definedRegisters.append(arguments.split(" ")[0]);

                    break;

                // Imprime o valor de um registrador
                case "out":

                    message = Instructions.out(arguments, registers);

                    // Retorna possíveis erros
                    if (message != null) {
                        return message;
                    }

                    break;

                // Pula para uma linha especifica se o valor
                // do primeiro registrador não for zero
                case "jnz":
                    // Como jnz altera a ordem de execução do buffer então ele retorna a String
                    // contendo alguma mensagem e a nova instrução atual
                    JnzResult jnzResult = Instructions.jnz(commandBuffer, current, arguments, registers);

                    if (jnzResult.hasError()) {
                        return jnzResult.error;
                    } else {
                        current = jnzResult.node;
                        jnzJumped = true;
                    }
                    break;

            }

            if (!jnzJumped) {
                current = current.getNext();
            } else {
                jnzJumped = false;
            }
        } while (current != commandBuffer.getHead());

        return null; // Não retorna nenhum erro quando a execução ocorre normalmente
    }

    /**
     * Carrega o conteúdo de um arquivo pelo caminho
     * fornecido para o buffer.
     *
     * @param loadedFileName        O caminho do arquivo que irá ser carregado.
     * @param currentBufferFileName o caminho do arquivo carregado atualmente
     * @return Uma mensagem de erro se ocorrer erro na operação do
     *         arquivo, ou null se for bem-sucedida.
     */
    public LoadResult loadBuffer(String loadedFileName, String currentBufferFileName) {

        // StringBuilder para acumular mensagens de erro
        StringBuilder errorMessages = new StringBuilder();
        LinkedList<String> bufferLineNumbers = new LinkedList<>();

        // Limpa o buffer de comandos antes de carregar novos dados
        commandBuffer.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(loadedFileName))) {
            String currentLine;
            int lineNumber = 1; // Número da linha para identificar erros

            // Lê o arquivo linha por linha e adiciona cada linha ao buffer de comandos
            while ((currentLine = br.readLine()) != null) {

                String[] splitCurrentLine = currentLine.split(" ", 3);

                // Verifica se a linha está vazia
                if (currentLine.trim().isEmpty()) {
                    errorMessages.append("linha ").append(lineNumber).append(": linha vazia.\n");
                    commandBuffer.append(currentLine);

                }
                // Verifica se a linha é duplicada
                else if (bufferLineNumbers.getNode(currentLine.split(" ")[0]) != null) {
                    errorMessages.append("linha ").append(lineNumber)
                            .append(": linha " + currentLine
                                    + " possui número de linha duplicada com outra linha anterior.\n");
                    commandBuffer.append(currentLine);
                }

                // Verifica se a linha tem menos de 3 partes (quando deve ter pelo menos 3)
                else if (splitCurrentLine.length < 3) {
                    errorMessages.append("linha ").append(lineNumber)
                            .append(": comando com número insuficiente de argumentos ou comando é inválido.\n");
                    commandBuffer.append(currentLine);

                }

                // Verifica se linha é menor que linha anterior
                else if (lineIsSmaller(currentLine.split(" ")[0], bufferLineNumbers)) {
                    errorMessages.append("linha ").append(lineNumber)
                            .append(": linha " + currentLine
                                    + " possui número de linha menor que outra linha já inserida.\n");
                    commandBuffer.append(currentLine);
                }
                // Valida a sintaxe da linha
                else {
                    String syntaxError = Instructions.validateSyntax(splitCurrentLine[1], splitCurrentLine[2]);
                    if (syntaxError != null) {
                        errorMessages.append("linha ").append(lineNumber).append(": ").append(syntaxError)
                                .append("\n");
                        commandBuffer.append(currentLine);

                    } else {
                        // Linha é válida
                        commandBuffer.append(currentLine);
                        bufferLineNumbers.append(currentLine.split(" ")[0]);
                    }
                }

                lineNumber++;
            }

        } catch (IOException e) {
            // Retorna uma mensagem de erro se ocorrer uma exceção durante a leitura do
            // arquivo
            return new LoadResult("arquivo " + loadedFileName + " inexistente ou não acessível.",
                    currentBufferFileName);
        }

        // Se houver erros, retorna a mensagem com todos os erros encontrados
        if (errorMessages.length() > 0) {
            return new LoadResult(
                    "Arquivo com erros de sintaxe carregado, então não será possível executá-lo completamente e alguns comandos não funcionarão corretamente até que os erros sejam corrigidos:\n"
                            +
                            errorMessages.toString(),
                    loadedFileName);
        }

        return new LoadResult("Arquivo carregado com sucesso.", loadedFileName);
    }

    private boolean lineIsSmaller(String currentNumber, LinkedList<String> bufferLineNumbers) {

        // Itera sobre cada nó em bufferLineNumbers
        Node<String> current = bufferLineNumbers.getHead();
        do {

            if (bufferLineNumbers.isEmpty()) {
                return false;
            }

            try {
                int currentLineNumber = Integer.parseInt(currentNumber);
                int storedLineNumber = Integer.parseInt(current.getValue());

                // Verifica se o número da linha atual é menor que o número de linha armazenado
                if (currentLineNumber < storedLineNumber) {
                    return true; // Se encontrado, retorna true, pois a linha atual é menor
                }
            } catch (NullPointerException e) {
                continue;
            }

            current = current.getNext(); // Avança para o próximo nó na lista
        } while (current != bufferLineNumbers.getHead());

        // Se nenhum número de linha menor for encontrado, retorna false
        return false;
    }

    /**
     * Salva o conteúdo do buffer em um arquivo especificado pelo caminho fornecido.
     *
     * @param loadedFileName        O caminho do arquivo a ser carregado
     * @param currentBufferFileName o caminho do arquivo carregado atualmente
     * @return Uma mensagem de erro se ocorrer algum erro durante a operação, ou
     *         null se for bem-sucedida.
     */
    public String saveBuffer(String loadedFileName, String currentBufferFileName) {
        if (commandBuffer.isEmpty()) {
            return "não há nenhum código na memória atualmente.";
        }
        // Verifica se o arquivo já existe
        File file = new File(loadedFileName);
        if (file.exists()) {
            Scanner scanner = new Scanner(System.in);
            String response;

            while (true) {
                System.out.print("O arquivo " + loadedFileName + " já existe. Deseja sobrescrevê-lo? (S/N)\n> ");
                response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("s")) {
                    break;
                } else if (response.equals("n")) {
                    return "operação de salvamento cancelada pelo usuário.";
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(loadedFileName))) {
            // Escreve o conteúdo do buffer no arquivo
            bw.write(commandBuffer.toString());
            bw.newLine();

        } catch (IOException e) {
            // Retorna uma mensagem de erro se ocorrer uma exceção durante a gravação do
            // arquivo
            return "erro ao salvar o arquivo " + ((loadedFileName == "") ? " sem nome" : loadedFileName) + ".";
        } catch (NullPointerException e) {
            // Retorna uma mensagem de erro se arquivo estiver vazio
            return "arquivo " + ((loadedFileName == "") ? "sem nome" : loadedFileName) + " vazio.";
        }

        // Torna o arquivo atual o arquivo salvo
        currentBufferFileName = loadedFileName;
        return null;
    }

    /**
     * Retorna o buffer de comandos.
     *
     * @return O buffer de comandos como uma LinkedList de Strings.
     */
    public LinkedList<String> getCommandBuffer() {
        return commandBuffer;
    }

    /**
     * Retorna uma representação em string do buffer de comandos.
     * 
     * @return Uma string com todos os comandos no buffer em ordem.
     */
    @Override
    public String toString() {
        return commandBuffer.toString();
    }

}
