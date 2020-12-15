package solver;

import java.util.ArrayDeque;
import java.util.Deque;

public class EchelonMatrixConverter {

    private final Deque<Swap> swapsHistory;

    public EchelonMatrixConverter() {
        this.swapsHistory = new ArrayDeque<>();
    }

    public ComplexNumber[][] convert(SystemOfEquations system) {
        ComplexNumber[][] matrix = system.getExtendedMatrix();
        for (int i = 0; i < matrix.length; i++) {
            ComplexNumber diagonalElement = matrix[i][i];
            if (diagonalElement.equals(ComplexNumber.ZERO)) {
                matrix = swap(matrix, i);
                diagonalElement = matrix[i][i];
            }

            if (diagonalElement.equals(ComplexNumber.ZERO)) {
                break;
            }
            if (!diagonalElement.equals(ComplexNumber.ONE)) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = ComplexNumber.divide(matrix[i][j], diagonalElement);
                }
            }
            for (int j = i + 1; j < matrix.length; j++) {
                ComplexNumber firstElementInColumn = matrix[j][i];
                for (int k = 0; k < matrix[j].length; k++) {
                    matrix[j][k] = ComplexNumber.add(matrix[j][k], ComplexNumber.multiply(ComplexNumber.negate(firstElementInColumn), matrix[i][k]));
                }
            }
        }
        return matrix;
    }

    public ComplexNumber[][] revertSwaps(ComplexNumber[][] matrix) {
        for (int i = 0; i < swapsHistory.size(); i++) {
            Swap swap = swapsHistory.pollLast();
            if ("row".equals(swap.getType())) {
                matrix = swapRows(matrix, swap.getIndexToSwap(), swap.getSwappedIndex());
            } else if ("column".equals(swap.getType())) {
                matrix = swapColumns(matrix, swap.getIndexToSwap(), swap.getSwappedIndex());
            } else {
                System.out.println("Invalid swap data.");
            }
        }
        return matrix;
    }

    private ComplexNumber[][] swap(ComplexNumber[][] matrix, int positionToSwap) {
        for (int j = positionToSwap + 1; j < matrix.length; j++) {
            if (!matrix[j][positionToSwap].equals(ComplexNumber.ZERO)) {
                matrix = swapRows(matrix, positionToSwap, j);
                swapsHistory.offerLast(new Swap(positionToSwap, j, "row"));
                return matrix;
            }
        }
        for (int j = positionToSwap + 1; j < matrix[positionToSwap].length; j++) {
            if (!matrix[positionToSwap][j].equals(ComplexNumber.ZERO)) {
                matrix = swapColumns(matrix, positionToSwap, j);
                swapsHistory.offerLast(new Swap(positionToSwap, j, "column"));
                return matrix;
            }
        }
        for (int j = positionToSwap + 1; j < matrix.length; j++) {
            for (int k = positionToSwap + 1; k < matrix[j].length; k++) {
                if (!matrix[j][k].equals(ComplexNumber.ZERO)) {
                    matrix = swapRows(matrix, positionToSwap, j);
                    swapsHistory.offerLast(new Swap(positionToSwap, j, "row"));
                    matrix = swapColumns(matrix, positionToSwap, k);
                    swapsHistory.offerLast(new Swap(positionToSwap, k, "column"));
                    return matrix;
                }
            }
        }
        return matrix;
    }

    private ComplexNumber[][] swapColumns(ComplexNumber[][] matrix, int i, int j) {
        ComplexNumber tmp;
        for (int k = 0; k < matrix.length; k++) {
            tmp = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = tmp;
        }
        return matrix;
    }

    private ComplexNumber[][] swapRows(ComplexNumber[][] matrix, int i, int j) {
        ComplexNumber[] tmp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tmp;
        return matrix;
    }
}
