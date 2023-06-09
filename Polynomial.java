import java.io.*;
import java.util.Scanner;

class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = null;
        this.exponents = null;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        Scanner in = new Scanner(file);
        String line = in.nextLine();
        in.close();
        String[] terms = line.split("(?<!^)-|\\+");
        double[] coeffs = new double[terms.length];
        int[] exps = new int[terms.length];
        for (int i = 0; i < terms.length; i++) {
            String[] chars = terms[i].split("x");
            if (terms[i].equals("x"))
                chars = new String[] { "1" };
            if (line.contains("-" + terms[i]))
                coeffs[i] = Double.parseDouble("-" + chars[0]);
            else
                coeffs[i] = Double.parseDouble(chars[0]);

            if (chars.length == 2)
                exps[i] = Integer.parseInt(chars[1]);
            else if (terms[i].contains("x"))
                exps[i] = 1;
        }

        this.coefficients = coeffs;
        this.exponents = exps;
    }

    public Polynomial add(Polynomial other) {
        double[] coeffs = new double[this.exponents.length + other.exponents.length];
        int[] exps = new int[this.exponents.length + other.exponents.length];

        int i = 0;
        int j = 0;
        int k = 0;

        // Add the two polynomials/Merge the exponents
        while (i < exponents.length && j < other.exponents.length) {
            if (exponents[i] < other.exponents[j]) {
                coeffs[k] = coefficients[i];
                exps[k] = exponents[i];
                i++;
                k++;
            } else if (exponents[i] > other.exponents[j]) {
                coeffs[k] = other.coefficients[j];
                exps[k] = other.exponents[j];
                j++;
                k++;
            } else {
                coeffs[k] = coefficients[i] + other.coefficients[j];
                exps[k] = exponents[i];
                k++;
                i++;
                j++;
            }
        }

        while (i < exponents.length) {
            coeffs[k] = coefficients[i];
            exps[k] = exponents[i];
            i++;
            k++;
        }

        while (j < other.exponents.length) {
            coeffs[k] = other.coefficients[j];
            exps[k] = other.exponents[j];
            j++;
            k++;
        }

        // Remove zeros
        int notZeros = 0;
        for (double c : coeffs) {
            if (c != 0)
                notZeros++;
        }

        double[] new_c = new double[notZeros];
        int[] new_e = new int[notZeros];

        for (int l = 0, index = 0; l < coeffs.length; l++) {
            if (coeffs[l] != 0) {
                new_c[index] = coeffs[l];
                new_e[index] = exps[l];
                index++;
            }
        }

        return new Polynomial(new_c, new_e);
    }

    public Polynomial multiply(Polynomial other) {
        Polynomial product = new Polynomial(new double[] { coefficients[0] * other.coefficients[0] },
                new int[] { exponents[0] + other.exponents[0] });

        for (int i = 0; i < coefficients.length; i++) {
            for (int j = (i == 0 ? 1 : 0); j < other.coefficients.length; j++) {
                product = product.add(new Polynomial(new double[] { coefficients[i] * other.coefficients[j] },
                        new int[] { exponents[i] + other.exponents[j] }));
            }
        }

        return product;
    }

    public double evaluate(double num) {
        double res = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            res += this.coefficients[i] * Math.pow(num, this.exponents[i]);
        }
        return res;
    }

    public boolean hasRoot(double num) {
        return evaluate(num) == 0;
    }

    public void saveToFile(String filename) throws FileNotFoundException {
        PrintStream out = new PrintStream(filename);
        if (!(coefficients == null || exponents == null)) {
            if (coefficients.length == exponents.length) {
                if (coefficients.length >= 1 && exponents.length >= 1) {
                    out.print(
                            coefficients[0] + (exponents[0] >= 1 ? "x" : "")
                                    + (exponents[0] >= 2 ? exponents[0] : ""));
                    for (int i = 1; i < this.coefficients.length; i++) {
                        out.print(
                                (coefficients[i] < 0 ? "-" : "+") + Math.abs(coefficients[i])
                                        + (exponents[i] >= 1 ? "x" : "")
                                        + (exponents[i] >= 2 ? exponents[i] : ""));
                    }
                }
            } else
                System.out.println("Coefficients and exponents do not have the same length");
        }
        out.close();
    }

    public void print() {
        if (coefficients.length >= 1 && exponents.length >= 1) {
            System.out.print(Math.round(coefficients[0]) + (exponents[0] == 1 ? "x" : exponents[0] > 1 ? "x^" : "")
                    + (exponents[0] >= 2 ? exponents[0] : ""));
            for (int i = 1; i < this.coefficients.length; i++) {
                System.out.print(
                        (Math.round(coefficients[i]) < 0 ? " - " : " + ") + Math.abs(Math.round(coefficients[i]))
                                + (exponents[i] == 1 ? "x" : exponents[i] > 1 ? "x^" : "")
                                + (exponents[i] >= 2 ? exponents[i] : ""));
            }
        }
        System.out.println();
    }
}
