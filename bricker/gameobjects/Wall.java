package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an invisible wall that bounds the game area.
 * Walls prevent game objects from leaving the play area (left, right, and top).
 */
public class Wall extends GameObject {
    /**
     * Constructs a new Wall instance.
     *
     * @param topLeftCorner Position of the wall in window coordinates.
     * @param dimensions Width and height of the wall.
     * @param renderable The renderable image for the wall (typically null for invisible walls).
     */
    public Wall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }
}
