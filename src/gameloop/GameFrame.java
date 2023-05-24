package gameloop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    // Criemos uma estratégia de buffering para tornar mais eficiente a renderização
    private BufferStrategy bufferStrategy;

    /**
     * Construtor que apenas cria a janela
     */
    public GameFrame(int width, int height) {
        super("Dungeons n Capybaras");
        setResizable(false);
        setSize(width, height);

    }

    /**
     * Função que inicia a estratégia de buffering
     */
    public void initBufferStrategy() {
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * @param gameState gameState atual, para ser possível renderização de entidades
     *                  Método chamada toda vez que há alguma atualização de tick,
     *                  que renderiza os
     *                  todos os objetos necessários na tela
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
     * @param g2d       instância do objeto Graphics2D, utilizado para desenhar
     *                  coisas na tela
     * @param gameState Estado atual do game, para ser possível desenhar os objetos
     *                  presentes.
     */
    private void doRendering(Graphics2D g2d, GameState gameState) {
        // RENDERIZA O QUE PRECISAR
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // exemplo
        g2d.setColor(Color.BLACK);
        g2d.fillOval(gameState.getPlayer().getPosX(), gameState.getPlayer().getPosY(), 20, 20);
    }
}
