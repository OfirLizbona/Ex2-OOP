package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

public interface CollisionStrategy {
    public void onCollision(Brick caller, GameObject other);
}

