package utils;

import java.util.ArrayList;

/**A tool for building UI at your convenience.
 * @author hatohui
 * @version 1.0.0
 * */
public class UIBuilder {
    private final UIComponents Compo = new UIComponents();
    private final ColorWrapper Color = new ColorWrapper();
    private UserInterface UI;
    private ArrayList<String> currentUI = new ArrayList<>();
    private String emptyWall = "";
    private String topWall = "";
    private String bottomWall = "";

    /**Default constructor which create a
     * new empty UserInterface by default.
     * UI by default have the size of 70.
     * */
    public UIBuilder() {
        this.UI = new UserInterface();
    }

    /**Constructor for UIBuilder, takes one parameter
     * and create a new empty User Interface.
     * @param horizontalLength set the horizontal length
     *                         of the said UI.
     * */
    public UIBuilder(int horizontalLength) {
        this.UI = new UserInterface();
        Compo.setHorizontalLength(horizontalLength);
    }

    /* UTILIZING UI COMPONENTS */

    /**Add a header for your UI displaying the given text
     * in the middle.
     * @param name a String to be displayed.
     * */
    public UIBuilder header(String name) {
        currentUI.add(Compo.header(name) + "\n");
        return this;
    }

    /**Add the top wall (ceiling) for your UI.
     * */
    public UIBuilder topWall() {
        if (topWall.isEmpty()) topWall = Compo.topWall() + "\n";
        currentUI.add(topWall);
        return this;
    }

    /**Add a separator line to your UI.
     * */
    public UIBuilder separator() {
        currentUI.add(Compo.separator() + "\n");
        return this;
    }

    /**Add a separator line to your UI made of given
     * material.
     * @param material a char symbol constructing the separator.
     * */
    public UIBuilder separator(Character material) {
        currentUI.add(Compo.separator(material) + "\n");
        return this;
    }

    /**Add the bottom wall String (floor) to your UI.
     * */
    public UIBuilder bottomWall() {
        if (bottomWall.isEmpty())
            bottomWall = Compo.bottomWall() + "\n";

        currentUI.add(bottomWall);
        return this;
    }

    /**Add an empty space with two walls on the side to
     * your UI.
     * */
    public UIBuilder emptyWall() {
        if (emptyWall.isEmpty()) emptyWall =  Compo.emptyWall() + "\n";
        currentUI.add(emptyWall);
        return this;
    }

    /**Add a certain amount of options to your UI for display.
     * Amount is indicated by the length of the String array passed.
     * @param options a String array containing the options to be
     *                printed.
     * */
    public UIBuilder options(String[] options) {
        currentUI.add(Compo.options(options) + "\n");
        return this;
    }

    /**Add an option bar using a custom button text and the description
     * of the option to the UI.
     * @param buttonText a String to display on the button
     * @param optionText a String to what the option is for
     * */
    public UIBuilder option(String buttonText, String optionText) {
        currentUI.add(Compo.option(buttonText, optionText) + "\n");
        return this;
    }

    /**Add a block displaying the given text to the UI. The
     * distance from the UI's walls to the String is given.
     * @param padding an integer displaying the distance from
     *                the wall to the content
     * @param text a String to display.
     * */
    public UIBuilder description(String text, int padding) {
        currentUI.add(Compo.description(text, padding) + "\n");
        return this;
    }

    /**Add a String to be displayed along with a text
     * closer to the left, the distance is decided with the
     * padding given.
     * @param text a String to be displayed.
     * @param padding an integer indicating the distance from the
     *                left wall to its content.
     * */
    public UIBuilder leftString(String text, int padding) {
        currentUI.add(Compo.leftString(text, padding) + "\n");
        return this;
    }

    /**Add a String to be displayed along with a text
     * closer to the right, the distance is decided with the
     * padding given.
     * @param text a String to be displayed.
     * @param padding an integer indicating the distance from the
     *                right wall to its content.
     * */
    public UIBuilder rightString(String text, int padding) {
        currentUI.add(Compo.rightString(text, padding) + "\n");
        return this;
    }

    //FUNCTIONALITY
    /**Reset the UIBuilder, wipes all data so the UI
     * return to its initial state.
     */
    public void reset() {
        UI = new UserInterface();
        currentUI.clear();
    }

