package game_entity.mobs;

import game_entity.Counter;
import game_entity.Vector;

/**
 * Classe que representa uma estratégia de inimigo passiva, isto é, de um inimigo que tenta
 * evitar contato próximo com o player
 */
public class PassiveStrategy implements EnemyStrategy{
    private EnemyState state; // Estado atual da estratégia
    private final int forgetDistance; // Distância da qual o inimigo se esquece do player e para de o seguir
    private final int detectDistance; // Distância da qual o inimigo detecta o player e o tenta atingir
    private final int safeDistance; // O inimigo sempre tenta manter uma distância mínima do player
    private final Counter patrolCounter; // Contador para tempo de patrulha
    private final Counter patrolCDCounter; // Contador para tempo entre as patrulhas
    private Vector direction; // Vetor direção do próximo movimento do inimigo
    private boolean shouldShoot = false; // true caso deva atirar

    /**
     * @param forgetDistance Distância da qual o inimigo se esquece do player e para de o seguir
     * @param detectDistance Distância da qual o inimigo detecta o player e o tenta atingir
     * @param safeDistance O inimigo sempre tenta manter uma distância mínima do player
     * @param patrolTime tempo de patrulha
     * @param patrolCdTime tempo entre as patrulhas
     */
    public PassiveStrategy(int forgetDistance, int detectDistance, int safeDistance, int patrolTime, int patrolCdTime) {
        this.state = EnemyState.PATROL;
        this.forgetDistance = forgetDistance;
        // distância a partir da qual o inimigo esquece do player, vai para estado PATROL
        this.detectDistance = detectDistance;
        this.safeDistance = safeDistance;
        // distância a partir da qual o inimigo detecta o player, vai para estado ACTIVE
        // cria os contadores
        this.patrolCounter = new Counter(patrolTime, 1);
        this.patrolCounter.resetCounter();
        this.patrolCDCounter = new Counter(patrolCdTime, 1);
        this.patrolCDCounter.resetCounter();
    }

    /**
     * @return Clone de uma estratégia passiva
     */
    @Override
    public PassiveStrategy clone(){
        return new PassiveStrategy(
                this.forgetDistance,
                this.detectDistance,
                this.safeDistance,
                this.patrolCounter.getThreshold(),
                this.patrolCDCounter.getThreshold()
        );
    }

    @Override
    public Vector newDirection(Vector pos, Vector playerPos) {
        this.patrolCounter.tick(); // atualizamos ambos os contadores
        this.patrolCDCounter.tick();
        // Vector distância entre o player e o inimigo
        Vector distanceVector = new Vector(
                playerPos.x - pos.x,
                playerPos.y - pos.y);
        // verificamos a distância entre o player e o inimigo e mudamos seu state baseado nela
        checkPlayer((int) distanceVector.module());
        // dependendo do state atual, ou o inimigo está em patrulha, ou está ativo
        switch (this.state){
            case PATROL -> patrol();
            case ACTIVE -> active(distanceVector);
        }
        return this.direction;
    }

    /**
     * @param distance distância do inimigo até o player
     */
    private void checkPlayer(int distance){
        if (distance <= this.detectDistance) this.state = EnemyState.ACTIVE;
        else if (distance >= this.forgetDistance) this.state = EnemyState.PATROL;
    }

    private void patrol(){
        if (patrolCDCounter.isZero() && this.patrolCounter.isZero()) {
            this.direction = Vector.randomUnitVector(); // caso devemos patrulhar, geramos uma direção aleatória
            this.patrolCounter.start(); // iniciamos o contador da patrulha
            this.patrolCDCounter.start(); // iniciamos o contador do cooldown da patrulha
        }
        else if (patrolCounter.isCounting())  // caso o contador da patrulha esteja contando
            this.patrolCDCounter.stop(); // paramos o contador do cooldown da patrulha para diferir de 1, e possamos separar os períodos
        else {
            patrolCDCounter.start();
            this.direction = new Vector(0, 0); // caso esteja no CD, direção é nula.
        }
        this.shouldShoot = false; // não deve atirar durante a patrulha
    }
    private void active(Vector distanceVector){
        this.shouldShoot = false; // não deve atirar se estiver fugindo
        // Caso o player chegue muito perto, o inimigo tenta fugir para a direção oposta
        if (distanceVector.module() <= this.safeDistance)
            this.direction = Vector.rotateVector(Vector.unitVector(distanceVector), 180);
        // Caso o player esteja na distância segura, para e tenta atirar no player
        else {
            this.direction = new Vector(0, 0);
            this.shouldShoot = true;
        }
    }
    @Override
    public boolean shouldShoot() {
        return this.shouldShoot;
    }
}