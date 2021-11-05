import java.util.Scanner;

public class Main{
    public static void main(String args[]){
        CommandLineMenu clm = new CommandLineMenu();
        int choice = -1;
        final Scanner sc = new Scanner(System.in);
        while(true){
            clm.show();
            if(sc.hasNextInt()){
                choice = sc.nextInt();
                clm.getMenuOption(choice).execute();
            }


            //clm.clearConsole();
        }

    }
}
