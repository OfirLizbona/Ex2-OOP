package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

/**
 * Interface for defining collision behavior when a brick is hit.
 */
public interface CollisionStrategy {
    /**
     * Handles collision event between a brick and another game object.
     *
     * @param caller The brick that was involved in the collision.
     * @param other The other game object in the collision.
     */
    void onCollision(Brick caller, GameObject other);
    int getCollisionStrategiesNumber();
}

