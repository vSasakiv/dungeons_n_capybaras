package game_entity;

import java.awt.*;

/**
 * Classe que guarda todos os atributos pertinentes a inimigos e outras gameEntities
 */
public class Attributes {
    int currentHealth, currentArmor, currentMana;
    int maxHealth, maxArmor, maxMana;

    /**
     * @param health pontos de vida da entidade
     * @param armor pontos de armadura da entidade
     * @param mana pontos de mana/energia da entidade
     */
    public Attributes(int health, int armor, int mana) {
        this.maxHealth = health;
        this.currentHealth = health;
        this.maxArmor = armor;
        this.currentArmor = armor;
        this.maxMana = mana;
        this.currentMana = mana;
    }

    /**
     * @param damage quantidade de dano que a entidade recebeu
     */
    public void takeDamage(int damage){
        if (this.currentArmor == 0)
            this.currentHealth -= damage;
        else this.currentArmor -= damage;
    }

    /**
     * @param g2d Desenha barras de vida, armadura e mana no canto da tela
     */
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.RED);
        g2d.fillRect(50, 50, this.currentHealth*50, 15);
        g2d.setColor(Color.black);
        g2d.drawRect(50, 50, this.maxHealth*50 + 1, 16);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(50, 70, this.currentArmor*50, 15);
        g2d.setColor(Color.black);
        g2d.drawRect(50, 70, this.maxArmor*50 + 1, 16);
        g2d.setColor(Color.cyan);
        g2d.fillRect(50, 90, this.currentMana, 15);
        g2d.setColor(Color.black);
        g2d.drawRect(50, 90, this.maxMana + 1, 16);
    }
}
