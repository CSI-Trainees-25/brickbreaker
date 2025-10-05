import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class BrickBreakerGame extends JPanel implements ActionListener, KeyListener {

     private static final int WIDTH = 600, HEIGHT = 600;

    
    private int padX = 250;
    private static final int PAD_W = 100, PAD_H = 15;

    
    private double bx = 300, by = 300;
    private double bdx = 3, bdy = 3;
    private final int B_SIZE = 20;

   
    private static final int ROW = 4, COL = 8;
    private static final int BW = 70, BH = 25, GAP = 5;
    private boolean[][] brick;
    
     private boolean playing = true;
    private Timer timer;
    private int score = 0;
    private int leftBricks;






       public BrickBreakerGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        brick = new boolean[ROW][COL];
        leftBricks = ROW * COL;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                brick[i][j] = true;
            }
        }

        timer = new Timer(16, this);
        timer.start();
    }

  
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(padX, HEIGHT - 50, PAD_W, PAD_H);

       
        g.setColor(Color.RED);
        g.fillOval((int) bx, (int) by, B_SIZE, B_SIZE);

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (brick[i][j]) {
                    g.setColor(new Color(100, 150, 255));
                    g.fillRect(j * (BW + GAP) + GAP, 
                              i * (BH + GAP) + GAP + 50, 
                              BW, BH);
                }
            }
        }


          g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 20, 25);
        g.drawString("Bricks: " + leftBricks, WIDTH - 120, 25);


         if (!playing && leftBricks > 0) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", WIDTH / 2 - 110, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Final Score: " + score, WIDTH / 2 - 70, HEIGHT / 2 + 40);
            g.drawString("Press R to Restart", WIDTH / 2 - 85, HEIGHT / 2 + 70);
        } else if (leftBricks == 0) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("YOU WIN!", WIDTH / 2 - 90, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, WIDTH / 2 - 50, HEIGHT / 2 + 40);
            g.drawString("Press R to Play Again", WIDTH / 2 - 100, HEIGHT / 2 + 70);
            playing = false;
        }
    }
}