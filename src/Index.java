import utils.IntegerID;
import utils.UIComponents;

public class Index {
    public static void main(String[] args) {
       IntegerID.setLength(6);
       UIComponents.loadingBarWithColor("Initializing app...", 30, 100, "GREEN");
       App.run();
    }
}


