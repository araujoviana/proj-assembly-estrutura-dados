/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////
public class Node<T> {

	// Valor armazenado no nó
	private T value;

	private Node<T> next; // Próximo nó
	private Node<T> prev; // Nó anterior

	// REVIEW Múltiplos construtores não parecem ser necessários pro nosso caso
	////////////////////////////////////////////////
	// public Node() { //
	// this(-1); //
	// } //
	// //
	// public Node(int value, Node next) { //
	// this.value = value; //
	// this.next = next; //
	// } //
	////////////////////////////////////////////////

	public Node(T value) {
		this.value = value;
		this.next = null;
		this.prev = null;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
   @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", next=" + (next != null ? next.getValue() : "null") +
                ", prev=" + (prev != null ? prev.getValue() : "null") +
                '}';
    }

}
