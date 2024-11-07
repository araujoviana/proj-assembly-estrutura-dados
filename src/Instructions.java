// FIXME vai estar bagunçado isso aqui
public class Instructions {
    String[] instructions = { "mov", "inc", "dec", "add", "sub", "mul", "div", "jnz", "out" };

    public String[] getInstructions() {
        return instructions;
    }

    // Verifica se a string fornecida é uma instrução válida
    public boolean isInstruction(String input) {
        for (String instruction : instructions) {
            if (instruction.equals(input)) {
                return true;
            }
        }
        return false;
    }
}
