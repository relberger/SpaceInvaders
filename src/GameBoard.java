package src;

import java.awt.*;
import java.util.*;
import java.awt.image.ImageObserver;

public class GameBoard
{

	private int cellSize;
	private Shooter shooter;
	public Projectile projectile;
	private int score = 0;
	private ArrayList<Square> gameBoard;
	private ArrayList<Alien> aliens;
	private ImageObserver imgObs;
	public final int BOARD_COLUMNS = 15;
	public final int BOARD_ROWS = 12;
	public Direction movement;
	private boolean gameOver;
	Graphics2D g;

	GameBoard(int cellSize)
	{
		this.cellSize = cellSize;
		this.shooter = new Shooter(BOARD_ROWS - 1, (BOARD_COLUMNS - 1) / 2, true);

		gameBoard = new ArrayList<>();
		aliens = new ArrayList<>();
		generateGameBoard();
		generateAliens();
	}

	private void generateGameBoard()
	{
		for (int i = 0; i < BOARD_ROWS; i++)
		{
			for (int j = 0; j < BOARD_COLUMNS; j++)
			{
				Square square = new Square(i, j);
				gameBoard.add(square);
			}
		}
	}

	private void generateAliens()
	{
		for (int i = 0; i < (5 * BOARD_COLUMNS); i++)
		{
			Square square = gameBoard.get(i);
			square.setEntity(Square.Entity.Alien);
			Alien alien = new Alien(square.getX(), square.getY(), cellSize, cellSize);
			aliens.add(alien);
		}
	}


	public void moveShooter()
	{

		Square current = shooter.getLocation();
		Square newLoc;
		boolean[] bounds = checkBounds();
		if (movement == Direction.LEFT && !bounds[0])
		{
			newLoc = new Square(Square.Entity.Shooter, current.getX(), current.getY() - 1);
			shooter.setLocation(newLoc);
		}
		else if (movement == Direction.RIGHT && !bounds[1])
		{
			newLoc = new Square(Square.Entity.Shooter, current.getX(), current.getY() + 1);
			shooter.setLocation(newLoc);
		}
	}


	private boolean[] checkBounds()
	{
		Square sq = shooter.getLocation();
		boolean tooFarLeft = sq.getY() == 0;
		boolean tooFarRight = sq.getY() == BOARD_COLUMNS - 1;
		boolean[] bounds = {tooFarLeft, tooFarRight};
		return bounds;
	}


	private void exit()
	{
		System.out.println("Final Score: " + getScore());
		System.exit(0);
	}

	int getScore()
	{
		return score;
	}


/*    private void removeAlienIfShot() {
        for (int i = 0; i < gameBoard.size(); i++) {
            Square alien = gameBoard.get(i);
            if (projectile.getLocation().equals(alien)) {
                alien.setEntity(Square.Entity.Empty);
                Alien deadAlien = aliens.get(i);
                deadAlien.setAlive(false);
                score += 10;
                break;

            }
        }
    }*/


	void paint(Graphics graphics)
	{

		g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		paintShooter(g);
		paintAliens(g);
	}

	private void paintShooter(Graphics2D g)
	{
		int col = shooter.getLocation().getY();
		int row = shooter.getLocation().getX();
		gameBoard.get(getSquareIndex(col, row));
		g.drawImage(shooter.getShooterIcon(), col * cellSize, row * cellSize, imgObs);
	}

	public int getSquareIndex(int row, int col)
	{
		for (int i = 0; i < gameBoard.size(); i++)
		{
			if (gameBoard.get(i).getY() == row)
			{
				if (gameBoard.get(i).getX() == col)
				{
					return i;
				}
			}
		}
		return -1;
	}

	private void paintAliens(Graphics2D g)
	{

		for (int i = 0; i < aliens.size(); i++)
		{
			Alien alien = aliens.get(i);
			if (alien.isAlive())
			{
				g.drawImage(alien.getAlienPic(), alien.getCol() * cellSize, alien.getRow() * cellSize, imgObs);
			}
		}
	}

	private void paintShot(Graphics2D g)
	{
		for (int row = shooter.getLocation().getY(); row < 12; row++)
		{
			if (gameBoard.get(getSquareIndex(row, shooter.getLocation().getX())).getEntity() != Square.Entity.valueOf("Alien"))
			{
				Thread shootThread = new Thread();
				shootThread.start();

				g.setColor(Color.white);
				g.fillOval(shooter.getLocation().getY(), row, (shooter.getLocation().getY() * cellSize),
						(shooter.getLocation().getX() * cellSize));
				System.out.println("shoot");

				try
				{
					shootThread.sleep(500);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void shoot()
	{
		projectile = new Projectile(shooter.getLocation().getX());
		paintShot(g);
		//removeAlienIfShot();
	}

	public boolean isGameOver()
	{
		return gameOver;
	}
}