/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// TODO descrição dessa classe
public class Repl {

    Commands commands = new Commands();
    Instructions instructions = new Instructions();

    /**
     * Converte a entrada inicial para maíusculo e remove todos os espaços
     * desnecessários
     *
     * @param input entrada não formatada como "10 MoV A 5 "
     * @return entrada formatada tipo "10 mov a 5"
     */
    public String formatInput(String input) {
        return input.toLowerCase().replaceAll("\\s+", " ").trim();
    }

    // TODO mudar isso pra retornar a string de erro
    /**
     * Verifica se a entrada do usuário é válida para ser avaliada pelo REPL,
     * isso não quer dizer que ele não cause erros dentro da funcionalidade interna
     * dos comandos, essa é apenas uma verificação mínima, os erros de cada
     * instrução/comando são tratados em seus métodos.
     *
     * @param input entrada formatada
     * @return true se o comando for válido e false se for inválido
     */
    public boolean validateInput(String input) {

        // Verifica se o comando está vazio
        if (input.isEmpty()) {
            return false;
        }
        String[] splitInput = input.split(" ");

        // Verifica se o comando começa com um número
        try {
            Integer.parseInt(splitInput[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        // Verifica se existe um comando para verificar
        if (splitInput.length < 2) {
            return false;
        }

        // Verifica se a segunda parte da entrada é um comando ou instrução válida
        String secondPart = splitInput[1];
        boolean foundInCommands = false;
        for (String command : commands.getCommands()) {
            if (command.equals(secondPart)) {
                foundInCommands = true;
                break;
            }
        }
        boolean foundInInstructions = false;
        for (String instruction : instructions.getInstructions()) {
            if (instruction.equals(secondPart)) {
                foundInInstructions = true;
                break;
            }
        }

        // Se a segunda parte não for encontrada em nenhum dos comandos e instruções
        // então ela é inválida
        if (!foundInCommands && !foundInInstructions) {
            return false;
        }

        return true;
    }

}
