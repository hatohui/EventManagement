package utils;

public class UserInterface {
    private String UI;

    /**
     * Constructor to create a new UserInterface.
     */
    public UserInterface() {
        this.UI = "";
    }

    /**
     * Set the UI to the given string.
     *
     * @param UI a String that consists the UI.
     */
    public void setUI(String UI) {
        this.UI = UI;
    }

    /**
     * Return a string that is the UI itself.
     *
     * @return a String that indicates the UI.
     */
    public String getUI() {
        return UI;
    }

    /**
     * Print out the UI to the console.
     */
    public void deploy() {
        System.out.println("\n".repeat(30));
        System.out.print(this.UI);
    }
}
