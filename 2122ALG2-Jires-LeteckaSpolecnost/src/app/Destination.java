package app;

import java.io.FileWriter;
import java.io.IOException;

public class Destination {

    private String name;

    public Destination(String name) {
        this.name = name;
        try {
            FileWriter myWriter = new FileWriter("destinations.txt", true);
            myWriter.write("\n" + name);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
