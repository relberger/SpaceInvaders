import java.awt.*;
import java.util.*;


class GameBoard {

    private Shooter shoot;
    private Projectile proj;
    private int score = 0;
    private ArrayList<Square> alienList;
    public final int BOARD_COLUMNS = 15;
    public final int BOARD_ROWS = 12;
    private Direction movement;
    Graphics2D g;

    GameBoard() {
        this.shoot = new Shooter(true);
        this.proj = new Projectile();
        alienList = new ArrayList<>();
        generateAliens();
        update();
    }

    void update() {
        moveShooter();
    }


    private void generateAliens() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                Square square = new Square(Square.Entity.Alien, i, j);
                alienList.add(square);
            }
        }

    }

    private void moveShooter() {

        if (movement == Direction.LEFT) {
            moveShooterLeft();
        } else if (movement == Direction.RIGHT) {
            moveShooterRight();
        }
    }

    void moveShooterLeft() {
        if (!checkBounds()) {
            movement = Direction.LEFT;
        }
    }

    void moveShooterRight() {
        if (!checkBounds()) {
            movement = Direction.RIGHT;
        }
    }


    private boolean checkBounds() {
        Square sq = shoot.getLocation();
        boolean tooFarLeft = sq.getX() < 0;
        boolean tooFarRight = sq.getX() >= BOARD_COLUMNS;

        return tooFarLeft || tooFarRight;
    }


    private void exit() {
        System.out.println("Final Score: " + getScore());
        System.exit(0);
    }

    int getScore() {
        return score;
    }

    private boolean removeAlienIfShot() {
        for (int i = 0; i < alienList.size(); i++) {
            Square alien = alienList.get(i);
            if (proj.getLoc().equals(alien)) {
                alienList.remove(i);
                return true;
            }
        }
        return false;
    }


    void paint(Graphics graphics) {

        g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintShooter(g);
        paintAliens(g);
    }

    private void paintShooter(Graphics2D g) {

    }

    private void paintAliens(Graphics2D g) {

    }

    private void paintShot(Graphics2D g) {

    }

    public void shoot() {
        paintShot(g);
        removeAlienIfShot();
    }
}