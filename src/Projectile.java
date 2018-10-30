<<<<<<< HEAD
public class Projectile {
<<<<<<< HEAD
=======
public class Projectile
{
	double angle;
	double velocity;


	public Projectile(double angle, double velocity)
	{
		this.angle = angle;
		this.velocity = velocity;
	}

	public double getX(double time)
	{
		return Math.cos(Math.toRadians(angle)) * velocity * time;
	}

	public double getY(double time)
	{
		return Math.sin(Math.toRadians(angle)) * velocity * time -
				(.5 * 9.8 * time * time);
	}
>>>>>>> ca24d43b1e852420212908a83be22a4bda328f92
}
=======
    public Object getLoc() {
        return null;
    }
}
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b
