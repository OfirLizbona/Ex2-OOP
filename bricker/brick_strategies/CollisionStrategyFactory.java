package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import bricker.main.LiveManager;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;

import java.util.Random;

public class CollisionStrategyFactory {


    private final BrickerGameManager gameManager;
    private final BricksManager bricksManager;
    private Random random;

    public CollisionStrategyFactory(BrickerGameManager gameManager, BricksManager bricksManager) {
        this.gameManager = gameManager;
        this.bricksManager = bricksManager;
        random = new Random();
    }

    public CollisionStrategy buildStrategy(String strategy) {
        switch (strategy) {
            case "random": {
                if (random.nextBoolean()) {
                    return buildStrategy("basic");
                }

                int StrategyNumber = random.nextInt(5);
                switch (StrategyNumber) {
                    case 0:
                        // Blowing Brick
                        return new BlowingBrickCollisionStrategy(gameManager, bricksManager);
                    case 1:
                        // Extra Lives
                        return new ExtraLivesCollisionStrategy(gameManager, bricksManager);
                    case 2:
                        // Pucks
                        //return new PucksCollisionStrategy(bricksManager, gameManager);
                        return new BasicCollisionStrategy(gameManager, bricksManager);

                    case 3:
                        // Extra Paddle
                        return new ExtraPaddleCollisionStrategy(gameManager, bricksManager);

                    case 4:
                        // Double
                        // ??
                        return new BasicCollisionStrategy(gameManager, bricksManager);

                }
                break;
            }
            case "basic":
                return new BasicCollisionStrategy(gameManager, bricksManager);

            default:
                return null;
        }

        return null;
    }
}
