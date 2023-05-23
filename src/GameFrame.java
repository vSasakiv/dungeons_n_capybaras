import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public static final int HEIGHT = 720; // janela em 720p
    public static final int WIDTH = 16 * HEIGHT / 9; // formato 16:9

    // Criemos uma estratégia de buffering para tornar mais eficiente a renderização
    private BufferStrategy bufferStrategy;

    /**
     * Construtor que apenas cria a janela
     */
    public GameFrame() {
        super("Dungeons n Capybaras");
        setResizable(false);
        setSize(WIDTH, HEIGHT);

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
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
        // exemplo
        g2d.setColor(Color.BLACK);
        g2d.fillOval(gameState.x, gameState.y, 20, 20);
        g2d.fillOval(gameState.x + 20, gameState.y, 20, 20);
        
    }
}
