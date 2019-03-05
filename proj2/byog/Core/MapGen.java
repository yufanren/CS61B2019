package byog.Core;

import java.util.ArrayList;
import java.util.Random;
import byog.TileEngine.*;
import java.util.HashSet;
import java.util.LinkedList;

class MapGen {

    final Random RANDOM;

    int WIDTH;

    int HEIGHT;

    TETile[][] MAP;

    HashSet<Position> POSITIONS = new HashSet();

    LinkedList<Room> ROOMS;

    ArrayList<Position> WALLS = new ArrayList();

    ArrayList<Position> DOORS = new ArrayList();

    MapGen(TETile[][] world, long seed, int width, int height) {

        MAP = world;
        WIDTH = width;
        HEIGHT = height;
        RANDOM = new Random(seed);
    }

    TETile[][] Generate() {

        fillMAP();

        roomGen();

        HallPlower Per = new HallPlower(this);
        Per.plow();

        Wall Wal = new Wall(this);
        Wal.placeWall();

        placeDoor();

        return MAP;
    }

    // Generate a list of rooms. Place down the tiles.
    private void roomGen() {
        ROOMS = new LinkedList();

        placeBRoom(RandomUtils.uniform(RANDOM, 6, 8));
        placeRoom(RandomUtils.uniform(RANDOM, 24, 27));

        /* Remove overlaped rooms. Place tiles for remaining rooms. */
        for (Room R : ROOMS) {
            for (Position P : R.TILES) {
                MAP[P.Px][P.Py] = Tileset.FLOOR;
            }
        }
    }
    // Create n number of small rooms.
    // For each parameter set: 0-LLcorner Px, 1-LLcorner Py, 2-Width, 3-Height.
    private void placeRoom(int n) {
        int[] parameters = new int[4];
        for (int i = 0; i < n; i += 1) {
            parameters[0] = RandomUtils.uniform(RANDOM, 2, WIDTH * 7 / 8);
            parameters[1] = RandomUtils.uniform(RANDOM, 2, HEIGHT * 4 / 5);
            parameters[2] = RandomUtils.uniform(RANDOM, 2, 5);
            parameters[3] = RandomUtils.uniform(RANDOM, 2, 5);
            Room temp = new Room(this, new Position(parameters[0], parameters[1]), parameters[2], parameters[3]);
            if (temp.OVERLAP) {
                temp.destroyRoom();
            } else {
                addRoom(temp);
            }
        }
    }

    /* Add rooms in order of llCorner stat. */
    private void addRoom(Room R) {
        if (ROOMS.size() == 0) {
            ROOMS.add(R);
            return;
        }
        for (int i = 0; i < ROOMS.size(); i += 1) {
            if (R.minRoom(ROOMS.get(i))) {
                ROOMS.add(i, R);
                return;
            }
        }
        ROOMS.add(R);
    }

    // Create n number of large rooms.
    // For each parameter set: 0-LLcorner Px, 1-LLcorner Py, 2-Width, 3-Height.
    private void placeBRoom(int n) {
        int[] parameters = new int[4];
        for (int i = 0; i < n; i += 1) {
            parameters[0] = RandomUtils.uniform(RANDOM, 2, WIDTH * 7 / 8);
            parameters[1] = RandomUtils.uniform(RANDOM, 2, HEIGHT * 4 / 5);
            parameters[2] = RandomUtils.uniform(RANDOM, 6, 10);
            parameters[3] = RandomUtils.uniform(RANDOM, 5, 10);
            BossRoom temp = new BossRoom(this, new Position(parameters[0], parameters[1]), parameters[2], parameters[3]);
            if (temp.OVERLAP) {
                temp.destroyRoom();
            } else {
                ROOMS.add(temp);
            }
        }

    }

    // Fill map with nothing tiles, replacing null.
    // Generate a list of all positions in the map.
    private void fillMAP() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                MAP[x][y] = Tileset.NOTHING;
            }
        }
        for (int x = 2; x < WIDTH - 2; x += 1) {
            for (int y = 2; y < HEIGHT - 2; y += 1) {
                POSITIONS.add(new Position(x, y));
            }
        }
    }

    /* place door in the dungeon. */
    private void placeDoor() {
        Position P = WALLS.get(RandomUtils.uniform(RANDOM, 0, WALLS.size()));
        DOORS.add(P);
        WALLS.remove(P);
        MAP[P.Px][P.Py] = Tileset.LOCKED_DOOR;
    }

    // This main method is for testing only.
    /*
   public static void main(String[] args) {
        int w = 80;
        int h = 30;
        long seed = 11936;
        TERenderer ter = new TERenderer();
        ter.initialize(w, h);
        TETile[][] world = new TETile[w][h];

        MapGen Generator = new MapGen(world, seed, w, h);
        world = Generator.Generate();

        ter.renderFrame(world);
    } */
}