    /**Return the UserInterface Object containing data of your
     * preset Interface then RESET the UIBuilder to its original
     * state. All data are wiped.
     * @return an UserInterface object
     * */
    public UserInterface saveAndReset() {
        UserInterface value = new UserInterface();
        value.setUI(String.join("",currentUI));
        UI = new UserInterface();
        currentUI.clear();
        return value;
    }

    /**Store the UI given to the current UserInterface
     * object then reset the data in the UIBuilder. If you wish
     * to access or restore the data use load()
     * */
    public UIBuilder save() {
        UI.setUI(String.join("",currentUI));
        currentUI.clear();
        return this;
    }

    /**Load the UI into the current data in the builder
     * for further operations. Usually used to load presets.
     * */
    public UIBuilder load() {
        String[] values = UI.getUI().split("\n");
        for (String value: values) {
            currentUI.add(value + "\n");
        }
        return this;
    }

    /**Save the UI into the UserInterface object and return it.
     * @return UserInterface object
     * */
    public UserInterface saveAndReturn() {
        UI.setUI(String.join("",currentUI));
        return UI;
    }
    /**Save the UI as a String and return it.
     * @return a String
     * */
    public String saveToString() {
        return String.join("", currentUI);
    }

    /**Set the current UserInterface object that UIBuilder
     * is functioning on to a different one.
     * */
    public UIBuilder use(UserInterface newUI) {
        UI = newUI;
        return this;
    }

    /**Print out the current state of the UI data in the Builder
     * object to the console. Used for debugging.
     * */
    public void build() {
        System.out.print(currentUI);
    }

    /**Adding color into your components sparing the border.
     *@param color to add Color into the prior text. Available colors
     *             includes BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN
     *             ,WHITE.
     * */
    public UIBuilder withColor(String color) {
        String vertical = String.valueOf(UIComponents.getVertical());
        StringBuilder newString = new StringBuilder();

        String[] parts = currentUI.getLast()
                .split(vertical);

        for (String part: parts) {
            if (part.length() <= 1) continue;
            newString.append(vertical)
                .append(Color.addColor(part, color))
                .append(vertical).append("\n");
        }

        currentUI.removeLast();
        currentUI.add(newString.toString());

        return this;
    }

    /**Adding color into your components sparing the border.
     * @param color to add Color into the prior text. Available colors
     *             includes BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN
     *             ,WHITE.
     * @param background to add Color into the background of component. Available colors
     *             includes BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN
     *             ,WHITE.
     * */
    public UIBuilder withColor(String color, String background) {
        String vertical = String.valueOf(UIComponents.getVertical());
        StringBuilder newString = new StringBuilder();

        String[] parts = currentUI.getLast()
                .split(vertical);

        for (String part: parts) {
            if (part.length() <= 1) continue;
            newString.append(vertical)
                    .append(Color.addColor(part, color, background))
                    .append(vertical).append("\n");
        }

        currentUI.removeLast();
        currentUI.add(newString.toString());

        return this;
    }

    /**Adding color into your components including the border.
     * @param color to add Color into the prior text. Available colors
     *             includes BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN
     *             ,WHITE.
     * */
    public UIBuilder withColorIncludingBorder(String color) {
        String toAddColor = currentUI.getLast();
        toAddColor = Color.addColor(toAddColor, color);
        currentUI.removeLast();
        currentUI.add(toAddColor);
        return this;
    }

    /**Adding color into your components including the border.
     * @param color to add Color into the prior text. Available colors
     *             includes BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN
     *             ,WHITE.
     * @param background to add Color into the background of component. Available colors
     *             includes BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN
     *             ,WHITE.
     * */
    public UIBuilder withColorIncludingBorder(String color, String background) {
        String toAddColor = currentUI.getLast();
        toAddColor = Color.addColor(toAddColor, color);
        toAddColor = Color.addBackgroundColor(toAddColor,color);
        currentUI.removeLast();
        currentUI.add(toAddColor);
        return this;
    }

    /**Setting the default text color for the page.
     * @param color takes in the colors name
     * */
    public UIBuilder setDefaultColor(String color) {
        ArrayList<String> newUI = new ArrayList<>();
        for (String string: currentUI) {
            if (string.contains("\u001B[0m")) {
                string = string.replace("\u001B[0m", "\u001B[0m" + ColorWrapper.getColor(color));
            }
            string = Color.addColor(string, color);
            newUI.add(string);
        }
        currentUI = newUI;
        return this;
    }
}
