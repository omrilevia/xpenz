import java.util.Scanner;

public class ExpenseInput implements Receiver {


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
                    System.out.println("Item written to db");
                }
                System.out.print("Enter: ");
            }
        }while(!(input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")));

    }

    public boolean validateInput(String input){
        return true;
    }



}
