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

}