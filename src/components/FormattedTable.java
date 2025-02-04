package components;

import utils.ColorWrapper;
import utils.Input;

public class FormattedTable {
    private final int[] sizes;
    private final String[] colors;

    public FormattedTable(int[] sizes, String[] colors) {
        this.sizes = sizes;
        this.colors = colors;
    }

    public void printWithColor(String string, String split) {
        String[] strings = string.split(split);
        if (strings.length < this.sizes.length || strings.length < this.colors.length)
            throw new IllegalStateException("Not enough parameters for set size or colors");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.length; ++i) {
            String color = "";
            if (!colors[i].isEmpty())
                 color = ColorWrapper.getColor(colors[i]);
            if (colors[i].equalsIgnoreCase("BOOL")) {
                color = switch (strings[i].strip()) {
                    case "true" -> ColorWrapper.BRIGHT_GREEN;
                    case "false" -> ColorWrapper.RED;
                    default -> color;
                };
            }
            result.append("| ");
            if (!color.isEmpty()) result.append(color);
            result.append(Input.formatLength(strings[i], sizes[i]));
            if (!color.isEmpty()) result.append(ColorWrapper.getColor("RESET"));
            result.append(" ");
        }

        result.append("|");
        System.out.println(result);
    }

    public void print(String string, String split) {
        String[] strings = string.split(split);

        if (strings.length < this.sizes.length)
            throw new IllegalStateException("Not enough parameters for set size");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.length; ++i) {
            result.append("║ ");
            result.append(Input.formatLength(strings[i], sizes[i]));
            result.append(" ");
        }

        result.append("║");
        System.out.println(result);
    }
}
