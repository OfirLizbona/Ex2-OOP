package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.LiveManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private final LiveManager liveManager;
    private final BrickerGameManager gameManager;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.liveManager = gameManager.getLiveManager();

    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
            liveManager.increment();
            gameManager.removeObject(this);
    }
    public void startMove() {
        setVelocity(new Vector2(0, 100));
    }
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}