package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import byog.TileEngine.*;

import static byog.TileEngine.Tileset.PLAYER;

public class Play implements Serializable {
    private int width;
    private int height;
    private boolean gameOver;
    Random ranDom;
    private Font bigFont;
    private Font smallFont;
    TETile[][] WORLD;
    Player AVATAR;
    Position Destination;
    private ArrayList<TETile> IMPASSIBLES = new ArrayList<TETile>(Arrays.asList(Tileset.WALL, Tileset.LOCKED_DOOR));
    MapGen Generator;

    public Play(int width, int height) {

        this.width = width;
        this.height = height;

        bigFont = new Font("Monaco", Font.BOLD, 40);
        smallFont = new Font("Roman", Font.BOLD, 20);
    }

    void startPlay() {
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();

        startMenu(bigFont, smallFont);
    }

    /* Display a menu and wait for player to pick an option. */
    private void startMenu(Font bigFont, Font smallFont) {
        StdDraw.setFont(bigFont);
        StdDraw.text(width / 2, height - 10, "The Descend!");
        StdDraw.setFont(smallFont);
        StdDraw.text(width / 2, height / 2, "(N) New Game");
        StdDraw.text(width / 2, height / 2 - 2, "(L) Load Save");
        StdDraw.text(width / 2, height / 2 - 4, "(Q) Exit");
        StdDraw.show();

        menuInput();
    }

    /* React to player menu input. */
    private void menuInput() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            switch (key) {
                case 'n':
                case 'N':
                    newGame();
                    break;

                case 'l':
                case 'L':
                    try {
                        loadGame();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                    break;

                case 'q':
                case 'Q':
                    quitGame();
                    break;
            }
        }
    }

    /* Load saved game. */
    private void loadGame() throws IOException, ClassNotFoundException {

        ObjectInputStream IN = new ObjectInputStream(new FileInputStream("gameSave.txt"));
        Play LOAD = (Play) IN.readObject();

        TERenderer ter = new TERenderer();
        ter.initialize(LOAD.width, LOAD.height);
        ter.renderFrame(LOAD.WORLD);
        LOAD.controlPlayer();
    }

    /* Exit game. */
    private void quitGame() {
        System.exit(0);
    }

    /* Start a new game. */
    private void newGame() {
        promptSeed(smallFont);
        long seed = processSeed();
        if (seed == 0) {
            newGame();
            return;
        }
        ranDom = new Random(seed);
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        TETile[][] world = new TETile[width][height];

        Generator = new MapGen(world, ranDom, width, height);
        WORLD = Generator.Generate();

        AVATAR = placePlayer(WORLD, Generator);
        WORLD[AVATAR.Px][AVATAR.Py] = PLAYER;

        ter.renderFrame(WORLD);

        controlPlayer();
    }

    /* After entering a game, make player perform actions if allowed. */
    private void controlPlayer() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            String key = String.valueOf(StdDraw.nextKeyTyped());
            switch (key) {
                case "A":
                case "a":
                case "W":
                case "w":
                case "S":
                case "s":
                case "D":
                case "d":
                    if (destinationEval(key)) {
                        WORLD = AVATAR.movePlayer(Destination, WORLD);
                        StdDraw.show();
                    }
                    break;
                case ":":
                    confirmSave();
                    break;
            }
        }
    }

    void confirmSave() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char entry = StdDraw.nextKeyTyped();
            if (entry == 'Q' || entry == 'q') {
                try {
                    saveGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }
    }

    void stringSave(LinkedList<String> list) {
        String key = list.peekFirst();
        if (key.equals("Q") || key.equals("q")) {
            try {
                saveGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Save and exit the game. */
    private void saveGame() throws IOException {
        ObjectOutputStream OUT = new ObjectOutputStream(new FileOutputStream("gameSave.txt"));
        OUT.writeObject(this);
        System.exit(0);
    }

    /* Check if destination position can be moved to. */
    boolean destinationEval(String direction) {
        switch (direction) {
            case "A":
            case "a":
                Destination = new Position(AVATAR.Px - 1, AVATAR.Py);
                break;
            case "S":
            case "s":
                Destination = new Position(AVATAR.Px, AVATAR.Py - 1);
                break;
            case "D":
            case "d":
                Destination = new Position(AVATAR.Px + 1, AVATAR.Py);
                break;
            case "W":
            case "w":
                Destination = new Position(AVATAR.Px, AVATAR.Py + 1);
                break;
            default:
                return false;
        }
        return (!IMPASSIBLES.contains(WORLD[Destination.Px][Destination.Py]));
    }

    /* Place the player' avatar in a random location in dungeon. */
    Player placePlayer(TETile[][] world, MapGen Generator) {
        Room room = Generator.ROOMS.get(ranDom.nextInt(Generator.ROOMS.size()));
        Position P = room.TILES.get(ranDom.nextInt(room.TILES.size()));
        return new Player(P.Px, P.Py, WORLD[P.Px][P.Py]);
    }

    /* Ask player to enter a seed. */
    private void promptSeed(Font smallFont) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(width / 2, height / 2, "Please enter seed then press S.");
        StdDraw.show();
    }

    /* Take a seed from player input. */
    private long processSeed() {
        long seed = 0;
        long n;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            String key = String.valueOf(StdDraw.nextKeyTyped());
            if (key.equals("S") || key.equals("s")) {
                return seed;
            }
            try {
                n = Long.parseLong(key);
            } catch (NumberFormatException e) {
                continue;
            }
            seed = seed * 10 + n;
        }
    }

}
