package byog.Core;

import byog.TileEngine.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Random;

import static byog.TileEngine.Tileset.PLAYER;

class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    void playWithKeyboard() {
        Play game = new Play(WIDTH, HEIGHT);
        game.startPlay();
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
    TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                finalWorldFrame[i][j] = Tileset.NOTHING;
            }
        }

        LinkedList<String> QUEUE = new LinkedList<String>();
        for (int i = 0; i < input.length(); i += 1) {
            QUEUE.add(String.valueOf(input.charAt(i)));
        }
        Play game = new Play(WIDTH, HEIGHT);
        String key = QUEUE.remove();
        if (key.equals("q") || key.equals("Q")) {
            return finalWorldFrame;
        } else if (key.equals("l") || key.equals("L")) {
            try {
                return loadString(QUEUE);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return finalWorldFrame;
            }
        } else if (key.equals("n") || key.equals("N")) {
            if (QUEUE.size() == 0) {
                return finalWorldFrame;
            }
            long SEED = processSeed(QUEUE);
            Random RANDOM = new Random(SEED);
            MapGen Generator = new MapGen(finalWorldFrame, RANDOM, WIDTH, HEIGHT);
            finalWorldFrame = Generator.Generate();
            game.ranDom = RANDOM;
            game.WORLD = finalWorldFrame;
            game.Generator = Generator;
            game.AVATAR = game.placePlayer(game.WORLD, game.Generator);
            game.WORLD[game.AVATAR.Px][game.AVATAR.Py] = PLAYER;
            return playwString(game, QUEUE);
        }
        return finalWorldFrame;
    }

    private TETile[][] playwString(Play game, LinkedList<String> QUEUE) {
        while (QUEUE.size() > 0) {
            String key = QUEUE.remove();
            switch (key) {
                case "A":
                case "a":
                case "W":
                case "w":
                case "S":
                case "s":
                case "D":
                case "d":
                    if (game.destinationEval(key)) {
                        game.WORLD = game.AVATAR.moveNoDraw(game.Destination, game.WORLD);
                    }
                    break;
                case ":":
                    game.stringSave(QUEUE);
                    break;
            }
        }
        return game.WORLD;
    }

    private TETile[][] loadString(LinkedList<String> list) throws IOException, ClassNotFoundException {

        ObjectInputStream IN = new ObjectInputStream(new FileInputStream("gameSave.txt"));
        Play LOAD = (Play) IN.readObject();
        return playwString(LOAD, list);
    }


    /* private static long processString(LinkedList input) {
        if (input.size() == 0) {
            return processSeed(input);
        }
            String c = (String) input.remove();
            if (c.equals("N") || c.equals("n")) {
                return processSeed(input);
            } else {
                return processString(input);
            }
    }*/

    private static long processSeed(LinkedList QUEUE) {
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
