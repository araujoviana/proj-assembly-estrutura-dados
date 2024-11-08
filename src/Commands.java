/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// FIXME vai estar bagunçado isso aqui
public class Commands {
    String[] commands = { "load", "list", "run", "ins", "del", "save", "exit" };
    Instructions instructions;

    public Commands() {
        instructions = new Instructions();
    }

    public String[] getCommands() {
        return commands;
    }

    // Verifica se a string fornecida é uma instrução válida
    public boolean isCommand(String input) {
        for (String command : commands) {
            if (command.equals(input)) {
                return true;
            }
        }
        return false;
    }

    // As strings retornadas são erros
    // Se comunica diretamente com o buffer para adicionar as linhas
    public String insert(Buffer buffer, int lineNumber, String instruction, String parameters) {
        // Verifica se o número da linha é positivo
        if (lineNumber <= 0) {
            return "o número da linha deve ser positivo.";
        }

        // Verifica se a instrução fornecida é válida
        if (!instructions.isInstruction(instruction)) {
            return "instrução inválida.";
        }

        return buffer.insertLine(lineNumber, instruction, parameters);
    }

    public String run(Buffer buffer) {
        LinkedList<String> commandBuffer = buffer.getCommandBuffer();

        // Verifica se há código carregado no buffer
        if (commandBuffer.isEmpty()) {
            return "nenhum código carregado na memória.";
        }

        return buffer.evaluateBuffer();
    }

    public String list(Buffer buffer) {
        LinkedList<String> commandBuffer = buffer.getCommandBuffer();

        // Verifica se há código carregado no buffer
        if (commandBuffer.isEmpty()) {
            return "nenhum código carregado na memória.";
        }

        StringBuilder sb = new StringBuilder();
        Node<String> currentNode = commandBuffer.getHead();
        int count = 0;

        // Percorre a lista encadeada e adiciona as 20 primeiras linhas
        do {
            sb.append(currentNode.getValue()).append("\n");
            currentNode = currentNode.getNext();
            count++;
        } while (currentNode != commandBuffer.getHead() && count < 20);

        // Retorna as primeiras 20 linhas ou a quantidade disponível
        System.out.println(sb.toString());

        return null;
    }

    public String delete(Buffer buffer, Integer[] lineNumbers) {

        if (lineNumbers.length == 2) {
            return buffer.removeInterval(lineNumbers[0], lineNumbers[1]);
        } else if (lineNumbers.length == 1) {
            return buffer.removeLine(lineNumbers[0]);
        } else {
            return "comando delete precisa de dois inteiros válidos.";
        }

    }

    public String load(Buffer buffer, String filePath) {

        // TODO polir essa função

        String result = buffer.loadBuffer(filePath);

        return result;
    }

    public String save(Buffer buffer, String savedFilePath) {

        // TODO polir essa função

        String result = buffer.saveBuffer(savedFilePath);

        return result;
    }

    public String exit(Buffer buffer, String fileName) {
        // Verificações

        return null;
    }


}
