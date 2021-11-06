public class MenuOption implements Command {
    private String menuName;
    private Receiver receiver;

    public MenuOption(String menuName){
        this.menuName = menuName;
        this.receiver = null;
    }

    public MenuOption(String menuName, Receiver r){
        this.menuName = menuName;
        this.receiver = r;
    }

    public void setReceiver(Receiver r){
        this.receiver = r;
    }

    public void execute(){
        receiver.doAction();
    }

    public String getMenuName(){
        return this.menuName;
    }
}
