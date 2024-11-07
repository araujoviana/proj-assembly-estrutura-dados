/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

// TODO verificar ortografia de algum desses comentários...

// Lista duplamente encadeada circular genérica
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

    /**
     * Remove o nó no início da lista (head) e retorna o nó removido.
     *
     * @return o nó removido ou null se a lista estiver vazia
     */
    public Node<T> removeHead() {
        // Se a lista estiver vazia, retorna null
        if (head == null) {
            return null;
        }

        Node<T> removedNode = head; // O nó que será removido

        // Se houver apenas um nó na lista
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // Atualiza o head para o próximo nó
            head = head.getNext();
            head.setPrev(tail); // O antigo tail agora aponta para o novo head
            tail.setNext(head); // O tail agora aponta para o novo head
        }

        --count; // Decrementa a quantidade de nós
        return removedNode; // Retorna o nó removido
    }

    /**
     * Remove o nó no final da lista (tail) e retorna o nó removido.
     *
     * @return o nó removido ou null se a lista estiver vazia
     */
    public Node<T> removeTail() {
        // Se a lista estiver vazia, retorna null
        if (tail == null) {
            return null;
        }

        Node<T> removedNode = tail; // O nó que será removido

        // Se houver apenas um nó na lista
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // Atualiza o tail para o nó anterior
            tail = tail.getPrev();
            tail.setNext(head); // O novo tail aponta para o head
            head.setPrev(tail); // O head aponta para o novo tail
        }

        --count; // Decrementa a quantidade de nós
        return removedNode; // Retorna o nó removido
    }

    /**
     * Remove o primeiro nó que contém o valor especificado.
     * Como o usuário já sabe o valor do nó, ele não é retornado.
     *
     * @param value o valor do nó a ser removido
     * @return true se o nó foi removido, false se o valor não foi encontrado
     */
    public boolean removeNode(T value) {
        // Se a lista estiver vazia, não há nada para remover
        if (head == null) {
            return false;
        }

        Node<T> current = head;

        // Percorre a lista circular até encontrar o valor ou voltar ao head
        do {
            if (current.getValue().equals(value)) {
                // Se o nó encontrado é o único na lista
                if (head == tail) {
                    head = null;
                    tail = null;
                }
                // Se o nó é o head
                else if (current == head) {
                    removeHead();
                }
                // Se o nó é o tail
                else if (current == tail) {
                    removeTail();
                }
                // Nó no meio da lista
                else {
                    Node<T> prevNode = current.getPrev();
                    Node<T> nextNode = current.getNext();

                    prevNode.setNext(nextNode);
                    nextNode.setPrev(prevNode);
                    --count;
                }
                return true; // Valor encontrado e removido
            }
            current = current.getNext();
        } while (current != head);

        return false; // Valor não encontrado
    }

    /**
     * Retorna o nó no início da lista
     *
     * @return o nó no início da lista ou null se a lista estiver vazia
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Retorna o nó no final da lista
     *
     * @return o nó no final da lista ou null se a lista estiver vazia
     */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * Retorna o primeiro nó que contém o valor especificado.
     *
     * @param value o valor do nó a ser encontrado
     * @return o nó que contém o valor especificado ou null se o valor não foi
     *         encontrado
     */
    public Node<T> getNode(T value) {
        // Se a lista estiver vazia, retorna null
        if (head == null) {
            return null;
        }

        Node<T> current = head;

        // Percorre a lista circular até encontrar o valor ou voltar ao head
        do {
            if (current.getValue().equals(value)) {
                return current; // Nó encontrado
            }
            current = current.getNext();
        } while (current != head);

        return null; // Valor não encontrado
    }

    /**
     * Retorna a quantidade de nós na lista.
     *
     * @return o número de nós na lista
     */
    public int getCount() {
        return count;
    }

    /**
     * Verifica se a lista contém o valor especificado.
     *
     * @param value o valor a ser procurado
     * @return true se o valor está na lista, false caso contrário
     */
    public boolean contains(T value) {
        if (head == null) {
            return false;
        }

        Node<T> current = head;
        do {
            if (current.getValue().equals(value)) {
                return true;
            }
            current = current.getNext();
        } while (current != head);

        return false;
    }

    /**
     * Retorna o índice do primeiro nó com o valor especificado.
     *
     * @param value o valor a ser procurado
     * @return o índice do nó ou -1 se o valor não for encontrado
     */
    public int indexOf(T value) {
        if (head == null) {
            return -1;
        }

        Node<T> current = head;
        int index = 0;
        do {
            if (current.getValue().equals(value)) {
                return index;
            }
            current = current.getNext();
            index++;
        } while (current != head);

        return -1; // Valor não encontrado
    }

    /**
     * Verifica se a lista encadeada está vazia
     *
     * @return true se head for nulo, o que significa que não há elementos na lista,
     *         e false se não for nulo
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Desconecta o head e tail da lista encadeada, deixando-a vazia
     */
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Retorna uma representação em string da lista encadeada.
     *
     * @return uma string representando a lista encadeada
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Lista vazia
        if (head == null) {
            return "[]";
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
