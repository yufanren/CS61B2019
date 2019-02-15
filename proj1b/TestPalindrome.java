import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("whale"));
        assertTrue(palindrome.isPalindrome("xanax"));
        assertTrue(palindrome.isPalindrome("g"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindrome2() {
        OffByOne comp = new OffByOne();
        assertFalse(palindrome.isPalindrome("tweak", comp));
        assertTrue(palindrome.isPalindrome("flake", comp));
        assertTrue(palindrome.isPalindrome("t", comp));
        assertTrue(palindrome.isPalindrome("", comp));
    }
}
