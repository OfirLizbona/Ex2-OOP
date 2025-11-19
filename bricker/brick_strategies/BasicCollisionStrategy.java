package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy {
    protected final BricksManager bricksManager;
    protected final BrickerGameManager gameManager;

    public BasicCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        // TODO explain why we choose this approach, also at README file.
        this.bricksManager = bricksManager;

        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(Brick caller, GameObject other) {
        bricksManager.removeBrick(caller.getRow(), caller.getCol());
    }


}

