import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private int uLLon;
    private int uLLat;
    private int lRLon;
    private int lRLat;

    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double[] lonDPPs = getDPPperLevel(new double[8]);
        Integer depth = getLevel(params.get("lrlon"),
                params.get("ullon"), params.get("w"), lonDPPs);
        //Calculate depth of image.
        results.put("depth", depth);
        double l = Math.pow(2, depth);
        long length = (long) l;
        String[][] grid = getFiles(depth, length, params);
        if (grid[0].length == 0) {
            results.put("query_success", false);
        } else {
            results.put("query_success", true);
        }
        results.put("render_grid", grid);
        results.put("raster_ul_lon", calAxis(uLLon, length, MapServer.ROOT_ULLON,
                MapServer.ROOT_LRLON));
        results.put("raster_lr_lon", calAxis(lRLon, length, MapServer.ROOT_ULLON,
                MapServer.ROOT_LRLON));
        results.put("raster_ul_lat", calAxis(uLLat, length, MapServer.ROOT_ULLAT,
                MapServer.ROOT_LRLAT));
        results.put("raster_lr_lat", calAxis(lRLat, length, MapServer.ROOT_ULLAT,
                MapServer.ROOT_LRLAT));
        return results;
    }

    /* Calculate Lon/Lat for a given point on the map, for theraster. */
    private double calAxis(int a, long length, double lbound, double hbound) {
        return lbound + a * (hbound - lbound) / length;
    }

    /* Find the files to display at specified depth. */
    private String[][] getFiles(long depth, long length, Map<String, Double> params) {

        Integer[] xFiles = getCoordinates(params.get("ullon"),
                params.get("lrlon"), length, MapServer.ROOT_ULLON, MapServer.ROOT_LRLON);
        // reversed input of low vs high.
        Integer[] yFiles = getCoordinates(params.get("ullat"),
                params.get("lrlat"), length, MapServer.ROOT_ULLAT, MapServer.ROOT_LRLAT);
        uLLon = xFiles[0];
        lRLon = xFiles[1] + 1;
        uLLat = yFiles[0];
        lRLat = yFiles[1] + 1;
        LinkedList<Integer> xList = boundToList(xFiles, length);
        LinkedList<Integer> yList = boundToList(yFiles, length);
        String[][] grid = listToGrid(xList, yList, depth);
        return grid;
    }

    /* Convert list of X and Y numbers to the files for display. */
    private String[][] listToGrid(LinkedList<Integer> xlist, LinkedList<Integer> ylist,
                                  long depth) {
        String[][] grid = new String[ylist.size()][xlist.size()];
        for (int i = 0; i < ylist.size(); i += 1) {
            for (int j = 0; j < xlist.size(); j += 1) {
                grid[i][j] = fNameConstructor(ylist.get(i), xlist.get(j), depth);
            }
        }
        return grid;
    }

    /* create file name based on depth and grid position. */
    private String fNameConstructor(int y, int x, long depth) {
        String fileName = "d" + String.valueOf(depth) + "_x"
                + String.valueOf(x) + "_y" + String.valueOf(y) + ".png";
        return fileName;
    }

    /* Convert bounds to a list of valid numbers for locating files. */
    private LinkedList<Integer> boundToList(Integer[] bounds, long length) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = bounds[0]; i <= bounds[1]; i += 1) {
            if (i >= 0 && i <= length) {
                list.add(i);
            }
        }
        return list;
    }

    /*Find the x coordinate of files to display. */
    private Integer[] getCoordinates(double low, double high, long length,
                                               double lBound, double hBound) {
        Integer[] list = new Integer[2];
        list[0] = getBound(low - lBound, (hBound - lBound) / length);
        list[1] = getBound(high - lBound, (hBound - lBound) / length);
        return list;
    }

    private int getBound(double bound, double lengthPerFile) {
        double countDouble = bound / lengthPerFile;
        int countInt = (int) countDouble;

        return countInt;
    }

    /* Find the appropriate image quality to display. Return the greatest LonDPP that is less than
    *   or equal to the LonDPP of the query, at maximun depth 7. */
    private Integer getLevel(double lrlon, double ullon, double width, double[] lonDPPs) {
        double lonDPP = (lrlon - ullon) / width;
        for (int i = 0; i < 8; i += 1) {
            if (lonDPPs[i] <= lonDPP) {
                return i;
            }
        }
        return 7;
    }

    /* Calculate the LonDPP for each level of zoom. */
    private double[] getDPPperLevel(double[] dPPs) {
        for (int i = 0; i < 8; i += 1) {
            dPPs[i] = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (256 * Math.pow(2, i));
        }
        return dPPs;
    }
}
