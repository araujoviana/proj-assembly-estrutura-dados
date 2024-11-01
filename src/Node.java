/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

public class Node {
	
	// Parte dos dados do nó.
	private int value;
	private char ch;

	// Parte da referência para o próximo nó.
	private Node next;
	
	public Node() { this(-1, '\0'); }
	public Node(int value, char ch) { this(value, ch, null); }
	public Node(int value, char ch, Node next) {
		this.value = value;
		this.ch = ch;
		this.next = next;
	}
	
	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; }
	
	public char getChar() { return ch; }
	public void setChar(char ch) { this.ch = ch; }
	
	public Node getNext() { return next; }
	public void setNext(Node next) { this.next = next; }
	
	@Override
	public String toString() {
		return super.toString() + " - value: " + value
				+ ", char: " + ch
				+ ", next: ("
				+ (next != null ? (next.getValue() + "," + next.getChar()) : "null")
				+ ")";
	}

}
