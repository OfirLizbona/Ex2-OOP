package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
/**
 * Collision strategy that applies two random special effects simultaneously.
 * This strategy prevents infinite recursion by using a flag to limit nesting depth.
 * Package-private as it's only accessed through the CollisionStrategyFactory.
 */
class DoubleCollisionStrategy extends BasicCollisionStrategy {
    // Private static variable
    private static final int STRATEGIES_NUMBER = 2;

    // Private non-static variables
    private CollisionStrategy[] collisionStrategies;
    private CollisionStrategyFactory collisionStrategyFactory;
    private boolean isFirstDoubleStrategy;

    // Private function
    private void makeStrategies() {
        collisionStrategies = new CollisionStrategy[STRATEGIES_NUMBER];
        if(isFirstDoubleStrategy) {
            for(int i = 0; i < STRATEGIES_NUMBER; i++) {
                collisionStrategies[i] = collisionStrategyFactory.buildStrategy("special");
            }
        } else {
            for(int i = 0; i < STRATEGIES_NUMBER; i++) {
                collisionStrategies[i] = collisionStrategyFactory.buildStrategy("third");
            }
        }
    }

    // Public functions
//    public DoubleCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
//        super(gameManager, bricksManager);
//        this.collisionStrategyFactory = new CollisionStrategyFactory(gameManager, bricksManager);
//        makeStrategies();
//        this.isFirstDoubleStrategy = true;
//    }
    /**
     * Constructs a new DoubleCollisionStrategy.
     * This overload constructor allows controlling the nesting depth to prevent infinite recursion.
     *
     * @param gameManager Reference to the game manager.
     * @param bricksManager Reference to the bricks manager.
     * @param isFirstDoubleStrategy If true, allows creating nested double strategies;
     *                              if false, prevents further nesting.
     */
    public DoubleCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager, boolean isFirstDoubleStrategy) {
        super(gameManager, bricksManager);
        this.collisionStrategyFactory = new CollisionStrategyFactory(gameManager, bricksManager);
        this.isFirstDoubleStrategy = isFirstDoubleStrategy;
        makeStrategies();
    }
    /**
     * Handles collision by applying all configured collision strategies.
     *
     * @param caller The brick that was hit.
     * @param other The object that collided with the brick.
     */
    @Override
    public void onCollision(Brick caller, GameObject other) {
        super.onCollision(caller, other);
        for(CollisionStrategy strategy : collisionStrategies) {
            strategy.onCollision(caller, other);
        }
    }

}