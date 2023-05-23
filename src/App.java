import java.awt.EventQueue;
import javax.swing.JFrame;

class App {
  public static void main(String[] args) {
    // Inicializa a Threadpool
    ThreadPool.init();

    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        GameFrame frame = new GameFrame();
        frame.setLocationRelativeTo(null); // inicia o GameFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.initBufferStrategy(); // inicia o buffering
        // Cria e executa o game loop
        GameLoop game = new GameLoop(frame);
        game.start();
        ThreadPool.execute(game);
      }
    });
  }
}