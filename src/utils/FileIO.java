package utils;

import java.io.*;
import java.util.ArrayList;

public class FileIO extends ArrayList<String> {
    private final File file;

    /**Return an array containing the current state of data in the File.
     * @return an ArrayList<String> containing the current data in the file.
     * */
    public ArrayList<String> getState() {
        return this;
    }

    /**Constructor for FileIO
     * Used for adding urls
     * @param url the path to the file.
     * */
    public FileIO(String url) {
        file = new File(url);
        fetch();
    }

    /**Get data from the file and store in itself (ArrayList<String>)
     * */
    public void fetch() {
        try {
            this.clear();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                this.add(line + "\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("File not found.");
            }
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the data from the current state into the file.
     */
    public void commit() {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
            for (String str : this) {
                writer.append(str.strip()).append("\n");
            }
            writer.close();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("File not found.");
            }
        }
    }

    /**Find the data in the file including the inputted string
     *
     * @param value subString to be included
     * @return an ArrayList of String containing the data included
     * */
    public ArrayList<String> findInclude(String value) {
        ArrayList<String> result = new ArrayList<>();
        for (String str: this) {
            if (str.toLowerCase().contains(value.toLowerCase())) {
                result.add(str);
            }
        }
        return result;
    }

    /**Remove the line containing the data from the state.
     * */
    public boolean removeFirstInState(String value) {
        for (String str: this) {
            if (str.strip().equalsIgnoreCase(value.strip())) {
                this.remove(str);
                return true;
            };
        }
        return false;
    }

    /**Append a String into current state (Remember to commit to save the data)
     * */
    public void append(String str) {
        this.add(str + "\n");
    }

    /**For printing stuffs
     * */
    public String toString() {
        return String.join("", getState());
    }
}