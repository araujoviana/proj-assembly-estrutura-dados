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
            displayError("entrada está vazia.");
            return false;
        }
        String[] splitInput = input.split(" ");

        String potentialCommand = splitInput[0];

        // Verifica se o primeiro termo é um comando válido
        if (!commands.isCommand(potentialCommand)) {
            displayError("O comando fornecido não é válido.");
            return false;
        }

        // Verifica se existe um comando para verificar
        if (splitInput.length < 2) {
            displayError("entrada não contém parâmetros do comando.");
            return false;
        }

        return true;
    }

    public void evaluateInput(String input) {
        // REVIEW precisa ser assim?

    }

    public void displayError(String errorMessage) {
        System.out.println("Erro: " + errorMessage);
    }

}
