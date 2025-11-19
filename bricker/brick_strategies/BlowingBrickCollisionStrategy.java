package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.SoundReader;

class BlowingBrickCollisionStrategy extends BasicCollisionStrategy{


    private static final String BLOWUP_SOUND_PATH = "bricker/assets/explosion.wav";
    private static final int BLOWUP_RADIUS = 1;

    private final Sound blowupSond;

    public BlowingBrickCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
        this.blowupSond = gameManager.readSound(BLOWUP_SOUND_PATH);
    }

     @Override
     public void onCollision(Brick caller, GameObject other) {

         super.onCollision(caller, other);

         blowupSond.play();

         // blow up all neighbours.
         int row = caller.getRow();
         int col = caller.getCol();
         for( int i = -1*(BLOWUP_RADIUS); i <= BLOWUP_RADIUS; i++) {
             if (i != 0) {
                 Brick neighbor = bricksManager.getBrick(row + i, col);
                 if (neighbor != null) {
                     neighbor.onCollisionEnter(neighbor, null);
                 }
             }
         }

         for (int j = -1*(BLOWUP_RADIUS); j <= BLOWUP_RADIUS; j++) {
             if (j != 0) {
                 Brick neighbor = bricksManager.getBrick(row, col + j);
                 if (neighbor != null) {
                     neighbor.onCollisionEnter(neighbor,null);
                 }
             }
         }
     }
 }