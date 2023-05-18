class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[] { 0 };
    }

    public Polynomial(double[] arr) {
        this.coefficients = arr;
    }

    public Polynomial add(Polynomial other) {
        int larger = Math.max(coefficients.length, other.coefficients.length);
        double[] sum = new double[larger];
        for (int i = 0; i < larger; i++) {
            if (i < coefficients.length && i < other.coefficients.length) {
                sum[i] = this.coefficients[i] + other.coefficients[i];
            } else if (i < coefficients.length) {
                sum[i] = this.coefficients[i];
            } else if (i < other.coefficients.length) {
                sum[i] = other.coefficients[i];
            }
        }
        return new Polynomial(sum);
    }

    public double evaluate(double num) {
        double res = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            res += coefficients[i] * Math.pow(num, i);
        }
        return res;
    }

    public boolean hasRoot(double num) {
        return evaluate(num) == 0;
    }
}
