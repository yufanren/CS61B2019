package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.Random;

public class Tesselation {
    private static final long SEED = 316600;
    private static final Random RANDOM = new Random(SEED);

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private static Position Start;

    //*Find out where is the bottom left hexagon starts. */
    private static Position Bleft(Position P, int size) {
        return new Position(P.Px - size * 2 + 1, P.Py - size);
    }

    //*Find out where is the bottom right hexagon starts. */
    private static Position Bright(Position P, int size) {
        return new Position(P.Px + size * 2 - 1, P.Py - size);
    }

    //*Draw a column of Hexagons with number of N. */
    private static void DrawCol(TETile[][] world, Position P, int size, int N) {
        if (N == 0) {
            return;
        }
        addHexagon Hex = new addHexagon(randomTile());
        Hex.HexGen(world, size, P);
        DrawCol(world, new Position(P.Px, P.Py - size * 2), size, N - 1);
    }

    //*Make random tile. */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.WATER;
            case 2: return Tileset.FLOWER;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.TREE;
            default: return Tileset.FLOOR;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Start = new Position(50, 49);
        int Size = 5;

        DrawCol(world, Start, Size, 5);
        DrawCol(world, Bleft(Start, Size), Size, 4);
        DrawCol(world, Bleft(Bleft(Start, Size), Size), Size, 3);
        DrawCol(world, Bright(Start, Size), Size, 4);
        DrawCol(world, Bright(Bright(Start, Size), Size), Size, 3);

        ter.renderFrame(world);
    }
}
