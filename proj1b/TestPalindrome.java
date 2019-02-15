import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();*/

    @Test
    public void testWordToDeque() {
        Deque d = Palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(Palindrome.isPalindrome("whale"));
        assertTrue(Palindrome.isPalindrome("xanax"));
        assertTrue(Palindrome.isPalindrome("g"));
        assertTrue(Palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindrome2() {
        OffByOne comp = new OffByOne();
        assertFalse(Palindrome.isPalindrome("tweak", comp));
        assertTrue(Palindrome.isPalindrome("flake", comp));
        assertTrue(Palindrome.isPalindrome("t", comp));
        assertTrue(Palindrome.isPalindrome("", comp));
    }
}
