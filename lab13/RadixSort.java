/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        /* find max length for strings in asciis. */
        int length = Integer.MIN_VALUE;
        for (String s:asciis) {
            length = s.length() > length ? s.length() : length;
        }
        //Make a copy of the original array.
        String[] sort = new String[asciis.length];
        System.arraycopy(asciis, 0, sort, 0, asciis.length);
        //Pad Strings with 0 on right side up to longest string's length.
        for (int i = 0; i < sort.length; i += 1) {
            sort[i] = padRight(sort[i], length);
        }
        //Perform sorting on the array by each digit of String.
        for (int i = length - 1; i >= 0; i -= 1) {
            sortHelperLSD(sort, i);
        }
        //Unpad Strings on the array.
        for (int i = 0; i < sort.length; i += 1) {
            sort[i] = unpadRight(sort[i]);
        }
        return sort;
    }

    private static String padRight(String s, int length) {
        char nu = (char) 0;
        String c = Character.toString(nu);
        while (s.length() < length) {
            s = s + c;
        }
        return s;
    }

    private static String unpadRight(String s) {
        char nu = (char) 0;
        while (s.charAt(s.length() - 1) == nu) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] counts = new int[256];
        for (String s:asciis) {
            char nu = s.charAt(index);
            int in = (int) nu;
            counts[in] += 1;
        }
        int[] starts = new int[256];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < sorted.length; i += 1) {
            char nu = asciis[i].charAt(index);
            int in = (int) nu;
            sorted[starts[nu]] = asciis[i];
            starts[nu] += 1;
        }
        asciis = sorted;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
