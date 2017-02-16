import java.awt.*;

public abstract class Entity {

    private Game game;
    private Color color;
    private int x, y, width, height;
    private double dx, dy;

    public Entity(Color color, int x, int y, int width, int height, Game game) {
        this.game = game;
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        while (dx < 1 || dy < 1) {
            double angle = 2 * Math.PI * Math.random();
            double speed = 4 + 8 * Math.random();
            dx = Math.cos(angle) * speed;
            dy = Math.sin(angle) * speed;
        }
    }

    //GENERIC MOVE METHOD
    public void move(){
        double nextLeft = x + dx;
        double nextRight = x + width + dx;
        double nextTop = y + dy;
        double nextBottom = y + height + dy;

        if(nextTop <=0 || nextBottom > game.getHeight()) {
            dy *= -1;
        }
        if (nextLeft <= 0 || nextRight > game.getWidth()) {
            dx*=-1;
        }
        x+=dx;
        y+=dy;
    }

    public void playerMove(){
        setX(game.positionX);
        setY(game.positionY);
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public boolean collides(Entity other){
        if (getBounds().intersects(other.getBounds())){
            return true;
        }
        return false;
    }

    public abstract void paint(Graphics g);

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}

