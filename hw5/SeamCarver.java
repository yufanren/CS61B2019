import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture sourcePic;
    private double[][] energyMap;

    /* find a sequence of indices for horizontal seam. */
    public int[] findHorizontalSeam() {
        energyMap = arrayTranspose(energyMap);
        int[] hSeam = findVerticalSeam();
        energyMap = arrayTranspose(energyMap);
        return hSeam;
    }

    /* find a sequence of indices for horizontal seam. */
    public int[] findVerticalSeam() {
        double[][] energyMin = new double[energyMap.length][energyMap[0].length];
        for (int i = 0; i < energyMin[0].length; i++) {
            for (int j = 0; j < energyMin.length; j++) {
                energyMin[j][i] = energyMap[j][i] + minRoute(j, i, energyMin);
            }
        }
        return retraceRoute(energyMin);
    }

    //retrace the steps from the route of lowest energy to form a Seam.
    private int[] retraceRoute(double[][] map) {
        int[] rList = new int[map[0].length];
        int index = 0;
        double min = map[index][map[0].length - 1];
        for (int i = 0; i < map.length; i++) {
            if (map[i][map[0].length - 1] < min) {
                min = map[i][map[0].length - 1];
                index = i;
            }
        }
        rList[rList.length - 1] = index;
        for (int i = rList.length - 2; i > -1; i--) {
            int start, end;
            if (index == 0) {
                start = index;
                end = index + 2;
            } else if (index == map.length - 1) {
                start = index - 1;
                end = index + 1;
            } else {
                start = index - 1;
                end = index + 2;
            }
            min = map[start][i];
            index = start;
            for (int j = start; j < end; j++) {
                if (map[j][i] < min) {
                    min = map[j][i];
                    index = j;
                }
            }
            rList[i] = index;
        }
        return rList;
    }

    //find the minimum enegry of possible incoming routes.
    private double minRoute(int j, int i, double[][] map) {
        if (i == 0) {
            return 0;
        }
        if (j == 0) {
            return Math.min(map[j][i - 1], map[j + 1][i - 1]);
        } else if (j == map.length - 1) {
            return Math.min(map[j - 1][i - 1], map[j][i - 1]);
        }
        return Math.min(map[j][i - 1], Math.min(map[j - 1][i - 1], map[j + 1][i - 1]));
    }

    //remove horizontal seam from picture.
    public void removeHorizontalSeam(int[] seam) {
        if (validateSeam(seam)) {
            sourcePic = SeamRemover.removeHorizontalSeam(sourcePic, seam);
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }

    //remove vertical seam from picture.
    public void removeVerticalSeam(int[] seam) {
        if (validateSeam(seam)) {
            sourcePic = SeamRemover.removeVerticalSeam(sourcePic, seam);
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }

    //return energy of pixel at column x and row y.
    public double energy(int x, int y) {
        return energyMap[x][y];
    }

    //return current picture.
    public Picture picture() {
        return sourcePic;
    }

    //return picture width.
    public int width() {
        return sourcePic.width();
    }

    //return pcture height.
    public int height() {
        return sourcePic.height();
    }

    public SeamCarver(Picture picture) {
        sourcePic = picture;
        energyMap = getEnergy();
    }

    //calculate energy map for the picture.
    private double[][] getEnergy() {
        double[][] map = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                int cLeft = sourcePic.getRGB(xLower(i), j);
                int cRight = sourcePic.getRGB(xHigher(i), j);
                int cTop = sourcePic.getRGB(i, yLower(j));
                int cBottom = sourcePic.getRGB(i, yHigher(j));
                map[i][j] = getGradient(cLeft, cRight) + getGradient(cTop, cBottom);
            }
        }
        return map;
    }

    //get energy difference of one gradient.
    private double getGradient(int a, int b) {
        int dR = getRed(b) - getRed(a);
        int dG = getGreen(b) - getGreen(a);
        int dB = getBlue(b) - getBlue(a);
        int gradient = dR * dR + dG * dG + dB * dB;
        double g = (double) gradient;
        return g;
    }

    //calculate what pixel to use for calculating energy.
    //use pixel on other edge if neighbour is non-existent.
    private int xLower(int x) {
        return (x == 0) ? width() - 1 : x - 1;
    }

    private int xHigher(int x) {
        return (x == width() - 1) ? 0 : x + 1;
    }

    private int yLower(int y) {
        return (y == 0) ? height() - 1 : y - 1;
    }

    private int yHigher(int y) {
        return (y == height() - 1) ? 0 : y + 1;
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0x000000FF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0x000000FF;
    }

    private int getBlue(int rgb) {
        return (rgb) & 0x000000FF;
    }

    //Return a transposed array.
    private double[][] arrayTranspose(double[][] array) {
        double[][] a2 = new double[array[0].length][array.length];
        for (int i = 0; i < a2.length; i++) {
            for (int j = 0; j < a2[0].length; j++) {
                a2[i][j] = array[j][i];
            }
        }
        return a2;
    }

    //check if a seam is valid.
    private boolean validateSeam(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
            int dSeam = seam[i] - seam[i - 1];
            if (dSeam * dSeam > 1) {
                return false;
            }
        }
        return true;
    }
}
