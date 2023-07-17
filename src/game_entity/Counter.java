package game_entity;

/**
 * Classe contador utilizada para realizar medições de tempo, como
 * cooldown de armas, transição entre estados, entre outros
 */
public class Counter {
    private int counter = 0;
    private final int threshold;
    private final int increment;

    private boolean canCount = false;

    /**
     * @param threshold valor máximo do contador
     * @param increment valor incrementado ao contador a cada contagem
     */
    public Counter(int threshold, int increment) {
        this.threshold = threshold;
        this.increment = increment;
    }
    /**
     * Adiciona o incremento ao contador, desde que o mesmo seja menor que o limite
     */
    public void count(){
        if (this.counter < this.threshold)
            this.counter += this.increment;
    }

    /**
     * Atribui 0 ao contador
     */
    public void resetCounter(){
        this.counter = 0;
    }

    /**
     * Faz com que o contador conte toda vez que o método tick() é chamado
     */
    public void start(){
        this.canCount = true;
    }

    /**
     * Faz com que o contador pare de contar toda vez que o método tick() é chamado
     */
    public void stop(){
        this.canCount = false;
    }

    /**
     * Função que atualiza o contador seguindo as seguintes diretrizes:
     * - caso o contador tenha sido iniciado com start(), incrementa o counter
     * - caso o counter seja maior que o threshold, reiniciamos o contador e o paramos
     */
    public void tick(){
        if (this.canCount)
            this.count();
        if (this.verify()) {
            this.resetCounter();
            this.stop();
        }
    }

    /**
     * @return true caso o valor no contador seja maior que o limite
     */
    public boolean verify(){
        return this.counter >= this.threshold;
    }

    /**
     * @return true caso esteja contando
     */
    public boolean isCounting(){
        return this.canCount;
    }

    /**
     * @return true caso o contador seja igual a 0
     */
    public boolean isZero(){
        return this.counter == 0;
    }


    public int getIncrement() {
        return increment;
    }

    public int getThreshold() {
        return threshold;
    }

}
