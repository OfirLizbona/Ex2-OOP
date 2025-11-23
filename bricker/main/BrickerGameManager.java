package bricker.main;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import bricker.gameobjects.Wall;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Main game manager for the Bricker game.
 * Manages game initialization, game loop, and win/lose conditions.
 */
public class BrickerGameManager extends GameManager {

    // Private static final variables
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BALL_RADIUS = 20;
    private static final Vector2 PADDLE_SIZE = new Vector2(400,15);
    private static final float PADDLE_MARGIN = 2;
    private static final float WALL_WIDTH = 10;
    //private static final float BRICK_HEIGHT = 15;
    private static final String DARK_BG_PATH = "bricker/assets/DARK_BG2_small.jpeg";
    private static final String GAME_TITLE = "Bricker";
    //private static final float BRICK_MARGIN = 4;
    private static final String LOSE_PROMPT = "You lost. play again?";
    private static final int INITIAL_LIVES_NUMBER = 3;
    private static final String WIN_PROMPT = "You win! play again?";

    // Private non-static variables
    private final String[] args;
    private Ball ball;
    private float windowY;
    private float windowX;
    private WindowController windowController;
    private LiveManager liveManager;
    private BricksManager bricksManager;
    private boolean hasExtraPaddle;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;

    // Private functions
    private void initializeBricksManager() {
        this.bricksManager = new BricksManager(this, args);
    }
    private void initializeLiveManager() {
        this.liveManager = new LiveManager(this, INITIAL_LIVES_NUMBER);
    }
    private void initializePaddle() {
        Vector2 paddleLocation = new Vector2(
                windowX / 2,
                windowY - PADDLE_SIZE.y() - PADDLE_MARGIN);

        Paddle paddle = new Paddle(Vector2.ZERO, PADDLE_SIZE, PADDLE_MARGIN, this);
        paddle.setCenter(paddleLocation);
        this.gameObjects().addGameObject(paddle);
    }
    private void initializeWalls() {
        Vector2 wallSize = new Vector2(WALL_WIDTH, windowY);
        Wall leftWall = new Wall(Vector2.ZERO, wallSize, null);
        Wall rightWall = new Wall(Vector2.RIGHT.mult(windowX - WALL_WIDTH), wallSize, null);
        Wall roof = new Wall(Vector2.ZERO, new Vector2(windowX, WALL_WIDTH), null);
        this.gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(roof, Layer.STATIC_OBJECTS);

        Renderable backgroundImage = imageReader.readImage(DARK_BG_PATH, true);
        GameObject background = new GameObject(Vector2.ZERO,
                windowController.getWindowDimensions(), backgroundImage);
        background.setCenter(windowController.getWindowDimensions().mult(0.5f));
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }
    private void initializeBall() {
        ball = new Ball(Vector2.ZERO,Vector2.ONES.mult(BALL_RADIUS), this);
        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
        ball.startMove();
        this.gameObjects().addGameObject(ball);
    }
    private void checkForGameEnd() {
        // Check for a win
        if (bricksManager.getBricksNumber() == 0) {
            endGame(true);
        }

        // Check if ball fell below screen
        float ballHeight = ball.getCenter().y();
        if (ballHeight >= windowY) {
            liveManager.decrement();
            if (liveManager.getLives() == 0) {
                endGame(false);
            }
            ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
            ball.startMove();
        }
    }
    private void endGame(boolean isWin) {
        String prompt;
        if (isWin) {
            prompt = WIN_PROMPT;
        } else {
            prompt = LOSE_PROMPT;
        }

        if (windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    // Public functions
    /**
     * Constructs a new BrickerGameManager instance.
     *
     * @param windowTitle The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param args Command line arguments for number of brick rows and columns.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, String[] args) {
        super(windowTitle, windowDimensions);
        this.args = args;
    }
    /**
     * Initializes the game by creating all game objects and setting up the game state.
     *
     * @param imageReader Used to read images from files.
     * @param soundReader Used to read sounds from files.
     * @param inputListener Used to read user input.
     * @param windowController Used to control the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowY = windowController.getWindowDimensions().y();
        this.windowX = windowController.getWindowDimensions().x();
        this.hasExtraPaddle = false;

        // Initialize LiveManager
        initializeLiveManager();

        // Initialize Paddle
        initializePaddle();

        // walls creation
        initializeWalls();

        // bricks creation
        initializeBricksManager();


        // ball creation
        initializeBall();
    }
    /**
     * TODO - command for the function
     * */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }
    /**
     * Adds a game object to the specified layer.
     *
     * @param object The game object to add.
     * @param layer The layer to add the object to.
     */
    public void addObject(GameObject object, int layer) {
        gameObjects().addGameObject(object, layer);
    }
    /**
     * Adds a game object to the default layer.
     *
     * @param object The game object to add.
     */
    public void addObject(GameObject object) {
        gameObjects().addGameObject(object);
    }
    /**
     * Removes a game object from all layers.
     *
     * @param other The game object to remove.
     */
    public void removeObject(GameObject other) {
        gameObjects().removeGameObject(other);
    }
    /**
     * Removes a game object from a specific layer.
     *
     * @param other The game object to remove.
     * @param layer The layer to remove the object from.
     * @return True if the object was removed, false otherwise.
     */
    public boolean removeObject(GameObject other, int layer) {
        return gameObjects().removeGameObject(other, layer);
    }
    /**
     * Gets the window dimensions.
     *
     * @return The window dimensions as a Vector2.
     */
    public Vector2 getWindowDims() {
        return windowController.getWindowDimensions();
    }
    /**
     * Reads an image from the specified path.
     *
     * @param path The path to the image file.
     * @param isTransparent Whether the image has transparency.
     * @return The renderable image.
     */
    public Renderable readImage(String path, Boolean isTransparent) {
        return imageReader.readImage(path, isTransparent);
    }
    /**
     * Reads a sound from the specified path.
     *
     * @param path The path to the sound file.
     * @return The sound object.
     */
    public Sound readSound(String path) {
        return soundReader.readSound(path);
    }
    /**
     * Gets the user input listener.
     *
     * @return The input listener.
     */
    public UserInputListener getInputListener() {
        return inputListener;
    }
    /**
     * Gets the live manager instance.
     *
     * @return The live manager.
     */
    public LiveManager getLiveManager() {
        return liveManager;
    }
    /**
     * Gets the bricks manager instance.
     *
     * @return The bricks manager.
     */
//    public BricksManager getBricksManager() {
//        return bricksManager;
//    }
    /**
     * Checks if an extra paddle is currently active.
     *
     * @return True if an extra paddle exists, false otherwise.
     */
    public boolean hasExtraPaddle() {
        return hasExtraPaddle;
    }
    /**
     * Sets whether an extra paddle is currently active.
     *
     * @param hasExtraPaddle True if an extra paddle should be marked as active.
     */
    public void setHasExtraPaddle(boolean hasExtraPaddle) {
        this.hasExtraPaddle = hasExtraPaddle;
    }
    /**
     * Gets the ball radius constant.
     *
     * @return The ball radius in pixels.
     */
    public int getBallRadius() {
        return BALL_RADIUS;
    }
    /**
     * Main entry point for the Bricker game.
     *
     * @param args Command line arguments: [numRows, numCols] for brick grid dimensions.
     */
    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager(
                GAME_TITLE, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT), args);
        gameManager.run();
    }
}

