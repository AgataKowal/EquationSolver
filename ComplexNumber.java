package solver;

import java.util.Objects;

public class ComplexNumber {

    public static final ComplexNumber ZERO = new ComplexNumber(0, 0);
    public static final ComplexNumber ONE = new ComplexNumber(1, 0);
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static ComplexNumber module(ComplexNumber cn) {
        return new ComplexNumber(Math.sqrt(Math.pow(cn.getReal(), 2.0) + Math.pow(cn.getImaginary(), 2.0)), 0.0);
    }

    public static ComplexNumber negate(ComplexNumber cn) {
        return new ComplexNumber(-cn.getReal(), -cn.getImaginary());
    }

    public static ComplexNumber add(ComplexNumber z1, ComplexNumber z2) {
        return new ComplexNumber(z1.getReal() + z2.getReal(), z1.getImaginary() + z2.getImaginary());
    }

    public static ComplexNumber subtract(ComplexNumber z1, ComplexNumber z2) {
        return new ComplexNumber(z1.getReal() - z2.getReal(), z1.getImaginary() - z2.getImaginary());
    }

    public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
        double tempReal = z1.getReal() * z2.getReal() - z1.getImaginary() * z2.getImaginary();
        double tempImaginary = z1.getImaginary() * z2.getReal() + z1.getReal() * z2.getImaginary();
        return new ComplexNumber(tempReal, tempImaginary);
    }

    public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
        double denominator = z2.getReal() * z2.getReal() + z2.getImaginary() * z2.getImaginary();
        double tempReal = (z1.getReal() * z2.getReal() + z1.getImaginary() * z2.getImaginary()) / denominator;
        double tempImaginary = (z1.getImaginary() * z2.getReal() - z1.getReal() * z2.getImaginary()) / denominator;
        return new ComplexNumber(tempReal, tempImaginary);
    }

    public static ComplexNumber valueOf(String value) {
        String[] complexNumber;
        double realPart;
        double imaginaryPart;
        if (value.contains("+")) {
            complexNumber = value.trim().split("\\+");
            realPart = Double.parseDouble(complexNumber[0]);
            if (complexNumber[1].equals("i")) {
                imaginaryPart = 1.0;
            } else {
                imaginaryPart = Double.parseDouble(complexNumber[1].replace("i", ""));
            }
        } else if (value.substring(1).contains("-")) {
            int indexOfMinus = value.substring(1).indexOf("-") + 1;
            String real = value.substring(0, indexOfMinus);
            String imaginary = value.substring(indexOfMinus + 1);
            realPart = Double.parseDouble(real);
            if (imaginary.equals("i")) {
                imaginaryPart = -1.0;
            } else {
                imaginaryPart = -Double.parseDouble(imaginary.replace("i", ""));
            }
        } else if (value.contains("i")) {
            realPart = 0.0;
            if (value.equals("i")) {
                imaginaryPart = 1.0;
            } else if (value.equals("-i")) {
                imaginaryPart = -1.0;
            } else {
                imaginaryPart = Double.parseDouble(value.replace("i", ""));
            }
        } else {
            realPart = Double.parseDouble(value);
            imaginaryPart = 0.0;
        }
        return new ComplexNumber(realPart, imaginaryPart);
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        if (imaginary == 0) {
            return String.valueOf(real);
        } else if (real == 0) {
            if (imaginary == 1) {
                return "i";
            } else if (imaginary == -1) {
                return "-i";
            } else {
                return imaginary + "i";
            }
        } else {
            if (imaginary == 1) {
                return real + "+i";
            } else if (imaginary == -1) {
                return real + "-i";
            } else if (imaginary < 0) {
                return real + (imaginary + "i");
            } else {
                return real + "+" + imaginary + "i";
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.real, real) == 0 &&
                Double.compare(that.imaginary, imaginary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }
}
