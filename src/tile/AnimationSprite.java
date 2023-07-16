package tile;

import java.awt.image.BufferedImage;

public class AnimationSprite extends Sprite{
    BufferedImage[] spriteArray;  //Array com as imagens da animação

    /**
     * Lida com sprites sequênciais de animação
     * @param file caminho para o spritesheet
     * @param width comprimento dos sprites
     * @param height altura dos sprites
     * @param row linha dos sprites no spritesheet
     * @param column coluna dos sprites no spritesheet
     * @param numSprites tamanho da sequência de sprites
     */
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
