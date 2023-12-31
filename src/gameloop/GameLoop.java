package gameloop;

import gameloop.game_states.GameState;

public class GameLoop implements Runnable {

    /*
     * Dentro de game loop devemos ter um GameFrame, onde será renderizado os
     * gráficos
     * e um GameState, onde será guardado todas as entidades e realizadas as
     * atualizações a cada tick.
     */
    private final GameFrame gameFrame;
    private GameState gameState;
    private boolean running = false;
    public Thread thread;

    /**
     * @param gameFrame GameFrame inicial para o jogo.
     */
    public GameLoop(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    /**
     * Criamos o nosso gameState e iniciamos uma thread para o jogo
     */
    public synchronized void start() {
        gameState = new GameState();
        this.gameFrame.addKeyListener(gameState.getKeyHandler());
        this.gameFrame.addMouseListener(gameState.getMouseListener());
        this.gameFrame.addMouseMotionListener(gameState.getMouseMotionListener());

        thread = new Thread(this);
        this.thread.start();
        this.running = true;
    }

    /**
     * Quando chamada o jogo para
     */
    public synchronized void stop() {
        this.running = false;
    }

    /**
     * Método que inicia o jogo, isto é, inicia o game loop, onde iremos ter ticks
     * periódicos de 15ms cada, além de atualizar a renderização.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 64; // número de nanosegundos por tick do jogo

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis(); // contador do sistema para garantir dt constante
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick; // somamos a diferença de valor / dt ao delta
            lastTime = now;
            boolean shouldRender = false;

            /*
             * Toda vez que um tempo dt passar, delta sera igual a dt/dt, igual a 1,
             * logo devemos ativar um tick e atualizar o gameState
             */
            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            /*
             * Caso tenha passado 1 tick, podemos renderizar novamente o jogo, para garantir
             * que as mudanças foram passadas para o jogador
             */
            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    /**
     * Função chamada toda vez que dt passa, sendo assim precisamos atualizar o
     * gameState
     */
    public void tick() {
        gameState.tick();
    }

    public void render() {
        gameFrame.render(gameState);
    }
}