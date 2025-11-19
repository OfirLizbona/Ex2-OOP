package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.gameobjects.Brick;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.util.Counter;
import danogl.util.Vector2;

public class BricksManager{

    private static final int ROWS_DEFAULT_LENGTH = 8;
    private static final int ROWS_DEFAULT_NUMBER = 7;
    private static final float BRICK_MARGIN = 4;
    private static final float BRICK_HEIGHT = 15;
    private int rowsNumber;
    private int rowsLength;
    private Brick[][] bricks;
    private final BrickerGameManager gameManager;
    private final LiveManager liveManager;

    private Counter bricksNumber;

    BricksManager( BrickerGameManager gameManager, String[] args) {
        this.gameManager = gameManager;
        this.liveManager = gameManager.getLiveManager();
        rowsNumber = ROWS_DEFAULT_NUMBER;
        rowsLength = ROWS_DEFAULT_LENGTH;
        if (args.length > 0) {
            rowsNumber = Integer.parseInt(args[0]);
            rowsLength = Integer.parseInt(args[1]);
        }
        bricks = new Brick[rowsNumber][rowsLength];
        generateBricks();
    }

    private void generateBricks() {

        CollisionStrategyFactory factory = new CollisionStrategyFactory(gameManager, this);
        bricksNumber = new Counter(rowsNumber * rowsLength);
        float brickWidth = (gameManager.getWindowDims().x() / rowsLength ) - BRICK_MARGIN;

        for (int row = 0; row < rowsNumber; row++) {
            for (int col = 0; col < rowsLength ; col++) {
                Vector2 currentPosition = new Vector2(
                        col * ( brickWidth + BRICK_MARGIN ) + (BRICK_MARGIN /2) ,
                        row * (BRICK_HEIGHT + BRICK_MARGIN) + (BRICK_MARGIN /2));
                CollisionStrategy collisionStrategy = factory.buildStrategy("random");
                bricks[row][col] = new Brick(currentPosition,
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        collisionStrategy, gameManager, row, col);
                gameManager.addObject(bricks[row][col], Layer.STATIC_OBJECTS);
            }
        }
    }

    public void removeBrick(int row, int col) {
        if(gameManager.removeObject(bricks[row][col], Layer.STATIC_OBJECTS)) {
            bricks[row][col] = null;
            bricksNumber.decrement();
        }
    }

    public Brick getBrick(int row, int col) {
        if (row >= 0 && row < rowsNumber && col >= 0 && col < rowsLength) {
            return bricks[row][col];
        }
        return null;
    }

    public int getBricksNumber() {
        return bricksNumber.value();
    }

}