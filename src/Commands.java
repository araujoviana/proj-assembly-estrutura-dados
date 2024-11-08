/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/**
 * Gerencia comandos para manipulação do buffer de código e
 * Realiza validações e operações no buffer de acordo com o comando fornecido.
 */
public class Commands {

    /**
     * Verifica se a string fornecida é um comando válido.
     * 
     * @param input a entrada do usuário
     * @return true se a entrada for um comando válido, e
     *         false se for inválido
     */
    public boolean isCommand(String input) {
        String[] commands = { "load", "list", "run", "ins", "del", "save", "exit" };
        for (String command : commands) {
            if (command.equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Insere uma linha de código no buffer na posição especificada, com a instrução
     * e os parâmetros fornecidos.
     * 
     * @param buffer      o buffer onde a linha de código será inserida
     * @param lineNumber  o número da linha onde a instrução deve ser inserida
     * @param instruction a instrução a ser inserida
     * @param arguments   os argumentos associados à instrução
     * @return uma mensagem de erro se a entrada conter erros, caso contrário,
     *         retorna a mensagem do resultado da inserção no buffer
     */
    public String insert(Buffer buffer, int lineNumber, String instruction, String arguments) {

        // Verifica se o número da linha é positivo
        if (lineNumber <= 0) {
            return "o número da linha deve ser positivo.";
        }

        // Verifica se a instrução fornecida é válida
        if (!Instructions.isInstruction(instruction)) {
            return "instrução inválida.";
        }

        // insere a linha no buffer e retorna o resultado
        return buffer.insertLine(lineNumber, instruction, arguments);
    }

    /**
     * Executa o código carregado no buffer.
     * 
     * @param buffer o buffer que contém o código a ser executado
     * @return uma mensagem de erro se o buffer conter erros, caso contrário,
     *         retorna o resultado da execução do código no buffer
     */
    public String run(Buffer buffer) {
        // Lista encadeada contendo o conteúdo do buffer
        LinkedList<String> commandBuffer = buffer.getCommandBuffer();

        // Verifica se há código carregado no buffer
        if (commandBuffer.isEmpty()) {
            return "nenhum código carregado na memória.";
        }

        // Executa o código no buffer e retorna o resultado
        return buffer.evaluateBuffer();
    }

    /**
     * Imprime as linhas de código carregadas no buffer.
     * 
     * @param buffer o buffer que contém o código a ser listado
     * @return uma mensagem de erro se o buffer conter erros, caso contrário,
     *         retorna null após exibir as linhas no console
     */
    public String list(Buffer buffer) {
        LinkedList<String> commandBuffer = buffer.getCommandBuffer();

        // Verifica se há código carregado no buffer
        if (commandBuffer.isEmpty()) {
            return "nenhum código carregado na memória.";
        }

        // Constrói a lista de comandos a ser exibida
        StringBuilder sb = new StringBuilder();
        Node<String> currentNode = commandBuffer.getHead();
        int count = 0;

        // Percorre a lista encadeada e adiciona 20 linhas
        do {
            sb.append(currentNode.getValue()).append("\n");
            currentNode = currentNode.getNext();
            count++;
        } while (currentNode != commandBuffer.getHead() && count < 20);

        // Retorna as primeiras 20 linhas ou a quantidade disponível
        return sb.toString();
    }

    /**
     * Remove uma ou um intervalo de linhas do buffer, com base nos números
     * fornecidos.
     * 
     * @param buffer      o buffer de código onde as linhas serão removidas
     * @param lineNumbers um array de inteiros com um ou dois números de linha a
     *                    serem removidos
     * @return uma mensagem de erro se o um erro for gerado caso contrário, retorna
     *         a mensagem do resultado da remoção das linhas
     */
    public String delete(Buffer buffer, Integer[] lineNumbers) {

        // Verifica se o array contém dois números, indicando um intervalo de linhas
        if (lineNumbers.length == 2) {
            return buffer.removeInterval(lineNumbers[0], lineNumbers[1]);
        } else if (lineNumbers.length == 1) {
            // Array contém um número, indicando uma única linha para remoção
            return buffer.removeLine(lineNumbers[0]);
        } else {
            return "comando delete precisa de dois inteiros válidos.";
        }

    }

    /**
     * Carrega o código de um arquivo para o buffer.
     * 
     * @param buffer   o buffer onde o código será carregado
     * @param filePath o caminho do arquivo de onde o código será carregado
     * @return o resultado da tentativa de carregar o arquivo
     */
    public String load(Buffer buffer, String filePath) {

        // TODO polir essa função

        String result = buffer.loadBuffer(filePath);

        return result;
    }

    /**
     * Salva o código do buffer para um arquivo.
     * 
     * @param buffer        o buffer onde o código será salvo
     * @param savedFilePath o caminho do arquivo de onde o código será salvo
     * @return o resultado da tentativa de salvar o arquivo
     */
    public String save(Buffer buffer, String savedFilePath) {

        // TODO polir essa função

        String result = buffer.saveBuffer(savedFilePath);

        return result;
    }

    /**
     * Finaliza a execução do programa, realizando verificações relacionadas ao
     * arquivo.
     * 
     * @param buffer   o buffer de código
     * @param fileName o nome do arquivo associado à operação de saída
     * @return null se o programa conseguir sair normalmente, caso contrário,
     *         retorna uma mensagem contendo alguma mensagem
     */
    public String exit(Buffer buffer, String fileName) {
        // Verificações

        return null;
    }

}
