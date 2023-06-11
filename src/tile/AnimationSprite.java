package tile;

import java.awt.image.BufferedImage;

public class AnimationSprite extends Sprite{
    //Array com as imagens da animação
    BufferedImage[] spriteArray;

    public AnimationSprite(String file, int width, int height, int row, int column, int numSprites) {
        super(file, width, height);
        loadSpriteArray(row, column, numSprites);
    }

    /**
     * Método que carrega um array com uma sequência de sprites
     * @param row Linha do início da sequência
     * @param column Coluna do início da sequência
     * @param numSprites Quantidade de sprites da animação
     */
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
