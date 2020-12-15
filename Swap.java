package solver;

public class Swap {

    private final int indexToSwap;
    private final int swappedIndex;
    private final String type;

    public Swap(int indexToSwap, int swappedIndex, String type) {
        this.indexToSwap = indexToSwap;
        this.swappedIndex = swappedIndex;
        this.type = type;
    }

    public int getIndexToSwap() {
        return indexToSwap;
    }

    public int getSwappedIndex() {
        return swappedIndex;
    }

    public String getType() {
        return type;
    }
}
