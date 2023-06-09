import java.io.*;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException {
        double[] c1 = { 6, 5 };
        int[] e1 = { 0, 3 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { -2, -9 };
        int[] e2 = { 1, 4 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        Polynomial m = p1.multiply(p2);
        if (m.hasRoot(0))
            System.out.println("0 is a root of m");
        else
            System.out.println("0 is not a root of m");
        if (m.hasRoot(2))
            System.out.println("2 is a root of m");
        else
            System.out.println("2 is not a root of m");

        PrintStream out = new PrintStream("file.txt");
        out.println("-3-6x+5-3x2+7x8-x-4x+10");
        out.close();
        File file = new File("file.txt");
        Polynomial p3 = new Polynomial(file);
        Polynomial p = p1.multiply(p3);
        p.saveToFile("file.txt");
    }
}