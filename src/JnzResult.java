/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

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
