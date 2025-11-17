package bricker.brick_strategies;

import danogl.GameObject;

public interface CollisionStrategy {
    public void onCollision(GameObject calle, GameObject other);
}

