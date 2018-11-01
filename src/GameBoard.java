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
		gameBoard.get(getSquareIndex(shooter.getLocation().getY(), shooter.getLocation().getX())).setEntity(Square.Entity.Shooter);
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
			newLoc = gameBoard.get(getSquareIndex(current.getY() - 1, current.getX()));
			shooter.setLocation(newLoc);
		}
		else if (movement == Direction.RIGHT && !bounds[1])
		{
			newLoc = gameBoard.get(getSquareIndex(current.getY() + 1, current.getX()));
			shooter.setLocation(newLoc);
		}
		shooter.getLocation().setEntity(Square.Entity.Shooter);
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


    private void removeAlienIfShot(int row, int col) {
		Square alien = gameBoard.get(getSquareIndex(row, col));
		alien.setEntity(Square.Entity.Empty);
		Alien deadAlien = aliens.get(getAlienIndex(row, col));
		deadAlien.setAlive(false);
		score += 10;
    }


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

	public int getAlienIndex(int row, int col)
	{
		for (int i = 0; i < aliens.size(); i++)
		{
			if (aliens.get(i).getRow() == row)
			{
				if (aliens.get(i).getCol() == col)
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
		for (int column = shooter.getLocation().getX(); column >= 0; column--)
		{
			int row = shooter.getLocation().getY();
			Square square = gameBoard.get(getSquareIndex(row, column));

			if (square.getEntity() != Square.Entity.valueOf("Alien"))
			{square.setEntity(Square.Entity.Projectile);

				g.setColor(Color.white);
				g.fillOval(square.getX(), square.getY(), 20, 40);
				System.out.println("shoot" + square.getX() + square.getY() + square.getEntity());

				try
				{
					Thread.sleep(200);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				removeAlienIfShot(square.getX(), square.getY());
				square.setEntity(Square.Entity.Empty);
				return;
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