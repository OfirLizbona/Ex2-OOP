package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.gameobjects.Brick;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Manages the creation, tracking, and removal of bricks in the game.
 * Creates a grid of bricks with random collision strategies.
 */
public class BricksManager {

    private static final int ROWS_DEFAULT_LENGTH = 4;
    private static final int ROWS_DEFAULT_NUMBER = 4;
    private static final float BRICK_MARGIN = 4;
    private static final float BRICK_HEIGHT = 15;

    private final int rowsNumber;
    private final int rowsLength;
    private final Brick[][] bricks;
    private final BrickerGameManager gameManager;
    private final LiveManager liveManager;
    private final Counter bricksNumber;

    /**
     * Constructs a new BricksManager.
     * Package-private constructor as it's only created by BrickerGameManager.
     *
     * @param gameManager Reference to the game manager.
     * @param args Command line arguments: [numRows, numCols] for brick grid dimensions.
     */
    BricksManager(BrickerGameManager gameManager, String[] args) {
        this.gameManager = gameManager;
        this.liveManager = gameManager.getLiveManager();
        this.rowsNumber = (args.length > 0) ? Integer.parseInt(args[0]) : ROWS_DEFAULT_NUMBER;
        this.rowsLength = (args.length > 1) ? Integer.parseInt(args[1]) : ROWS_DEFAULT_LENGTH;
        this.bricks = new Brick[rowsNumber][rowsLength];
        this.bricksNumber = new Counter(rowsNumber * rowsLength);
        generateBricks();
    }

    private void generateBricks() {
        CollisionStrategyFactory factory = new CollisionStrategyFactory(gameManager, this);
        float brickWidth = (gameManager.getWindowDims().x() / rowsLength) - BRICK_MARGIN;

        for (int row = 0; row < rowsNumber; row++) {
            for (int col = 0; col < rowsLength; col++) {
                Vector2 currentPosition = new Vector2(
                        col * (brickWidth + BRICK_MARGIN) + (BRICK_MARGIN / 2),
                        row * (BRICK_HEIGHT + BRICK_MARGIN) + (BRICK_MARGIN / 2));
                CollisionStrategy collisionStrategy = factory.buildStrategy("random");
                bricks[row][col] = new Brick(currentPosition,
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        collisionStrategy, gameManager, row, col);
                gameManager.addObject(bricks[row][col], Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Removes a brick at the specified grid position.
     *
     * @param row The row index of the brick.
     * @param col The column index of the brick.
     */
    public void removeBrick(int row, int col) {
        if (gameManager.removeObject(bricks[row][col], Layer.STATIC_OBJECTS)) {
            bricks[row][col] = null;
            bricksNumber.decrement();
        }
    }

    /**
     * Gets the brick at the specified grid position.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The brick at that position, or null if out of bounds or already removed.
     */
    public Brick getBrick(int row, int col) {
        if (row >= 0 && row < rowsNumber && col >= 0 && col < rowsLength) {
            return bricks[row][col];
        }
        return null;
    }

    /**
     * Gets the current number of bricks remaining in the game.
     *
     * @return The number of bricks that haven't been destroyed yet.
     */
    public int getBricksNumber() {
        return bricksNumber.value();
    }
}