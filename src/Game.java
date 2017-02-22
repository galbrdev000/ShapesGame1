import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends JPanel implements ActionListener{

    Timer timer;
    int positionX, positionY;
    int squares = 10;
    int circles = 1;

    ArrayList<Entity> entities;

    public Game() {
        setPreferredSize(new Dimension(800, 800));
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                positionX = e.getX();
                positionY = e.getY();

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), null));
            }
        });

        JFrame frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("SHAPES");
        frame.setLocationRelativeTo(null);
        setBackground(Color.black);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        collisions();
        entities.get(0).playerMove();
        for(int i = 1; i < entities.size(); i++){
            entities.get(i).move();
        }
        repaint();
    }

    public void init(){
        int q = 0;
        if(Stats.level <= 10) {
            q = Stats.level * 2500;
        }
        timer = new Timer(q/60, this);
        if (Stats.level == 1 || Stats.level == 5 || Stats.level == 9) {
            setBackground(Color.black);
        }
        if (Stats.level == 2 || Stats.level == 6 || Stats.level == 10) {
            setBackground(Color.CYAN);
        }
        if (Stats.level == 3 || Stats.level == 7 || Stats.level == 11) {
            setBackground(Color.DARK_GRAY);
        }
        if (Stats.level == 4 || Stats.level == 8 || Stats.level == 12) {
            setBackground(Color.WHITE);
        }

        entities = new ArrayList<Entity>();
        entities.add(new Circle(Color.red, getWidth()/2, getHeight()/2, Stats.radius, this));

        for(int i = 0; i < squares; i++) {
            entities.add(new Food(Color.green, (int)(25 + (getWidth()-100)*Math.random()),
                    (int) (25+ (getHeight()-50)*Math.random()),  25, 25, this));
        }
        for(int i = 0; i < circles; i++){
            entities.add(new Circle(Color.blue, (int)(25 + (getWidth()-100)*Math.random()),
                    (int) (25+ (getHeight()-50)*Math.random()),  20, this));
        }
        timer.start();
    }

    public void collisions(){
        int g = Stats.level+1;
        if(Stats.score >= Stats.count+10){
           if(g == 2 || g == 4 || g == 6 || g == 8 || g == 10 ||  g == 12 || g == 14 || g == 16 || g == 18 || g == 20) {
               circles++;
           }
           if(Stats.level > 10){
               circles = 1;
           }
            Stats.radius+= 2;
            Stats.lives += 1;
            Stats.level += 1;
            Stats.count += 10;
            init();
        }
        for (int i = 1; i < entities.size(); i++){
              if (entities.get(0).collides(entities.get(i))) {
                  if (entities.get(i) instanceof Food) {
                      entities.remove(i);
                      if(Stats.isEnd() == false){
                          Stats.score++;
                          Stats.display++;
                      }
                  }
                  else if (entities.get(i) instanceof Circle) {
                      if(Stats.level <= 10) {
                          entities.remove(i);
                          Stats.lives--;
                          Stats.radius -= 2;
                          init();
                      }
                      if(Stats.level > 10) {
                          entities.remove(i);
                          Stats.lives -= 2;
                          Stats.radius--;
                          init();
                      }
                  }
                 }
            }
    }

    public void paint(Graphics g){
        super.paint(g);
        if(Stats.isPlay()) {
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            printSimpleString("Score: " + String.valueOf(Stats.display), getWidth()/2, 450, 50, g);
            printSimpleString("Lives: " + String.valueOf(Stats.lives), getWidth()/2, -60, 50, g);
            printSimpleString("Level: " + String.valueOf(Stats.level), getWidth()/2, 200, 50, g);
            for (Entity obj : entities)
                obj.paint(g);
            if(Stats.lives == 0 || Stats.level == 20) {
                timer.stop();
                Stats.endGame();
            }
        }

        else if(Stats.isEnd()){
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            if(Stats.lives == 0){
                printSimpleString("GAME OVER!", getWidth(), 0, 300, g);
                printSimpleString("You Survived " + (Stats.level - 1) + " Levels!", getWidth(), 0, 400, g);
                printSimpleString("You Scored " + Stats.display + " Points", getWidth(), 0, 450, g);
            }
            if(Stats.level >= 20){
                printSimpleString("YOU WIN!", getWidth(), 0, 300, g);
                printSimpleString("You Scored " + Stats.display + " Points", getWidth(), 0, 400, g);
            }
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        game.init();
    }

    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g2d){

        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s,g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start + XPos, YPos);

    }
}