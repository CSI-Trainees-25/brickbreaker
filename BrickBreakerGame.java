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


     private void moveGame() {
        if (!playing) return;

       
        bx += bdx;
        by += bdy;

        if (bx <= 0) {
            bx = 0;
            bdx = Math.abs(bdx);
        } else if (bx >= WIDTH - B_SIZE) {
            bx = WIDTH - B_SIZE;
            bdx = -Math.abs(bdx);
        }

        if (by <= 0) {
            by = 0;
            bdy = Math.abs(bdy);
        }


         if (by + B_SIZE >= HEIGHT - 50 && 
            by <= HEIGHT - 35 &&
            bx + B_SIZE >= padX && 
            bx <= padX + PAD_W) {
            
            double hitPos = (bx - padX) / (double) PAD_W; 
            double angle = (hitPos - 0.5) * Math.PI / 3;

            double speed = Math.sqrt(bdx * bdx + bdy * bdy);
            bdx = speed * Math.sin(angle);
            bdy = -speed * Math.cos(angle);

            if (Math.abs(bdy) < 2) {
                bdy = (bdy > 0) ? 2 : -2;
            }

            by = HEIGHT - 50 - B_SIZE;
        }

        if (by >= HEIGHT) {
            playing = false;
        }


         boolean hit = false;
        for (int i = 0; i < ROW && !hit; i++) {
            for (int j = 0; j < COL && !hit; j++) {
                if (brick[i][j]) {
                    int brickX = j * (BW + GAP) + GAP;
                    int brickY = i * (BH + GAP) + GAP + 50;

                    Rectangle ballRect = new Rectangle((int) bx, (int) by, B_SIZE, B_SIZE);
                    Rectangle brickRect = new Rectangle(brickX, brickY, BW, BH);

                    if (ballRect.intersects(brickRect)) {
                        brick[i][j] = false;
                        leftBricks--;
                        score += 10;
                        hit = true;

                        double ballCenterX = bx + B_SIZE / 2.0;
                        double ballCenterY = by + B_SIZE / 2.0;
                        double brickCenterX = brickX + BW / 2.0;
                        double brickCenterY = brickY + BH / 2.0;

                        double dx = ballCenterX - brickCenterX;
                        double dy = ballCenterY - brickCenterY;
                        double overlapX = (B_SIZE / 2.0 + BW / 2.0) - Math.abs(dx);
                        double overlapY = (B_SIZE / 2.0 + BH / 2.0) - Math.abs(dy);

                        if (overlapX < overlapY) {
                            bdx = -bdx;
                            if (dx > 0) bx = brickX + BW + 1;
                            else bx = brickX - B_SIZE - 1;
                        } else {
                            bdy = -bdy;
                            if (dy > 0) by = brickY + BH + 1;
                            else by = brickY - B_SIZE - 1;
                        }
                    }
                }
            }
        }


        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        moveGame();
    }

}



