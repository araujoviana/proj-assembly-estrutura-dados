/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/*
 * Nó dos registradores, possui um nome (caractere de 'a' a 'z') e um valor
 * inteiro associado.
 */
public class RegisterNode {
    private char name; // Nome do registrador
    private int value; // Valor associado ao registrador

    public RegisterNode(char name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter para o nome do registrador.
     *
     * @return O nome do registrador
     */
    public char getName() {
        return name;
    }

    /**
     * Getter para o valor do registrador.
     *
     * @return O valor do registrador
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter para o valor armazenado no registrador.
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
