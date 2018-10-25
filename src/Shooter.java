import java.awt.*;

public class Shooter
{
	private boolean exists;
	private Image shooterIcon;
	private Projectile projectile;
	private int location; //location can be an x coordinate...?

	public Shooter(boolean exists, int location)
	{
		this.exists = exists;
		this.location = location;
	}

	public boolean isExists()
	{
		return exists;
	}

	public void setExists(boolean exists)
	{
		this.exists = exists;
	}

	public Image getShooterIcon()
	{
		return shooterIcon;
	}

	public void setShooterIcon(Image shooterIcon)
	{
		this.shooterIcon = shooterIcon;
	}

	public Projectile getProjectile()
	{
		return projectile;
	}

	public void setProjectile(Projectile projectile)
	{
		this.projectile = projectile;
	}

	public int getLocation()
	{
		return location;
	}

	public void setLocation(int location)
	{
		this.location = location;
	}
}