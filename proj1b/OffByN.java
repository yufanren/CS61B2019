public class OffByN implements CharacterComparator {

    private int offby;

    public OffByN(int N) {
        offby = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == offby || x - y == -offby) {
            return true;
        }
        return false;
    }
}
