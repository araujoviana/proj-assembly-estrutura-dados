// FIXME vai estar bagunçado isso aqui
public class Commands {
    String[] commands = { "load", "list", "run", "ins", "del", "save", "exit" };

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
}