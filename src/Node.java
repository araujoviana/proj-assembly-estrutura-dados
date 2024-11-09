/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

/**
 * Nó da lista duplamente encadeada, e cada nó contém um valor genérico.
 */
public class Node<T> {

	private T value; // Valor armazenado no nó

	private Node<T> next; // Próximo nó
	private Node<T> prev; // Nó anterior

	public Node(T value) {
		this.value = value;
		this.next = null;
		this.prev = null;
	}

	/**
	 * Getter para o valor armazenado no nó.
	 *
	 * @return O valor armazenado no nó
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Setter para o valor armazenado no nó.
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * Getter para o nó next da lista encadeada.
	 *
	 * @return O próximo nó
	 */
	public Node<T> getNext() {
		return next;
	}

	/**
	 * Setter para o nó next da lista encadeada.
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}

	/**
	 * Getter para o nó previous da lista encadeada.
	 *
	 * @return O nó anterior
	 */
	public Node<T> getPrev() {
		return prev;
	}

	/**
	 * Setter para o nó previous da lista encadeada.
	 */
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
