import java.sql.Connection;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExpenseInput implements Receiver {
    private Pattern pattern;
    public ExpenseInput(){
        pattern = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])-[0-9]+-[0-9]+\\s[0-9]+\\.[0-9]+\\s[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
    }

    public void doAction() {
        System.out.println("Enter each expense in the format '[MM-DD-YYYY] xx.xx [tag]', enter q to quit");
        Scanner sc = new Scanner(System.in);

        String input = "";
        System.out.print("Enter: ");
        do{
            if(sc.hasNextLine()){
                input = sc.nextLine();
                if(!validateInput(input)){
                    System.out.println("Invalid input, please try again");
                }
                else{
                    SQLiteJDBC.createConnection();

                    System.out.println("Item written to db");
                }
                System.out.print("Enter: ");
            }
        }while(!(input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")));

    }

    public boolean validateInput(String input){
        final Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }



}
