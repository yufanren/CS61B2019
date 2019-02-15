import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {


    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffBy1() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('z', 'y'));
        assertTrue(offByOne.equalChars('m', 'n'));
        assertFalse(offByOne.equalChars('t', 'z'));
        assertFalse(offByOne.equalChars('u', 'V'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertFalse(offByOne.equalChars('@', '*'));
    }
}
