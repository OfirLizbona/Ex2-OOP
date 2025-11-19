package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;

import java.awt.event.KeyEvent;

public class Paddle  extends GameObject {

    private static final float PADDLE_SPEED = 300;
    private static final String PADDLE_IMAGE_PATH = "bricker/assets/paddle.png";
    private final UserInputListener inputListener;
    private final float margin;
    private final BrickerGameManager gameManager;

    /**
     *
     * @param topLeftCorner
     * @param dimensions
     * @param margin
     * @param gameManager
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, float margin,
            BrickerGameManager gameManager) {
        Renderable paddleImage =
                 gameManager.readImage(PADDLE_IMAGE_PATH, true);
        super(topLeftCorner, dimensions, paddleImage);
        this.gameManager = gameManager;
        this.inputListener = gameManager.getInputListener();
        this.margin = margin;
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
        if (getTopLeftCorner().x() >=  gameManager.getWindowDims().x() - getDimensions().x() ) {
            setTopLeftCorner(new Vector2(
                    gameManager.getWindowDims().x() - margin - getDimensions().x(),getTopLeftCorner().y()
            ));
            paddleVelocity = Vector2.ZERO;
        }

        setVelocity(paddleVelocity);
    }
}

