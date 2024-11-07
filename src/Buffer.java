/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// REVIEW descrição
// Age como um buffer que armazena o código atualmente carregado no REPL 
public class Buffer {

    // Armazena todos os comandos carregados
    private LinkedList<String> commandBuffer;

    public Buffer() {
        commandBuffer = new LinkedList<>();
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
