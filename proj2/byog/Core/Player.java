package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

class Player implements Serializable {

    int Px;
    int Py;
    private TETile TILE;
    //ArrayList<Item> ITEMS;
    //String DIRECTION;

    Player(int x, int y, TETile T) {
        Px = x;
        Py = y;
        TILE = T;
        //ITEMS = new ArrayList();
    }

    TETile[][] movePlayer(Position P, TETile[][] world) {
        world[Px][Py] = TILE;
        world[Px][Py].draw(Px, Py);
        Px = P.Px;
        Py = P.Py;
        TILE = world[Px][Py];
        world[Px][Py] = Tileset.PLAYER;
        world[Px][Py].draw(Px, Py);
        return world;
    }

    TETile[][] moveNoDraw(Position P, TETile[][] world) {
        world[Px][Py] = TILE;
        Px = P.Px;
        Py = P.Py;
        TILE = world[Px][Py];
        world[Px][Py] = Tileset.PLAYER;
        return world;
    }

    /*void moveTo(int x, int y) {
        int xTemp = Px;
        int yTemp = Py;
        Px = x;
        Py = y;
    } */
}
