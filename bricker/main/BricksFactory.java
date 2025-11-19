package bricker.main;
import bricker.gameobjects.Brick;
import bricker.brick_strategies.*;
import java.util.Random;
import danogl.util.Vector2;
import danogl.gui.rendering.Renderable;

public class BricksFactory{
    private static final int RANDOIZER_RANGE = 10;
    private final Random random;
    public BricksFactory() {
        random = new Random();
    }
    public Brick buildBrick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        int randomNumber = random.nextInt(RANDOIZER_RANGE);
        if(1 <= randomNumber && randomNumber <= 5) {
            return new Brick(topLeftCorner, dimensions, renderable, new BasicCollisionStrategy());
        }
        else if(randomNumber == 6) {
            return new Brick(topLeftCorner, dimensions, renderable, new ExtraLivesCollisionStrategy());
        }
        else if(randomNumber == 7) {
            return new Brick(topLeftCorner, dimensions, renderable, new DoubleCollisionStrategy());
        }
        else if(randomNumber == 8) {
            return new Brick(topLeftCorner, dimensions, renderable, new BlowingBrickCollisionStrategy());
        }
        else if(randomNumber == 9) {
            return new Brick(topLeftCorner, dimensions, renderable, new ExtraPaddleCollisionStrategy());
        }
        else if(randomNumber == 10) {
            return new Brick(topLeftCorner, dimensions, renderable, new PucksCollisionStrategy());
        }
        return null; // should not happen
    }
}