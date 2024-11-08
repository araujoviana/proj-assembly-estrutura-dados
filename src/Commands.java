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

        // HACK evite prints fora do displayMessage

        System.out.println(commandBuffer.toString());

        return null;

    }
}
