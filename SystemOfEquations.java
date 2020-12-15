package solver;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class SystemOfEquations {

    private final int numberOfVariables;
    private final ComplexNumber[][] extendedMatrix;

    private SystemOfEquations(int numberOfVariables, ComplexNumber[][] extendedMatrix) {
        this.numberOfVariables = numberOfVariables;
        this.extendedMatrix = extendedMatrix;
    }

    public static SystemOfEquations create(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useLocale(Locale.US);
        String[] size = scanner.nextLine().trim().split("\\s");
        int variablesQuantity = Integer.parseInt(size[0]);
        int equationsQuantity = Integer.parseInt(size[1]);
        ComplexNumber[][] equations = new ComplexNumber[equationsQuantity][variablesQuantity + 1];

        for (int i = 0; i < equations.length; i++) {
            String input = scanner.nextLine().trim();
            String[] coefficients = input.split("\\s+");
            for (int j = 0; j < coefficients.length; j++) {
                equations[i][j] = ComplexNumber.valueOf(coefficients[j]);
            }
        }
        return new SystemOfEquations(variablesQuantity, equations);
    }

    public ComplexNumber getA() {
        return extendedMatrix[0][0];
    }

    public ComplexNumber getB() {
        return extendedMatrix[0][1];
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public ComplexNumber[][] getExtendedMatrix() {
        return extendedMatrix;
    }
}
