package bricker.main;

import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.Color;

public class LiveManager {
    private static final int MAX_LIVES = 4;
    private static final int HEART_SIZE = 20;
    private static final int HEART_SPACING = 25;
    private static final int HEART_MARGIN_FROM_EDGE = 10;
    private static final String HEART_IMAGE_PATH = "bricker/assets/heart.png";
    private static final int NUMERICAL_DISPLAY_SIZE = 50;
    private static final Vector2 NUMERICAL_DISPLAY_POSITION = new Vector2(650, 450);
    
    private GameObject[] hearts;
    private int lives;
    private BrickerGameManager gameManager;
    private GameObject numericalDisplay;
    private TextRenderable textRenderable;
    private Vector2 windowDimensions;
    private ImageReader imageReader;

    public LiveManager(int initialLives, Vector2 windowDimensions, 
                      BrickerGameManager gameManager, ImageReader imageReader) {
                        
        this.lives = initialLives;
        this.windowDimensions = windowDimensions;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.hearts = new Heart[MAX_LIVES];
        
        // Initialize numerical display
        textRenderable = new TextRenderable(String.valueOf(lives));
        numericalDisplay = new GameObject(
            NUMERICAL_DISPLAY_POSITION,
            new Vector2(NUMERICAL_DISPLAY_SIZE, NUMERICAL_DISPLAY_SIZE),
            textRenderable
        );
        gameManager.addObject(numericalDisplay);
        updateNumericalColor();
        
        // Initialize hearts
        initializeHearts(initialLives);
    }
    
    private void initializeHearts(int numberOfHearts) {
        for (int i = 0; i < numberOfHearts; i++) {
            addHeartAtIndex(i);
        }
    }
    
    private void addHeartAtIndex(int index) {
        Vector2 heartPosition = new Vector2(
            HEART_MARGIN_FROM_EDGE + index * HEART_SPACING,
            windowDimensions.y() - HEART_SIZE - HEART_MARGIN_FROM_EDGE
        );
        hearts[index] = new Heart(
            heartPosition,
            new Vector2(HEART_SIZE, HEART_SIZE),
            imageReader.readImage(HEART_IMAGE_PATH, true),
            this
        );
        gameManager.addObject(hearts[index]);
    }
    
    public void increment() {
        if (lives < MAX_LIVES) {
            addHeartAtIndex(lives);
            lives++;
            updateNumericalDisplay();
        }
    }
    
    public void decrement() {
        if (lives > 0) {
            lives--;
            gameManager.removeObject(hearts[lives]);
            hearts[lives] = null;
            updateNumericalDisplay();
        }
    }
    
    private void updateNumericalDisplay() {
        textRenderable.setString(String.valueOf(lives));
        updateNumericalColor();
    }
    
    private void updateNumericalColor() {
        if (lives >= 3) {
            textRenderable.setColor(Color.GREEN);
        } else if (lives == 2) {
            textRenderable.setColor(Color.YELLOW);
        } else if (lives == 1) {
            textRenderable.setColor(Color.RED);
        }
    }
    
    public GameObject[] getHearts() {
        return hearts;
    }
    
    public int getLives() {
        return lives;
    }
}