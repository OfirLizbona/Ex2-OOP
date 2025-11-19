package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import java.util.Random;

public class Ball extends GameObject {


    private static final int BALL_SPEED = 300;
    private static final String STANDARD_BALL_PATH = "bricker/assets/ball.png";
    private static final String BLOP_SOUND_PATH = "bricker/assets/blop.wav";
    private final Sound collisionSound;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, BrickerGameManager gameManager) {
         Renderable ballImage =
                 gameManager.readImage(STANDARD_BALL_PATH, true);
        this.collisionSound = gameManager.readSound(BLOP_SOUND_PATH);
        super(topLeftCorner, dimensions, ballImage);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {

        super.onCollisionEnter(other, collision);
        if (other instanceof Heart) return;
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }



    public void startMove() {
        Random rand = new Random();
        int ballXspeed = BALL_SPEED;
        int ballYspeed = BALL_SPEED;
        if (rand.nextBoolean()) {
            ballXspeed = ballXspeed * -1;
        }
        if  (rand.nextBoolean()) {
            ballYspeed = ballYspeed * -1;
        }
        setVelocity(new Vector2(ballXspeed, ballYspeed));
    }

}

