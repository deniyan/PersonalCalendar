package bg.tu_varna.sit.f24621709.personalcalendar.command;

public class ExitCommand implements Command{
    @Override
    public void execute(String[] args) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
