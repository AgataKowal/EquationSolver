package solver;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Missing initial parameters. Try again.");
        } else {
            try (InputStream is = new FileInputStream(new File(args[1]));
                 OutputStream os = new FileOutputStream(new File(args[3]))) {

                Solver solver = new Solver();
                solver.solve(is, os);

            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println("Another file exception");
            }
        }
    }
}
