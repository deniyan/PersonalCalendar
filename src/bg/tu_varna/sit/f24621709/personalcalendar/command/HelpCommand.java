package bg.tu_varna.sit.f24621709.personalcalendar.command;

public class HelpCommand implements Command{
    @Override
    public void execute(String[] args) {

        System.out.println("The following commands are supported:");
        System.out.println("open <file> \topens <file>");
        System.out.println("close \t\t\tcloses currently opened file");
        System.out.println("save \t\t\tsaves the currently open file");
        System.out.println("save as <file> \tsaves the currently open file in <file>");
        System.out.println("help \t\t\tprints this information");
        System.out.println("exit \t\t\texits the program");

        System.out.println("\nbook \t\t\t<date> <starttime> <endtime> <name> <note>");
        System.out.println("unbook \t\t\t<date> <starttime> <endtime>");
        System.out.println("agenda \t\t\t<date>");
        System.out.println("change \t\t\t<date> <starttime> <option> <newvalue>");
        System.out.println("find \t\t\t<string>");
        System.out.println("holiday \t\t<date>");
        System.out.println("busydays \t\t<from> <to>");
        System.out.println("findslot \t\t<fromdate> <hours>");
        System.out.println("findslotwith \t<fromdate> <hours> <calendar>");
        System.out.println("merge \t\t\t<calendar>");
    }
}
