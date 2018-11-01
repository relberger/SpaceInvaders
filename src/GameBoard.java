package src;

import java.awt.*;
import java.util.*;
import java.awt.image.ImageObserver;

public class GameBoard {
    private int cellSize;
    private Shooter shooter;
    public Projectile projectile;
    private int score = 0;
    private ArrayList<Square> gameBoard;
    private ArrayList<Alien> aliens;
    private ImageObserver imgObs;
    public final int BOARD_SIZE = 15;
    public Direction movement;
    Graphics2D g;
    private boolean shooting;

    GameBoard(int cellSize) {
        this.cellSize = cellSize;
        this.shooter = new Shooter(BOARD_SIZE - 1, (BOARD_SIZE - 1) / 2, true);

        gameBoard = new ArrayList<>();
        aliens = new ArrayList<>();
        generateGameBoard();
        generateAliens();
        gameBoard.get(getSquareIndex(shooter.getLocation().getY(), shooter.getLocation().getX())).setEntity(Square.Entity.Shooter);
    }

    private void generateGameBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Square square = new Square(i, j);
                gameBoard.add(square);
            }
        }
    }

    private void generateAliens() {
        for (int i = 0; i < (5 * BOARD_SIZE); i++) {
            Square square = gameBoard.get(i);
            square.setEntity(Square.Entity.Alien);
            Alien alien = new Alien(square.getX(), square.getY(), cellSize, cellSize);
            aliens.add(alien);
        }
    }


    public void moveShooter() {

        Square current = shooter.getLocation();
        Square newLoc;
        boolean[] bounds = checkBounds();
        if (movement == Direction.LEFT && !bounds[0]) {
            newLoc = gameBoard.get(getSquareIndex(current.getY() - 1, current.getX()));
            shooter.setLocation(newLoc);
        } else if (movement == Direction.RIGHT && !bounds[1]) {
            newLoc = gameBoard.get(getSquareIndex(current.getY() + 1, current.getX()));
            shooter.setLocation(newLoc);
        }
        shooter.getLocation().setEntity(Square.Entity.Shooter);
    }


    private boolean[] checkBounds() {
        Square sq = shooter.getLocation();
        boolean tooFarLeft = sq.getY() == 0;
        boolean tooFarRight = sq.getY() == BOARD_SIZE - 1;
        boolean[] bounds = {tooFarLeft, tooFarRight};
        return bounds;
    }


    private void exit() {
        System.out.println("Final Score: " + getScore());
        System.exit(0);
    }

    int getScore() {
        return score;
    }


    private boolean removeAlienIfShot() {
        boolean dead = false;
        for (int i = aliens.size() - 1; i >= 0; i--) {
            int squareLoc = getSquareIndex(aliens.get(i).getRow(), aliens.get(i).getCol());
            if (squareLoc != -1) {
                Square alien = gameBoard.get(squareLoc);
                if (projectile.getLocation().equals(alien)) {
                    alien.setEntity(Square.Entity.Empty);
                    Alien deadAlien = aliens.get(i);
                    deadAlien.setAlive(false);
                    aliens.remove(i);
                    dead = true;
                    score += 10;
                    break;

                }
            }
        }
        return dead;
    }


    void paint(Graphics graphics) {

        g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintShooter(g);
        paintAliens(g);
        if (shooting) {
            paintShot(g);
        }

    }

    private void paintShooter(Graphics2D g) {
        int col = shooter.getLocation().getY();
        int row = shooter.getLocation().getX();
        g.drawImage(shooter.getShooterIcon(), col * cellSize, row * cellSize, imgObs);

    }

    private int getSquareIndex(int row, int col) {
        for (int i = 0; i < gameBoard.size(); i++) {
            if (gameBoard.get(i).getY() == row) {
                if (gameBoard.get(i).getX() == col) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void paintAliens(Graphics2D g) {

        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);
            if (alien.isAlive()) {
                g.drawImage(alien.getAlienPic(), alien.getCol() * cellSize, alien.getRow() * cellSize, imgObs);
            }
        }
    }

    private void paintShot(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(projectile.getLocation().getX() * cellSize, projectile.getLocation().getY() * cellSize, cellSize / 5, cellSize);
        sleep();

    }

    public void shoot() {
        for (int loc = BOARD_SIZE - 1; loc >= 0; loc--) {
            Square current = new Square(Square.Entity.Projectile, shooter.getLocation().getY(), loc);
            projectile = new Projectile(current);
            shooting = true;
            if (removeAlienIfShot()) {
                if (isGameOver()) {
                    System.out.println("Your final score is: " + score);
                    System.exit(0);
                }
                break;
            }
        }

    }

    public boolean isGameOver() {

        return aliens.isEmpty();
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}