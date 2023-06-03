package tile;

import java.awt.image.BufferedImage;

public class AnimationSprite extends Sprite{
    BufferedImage[] spriteArray;
    public AnimationSprite(String file, int width, int height, int row, int column, int numSprites) {
        super(file, width, height);
        loadSpriteArray(row, column, numSprites);
    }

    private void loadSpriteArray (int row, int column, int numSprites) {
        this.spriteArray = new BufferedImage[numSprites];
        for (int i = 0; i < numSprites; i++) {
            spriteArray[i] = this.getSprite(row, column + i);
        }
    }

    public BufferedImage[] getSpriteArray() {
        return spriteArray;
    }
}
