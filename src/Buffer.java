/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// REVIEW descrição
// Age como um buffer que armazena o código atualmente carregado no REPL 
public class Buffer {

    // Armazena todos os comandos carregados
    private LinkedList<String> commandBuffer;
    // Armazena todos os registradores carregados
    private Registers registers;

    public Buffer() {
        commandBuffer = new LinkedList<>();
        registers = new Registers();
    }

    public String insertLine(int lineNumber, String instruction, String parameters) {
        // Constrói a linha completa a partir dos parâmetros
        String line = lineNumber + " " + instruction + " " + parameters;

        Node<String> current = commandBuffer.getHead();

        // Verifica se a lista está vazia e insere diretamente se for o caso
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

                // HACK Buffer deveria se retornar a saída diretamente com REPL
                System.out.println("Linha:\n" + originalLine + "\nAtualizada para:\n" + line);
                return "atualizado";
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

    public String removeLine(int lineNumber) {
        // Inicia a busca a partir do cabeçalho da lista
        Node<String> current = commandBuffer.getHead();

        // Verifica se a lista está vazia e retorna uma mensagem de erro
        if (current == null) {
            return "a lista está vazia.";
        }

        do {
            // Divide o valor da linha atual para obter o número da linha
            String[] parts = current.getValue().split(" ", 2);
            int existingLineNumber = Integer.parseInt(parts[0]);

            // Verifica se esta é a linha que queremos remover
            if (existingLineNumber == lineNumber) {
                String lineToRemove = current.getValue();

                // Usa removeNode(T) para remover o nó e verifica se a remoção foi bem-sucedida
                boolean removed = commandBuffer.removeNode(lineToRemove);

                if (removed) {
                    return "Linha removida:\n" + lineToRemove;
                } else {
                    return "não foi possível remover a linha.";
                }
            }

            current = current.getNext();
        } while (current != commandBuffer.getHead());

        // Se a linha não foi encontrada após percorrer a lista
        return "linha " + lineNumber + " inexistente.";
    }

    // FIXME Remove apenas o primeiro nó se intervalo começar no inicio da lista
    public String removeInterval(int lineStart, int lineEnd) {
        // Verifica se os parâmetros do intervalo são válidos
        if (lineStart > lineEnd) {
            return "intervalo inválido.";
        }

        Node<String> current = commandBuffer.getHead();
        boolean removedAny = false;
        StringBuilder removedLines = new StringBuilder("Linhas removidas:\n");

        // Verifica se a lista está vazia
        if (current == null) {
            return "a lista está vazia.";
        }

        Node<String> nextNode = current.getNext(); // Guardando o próximo nó

        do {
            // Divide o valor da linha atual para obter o número da linha
            String[] parts = current.getValue().split(" ", 2);
            int existingLineNumber = Integer.parseInt(parts[0]);

            // Verifica se a linha está no intervalo
            if (existingLineNumber >= lineStart && existingLineNumber <= lineEnd) {

                String lineToRemove = current.getValue();

                // Tenta remover o nó
                boolean removed = commandBuffer.removeNode(lineToRemove);
                if (removed) {
                    removedAny = true;
                    removedLines.append(lineToRemove).append("\n");
                }

                // Como removemos o nó atual, precisamos reconfigurar o próximo nó
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

    public String evaluateBuffer() {
        // Check if the WHOLE BUFFER is good to go!
        // ...

        Node<String> current = commandBuffer.getHead();
        Instructions instructions = new Instructions();

        do {
            // Divide a linha em partes (número da linha, instrução e parâmetros)
            String[] parts = current.getValue().split(" ", 3);
            String lineNumber = parts[0]; // Número da linha (não utilizado diretamente aqui)
            String instruction = parts[1]; // Instrução (ex: 'mov', 'add')
            String parameters = parts[2]; // Parâmetros da instrução

            // Verifica se a instrução é válida
            if (!instructions.isInstruction(instruction)) {
                return "Erro: Instrução inválida na linha " + lineNumber + ": " + instruction;
            }
            // Valida a sintaxe dos parâmetros da instrução
            String validationError = instructions.validateSyntax(instruction, parameters);
            if (validationError != null) {
                return "Erro na linha " + lineNumber + ": " + validationError;
            }

            // Mensagem retornada pelas instruções
            String result;

            // Avalia comando respectivo
            switch (instruction) {
                case "add":

                    result = instructions.add(parameters, registers);

                    // Retorna possíveis erros
                    if (result != null) {
                        return result;
                    }
                    break;

                case "mov":

                    result = instructions.mov(parameters, registers);

                    if (result != null) {
                        return result;
                    }

                    break;
                case "out":

                    result = instructions.out(parameters, registers);

                    if (result != null) {
                        return result;
                    }

                    break;
                // Como jnz altera a ordem de execução do buffer então ele não retorna apenas a
                // String contendo algum erro mas também retorna a instrução atual
                case "jnz":
                    JnzResult jnzResult = instructions.jnz(commandBuffer, current, parameters, registers);

                    if (jnzResult.hasError()) {
                        return jnzResult.error;
                    } else {
                        current = jnzResult.node;
                    }
                    break;

            }

            current = current.getNext();
        } while (current != commandBuffer.getHead());

        return null; // Não retorna nenhum erro quando a execução ocorre normalmente
    }

    public String loadBuffer(String filePath) {

        // Verificações, presumindo que tudo deu certo...
        commandBuffer.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                commandBuffer.append(currentLine);
            }

            // TODO Atualizar o fileName
        } catch (IOException e) {
            return "erro ao ler o arquivo " + filePath + ".";
        }

        return null;
    }

    public String saveBuffer(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(commandBuffer.toString());
            bw.newLine();
        } catch (IOException e) {
            return "erro ao salvar o arquivo " + ((filePath == "") ? " sem nome" : filePath) + ".";
        }

        return null;
    }

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
