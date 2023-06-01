package gameloop;

import game_entity.weapons.Projectile;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    // Criemos uma estratégia de buffering para tornar mais eficiente a renderização
    private BufferStrategy bufferStrategy;

    /**
     * Construtor do GameFrame, com tamanho determinado
     * @param width largura da janela em pixels
     * @param height altura da janela em pixels
     */
    public GameFrame(int width, int height) {
        super("Dungeons n Capybaras");
        setResizable(false);
        setSize(width, height);
    }

    /**
     * Inicia a estratégia de buffering
     */
    public void initBufferStrategy() {
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * renderiza todos os objetos em tela, a cada atualização de tick
     * @param gameState gameState atual
     */
    public void render(GameState gameState) {
        do {
            do {
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, gameState);
                } finally {
                    graphics.dispose();
                }
            } while (bufferStrategy.contentsRestored());

            bufferStrategy.show();
            Toolkit.getDefaultToolkit().sync();
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Renderiza um gráfico em particular
     * @param g2d gráfico a ser desenhado
     * @param gameState Estado atual do game
     */
    private void doRendering(Graphics2D g2d, GameState gameState) {
        // RENDERIZA O QUE PRECISAR
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // exemplo
        g2d.setColor(Color.BLACK);

        gameState.tileManager.draw(g2d);
        //gameState.tm.render(g2d);

        for (Projectile p : gameState.getProjectiles()){
            p.draw(g2d, gameState.player);
        }

        gameState.player.draw(g2d);
        g2d.setColor(Color.red);
        g2d.fillOval(
                (int) (gameState.testEnemy.getWorldPosX() - gameState.player.getWorldPosX() + Constants.WIDTH/2.0),
                (int) (gameState.testEnemy.getWorldPosY() - gameState.player.getWorldPosY()+ Constants.HEIGHT/2.0),
                10, 10);
    }
}