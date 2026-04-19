package bg.tu_varna.sit.f24621709.personalcalendar.command;

public class HelpCommand implements Command{
    @Override
    public void execute(String[] args) {

        System.out.println("The following commands are supported:");
        System.out.println("open <file> opens <file>");
        System.out.println("close closes currently opened file");
        System.out.println("save saves the currently open file");
        System.out.println("save as <file> saves the currently open file in <file>");
        System.out.println("help prints this information");
        System.out.println("exit exits the program");
    }
}
