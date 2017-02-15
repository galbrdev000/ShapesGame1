import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends JPanel implements ActionListener, KeyListener{

    Timer timer;
    int positionX, positionY;
    int squares= 5;
    int circles = 1;

    ArrayList<Entity> entities;

    public Game(){
        setPreferredSize(new Dimension(600, 800));
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
                        new Point (0,0),null));
            }
        });

        addKeyListener(this);

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
        timer = new Timer(1500/60, this);
        entities = new ArrayList<Entity>();
        entities.add(new Circle(Color.red, getWidth()/2, getHeight()/2, 20, this));

        for(int i = 0; i < squares; i++) {
            entities.add(new Food(Color.green, (int)(25 + (getWidth()-100)*Math.random()),
                    (int) (25+ (getHeight()-50)*Math.random()),  30, 30, this));
        }

        for(int i = 0; i < circles; i++){
            entities.add(new Circle(Color.blue, (int)(25 + (getWidth()-100)*Math.random()),
                    (int) (25+ (getHeight()-50)*Math.random()),  30, this));
        }
        timer.start();
    }

    public void collisions(){
        if(Stats.lives <= 0){
            Stats.endGame();
        }
        if(Stats.score >= Stats.count+5){
            circles ++;
            Stats.lives += 1;
            Stats.level += 1;
            Stats.count += 5;
            init();
        }
        for (int i = 1; i < entities.size(); i++){
            if(entities.get(0).collides(entities.get(i))){
                if(entities.get(i)instanceof Food){
                    entities.remove(i);
                    Stats.score++;
                    Stats.display++;
                }
                else if(entities.get(i) instanceof Circle){
                    entities.remove(i);
                    Stats.lives--;
                    init();
                }
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        if(Stats.isPlay()) {
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            printSimpleString("Score: " + String.valueOf(Stats.display), getWidth()/2, 350, 50, g);
            printSimpleString("Lives: " + String.valueOf(Stats.lives), getWidth()/2, -50, 50, g);
            printSimpleString("Level: " + String.valueOf(Stats.level), getWidth()/2, 150, 50, g);
            for (Entity obj : entities)
                obj.paint(g);
        }
        else if(Stats.isMenu()){
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            printSimpleString("Insert Title Here", getWidth(), 0, 200, g);
            printSimpleString("Press *space* to Start", getWidth(), 0, 500, g);
            printSimpleString("Use the mouse to collect Green Squares", getWidth(), 0, 300, g);
            printSimpleString("Avoid the Blue Circles", getWidth(), 0, 400, g);
        }
        else if(Stats.isPause()){
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            printSimpleString("Press *P* to Resume", getWidth(), 0, 400, g);

        }
        else if(Stats.isEnd()){
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            printSimpleString("GAME OVER...", getWidth(), 0, 300, g);
            printSimpleString("Press * Space* to Try Again!!!", getWidth(), 0, 400, g);
            timer.stop();
        }
    }
    public static void main(String[] args){
        Game game = new Game();
        game.init();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                Stats.startPlay();
                init();
        }

        if(e.getKeyCode() == KeyEvent.VK_P) {
            Stats.togglePlay();
            Stats.togglePause();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g2d){

        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s,g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start + XPos, YPos);

    }
}