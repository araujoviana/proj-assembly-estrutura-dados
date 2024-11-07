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

    public String insert(Buffer buffer, int lineNumber, String instruction, String parameters) {
        // Verifica se o número da linha é positivo
        if (lineNumber <= 0) {
            return "Erro: o número da linha deve ser positivo.";
        }

        // Verifica se a instrução fornecida é válida
        if (!instructions.isInstruction(instruction)) {
            return "Erro: instrução inválida.";
        }

        return buffer.insertLine(lineNumber, instruction, parameters);
    }
}
