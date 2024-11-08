/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

public class Instructions {
    // List of valid instruction mnemonics
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

    /**
     * Valida a sintaxe dos parâmetros de uma instrução.
     * 
     * @param instruction A instrução que está sendo verificada
     * @param parameters  Os parâmetros a serem validados
     * @return Uma string de erro se os parâmetros forem inválidos, ou null se forem
     *         válidos
     */
    public String validateSyntax(String instruction, String parameters) {
        String[] parts = parameters.split(" ");

        switch (instruction) {
            case "mov":
            case "add":
            case "sub":
            case "mul":
            case "div":
                // Essas instruções exigem dois parâmetros: um registrador e um número ou um
                // registrador
                if (parts.length != 2) {
                    return "Erro: A instrução " + instruction + " precisa de dois parâmetros válidos.";
                }
                if (!isValidRegister(parts[0])) {
                    return "Erro: O primeiro parâmetro da instrução " + instruction
                            + " deve ser um registrador válido (a-z).";
                }
                if (!isValidRegisterOrNumber(parts[1])) {
                    return "Erro: O segundo parâmetro da instrução " + instruction
                            + " deve ser um número ou registrador válido.";
                }
                break;

            case "inc":
            case "dec":
            case "out":
                // Essas instruções exigem apenas um parâmetro: um registrador
                if (parts.length != 1) {
                    return "Erro: A instrução " + instruction + " precisa de um parâmetro válido.";
                }
                if (!isValidRegister(parts[0])) {
                    return "Erro: O parâmetro da instrução " + instruction + " deve ser um registrador válido (a-z).";
                }
                break;

            case "jnz":
                // "jnz" exige um registrador e um número ou um registrador
                if (parts.length != 2) {
                    return "Erro: A instrução jnz precisa de dois parâmetros válidos.";
                }
                if (!isValidRegister(parts[0])) {
                    return "Erro: O primeiro parâmetro da instrução jnz deve ser um registrador válido (a-z).";
                }
                if (!isValidRegisterOrNumber(parts[1])) {
                    return "Erro: O segundo parâmetro da instrução jnz deve ser um número ou registrador válido.";
                }
                break;

            default:
                return "Erro: Instrução desconhecida.";
        }

        return null; // Se nenhum erro, retorna null (sintaxe válida)
    }

    // Verifica se o parâmetro é um registrador válido (de 'a' a 'z')
    // TODO renomear!!
    private boolean isValidRegister(String param) {
        return param.length() == 1 && param.matches("[a-z]");
    }

    // Verifica se o parâmetro é um número ou um registrador válido
    private boolean isValidRegisterOrNumber(String param) {
        return isValidRegister(param) || isValidNumber(param);
    }

    // Verifica se o parâmetro é um número válido (inteiro)
    private boolean isValidNumber(String param) {
        try {
            Integer.parseInt(param);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String add(String parameters, Registers registers) {
        String[] paramParts = parameters.split(" ");

        // Obtém o valor do primeiro parâmetro (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Verifica se o segundo parâmetro é um número ou um registrador
        int val2;
        if (isValidNumber(paramParts[1])) {
            // Se for um número, converte diretamente para inteiro
            val2 = Integer.parseInt(paramParts[1]);
        } else {
            // Se não for um número, obtém o valor do registrador
            val2 = registers.getRegisterValue(paramParts[1].charAt(0));
        }

        // Realiza a soma e armazena o resultado no primeiro registrador
        int result = val1 + val2;
        registers.setRegisterValue(paramParts[0].charAt(0), result); // Atualiza o valor no primeiro registrador

        return null;
    }

    public String mov(String parameters, Registers registers) {
        String[] paramParts = parameters.split(" ");

        // Obtém o registrador de destino como char
        char regDestino = paramParts[0].charAt(0);

        // Verifica se o segundo parâmetro é um número ou um registrador
        int valor;
        if (isValidNumber(paramParts[1])) {
            // Se for um número, converte diretamente para inteiro
            valor = Integer.parseInt(paramParts[1]);
        } else {
            // Se não for um número, obtém o valor do registrador de origem
            char regOrigem = paramParts[1].charAt(0);
            valor = registers.getRegisterValue(regOrigem);
        }

        // Move o valor para o registrador de destino
        registers.setRegisterValue(regDestino, valor);

        return null;
    }

    public String out(String parameters, Registers registers) {
        String[] paramParts = parameters.split(" ");

        // Obtém o valor do registrador especificado
        char reg = paramParts[0].charAt(0);
        int valor = registers.getRegisterValue(reg);

        // Imprime o valor do registrador
        System.out.println(valor);

        return null;
    }

    public Object[] jnz(Node<String> current, String parameters, Registers registers) {
        String[] paramParts = parameters.split(" ");

        // Contém a instrução atual e a mensagem de erro
        Object[] jnzResult = new Object[2];

        // Obtém o valor do primeiro parâmetro (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Se x não for zero então não faz o pulo
        if (val1 != 0) {
            //
        }

        // Verifica se o segundo parâmetro é um número ou um registrador
        int val2;
        if (isValidNumber(paramParts[1])) {
            // Se for um número, converte diretamente para inteiro
            val2 = Integer.parseInt(paramParts[1]);
        } else {
            // Se não for um número, obtém o valor do registrador
            val2 = registers.getRegisterValue(paramParts[1].charAt(0));
        }

        return null;
    }

}
