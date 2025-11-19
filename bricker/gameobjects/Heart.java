package bricker.gameobjects;

import bricker.main.LiveManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private final LiveManager liveManager;
    private final boolean isCollectable;
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, LiveManager liveManager) {
        super(topLeftCorner, dimensions, renderable);
        this.liveManager = liveManager;
        this.isCollectable = false;
    }
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, LiveManager liveManager, boolean isCollectable) {
        super(topLeftCorner, dimensions, renderable);
        this.liveManager = liveManager;
        this.isCollectable = isCollectable;
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (isCollectable) {
            liveManager.increment();
        }
    }
    public void startMove() {
        setVelocity(new Vector2(0, 100));
    }
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}