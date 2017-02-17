import java.awt.*;

public class Wall extends Entity{

    public Wall(Color color, int x, int y, int width, int height, Game game){

        super(color, x,y, width, height, game);

    }

    public void paint(Graphics g){

        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
