package bricker.main;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.Color;

/**
 * Manages the player's lives and their visual representation.
 * Displays lives both as heart icons and as a numerical counter with color coding.
 */
public class LiveManager {
    // Private static final variables
    private static final int MAX_LIVES = 4;
    private static final int HEART_SIZE = 20;
    private static final int HEART_SPACING = 25;
    private static final int HEART_MARGIN_FROM_EDGE = 10;
    private static final String HEART_IMAGE_PATH = "bricker/assets/heart.png";
    private static final int NUMERICAL_DISPLAY_SIZE = 35;

    // Private non-static variables
    private final GameObject[] hearts;
    private int lives;
    private final BrickerGameManager gameManager;
    private GameObject numericalDisplay;
    private TextRenderable textRenderable;
    private final Vector2 windowDimensions;

    // Private functions
    private void initializeNumericalDisplay() {
        Vector2 position = new Vector2(this.windowDimensions.x() - NUMERICAL_DISPLAY_SIZE,
                this.windowDimensions.y() - NUMERICAL_DISPLAY_SIZE);
        this.textRenderable = new TextRenderable(String.valueOf(lives));
        this.numericalDisplay = new GameObject(
                position,
                new Vector2(NUMERICAL_DISPLAY_SIZE, NUMERICAL_DISPLAY_SIZE),
                textRenderable);
        gameManager.addObject(numericalDisplay, Layer.UI);
        updateNumericalColor();

    }
    private void initializeHearts(int numberOfHearts) {
        for (int i = 0; i < numberOfHearts; i++) {
            addHeartAtIndex(i);
        }
    }
    private void addHeartAtIndex(int index) {
        Vector2 heartPosition = new Vector2(
                HEART_MARGIN_FROM_EDGE + index * HEART_SPACING,
                windowDimensions.y() - HEART_SIZE - HEART_MARGIN_FROM_EDGE
        );
        hearts[index] = new GameObject(
                heartPosition,
                new Vector2(HEART_SIZE, HEART_SIZE),
                gameManager.readImage(HEART_IMAGE_PATH, true)
        );
        gameManager.addObject(hearts[index], Layer.UI);
    }
    private void updateNumericalDisplay() {
        textRenderable.setString(String.valueOf(lives));
        updateNumericalColor();
    }
    private void updateNumericalColor() {
        if (lives >= 3) {
            textRenderable.setColor(Color.GREEN);
        } else if (lives == 2) {
            textRenderable.setColor(Color.YELLOW);
        } else if (lives == 1) {
            textRenderable.setColor(Color.RED);
        }
    }

    // Public functions
    /**
     * Constructs a new LiveManager.
     *
     * @param gameManager Reference to the game manager.
     * @param initialLives The starting number of lives.
     */
    public LiveManager(BrickerGameManager gameManager, int initialLives) {
        this.lives = initialLives;
        this.windowDimensions = gameManager.getWindowDims();
        this.gameManager = gameManager;
        this.hearts = new GameObject[MAX_LIVES];

        // Initialize numerical display
//        this.textRenderable = new TextRenderable(String.valueOf(lives));
//        this.numericalDisplay = new GameObject(
//                NUMERICAL_DISPLAY_POSITION,
//                new Vector2(NUMERICAL_DISPLAY_SIZE, NUMERICAL_DISPLAY_SIZE),
//                textRenderable
//        );
//        gameManager.addObject(numericalDisplay, Layer.UI);
//        updateNumericalColor();
        initializeNumericalDisplay();
        // Initialize hearts
        initializeHearts(initialLives);
    }
    /**
     * Increments the number of lives if below maximum.
     * Adds a heart icon to the UI.
     */
    public void increment() {
        if (lives < MAX_LIVES) {
            addHeartAtIndex(lives);
            lives++;
            updateNumericalDisplay();
        }
    }
    /**
     * Decrements the number of lives if above zero.
     * Removes a heart icon from the UI.
     */
    public void decrement() {
        if (lives > 0) {
            lives--;
            gameManager.removeObject(hearts[lives], Layer.UI);
            hearts[lives] = null;
            updateNumericalDisplay();
        }
    }
    /**
     * Gets the current number of lives.
     *
     * @return The current number of lives.
     */
    public int getLives() {
        return lives;
    }
}