package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        /*if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        } */

        int seed = 1; //Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new java.util.Random(seed);
    }

    public String generateRandomString(int n) {
        if (n == 1) {
            return String.valueOf(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return String.valueOf(CHARACTERS[rand.nextInt(CHARACTERS.length)]) + generateRandomString(n - 1);
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        if (!gameOver) {
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 15));
            StdDraw.textLeft(1, height - 1, "Round: " + String.valueOf(round));
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[(round % ENCOURAGEMENT.length)]);
        }
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            drawFrame(String.valueOf(letters.charAt(i)));
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String s = "";
        drawFrame(s);
        for (int i = n; i > 0; i -= 1) {
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                drawFrame(s);
            } else {
                i += 1;
                StdDraw.pause(500);
                continue;
            }
        }
        return s;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        playerTurn = false;

        while (!gameOver) {
            playerTurn = false;
            String targetString = generateRandomString(round);
            flashSequence(targetString);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);
            if (userInput.equals(targetString)) {
                drawFrame("Great Job! Next Round!");
                StdDraw.pause(1000);
                round += 1;
            } else {
                gameOver = true;
                drawFrame("You Lose! Practice for another ten year!");
                StdDraw.pause(2000);
                drawFrame("The best you can do is round " + round + ", Pathetic!");
            }
        }

        //TODO: Establish Game loop
    }

}
