package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.util.Vector2;

class ExtraPaddleCollisionStrategy extends BasicCollisionStrategy{

    private static final Vector2 EXTRA_PADDLE_SIZE = new Vector2(80,15);
    private static final float EXTRA_PADDLE_MARGIN = 2;

    public ExtraPaddleCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
    }

    @Override
    public void onCollision(Brick caller, GameObject other) {
        ExtraPaddle paddle = new ExtraPaddle(Vector2.ZERO, EXTRA_PADDLE_SIZE, EXTRA_PADDLE_MARGIN, gameManager);
        paddle.setCenter(gameManager.getWindowDims().mult(0.5f));
        gameManager.addObject(paddle);
        super.onCollision(caller, other);

    }
}