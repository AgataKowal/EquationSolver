package solver;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Solver {


    public void solve(InputStream inputStream, OutputStream outputStream) {

        SystemOfEquations system = SystemOfEquations.create(inputStream);
        int degree = system.getNumberOfVariables();
        String result;

        if (degree == 1) {
            result = ComplexNumber.divide(system.getB(), system.getA()) + "\n";
        } else {
            EchelonMatrixConverter converter = new EchelonMatrixConverter();
            ComplexNumber[][] echelonMatrix = converter.convert(system);

            SolutionChecker checker = new SolutionChecker();
            if (checker.hasNoSolutions(echelonMatrix)) {
                result = "No solutions";
            } else if (checker.hasInfiniteSolutions(echelonMatrix)) {
                result = "Infinitely many solutions";
            } else if (checker.getNumberOfSignificantEquations(echelonMatrix) == echelonMatrix[0].length - 1) {
                int rowsToTrim = echelonMatrix.length - checker.getNumberOfSignificantEquations(echelonMatrix);
                ComplexNumber[][] gaussMatrix = performGaussElimination(echelonMatrix, rowsToTrim);
                //gaussMatrix = converter.revertSwaps(gaussMatrix);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < gaussMatrix.length - rowsToTrim; i++) {
                    sb.append(gaussMatrix[i][gaussMatrix[i].length - 1]).append("\n");
                }
                result = sb.toString();
            } else {
                result = "No solutions";
            }
        }
        saveResults(result, outputStream);
    }

    private ComplexNumber[][] performGaussElimination(ComplexNumber[][] echelonMatrix, int rowsToTrim) {
        for (int i = echelonMatrix.length - rowsToTrim - 2; i >= 0; i--) {
            for (int j = echelonMatrix.length - rowsToTrim - 1; j > i; j--) {
                ComplexNumber multiplier = ComplexNumber.negate(echelonMatrix[i][j]);
                for (int k = 0; k < echelonMatrix[i].length; k++) {
                    echelonMatrix[i][k] = ComplexNumber.add(ComplexNumber.multiply(multiplier, echelonMatrix[j][k]), echelonMatrix[i][k]);
                }
            }
        }
        return echelonMatrix;
    }

    private void saveResults(String result, OutputStream outputStream) {
        try {
            PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8.name());
            printStream.print(result);
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
