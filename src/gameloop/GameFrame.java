package gameloop;

import gameloop.game_states.GameState;
import gameloop.game_states.State;

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
     * Renderiza todos os objetos em tela, a cada atualização de tick
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
        State atual = gameState.getActualState();
        atual.draw(g2d);
    }
}