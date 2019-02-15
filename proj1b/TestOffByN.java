import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testOffByN() {
        OffByN offbyFour = new OffByN(4);
        assertTrue(offbyFour.equalChars('b', 'f'));
        assertTrue(offbyFour.equalChars('f', 'b'));
        assertTrue(offbyFour.equalChars('x', 't'));
        assertFalse(offbyFour.equalChars('s', 's'));
        assertFalse(offbyFour.equalChars('z', 'Z'));
    }
}
