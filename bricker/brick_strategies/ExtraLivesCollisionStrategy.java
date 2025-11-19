package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Heart;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import bricker.main.LiveManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

class ExtraLivesCollisionStrategy extends BasicCollisionStrategy {
    private static final float HEART_SIZE = 15;
    private final String HEART_IMAGE_PATH = "bricker/assets/heart.png";
    private final LiveManager liveManager;

    public ExtraLivesCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
        this.liveManager = gameManager.getLiveManager();
    }

    @Override
    public void onCollision(Brick caller, GameObject other) {
        super.onCollision(caller, other);
        Renderable heartImage = gameManager.readImage(HEART_IMAGE_PATH, true);
        Heart heart = new Heart(Vector2.ZERO,
                new Vector2(HEART_SIZE, HEART_SIZE),heartImage,gameManager);
        heart.setCenter(caller.getCenter());
        gameManager.addObject(heart);
        heart.startMove();
    }
}
