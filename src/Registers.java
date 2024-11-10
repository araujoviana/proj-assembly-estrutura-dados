/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * Gerencia o conjunto de registradores de 'a' a 'z' utilizando uma lista encadeada
 */
public class Registers {
    // A lista encadeada que armazena os registradores usa uma classe especial
    // responsável por armazenar o nome e valor dos registradores
    private LinkedList<RegisterNode> registerList;

    public Registers() {
        registerList = new LinkedList<>();

        /*
         * Adiciona registradores de 'a' a 'z' com valor inicial 0, mas eles não podem
         * ser acessados quando são indefinidos no código (isso é verificado no método
         * evaluateBuffer da classe Buffer)
         */
        for (char reg = 'a'; reg <= 'z'; reg++) {
            registerList.append(new RegisterNode(reg, 0));
        }
    }

    /**
     * Recupera o valor de um registrador específico pelo nome.
     *
     * @param reg o nome do registrador de "a" a "z"
     * @return o valor do registrador
     */
    public int getRegisterValue(char reg) {
        Node<RegisterNode> current = registerList.getHead();

        // Procura o valor pela lista
        do {
            if (current.getValue().getName() == reg) {
                return current.getValue().getValue();
            }
            current = current.getNext();
        } while (current != registerList.getHead());

        return 0;

    }

    /**
     * Define o valor de um registrador específico.
     *
     * @param reg   O nome do registrador
     * @param value O novo valor a ser atribuído ao registrador
     */
    public void setRegisterValue(char reg, int value) {
        Node<RegisterNode> current = registerList.getHead();

        do {
            if (current.getValue().getName() == reg) {
                current.getValue().setValue(value);
                return;
            }
            current = current.getNext();
        } while (current != registerList.getHead());

    }

    /*
     * REVIEW
     *
     * Vou deixar isso aqui, mas se não tiver serventia até o final do projeto, pode
     * deletar! NÃO ADICIONEI DOCSTRING
     */
    public void resetAllRegisters() {
        Node<RegisterNode> current = registerList.getHead();
        if (current == null)
            return;

        do {
            current.getValue().setValue(0);
            current = current.getNext();
        } while (current != registerList.getHead());
    }

    @Override
    public String toString() {
        return registerList.toString();
    }
}
