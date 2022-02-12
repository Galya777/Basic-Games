package Server.addIns;

public enum Result {
    SOLVED(1),
    UNSOLVED(0);

    private final int result;

    Result(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {

        return switch (this.result) {
            case 1 -> "solved";
            case 0 -> "unsolved";
            default -> "";
        };
    }
}
