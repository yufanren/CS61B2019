public class Palindrome {

    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> list = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            list.addLast(word.charAt(i));
        }
        return list;
    }

    public static boolean isPalindrome(String word) {
        Deque list = wordToDeque(word);
        return isPal(list);
    }

    private static boolean isPal(Deque list) {
        if (list.size() <= 1) {
            return true;
        } else if (list.removeFirst() == list.removeLast()) {
            return isPal(list);
        } else {
            return false;
        }
    }

    /** Offbyone method. */
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque list = wordToDeque(word);
        return isPal2(list, cc);
    }

    private static boolean isPal2(Deque list, CharacterComparator cc) {
        if (list.size() <= 1) {
            return true;
        } else if (cc.equalChars((char) list.removeFirst(), (char) list.removeLast())) {
            return isPal2(list, cc);
        } else {
            return false;
        }

    }
}

