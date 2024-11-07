/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

public class Registers {
    private int[] registers = new int[26];

    // Gets the value of a register (a-z)
    public int getRegister(char reg) {
        return registers[reg - 'a'];
    }

    // Sets the value of a register (a-z)
    public void setRegister(char reg, int value) {
        registers[reg - 'a'] = value;
    }

    // Resets all registers to zero (optional)
    public void resetRegisters() {
        for (int i : registers) {
            registers[i] = 0;
        }
    }

    // Helper method to check if a character is a valid register
    public boolean isValidRegister(char reg) {
        return reg >= 'a' && reg <= 'z';
    }
}
