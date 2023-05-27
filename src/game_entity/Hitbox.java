package game_entity;

public class Hitbox {
    // Implementação para uma região retangular
    private float minX; // menor coordenada x do retângulo
    private float maxX; // maior coordenada x do retângulo
    private float minY; // menor coordenada y do retângulo
    private float maxY; // maior coordenada y do retângulo

    /**
     * Construtor da Hitbox, com coordenadas já definidas
     * @param minX menor coordenada x do retângulo
     * @param maxX maior coordenada x do retângulo
     * @param minY menor coordenada y do retângulo
     * @param maxY maior coordenada y do retângulo
     */
    public Hitbox(float minX, float maxX, float minY, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Obtém menor coordenada x da hitbox
     * @return minX
     */
    public float getMinX() {
        return minX;
    }

    /**
     * Obtém menor coordenada y da hitbox
     * @return minY
     */
    public float getMinY() {
        return minY;
    }

    /**
     * Obtém maior coordenada x da hitbox
     * @return maxX
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     * Obtém maior coordenada y da hitbox
     * @return maxY
     */
    public float getMaxY() {
        return maxY;
    }

    /**
     * Define menor coordenada X da hitbox
     * @param minX novo valor para minX
     */
    public void setMinX(float minX) {
        this.minX = minX;
    }

    /**
     * Define menor coordenada Y da hitbox
     * @param minY novo valor para minY
     */
    public void setMinY(float minY) {
        this.minY = minY;
    }

    /**
     * Define maior coordenada x da hitbox
     * @param maxX novo valor para maxX
     */
    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    /**
     * Define maior coordenada y da hitbox
     * @param maxY novo valor para maxY
     */
    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    /**
     * Desloca a hitbox segundo um vetor de deslocamento
     * @param displacement vetor de deslocamento
     */
    public void move(Vector displacement) {
        setMinX(this.getMinX() + displacement.x);
        setMaxX(this.getMaxX() + displacement.x);
        setMinY(this.getMinY() + displacement.y);
        setMaxY(this.getMaxY() + displacement.y);
    }

    /**
     * Verifica se a hitbox está colidindo com outra
     * @param hitbox hitbox cuja colisão com a da classe será avaliada
     * */
    public boolean isHitting(Hitbox hitbox) {
        boolean veredict = false;

        if(hitbox.getMinX() > this.getMaxX() || hitbox.getMaxX() < this.getMinX()) {
            // disjunção horizontal
            veredict = false;
        } else if(hitbox.getMinY() > this.getMaxY() || hitbox.getMaxY() < this.getMinY()){
            // disjunção vertical
            veredict = false;
        } else {
            // intersecção não vazia
            veredict = true;
        }

        return  veredict;
    }
}
