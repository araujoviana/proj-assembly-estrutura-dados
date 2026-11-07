/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo //
// Enzo Carvalho Pagliarini     //
/////////////////////////////////////////////

/*
 * Resultado da instrução jnz, armazena o nó que será alvo do jump
 * e a mensagem de retorno do método
 */
public class JnzResult {
    public Node<String> node;
    public String error;

    public JnzResult(Node<String> node, String error) {
        this.node = node;
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
