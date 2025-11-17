package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {


    private static final int BALL_SPEED = 200;
    private final Sound collisionSound;

    private int collisionCounter = 0;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }
    @Override
    public void update(float delta) {
        super.update(delta);

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionCounter ++;
        System.out.println("Bounce!");
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

    public int getCollisionCounter() {
        return collisionCounter;
    }

}

