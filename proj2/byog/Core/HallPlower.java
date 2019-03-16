package byog.Core;

import java.io.Serializable;
import java.util.*;

import byog.TileEngine.*;

class HallPlower implements Serializable {

    //protected Room START;

    //protected Room END;

    private MapGen WORLD;


    HallPlower(MapGen world) {

        WORLD = world;
    }

    /* Main method for forming hallways in the dungeon. */
    void plow() {

        passAssign(WORLD.ROOMS);
        LinkedList<Room> unlinkedRooms = new LinkedList<Room>(WORLD.ROOMS);
        LinkedList<Room> linkedRooms = new LinkedList<Room>();
        connectRoom(unlinkedRooms, linkedRooms, unlinkedRooms.get(0), unlinkedRooms.get(RandomUtils.uniform(WORLD.RANDOM, 1, roomRemain(unlinkedRooms))));
    }
    /*Connect all rooms in the unlinked rooms list. */
    private void connectRoom(LinkedList<Room> unlinked, LinkedList<Room> linked,  Room a, Room b) {
        if (unlinked.size() <= 1) {
            return;
        }
        loopRoom(unlinked, linked, a, b);
    }


    /* Main loop for connecting all rooms. */
    private void loopRoom(LinkedList<Room> unlinked, LinkedList<Room> linked,  Room a, Room b) {

        Connect(a, b);
        a.hallWay -= 1;
        unlinked.remove(b);
        if (a.hallWay == 1) {
            linked.add(b);
        } else {
            unlinked.remove(a);
            linked.add(a);
            unlinked.add(0, b);
        }
        if (unlinked.size() == 1) {
            Connect(unlinked.get(0), linked.get(RandomUtils.uniform(WORLD.RANDOM, 0, linked.size())));
            return;
        }
        loopRoom(unlinked, linked, unlinked.get(0), unlinked.get(RandomUtils.uniform(WORLD.RANDOM, 1, roomRemain(unlinked))));
    }


    /* pick a method to connect two rooms. */
    private void Connect(Room a, Room b) {
        boolean CASE = RandomUtils.bernoulli(WORLD.RANDOM, 0.5);
        if (CASE = true) {
            plowV(a.randomTile(), b.randomTile());
        } else {
            plowH(a.randomTile(), b.randomTile());
        }
    }

    /* See how many rooms are left in the list. */
    private int roomRemain(LinkedList<Room> unlinkedRooms) {
        int SIZE = unlinkedRooms.size();
        if (SIZE > 4) {
            return 4;
        } else {
            return SIZE;
        }

    }

    /* Plow vertically then horizontally. */
    private void plowV(Position a, Position b) {

        for (int i = a.Py; i != b.Py; i += (b.Py - a.Py) / Math.abs(a.Py - b.Py)) {
            WORLD.MAP[a.Px][i] = Tileset.FLOOR;
        }
        for (int i = a.Px; i != b.Px; i += (b.Px - a.Px) / Math.abs(a.Px - b.Px)) {
            WORLD.MAP[i][b.Py] = Tileset.FLOOR;
        }
    }

    /* Plow horizontally then vertically. */
    private void plowH(Position a, Position b) {

        for (int i = a.Px; i != b.Px; i += (b.Px - a.Px) / Math.abs(a.Px - b.Px)) {
            WORLD.MAP[i][a.Py] = Tileset.FLOOR;
        }
        for (int i = a.Py; i != b.Py; i += (b.Py - a.Py) / Math.abs(a.Py - b.Py)) {
            WORLD.MAP[b.Px][i] = Tileset.FLOOR;
        }
    }

    /* Assign number of hallways for each room. */
    private void passAssign(LinkedList<Room> rooms) {
        for (Room R : WORLD.ROOMS) {
            boolean CASE;
            if (R.getClass() == BossRoom.class) {
                CASE = RandomUtils.bernoulli(WORLD.RANDOM, 0.5);
            } else {
                CASE = RandomUtils.bernoulli(WORLD.RANDOM, 0.01);
            }
            if (CASE) {
                R.hallWay = 2;
            } else {
                R.hallWay = 1;
            }
        }
    }



}
