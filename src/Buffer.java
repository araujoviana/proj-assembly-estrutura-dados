/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

        // REVIEW reduntante visto linha 111?
        // Se a linha não foi encontrada após percorrer a lista
        return "linha " + lineNumber + " inexistente.";
    }

    /**
     * Remove um intervalo de linhas do buffer. As linhas a serem removidas devem
     * estar dentro do intervalo especificado.
     * 
     * @param lineStart o número da linha inicial do intervalo
     * @param lineEnd   o número da linha final do intervalo
     * @return uma mensagem indicando se a operação foi bem-sucedida ou não
     */
    // FIXME Remove apenas o primeiro nó se intervalo começar no inicio da lista
    public String removeInterval(int lineStart, int lineEnd) {
        // Verifica se os parâmetros do intervalo são válidos
        if (lineStart > lineEnd) {
            return "inicio do intervalo maior que o final.";
        } else if (lineStart == lineEnd) {
            return "intervalos iguais.";
        }

        // Verifica se o intervalo está dentro do código
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

        Node<String> current = commandBuffer.getHead();
        boolean removedAny = false;
        StringBuilder removedLines = new StringBuilder("Linhas removidas:\n");

        // Verifica se a lista está vazia
        if (current == null) {
            return "a lista está vazia.";
        }

        Node<String> nextNode = current.getNext();

        do {
            // Divide o valor da linha atual para obter o número da linha
            String[] currentParts = current.getValue().split(" ", 2);
            int existingLineNumber = Integer.parseInt(currentParts[0]);

            // Verifica se a linha está no intervalo
            if (existingLineNumber >= lineStart && existingLineNumber <= lineEnd) {

                String lineToRemove = current.getValue();

                // Tenta remover o nó
                boolean removed = commandBuffer.removeNode(lineToRemove);
                if (removed) {
                    removedAny = true;
                    removedLines.append(lineToRemove).append("\n");
                }

                // reconfigura o próximo nó
                current = nextNode;
            } else {
                current = nextNode;
            }

            // Atualiza o próximo nó
            if (current != commandBuffer.getHead()) {
                nextNode = current.getNext();
            }

        } while (current != commandBuffer.getHead());

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

        boolean jnzJumped = false; // Verifica se jnz fez algum pulo

        do {

            // Divide a linha em partes (número da linha, instrução e argumentos)
            String[] parts = current.getValue().split(" ", 3);
            String lineNumber = parts[0]; // Número da linha (não utilizado diretamente aqui)

            // Verifica se a linha começa com um inteiro
            try {
                Integer.parseInt(lineNumber);
            } catch (NumberFormatException e) {
                return "número de linha inválido: " + lineNumber + ".";
            }

            String instruction = parts[1]; // Instrução (ex: 'mov', 'add')
            String arguments = parts[2]; // Argumentos da instrução

            // Verifica se a instrução é válida
            if (!Instructions.isInstruction(instruction)) {
                return "instrução inválida na linha " + lineNumber + ": " + instruction;
            }
            // Valida a sintaxe dos argumentos da instrução
            String validationError = Instructions.validateSyntax(instruction, arguments);
            if (validationError != null) {
                return validationError;
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
     * @param filePath O caminho do arquivo que irá ser carregado.
     * @param fileName o caminho do arquivo carregado atualmente
     * @return Uma mensagem de erro se ocorrer erro na operação do
     *         arquivo, ou null se for bem-sucedida.
     */
    public LoadResult loadBuffer(String filePath, String fileName) {

        // Flag pra indicar se o arquivo contém algum erro
        boolean fileContainsErrors = false;

        // Limpa o buffer de comandos antes de carregar novos dados
        commandBuffer.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            // Lê o arquivo linha por linha e adiciona cada linha ao buffer de comandos
            while ((currentLine = br.readLine()) != null) {

                // Verifica se linha está vazia
                if (currentLine.trim().isEmpty()) {
                    fileContainsErrors = true;
                }
                // Valida sintaxe da linha
                String[] SplitCurrentLine = currentLine.split(" ", 3);
                if (Instructions.validateSyntax(SplitCurrentLine[1], SplitCurrentLine[2]) != null) {
                    fileContainsErrors = true;
                }

                commandBuffer.append(currentLine);
            }

        } catch (IOException e) {
            // Retorna uma mensagem de erro se ocorrer uma exceção durante a leitura do
            // arquivo
            return new LoadResult("arquivo " + filePath + " inexistente.", fileName);
        }

        if (fileContainsErrors) {
            return new LoadResult(
                    "Arquivo com erros carregado, mas não irá conseguir ser executado até os erros serem corrigidos.",
                    filePath);
        }

        return new LoadResult(null, filePath);
    }

    /**
     * Salva o conteúdo do buffer em um arquivo especificado pelo caminho fornecido.
     *
     * @param filePath O caminho do arquivo a ser carregado
     * @param fileName o caminho do arquivo carregado atualmente
     * @return Uma mensagem de erro se ocorrer algum erro durante a operação, ou
     *         null se for bem-sucedida.
     */
    public String saveBuffer(String filePath, String fileName) {
        if (commandBuffer.isEmpty()) {
            return "não há nenhum código na memória atualmente.";
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Escreve o conteúdo do buffer no arquivo
            bw.write(commandBuffer.toString());
            bw.newLine();

        } catch (IOException e) {
            // Retorna uma mensagem de erro se ocorrer uma exceção durante a gravação do
            // arquivo
            return "erro ao salvar o arquivo " + ((filePath == "") ? " sem nome" : filePath) + ".";
        } catch (NullPointerException e) {
            // Retorna uma mensagem de erro se arquivo estiver vazio
            return "arquivo " + ((filePath == "") ? "sem nome" : filePath) + " vazio.";
        }

        // Torna o arquivo atual o arquivo salvo
        fileName = filePath;
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
