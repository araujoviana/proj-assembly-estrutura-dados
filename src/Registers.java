public class Registers {
    private LinkedList<RegisterNode> registerList;

    public Registers() {
        registerList = new LinkedList<>();

        for (char reg = 'a'; reg <= 'z'; reg++) {
            registerList.append(new RegisterNode(reg, 0));
        }
    }

    /**
     * Retrieves the value of a specific register by name.
     *
     * @param reg the name of the register (e.g., 'a' to 'z')
     * @return the value of the register
     * @throws IllegalArgumentException if the register name is invalid
     */
    public int getRegisterValue(char reg) {
        Node<RegisterNode> current = registerList.getHead();

        // Traverse circularly to find the register
        do {
            if (current.getValue().getName() == reg) {
                return current.getValue().getValue();
            }
            current = current.getNext();
        } while (current != registerList.getHead());

        // TODO Substitua
        return 0;

    }

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
