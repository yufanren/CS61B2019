package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.Random;

public class addHexagon {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private TETile Tile;

    public addHexagon()  {
        Tile = Tileset.NOTHING;
    }

    public addHexagon(TETile T) {
        Tile = T;
    }

    //* Main method to call for drawing a hexagon. */
    public void HexGen(TETile[][] world, int x, Position P) {
        int[] Row = LengthGen(x);
        Position[] Start = PositionGen(new Position(P.Px - x / 2, P.Py), x);
        for (int i = 0; i < x * 2; i += 1) {
            DrawRow(world, Start[i], Row[i]);
        }
    }
    //* Generate a array of row lengths. */
    private int[] LengthGen(int x) {
        int[] lengths = new int[x * 2];
        for (int i = 0; i < x; i += 1) {
            lengths[i] = i * 2 + x;
        }
        for (int i = x; i < x * 2; i += 1) {
            lengths[i] = x * 3 - 2 - (2 * (i - x));
        }
        return lengths;
    }

    //* Generate a array of starting positions. */
    private Position[] PositionGen(Position P, int x) {
        Position[] positions = new Position[x * 2];
        for (int i = 0; i < x; i += 1) {
            positions[i] = new Position(P.Px - i, P.Py - i);
        }
        for (int i = x; i < x * 2; i += 1) {
            positions[i] = new Position(P.Px - 2 * x + 1 + i, P.Py - i);
        }
        return positions;
    }

    //* Draw a row of a hexagon. */
    private void DrawRow(TETile[][] world, Position P, int length) {
        for (int i = 0; i < length; i += 1) {
            world[P.Px + i][P.Py] = Tile;
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

        addHexagon Hex1 = new addHexagon(Tileset.GRASS);
        Hex1.HexGen(world, 4, new Position(50, 20));
        Hex1.HexGen(world, 5, new Position(50, 49));

        ter.renderFrame(world);
    }

}
