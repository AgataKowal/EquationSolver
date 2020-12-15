package solver;

import java.util.Arrays;

public class SolutionChecker {

    public boolean hasNoSolutions(ComplexNumber[][] matrix) {
        ComplexNumber[] zeroArray = new ComplexNumber[matrix[0].length - 1];
        Arrays.fill(zeroArray, ComplexNumber.ZERO);

        ComplexNumber[] subArray;
        for (ComplexNumber[] numbers : matrix) {
            subArray = Arrays.copyOfRange(numbers, 0, numbers.length - 1);
            for (int j = 0; j < subArray.length; j++) {
                subArray[j] = ComplexNumber.module(subArray[j]);
            }
            if (Arrays.equals(zeroArray, subArray) && !numbers[numbers.length - 1].equals(ComplexNumber.ZERO)) {
                return true;
            }
        }
        if (matrix.length == 2 && matrix[0].length == 3) {
            return ((matrix[1][0].equals(ComplexNumber.ZERO) && !matrix[1][1].equals(ComplexNumber.ZERO))
                    || (!matrix[1][0].equals(ComplexNumber.ZERO) && matrix[1][1].equals(ComplexNumber.ZERO)))
                    && matrix[1][2].equals(ComplexNumber.ZERO);
        }
        return false;
    }

    public boolean hasInfiniteSolutions(ComplexNumber[][] matrix) {
        int numberOfSignificantEquations = getNumberOfSignificantEquations(matrix);
        int numberOfSignificantVariables = getNumberOfSignificantVariables(matrix);
        ComplexNumber[] lastSignificantEquation = matrix[numberOfSignificantEquations - 1];
        return numberOfSignificantEquations < numberOfSignificantVariables && lastEquationHasMoreThanOneNonZeroValue(lastSignificantEquation);
    }

    private boolean lastEquationHasMoreThanOneNonZeroValue(ComplexNumber[] lastSignificantEquation) {
        int nonZeroValues = 0;
        for (ComplexNumber value : lastSignificantEquation) {
            if (value.getReal() != 0.0 || value.getImaginary() != 0.0) {
                nonZeroValues += 1;
            }
        }
        return nonZeroValues > 1;
    }

    private int getNumberOfSignificantVariables(ComplexNumber[][] matrix) {
        return matrix[0].length - 1;
    }

    public int getNumberOfSignificantEquations(ComplexNumber[][] matrix) {
        int numberOfSignificantEquations = matrix.length;
        for (int i = matrix.length - 1; i >= 0; i--) {
            if (isZeroRow(matrix[i])) {
                numberOfSignificantEquations -= 1;
            }
        }
        return numberOfSignificantEquations;
    }

    private boolean isZeroRow(ComplexNumber[] matrix) {
        boolean isZeroRow = true;
        for (ComplexNumber number : matrix) {
            if (!number.equals(ComplexNumber.ZERO)) {
                isZeroRow = false;
                break;
            }
        }
        return isZeroRow;
    }
}
