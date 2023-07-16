package game_entity;

import java.awt.*;

/**
 * Classe que guarda todos os atributos pertinentes a inimigos e outras gameEntities
 */
public class Attributes {
    private int currentHealth;
    private int currentArmor;
    private int currentMana;
    private int maxHealth;
    private int maxArmor;
    private final int maxMana;

    private final Counter regenTimer;

    private int incremented = 0;

    /**
     * @param health pontos de vida da entidade
     * @param armor pontos de armadura da entidade
     * @param mana pontos de mana/energia da entidade
     */
    public Attributes(int health, int armor, int mana, int regenRate) {
        this.maxHealth = health;
        this.currentHealth = health;
        this.maxArmor = armor;
        this.currentArmor = armor;
        this.maxMana = mana;
        this.currentMana = mana;
        this.regenTimer = new Counter(1000, regenRate);
        this.regenTimer.start();
    }

    /**
     * Copy constructor para clonarmos atributos para cada inimigo
     * @param attributes atributos a serem clonados
     */
    public Attributes(Attributes attributes){
        this.maxHealth = attributes.maxHealth;
        this.currentHealth = attributes.currentHealth;
        this.maxArmor = attributes.maxArmor;
        this.currentArmor = attributes.currentArmor;
        this.maxMana = attributes.maxMana;
        this.currentMana = attributes.currentMana;
        this.incremented = attributes.incremented;
        this.regenTimer = new Counter(attributes.regenTimer.getThreshold(), attributes.regenTimer.getIncrement());
        this.regenTimer.start();
    }

    public void tick(){
        this.regenTimer.tick();
        this.armorRegen();
    }

    /**
     * @param damage quantidade de dano que a entidade recebeu
     */
    public void takeDamage(int damage){
        if (this.currentArmor <= 0)
            this.currentHealth -= damage;
        else this.currentArmor -= damage;
        this.regenTimer.resetCounter();
        this.regenTimer.count();
        this.regenTimer.start();
    }

    public void armorRegen(){
        if (this.regenTimer.isZero()){
            if (this.currentArmor < this.maxArmor){
                this.currentArmor += 1;
                this.regenTimer.start();
            }
        }
    }

    public void restore(){
        this.currentArmor = this.maxArmor;
        this.currentHealth = this.maxHealth;
        this.currentMana = this.maxMana;
    }

    public void setIncremented(int increment){
        this.incremented = increment;
    }

    public int getIncremented(){
        return this.incremented;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setMaxArmor(int maxArmor) {
        this.maxArmor = maxArmor;
    }

    public int getMaxArmor() {
        return maxArmor;
    }

    public boolean isDead(){
        return this.currentHealth <= 0;
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
