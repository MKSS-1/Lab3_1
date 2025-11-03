import util.ConsoleIO;

// Only for test purposes
void main() {
    int input = 0;
    while (input != -1) {
        ConsoleIO.print("Enter text: ");
        ConsoleIO.println("Input was:" + ConsoleIO.readString());
        ConsoleIO.print("Enter float: ");
        ConsoleIO.println("Input was:" + ConsoleIO.readFloat());
        ConsoleIO.print("Enter double: ");
        ConsoleIO.println("Input was:" + ConsoleIO.readDouble());
        ConsoleIO.print("Enter boolean: ");
        ConsoleIO.println("Input was:" + ConsoleIO.readBoolean());
        ConsoleIO.print("Enter integer number (Cancel with -1): ");
        input = ConsoleIO.readInt();
        ConsoleIO.println("Input was: " + input);
    }
}
