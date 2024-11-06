/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// TODO verificar ortografia de algum desses comentários...

public class LinkedList<T> {

    private Node<T> head; // Primeiro nó da lista
    private Node<T> tail; // Último nó da lista
    // private Node<T> current; // Nó atual da lista // REVIEW
    private int count; // Quantidade de nós na lista

    public LinkedList() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Adiciona um nó no final da lista encadeada, tornando-o o tail
     *
     * @param value valor a ser armazenado no novo nó
     */
    public void append(T value) {
        Node<T> newNode = new Node<>(value);
        // Se a lista estiver vazia, torna o novo nó head e tail
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.setNext(head);
            head.setPrev(head);
        }
        // Se a lista estiver preenchida, insere o nó no final e
        // torna ele o tail
        else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            newNode.setNext(head);
            head.setPrev(newNode);
            tail = newNode;
        }

        count++;

    }

    /**
     * Adiciona um nó no começo da lista encadeada, tornando-o o head
     *
     * @param value valor a ser armazenado no novo nó
     */
    public void insert(T value) {
        Node<T> newNode = new Node<>(value);
        // Se a lista estiver vazia, torna o novo nó head e tail
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.setNext(head);
            head.setPrev(head);
        }
        // Se a lista estiver preenchida, insere o nó no começo e
        // torna ele o head
        else {
            newNode.setNext(head);
            newNode.setPrev(tail);
            head.setPrev(newNode);
            tail.setNext(newNode);
            head = newNode;
        }

        count++;

    }

    public Node removeHead() {
        // Se a lista estiver vazia retorna null
        if (head == null) {
            return null;
        }

        Node<T> removedNode = head; // Nó que será removido

        // Se houver apenas um nó na lista
        if (head == tail) {
            head = null;
            tail = null;
        }
        // Se houver mais de um nó na lista
        else {
        head = head.getNext();
        }

        --count;

    }

    /**
     * Retorna uma representação em string da lista encadeada.
     *
     * @return uma string representando a lista encadeada
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (head == null) {
            return "[]"; // Lista vazia
        }

        Node<T> current = head;
        sb.append("[");

        // Percorre a lista até o nó que volta ao head
        do {
            sb.append(current.getValue()); // Adiciona o valor do nó
            current = current.getNext();

            if (current != head) {
                sb.append(", "); // Adiciona separador se não for o último nó
            }
        } while (current != head);

        sb.append("]");
        return sb.toString();
    }

}
