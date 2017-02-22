import java.awt.*;

public class Triangle extends Entity{

    public Triangle(Color color, int x, int y, int width, int height, Game game){

        super(color, x,y, width, height, game);

    }

    public void paint(Graphics g){

        g.setColor(getColor());
        g.fillPolygon(new int[] {10, 20, 30}, new int[] {100, 20, 100}, 3);
    }

}
