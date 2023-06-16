package src;

import gameloop.Constants;
import gameloop.GameFrame;
import gameloop.GameLoop;

import java.awt.EventQueue;
import javax.swing.JFrame;

class App {

  public static void runGame () {
    EventQueue.invokeLater(() -> {
      GameFrame frame = new GameFrame(Constants.WIDTH, Constants.HEIGHT);
      frame.setLocationRelativeTo(null); // inicia o game-loop
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.initBufferStrategy(); // inicia o buffering
      // Cria e executa o game loop
      GameLoop game = new GameLoop(frame);
      game.start();
    });
  }

  public static void main(String[] args) {
    App.runGame();
  }
}