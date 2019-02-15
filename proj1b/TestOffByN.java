import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testOffByN() {
        OffByN OffByFour = new OffByN(4);
        assertTrue(OffByFour.equalChars('b', 'f'));
        assertTrue(OffByFour.equalChars('f', 'b'));
        assertTrue(OffByFour.equalChars('x', 't'));
        assertFalse(OffByFour.equalChars('s', 's'));
        assertFalse(OffByFour.equalChars('z', 'Z'));
    }
}
