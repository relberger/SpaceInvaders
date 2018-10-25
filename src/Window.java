import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Window extends JFrame {

    private Engine engine;

    private static final int SQUARE_SIZE = 40;

    private Window() {
        engine = createEngine();
        setWindowProperties();
    }

    private Engine createEngine() {

        Container cp = getContentPane();
        GameBoard gameBoard = new GameBoard();
        Engine engine = new Engine(gameBoard);

        int canvasWidth = SQUARE_SIZE * gameBoard.BOARD_COLUMNS;
        int canvasHeight = SQUARE_SIZE * gameBoard.BOARD_ROWS;
        engine.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        addKeyListener(new MyKeyAdapter());

        cp.add(engine);

        return engine;
    }

    private void setWindowProperties() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Space Invaders - Score: 0");
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);// Center window
    }

    private void startGame(Engine engine) {
        Thread th = new Thread(engine);
        th.start();
    }

    /**
     * Contains the game loop.
     */
    private class Engine extends JPanel implements Runnable {

        private GameBoard gameBoard;
        private boolean running = false;

        private Engine(GameBoard gameBoard) {
            this.gameBoard = gameBoard;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            setBackground(new Color(0,0,0));

            gameBoard.paint(graphics);
        }

        public void run() {

            long lastTime = System.nanoTime();
            double elapsedTime = 0.0;
            double FPS = 15.0;

            // Game loop.
            while (true) {

                long now = System.nanoTime();
                elapsedTime += ((now - lastTime) / 1_000_000_000.0) * FPS;
                lastTime = System.nanoTime();

                if (elapsedTime >= 1) {
                    gameBoard.update();
                    setTitle("Snake - Score: " + gameBoard.getScore());
                    elapsedTime--;

                }

                sleep();
                repaint();
            }
        }

    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent keyEvent) {

            if (!engine.running) {
                startGame(engine);
                engine.running = true;
            }

            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                engine.gameBoard.moveShooterLeft();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                engine.gameBoard.moveShooterRight();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                engine.gameBoard.shoot();
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}