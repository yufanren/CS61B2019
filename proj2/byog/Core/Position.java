package byog.Core;

/* the position class includes x and y position of a tile in grid. */

import java.io.Serializable;

public class Position implements Serializable {

    int Px;

    int Py;

    Position(int x, int y) {

        Px = x;
        Py = y;
    }

    public boolean equals(Object O) {

        if (this.getClass() != O.getClass()) {
            return false;
        }
        Position P = (Position) O;
        if (this.Px != P.Px) {
            return false;
        }
        if (this.Py != P.Py) {
            return false;
        }
        return true;
    }
}
