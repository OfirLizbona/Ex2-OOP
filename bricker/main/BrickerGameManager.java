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
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager{

    // dimensions
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BALL_RADIUS = 20;
    private static final Vector2 PADDLE_SIZE = new Vector2(100,15);
    private static final float PADDLE_MARGIN = 2;
    private static final float WALL_WIDTH = 5;
    private static final float BRICK_HEIGHT = 15;

    // assets paths
    private static final String PADDLE_IMAGE_PATH = "bricker/assets/paddle.png";
    private static final String DARK_BG_PATH = "bricker/assets/DARK_BG2_small.jpeg";
    private static final String STANDARD_BALL_PATH = "bricker/assets/ball.png";
    private static final String BLOP_SOUND_PATH = "bricker/assets/blop.wav";
    private static final String BRICK_IMAGE_PATH = "bricker/assets/brick.png";

    private static final String GAME_TITLE = "Bricker";
    private static final float BRICK_MARGIN = 4;
    private static final String LOSE_PROMPT = "You lost. play again?";
    private static final int INITIAL_LIVES_NUMBER = 3;
    private static final String WIN_PROMPT = "You win! play again?";

    private final String[] args;

    private Ball ball;
    private int lives;
    private float windowY;
    private float windowX;
    private WindowController windowController;
    private Counter bricksNumber;

    public BrickerGameManager(String bricker, Vector2 vector2, String[] args) {
        super(bricker, vector2);
        this.args = args;
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowY = windowController.getWindowDimensions().y();
        windowX = windowController.getWindowDimensions().x();

        lives = INITIAL_LIVES_NUMBER;




        // paddle creation

        Renderable paddleImage =
                imageReader.readImage(PADDLE_IMAGE_PATH, true);
        Vector2 paddleLocation = new Vector2(
                windowX/2,
                windowY - PADDLE_SIZE.y() - PADDLE_MARGIN);
        
        Paddle paddle = new Paddle(Vector2.ZERO, PADDLE_SIZE,paddleImage , inputListener, PADDLE_MARGIN, windowController);

        paddle.setCenter(paddleLocation);

        this.gameObjects().addGameObject(paddle);

        // walls creation

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

        // bricks creation
        int bricksRowLength = 8;
        int bricksRowsNumber = 7;
        if (args.length == 2) {
            bricksRowLength = Integer.parseInt(args[0]);
            bricksRowsNumber = Integer.parseInt(args[1]);
        }
        bricksNumber = new Counter(bricksRowsNumber * bricksRowLength);
        float brickWidth = (windowX / bricksRowLength ) - BRICK_MARGIN;
        Renderable brickImage =  imageReader.readImage(BRICK_IMAGE_PATH,
                false);
        BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(this);
        for (int row = 0; row < bricksRowsNumber; row++) {
            for (int col = 0; col < bricksRowLength ; col++) {
                Vector2 currentPosition = new Vector2(
                        col * ( brickWidth + BRICK_MARGIN ) + (BRICK_MARGIN /2) , row * (BRICK_HEIGHT + BRICK_MARGIN) + (BRICK_MARGIN /2)
                );
                Brick brick = new Brick(currentPosition,
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage,
                        basicCollisionStrategy);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }

        // ball creation
        Renderable ballImage =
                imageReader.readImage(STANDARD_BALL_PATH, true);
        Sound collisionSound = soundReader.readSound(BLOP_SOUND_PATH);
        ball = new Ball(
                Vector2.ZERO,Vector2.ONES.mult(BALL_RADIUS),ballImage, collisionSound);

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
        if (bricksNumber.value() == 0) {
            endGame(true);
        }

        float ballHeight = ball.getCenter().y();
        if (ballHeight >= windowY) {
            lives--;
            //

            if (lives == 0) {
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


    public void removeObject(GameObject other, int layer) {
        gameObjects().removeGameObject(other, layer);
    }

    public void  removeBrick(Brick brick, int layer) {
        removeObject(brick, layer);
        bricksNumber.decrement();

    }

    private void generateBricks(int rowLength, int rowsCount) {

    }
}

