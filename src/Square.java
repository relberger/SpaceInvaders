/**
 * Represents a square on the board. Each Square has an Entity. An Entity
 * is what is on the Square: either there is the shooter, an alien, the projectile,
 * or it is empty.
 */
public class Square {

    private Entity entity;
    private int x;
    private int y;


    /**
     * Construct the Square with nothing on it.
<<<<<<< HEAD
     * @param x         the x coordinate of the square
     * @param y         the y coordinate of the square
     */
    public Square (int x, int y) {
=======
     *
     * @param x the x coordinate of the square
     * @param y the y coordinate of the square
     */
    public Square(int x, int y) {
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b
        this(Entity.Empty, x, y);
    }

    /**
     * Construct the Square with a specified entity.
<<<<<<< HEAD
     * @param entity    the entity that is on the square
     * @param x         the x coordinate of the square
     * @param y         the y coordinate of the square
     */
    Square (Entity entity, int x, int y) {
=======
     *
     * @param entity the entity that is on the square
     * @param x      the x coordinate of the square
     * @param y      the y coordinate of the square
     */
    Square(Entity entity, int x, int y) {
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b
        this.entity = entity;
        this.x = x;
        this.y = y;
    }

    /**
     * Change what is on the Square.
<<<<<<< HEAD
     * @param entity    the new entity
     */
    void setEntity (Entity entity) {
=======
     *
     * @param entity the new entity
     */
    void setEntity(Entity entity) {
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b
        this.entity = entity;
    }

    /**
     * Get what is on the Square.
<<<<<<< HEAD
     * @return          the entity on the Square
     */
    Entity getEntity () {
        return entity;
    }

    int getX () {
        return x;
    }

    int getY () {
=======
     *
     * @return the entity on the Square
     */
    Entity getEntity() {
        return entity;
    }

    int getX() {
        return x;
    }

    int getY() {
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b
        return y;
    }

    @Override
<<<<<<< HEAD
    public boolean equals (Object obj) {
=======
    public boolean equals(Object obj) {
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b

        if (!(obj instanceof Square)) {
            return false;
        }

        Square sq = (Square) obj;
        return sq.x == x && sq.y == y;
    }

    @Override
<<<<<<< HEAD
    public String toString () {
=======
    public String toString() {
>>>>>>> ae122d9c08e34487ecebe0db4735510363e6ae1b
        return entity + " at (" + x + ", " + y + ")";
    }

    /**
     * Represents what is on a particular square.
     */
    enum Entity {
        Empty,
        Shooter,
        Alien,
        Projectile
    }
}