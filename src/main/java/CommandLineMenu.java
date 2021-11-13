import java.util.ArrayList;
import java.util.List;

public class CommandLineMenu implements IDisplay{
    private List<MenuOption> menuOptions;

    public CommandLineMenu(){
        menuOptions = new ArrayList<MenuOption>();
        menuOptions.add(new MenuOption("Write expense to db", new ExpenseInput()));
        menuOptions.add(new MenuOption("Generate expense csv report", new CSVWriter()));
        menuOptions.add(new MenuOption("Dump table rows", ()->{
            SQLiteJDBC.dumpRows();
        }));
        menuOptions.add(new MenuOption("Show totals per month by tag", () ->{
            SQLiteJDBC.showTotalPerMonthByTag();
        }));
        menuOptions.add(new MenuOption("Quit", ()->{
            Runtime.getRuntime().exit(0);
        }));

    }
    public static void greeting(){
        System.out.println("Welcome to xPenz, " +
                "the premiere personal finance software\n");
        System.out.println("Please select from the menu options: ");
        return;
    }


    public  void clearConsole(){
        //System.out.println("Clearing console");
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void show() {
        greeting();
        for(int i = 0; i < menuOptions.size(); i++){
            System.out.println(i + ". " + menuOptions.get(i).getMenuName());
        }
    }

    public MenuOption getMenuOption(int option){
        return this.menuOptions.get(option);
    }
}
