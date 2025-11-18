package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle  extends GameObject {

    private static final float PADDLE_SPEED = 300;
    private final UserInputListener inputListener;
    private final float margin;
    private final WindowController windowController;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener keyboard listener for paddle movement
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, float margin, WindowController windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.margin = margin;
        this.windowController = windowController;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 paddleVelocity = Vector2.ZERO;

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            paddleVelocity = paddleVelocity.add(Vector2.LEFT.mult(PADDLE_SPEED));
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            paddleVelocity = paddleVelocity.add(Vector2.RIGHT.mult(PADDLE_SPEED));
        }

        if (getTopLeftCorner().x() <= 0 ){
            setTopLeftCorner(new Vector2(
                    margin,getTopLeftCorner().y()
            ));
            paddleVelocity = Vector2.ZERO;
        }
        if (getTopLeftCorner().x() >=  windowController.getWindowDimensions().x() - getDimensions().x() ) {
            setTopLeftCorner(new Vector2(
                    windowController.getWindowDimensions().x() - margin - getDimensions().x(),getTopLeftCorner().y()
            ));
            paddleVelocity = Vector2.ZERO;
        }

        setVelocity(paddleVelocity);
    }
}

