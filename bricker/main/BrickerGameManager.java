package bricker.main;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import bricker.gameobjects.Wall;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager{


    public static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BALL_RADIUS = 20;
    private static final int BALL_SPEED = 200;
    private static final Vector2 PADDLE_SIZE = new Vector2(100,15);
    public static final float PADDLE_MARGIN = 2;
    private static final float WALL_WIDTH = 5;


    public BrickerGameManager(String bricker, Vector2 vector2) {
        super(bricker, vector2);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // ball creation
        Renderable ballImage =
                imageReader.readImage("bricker/assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("bricker/assets/blop.wav");
        Ball ball = new Ball(
                Vector2.ZERO,Vector2.ONES.mult(BALL_RADIUS),ballImage, collisionSound);

        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));

        ball.startMove();

        this.gameObjects().addGameObject(ball);

        float windowY = windowController.getWindowDimensions().y();
        float windowX = windowController.getWindowDimensions().x();

        // paddle creation

        Renderable paddleImage =
                imageReader.readImage("bricker/assets/paddle.png", true);
        Vector2 paddleLocation = new Vector2(
                windowX/2,
                windowY - PADDLE_SIZE.y() - PADDLE_MARGIN);
        
        Paddle paddle = new Paddle(Vector2.ZERO, PADDLE_SIZE,paddleImage , inputListener);

        paddle.setCenter(paddleLocation);

        this.gameObjects().addGameObject(paddle);

        // walls creation

        Vector2 wallSize = new Vector2(WALL_WIDTH, windowY);
        Wall leftWall = new Wall(Vector2.ZERO, wallSize,null);
        Wall rightWall = new Wall(Vector2.RIGHT.mult(windowX - WALL_WIDTH), wallSize,null);
        Wall roof = new Wall(Vector2.ZERO, new Vector2(windowX,WALL_WIDTH),null);

        this.gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(roof, Layer.STATIC_OBJECTS);
        Renderable backgroundImage = imageReader.readImage("bricker/assets/DARK_BG2_small.jpeg", true);
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),backgroundImage);
        background.setCenter(windowController.getWindowDimensions().mult(0.5f));

        this.gameObjects().addGameObject(background, Layer.BACKGROUND);

    }



    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager("Bricker", new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT));
        gameManager.run();
    }




}

