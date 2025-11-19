package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import bricker.gameobjects.Wall;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager{

    // dimensions
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BALL_RADIUS = 20;
    private static final Vector2 PADDLE_SIZE = new Vector2(400,15);
    private static final float PADDLE_MARGIN = 2;
    private static final float WALL_WIDTH = 10;
    private static final float BRICK_HEIGHT = 15;

    // assets paths
    private static final String DARK_BG_PATH = "bricker/assets/DARK_BG2_small.jpeg";

    private static final String GAME_TITLE = "Bricker";
    private static final float BRICK_MARGIN = 4;
    private static final String LOSE_PROMPT = "You lost. play again?";
    private static final int INITIAL_LIVES_NUMBER = 3;
    private static final String WIN_PROMPT = "You win! play again?";

    private final String[] args;
    private Ball ball;
    // private int lives;
    private float windowY;
    private float windowX;
    private WindowController windowController;
    private LiveManager liveManager;
    private BricksManager bricksMangager;

    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;

    public BrickerGameManager(String bricker, Vector2 vector2, String[] args) {
        super(bricker, vector2);
        this.args = args;
    }


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

    private void initializeBricksManager() {
        this.bricksMangager = new BricksManager( this, args);
    }


    private void initializeLiveManager() {
        this.liveManager = new LiveManager(this, INITIAL_LIVES_NUMBER);
    }

    private void initializePaddle(){
        Vector2 paddleLocation = new Vector2(
                windowX/2,
                windowY - PADDLE_SIZE.y() - PADDLE_MARGIN);

        Paddle paddle = new Paddle(Vector2.ZERO, PADDLE_SIZE, PADDLE_MARGIN, this);
        paddle.setCenter(paddleLocation);
        this.gameObjects().addGameObject(paddle);
    }

    private void initializeWalls(){
        Vector2 wallSize = new Vector2(WALL_WIDTH, windowY);
        Wall leftWall = new Wall(Vector2.ZERO, wallSize,null);
        Wall rightWall = new Wall(Vector2.RIGHT.mult(windowX - WALL_WIDTH), wallSize,null);
        Wall roof = new Wall(Vector2.ZERO, new Vector2(windowX, WALL_WIDTH),null);
        this.gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(roof, Layer.STATIC_OBJECTS);

        Renderable backgroundImage = imageReader.readImage(
                DARK_BG_PATH, true);
        GameObject background = new GameObject(Vector2.ZERO,
                windowController.getWindowDimensions(),backgroundImage);
        background.setCenter(windowController.getWindowDimensions().mult(0.5f));
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void initializeBall() {
        ball = new Ball(
                Vector2.ZERO,Vector2.ONES.mult(BALL_RADIUS), this);
        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
        ball.startMove();
        this.gameObjects().addGameObject(ball);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        // check for a win
        if (bricksMangager.getBricksNumber() == 0) {
            endGame(true);
        }

        float ballHeight = ball.getCenter().y();
        if (ballHeight >= windowY) {
            liveManager.decrement();
            if (liveManager.getLives() == 0) {
                endGame(false);
            }
            ball.setCenter(new Vector2(windowController.getWindowDimensions().mult(0.5f)));
            ball.startMove();
        }
    }

    private void endGame(boolean isWin) {
        String prompt;
        if (isWin) {
            prompt = WIN_PROMPT;
        }
        else {
            prompt = LOSE_PROMPT;
        }

        if (windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        }
        else {
            windowController.closeWindow();
        }
    }


    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager(
                GAME_TITLE, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT), args);
        gameManager.run();
    }


    public void addObject(GameObject object, int layer) {
        gameObjects().addGameObject(object, layer);
    }
    public void addObject(GameObject object) {
        gameObjects().addGameObject(object);
    }
    
    public void removeObject(GameObject other) {
        gameObjects().removeGameObject(other);
    }

    public boolean removeObject(GameObject other, int layer) {
        return gameObjects().removeGameObject(other, layer);
    }

    public Vector2 getWindowDims() {
        return windowController.getWindowDimensions();
    }

    public Renderable readImage(String path, Boolean isTransperent ) {
        return imageReader.readImage(path, isTransperent);
    }

    public Sound readSound(String path) {
        return soundReader.readSound(path);
    }

    public UserInputListener getInputListener() {
        return inputListener;
    }

    public LiveManager getLiveManager() {
        return liveManager;
    }

    public BricksManager getBricksManager() {
        return bricksMangager;
    }

}

