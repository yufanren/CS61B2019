package byog.Core;

import byog.TileEngine.*;
import java.util.LinkedList;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        LinkedList<String> QUEUE = new LinkedList();
        for (int i = 0; i < input.length(); i += 1) {
            QUEUE.add(String.valueOf(input.charAt(i)));
        }
        long SEED = processString(QUEUE);
        if (SEED == 0) {
            for (int i = 0; i < WIDTH; i += 1) {
                for (int j = 0; j < HEIGHT; j += 1) {
                    finalWorldFrame[i][j] = Tileset.NOTHING;
                }
            }
        } else {
            MapGen Generator = new MapGen(finalWorldFrame, SEED, WIDTH, HEIGHT);
            finalWorldFrame = Generator.Generate();
        }
        return finalWorldFrame;
    }

    private long processString(LinkedList input) {
        if (input.size() == 0) {
            return processSeed(input);
        }
        while (input.size() > 0) {
            String c = (String) input.remove();
            if (c.equals("N") || c.equals("n")) {
                return processSeed(input);
            } else {
                return processString(input);
            }
        }
        return 0;
    }

    private long processSeed(LinkedList QUEUE) {
        long SEED = 0;
        long n;
        if (QUEUE.size() == 0) {
            return SEED;
        }
        while (QUEUE.size() > 0) {
            String c = (String) QUEUE.remove();
            if (c.equals("S") || c.equals("s")) {
                return SEED;
            } else {
                try {
                    n = Long.parseLong(c);
                } catch (NumberFormatException e) {
                    continue;
                }
                SEED = SEED * 10 + n;
            }
        }
        return 0;
    }
}
