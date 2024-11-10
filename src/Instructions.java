/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/**
 * Fornece métodos para executar as instruções, além de verificar se as
 * instruções são válidas e validar a sintaxe dos argumentos e executa operações
 * com registradores.
 */
public class Instructions {

    /**
     * Verifica se a string fornecida é uma instrução válida.
     *
     * @param input A string a ser verificada.
     * @return true se a string for uma instrução válida, false caso contrário.
     */
    public static boolean isInstruction(String input) {
        // Lista de todos as instruções
        String[] instructions = { "mov", "inc", "dec", "add", "sub", "mul", "div", "jnz", "out" };
        for (String instruction : instructions) {
            if (instruction.equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida a sintaxe dos argumentos de uma instrução.
     * 
     * @param instruction A instrução que está sendo verificada
     * @param arguments   Os argumentos a serem validados
     * @return Uma string de erro se os argumentos forem inválidos, ou null se forem
     *         válidos
     */
    public static String validateSyntax(String instruction, String arguments) {
        String[] parts = arguments.split(" ");

        switch (instruction) {
            // Essas instruções exigem dois argumentos: um registrador e um número ou um
            // registrador
            case "mov":
            case "add":
            case "sub":
            case "mul":
            case "div":
            case "jnz":
                if (parts.length != 2) {
                    return "a instrução " + instruction + " precisa de dois argumentos válidos.";
                }
                if (!isValidRegister(parts[0])) {
                    return "o primeiro argumento da instrução " + instruction
                            + " deve ser um registrador válido (a-z).";
                }
                if (!isValidRegisterOrNumber(parts[1])) {
                    return "o segundo argumento da instrução " + instruction
                            + " deve ser um número ou registrador válido.";
                }
                break;

            // Essas instruções exigem apenas um argumento: um registrador
            case "inc":
            case "dec":
            case "out":
                if (parts.length != 1) {
                    return "a instrução " + instruction + " precisa de um argumento válido.";
                }
                if (!isValidRegister(parts[0])) {
                    return "o argumento da instrução " + instruction + " deve ser um registrador válido (a-z).";
                }
                break;
            default:
                return "instrução " + instruction + " desconhecida.";
        }

        return null; // Se nenhum erro, retorna null
    }

    /**
     * Verifica se o argumento é um registrador válido (letra de 'a' a 'z').
     *
     * @param param O argumento a ser verificado.
     * @return true se o argumento for um registrador válido, false caso contrário.
     */
    public static boolean isValidRegister(String param) {
        return param.length() == 1 && param.matches("[a-z]");
    }

    /**
     * Verifica se o argumento é um número inteiro válido.
     *
     * @param param O argumento a ser verificado.
     * @return true se o argumento for um número inteiro, false caso contrário.
     */
    public static boolean isValidNumber(String param) {
        try {
            Integer.parseInt(param);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica se o argumento é um número ou um registrador válido.
     *
     * @param param O argumento a ser verificado.
     * @return true se o argumento for um número ou um registrador válido, false
     *         caso contrário.
     */
    public static boolean isValidRegisterOrNumber(String param) {
        return isValidRegister(param) || isValidNumber(param);
    }

    /**
     * Executa a instrução de adição entre o valor de um registrador e um
     * segundo valor (registrador ou número).
     * O resultado é armazenado no registrador especificado como primeiro argumento.
     *
     * @param arguments A string contendo os argumentos da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String add(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do primeiro argumento (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Verifica se o segundo argumento é um número ou um registrador
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

    /**
     * Executa a instrução de subtração entre o valor de um registrador e um
     * segundo valor (registrador ou número).
     * O resultado é armazenado no registrador especificado como primeiro argumento.
     *
     * @param arguments A string contendo os argumentos da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String sub(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do primeiro argumento (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Verifica se o segundo argumento é um número ou um registrador
        int val2;
        if (isValidNumber(paramParts[1])) {
            // Se for um número, converte diretamente para inteiro
            val2 = Integer.parseInt(paramParts[1]);
        } else {
            // Se não for um número, obtém o valor do registrador
            val2 = registers.getRegisterValue(paramParts[1].charAt(0));
        }

        // Realiza a subtração e armazena o resultado no primeiro registrador
        int result = val1 - val2;
        registers.setRegisterValue(paramParts[0].charAt(0), result); // Atualiza o valor no primeiro registrador

        return null;
    }

    /**
     * Executa a instrução de multiplicação entre o valor de um registrador e um
     * segundo valor (registrador ou número).
     * O resultado é armazenado no registrador especificado como primeiro argumento.
     *
     * @param arguments A string contendo os argumentos da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String mul(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do primeiro argumento (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Verifica se o segundo argumento é um número ou um registrador
        int val2;
        if (isValidNumber(paramParts[1])) {
            // Se for um número, converte diretamente para inteiro
            val2 = Integer.parseInt(paramParts[1]);
        } else {
            // Se não for um número, obtém o valor do registrador
            val2 = registers.getRegisterValue(paramParts[1].charAt(0));
        }

        // Realiza a multiplicação e armazena o resultado no primeiro registrador
        int result = val1 * val2;
        registers.setRegisterValue(paramParts[0].charAt(0), result); // Atualiza o valor no primeiro registrador

        return null;
    }

    /**
     * Executa a instrução de divisão (de inteiros) entre o valor de um registrador
     * e um segundo valor (registrador ou número).
     * O resultado é armazenado no registrador especificado como primeiro argumento.
     *
     * @param arguments A string contendo os argumentos da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String div(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do primeiro argumento (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Verifica se o segundo argumento é um número ou um registrador
        int val2;
        if (isValidNumber(paramParts[1])) {
            // Se for um número, converte diretamente para inteiro
            val2 = Integer.parseInt(paramParts[1]);
        } else {
            // Se não for um número, obtém o valor do registrador
            val2 = registers.getRegisterValue(paramParts[1].charAt(0));
        }

        try {

            // Realiza a divisão e armazena o resultado no primeiro registrador
            int result = val1 / val2;
            registers.setRegisterValue(paramParts[0].charAt(0), result); // Atualiza o valor no primeiro registrador
        } catch (ArithmeticException e) {
            return "operação " + paramParts[0] + " / " + paramParts[1] + "  é uma divisão por zero.";
        }

        return null;
    }

    /**
     * Executa a instrução de incremento no registrador.
     * O resultado é armazenado no próprio registrador.
     *
     * @param arguments A string contendo o argumento da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String inc(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do primeiro argumento (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Incrementa no primeiro registrador
        val1++;

        registers.setRegisterValue(paramParts[0].charAt(0), val1); // Atualiza o valor no primeiro registrador

        return null;
    }

    /**
     * Executa a instrução de decremento no registrador.
     * O resultado é armazenado no próprio registrador.
     *
     * @param arguments A string contendo o argumento da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String dec(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do primeiro argumento (sempre registrador)
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Decrementa no primeiro registrador
        val1--;

        registers.setRegisterValue(paramParts[0].charAt(0), val1); // Atualiza o valor no primeiro registrador

        return null;
    }

    /**
     * Executa a instrução de movimentação entre um registrador de origem ou
     * número e um registrador de destino. O valor do registrador de origem ou
     * número é armazenado no registrador especificado como primeiro argumento.
     *
     * @param arguments A string contendo os argumentos da instrução
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String mov(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o registrador de destino como char
        char regDestino = paramParts[0].charAt(0);

        // Verifica se o segundo argumento é um número ou um registrador
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

    /**
     * Executa a instrução de saída que imprime o valor armazenado em um registrador
     * específico.
     *
     * @param arguments A string contendo o registrador cujo valor será exibido.
     * @param registers A instância que gerencia os valores dos registradores.
     * @return null se a operação for bem-sucedida, ou uma string de erro se não
     *         for.
     */
    public static String out(String arguments, Registers registers) {
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do registrador especificado
        char reg = paramParts[0].charAt(0);
        int valor = registers.getRegisterValue(reg);

        // Imprime o valor do registrador
        System.out.println(valor);

        return null;
    }

    /**
     * Executa a instrução de jump não-zero, que altera a execução do buffer
     * de comandos para a linha especificada, se o valor do registrador não for
     * zero.
     *
     * @param commandBuffer A lista de comandos do buffer.
     * @param current       O comando atual.
     * @param arguments     A string contendo os argumentos da instrução, incluindo
     *                      o registrador de verificação e o valor ou registrador
     *                      alvo.
     * @param registers     A instância que gerencia os valores dos registradores.
     * @return Um objeto específico JnzResult contendo o novo nó de execução ou uma
     *         mensagem de erro caso a operação seja inválida
     */
    public static JnzResult jnz(LinkedList<String> commandBuffer, Node<String> current, String arguments,
            Registers registers) {

        // Divide os argumentos em partes, onde o primeiro é o registrador e o segundo é
        // o alvo de comparação
        String[] paramParts = arguments.split(" ");

        // Obtém o valor do registrador especificado no primeiro argumento
        int val1 = registers.getRegisterValue(paramParts[0].charAt(0));

        // Se o valor do registrador for zero, não realiza o jump e retorna o nó atual
        if (val1 == 0) {
            return new JnzResult(current.getNext(), null);
        }

        // Obtém o segundo valor que pode ser um número ou o valor de outro registrador
        int val2;
        if (isValidNumber(paramParts[1])) {
            val2 = Integer.parseInt(paramParts[1]);
        } else {
            val2 = registers.getRegisterValue(paramParts[1].charAt(0));
        }

        Node<String> newCurrent = commandBuffer.getHead();

        // Itera pela lista de comandos até encontrar um que comece com o valor de val2
        do {
            if (newCurrent.getValue().startsWith(Integer.toString(val2))) {
                break;
            }
            newCurrent = newCurrent.getNext();
        } while (newCurrent != commandBuffer.getHead());

        // Se não encontrar um comando correspondente, retorna um erro
        if (newCurrent == null) {
            return new JnzResult(null, "posição de jump inválida");
        }

        // Se encontrar um comando válido, retorna a nova instrução atual
        return new JnzResult(newCurrent, null);
    }

}
