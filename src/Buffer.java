/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// REVIEW descrição
// Age como um buffer que armazena o código atualmente carregado no REPL 
public class Buffer {

    // Armazena todos os comandos carregados
    private LinkedList<String> commandBuffer;
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
            return null; // Não retorna nada se for uma nova linha adicionada
        }

        // Percorre a lista para encontrar a posição correta de inserção
        do {
            String[] parts = current.getValue().split(" ", 2);
            int existingLineNumber = Integer.parseInt(parts[0]);

            // Verifica se a linha já existe
            if (existingLineNumber == lineNumber) {
                // Se a linha já existir, captura a linha original
                String originalLine = current.getValue();

                // Substitui a linha existente
                current.setValue(line);

                // Retorna a mensagem no formato solicitado
                return "Linha:\n" + originalLine + "\nAtualizada para:\n" + line;
            }

            // Insere antes de um número de linha maior, mantendo a ordem
            if (existingLineNumber > lineNumber) {
                commandBuffer.insertBefore(current, line);
                return null; // Não retorna nada quando a linha é nova
            }

            current = current.getNext();
        } while (current != commandBuffer.getHead());

        // Caso nenhuma posição de linha maior seja encontrada, insere no final
        commandBuffer.append(line);
        return null; // Não retorna nada quando a linha é nova
    }

    public String evaluateBuffer() {
        // Check if the WHOLE BUFFER is good to go!
        // ...

        Node<String> current = commandBuffer.getHead();
        Instructions instructions = new Instructions();
        
        do {
            // Divide a linha em partes (número da linha, instrução e parâmetros)
            String[] parts = current.getValue().split(" ", 3);
            String lineNumber = parts[0];   // Número da linha (não utilizado diretamente aqui)
            String instruction = parts[1];   // Instrução (ex: 'mov', 'add')
            String parameters = parts[2];    // Parâmetros da instrução

            // Verifica se a instrução é válida
            if (!instructions.isInstruction(instruction)) {
                return "Erro: Instrução inválida na linha " + lineNumber + ": " + instruction;
            }
            // Valida a sintaxe dos parâmetros da instrução
            String validationError = instructions.validateSyntax(instruction, parameters);
            if (validationError != null) {
                return "Erro na linha " + lineNumber + ": " + validationError;
            }
            // Avaliar !
            

            current = current.getNext();
        } while (current != commandBuffer.getHead());

        return null; // Não retorna nenhum erro quando a execução ocorre normalmente
    }

    public getCommandBuffer() {
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
