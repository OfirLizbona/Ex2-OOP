package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;

    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        // TODO explain why we choose this approach, also at README file.
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject caller, GameObject other) {
        gameManager.removeBrick((Brick)caller, Layer.STATIC_OBJECTS); // TODO: i dont like this down casting
    }


}

