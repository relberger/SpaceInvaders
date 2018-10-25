import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

public class Alien{
    //location
    private int column = 0;
    private int row = 0;

    //image
    private Image alienPic;
    private int height;
    private int width;

    //dead/alive
    private boolean alive = true;

    public Alien(int row, int column, int height, int width) {
        this.row = row;
        this.column = column;

        this.height = height;
        this.width = width;

        createAlienPic();


    }

    private void createAlienPic(){
        File imageFile = new File("alien.jpg");
        try{
            alienPic = resize(ImageIO.read(imageFile), height, width);
        }
        catch (IOException e){
            System.out.println("Image not found.");
        }

    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isAlive() {
        return alive;
    }

    public Image getAlienPic() {
        return alienPic;
    }
}
