package automatedtellermachine;

import java.util.Scanner; // program uses Scanner to obtain user input

public class Keypad {

    private Scanner input; // reads data from the command line

    public Keypad() {
        input = new Scanner(System.in);
    }

    public int getInput() {
        return input.nextInt(); // we assume that user enters an integer
    }
}
