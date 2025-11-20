package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;

import java.util.Random;

/**
 * Factory class for creating different types of collision strategies.
 * Supports random selection and specific basic strategy.
 */
public class CollisionStrategyFactory {

    private static final int SPECIAL_STRATEGY_NUMBER = 5;
    private static final int THIRD_STRATEGY_NUMBER = 4;
    private final BrickerGameManager gameManager;
    private final BricksManager bricksManager;
    private final Random random;

    /**
     * Constructs a new CollisionStrategyFactory.
     *
     * @param gameManager Reference to the game manager.
     * @param bricksManager Reference to the bricks manager.
     */
    public CollisionStrategyFactory(BrickerGameManager gameManager, BricksManager bricksManager) {
        this.gameManager = gameManager;
        this.bricksManager = bricksManager;
        this.random = new Random();
    }

    private CollisionStrategy buildSpecialStrategy(int randomLimit) {
        int strategyNumber = random.nextInt(randomLimit);
        switch (strategyNumber) {
            case 0:
                return new BlowingBrickCollisionStrategy(gameManager, bricksManager);
            case 1:
                return new ExtraLivesCollisionStrategy(gameManager, bricksManager);
            case 2:
                return new PucksCollisionStrategy(gameManager, bricksManager);
            case 3:
                return new ExtraPaddleCollisionStrategy(gameManager, bricksManager);
            case 4:
                return new DoubleCollisionStrategy(gameManager, bricksManager, false);
            default:
                return new BasicCollisionStrategy(gameManager, bricksManager); // Should never happen
        }
    }

    /**
     * Builds a collision strategy based on the specified type.
     *
     * @param strategy The type of strategy: "random", "special", "third", or "basic".
     * @return The constructed collision strategy.
     */
    public CollisionStrategy buildStrategy(String strategy) {
        switch (strategy) {
            case "random":
                if (random.nextBoolean()) {
                    return buildStrategy("basic");
                }
                return buildSpecialStrategy(SPECIAL_STRATEGY_NUMBER);
            case "special":
                return buildSpecialStrategy(SPECIAL_STRATEGY_NUMBER);
            case "third":
                return buildSpecialStrategy(THIRD_STRATEGY_NUMBER);
            case "basic":
                return new BasicCollisionStrategy(gameManager, bricksManager);
            default:
                return new BasicCollisionStrategy(gameManager, bricksManager); // Should never happen
        }
    }
}
