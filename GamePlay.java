package BBgame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePlay extends JPanel implements KeyListener, ActionListener,MouseMotionListener {

    private boolean playing = false;
    private int score = 0 , attempts = 1;
    private final int rows = 4 , cols = 8;
    private int blocksCount = rows * cols;

    private int paddleX = 500;
    private final int paddleY = 720, paddleW = 120, paddleH = 10;

    public int ballposX = 11, ballposY = 350, ballpathX = -4, ballpathY = -4;
    int ballSize = 35;

    Timer timer;
    Map map;
    Color mainColor = new Color(109, 133, 122);

    public GamePlay() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(12, this);
        timer.start();
        map = new Map( rows , cols );

    }

    public void paint(Graphics G) {

        //background
        Color backColor = new Color(189, 226, 210);
        G.setColor(backColor);
        G.fillRect(1, 1, 800, 800);

        //blocks
        map.draw((Graphics2D) G);

        //score
        G.setColor(mainColor);
        G.setFont(new Font("DialogInput", Font.BOLD, 20));
        G.drawString("score:" + score,10,25);
        G.setFont(new Font("DialogInput", Font.BOLD, 20));
        G.drawString("attempt:" + attempts,10,45);

        //paddle
        G.setColor(mainColor);
        G.fillOval(paddleX, paddleY, paddleW, paddleH);

        //ball

        G.setColor(Color.white);
        G.fillOval(ballposX, ballposY, ballSize, ballSize);

        // first screen
        if(!playing && score ==0)
        {
            G.setColor(mainColor);
            G.setFont(new Font("DialogInput", Font.BOLD, 40));
            G.drawString("Press L or R arrow to Start",80,340);
            G.setFont(new Font("DialogInput", Font.BOLD, 25));
            G.drawString("Be careful you just have 3 attempts",160,380);

        }
        // if all blocks smashed
        if(blocksCount == 0)
        {
            playing = false;
            ballpathX = -4;
            ballpathY = -4;
            ballposX = 11; ballposY = 350;
            attempts = 0;
                G.setColor(mainColor);
                G.setFont(new Font("DialogInput", Font.BOLD, 45));
                G.drawString("Congrats! You won the game", 50, 325);
                G.setFont(new Font("DialogInput", Font.BOLD, 25));
                G.drawString("Press Space to Play again", 200, 360);
        }

        //losing but the attempts haven't finished yet
        if (ballposY > getHeight() && attempts < 3) {
            playing = false;
            ballpathX = 0;
            ballpathY = 0;
            G.setColor(mainColor);
            G.setFont(new Font("DialogInput", Font.BOLD, 45));
            G.drawString("Attempt "+attempts+" failed",165,325);
            G.setFont(new Font("DialogInput", Font.BOLD, 25));
            G.drawString("Press Space To restart",202,360);
        }
        //the attempts finished
        else if(ballposY > getHeight() && attempts == 3)
        {
            G.setColor(mainColor);
            G.setFont(new Font("DialogInput", Font.BOLD, 45));
            G.drawString("Hard Luck",275,340);
            G.setFont(new Font("DialogInput", Font.BOLD, 30));
            G.drawString("You finish all the attempts ",150,380);

        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
        if (playing) {
            ballposX += ballpathX;
            ballposY += ballpathY;

            Rectangle paddle = new Rectangle(paddleX, paddleY, paddleW, paddleH);
            Rectangle ball = new Rectangle(ballposX, ballposY, ballSize, ballSize);

            if (ball.intersects(paddle))
            {
                ballpathY *= -1;
            }
            // when th ball hits the block
    point: for(int i = 0 ; i < map.blocks.length ; i++)
            {
                for (int j = 0 ; j < map.blocks[i].length ; j++)
                {
                    int blockWidth = map.blockWidth;
                    int blockHeight = map.blockHeight;
                    int brickX = j * blockWidth + 55;
                    int brickY = i * blockHeight + 60; //
                    Rectangle brick = new Rectangle(brickX,brickY,blockWidth,blockHeight);
                    if(map.blocks[i][j] > 0)
                    {
                        if(ball.intersects(brick))
                        {
                            map.BrickValue(0,i,j);
                            blocksCount--;
                            score+=5;
                            if(ballposY + ballSize <= brick.y || ballposY + ballSize >= brick.y)
                            {
                                ballpathY *= -1;
                            }

                            else if(ballposX + ballSize <= brick.x || ballposX >= brick.x + brick.height )
                            {
                                ballpathX *=-1;
                            }
                            break point;
                        }


                    }

                }

            }

            // when ball hits boarders
            if (ballposX < 0 || ballposX > getWidth() - ballSize)
            {
                ballpathX *= -1;
            }
            if (ballposY < 0)
            {
                ballpathY *= -1;
            }
        }

    }


    @Override
    public void keyPressed(KeyEvent keyEvent)
    {

        //Moving the paddle
       switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_RIGHT:
            {
                if (paddleX >= getWidth()-paddleW)
                    paddleX = 680;
                else
                    moveRight();
            }   break;

            case KeyEvent.VK_LEFT:
            {
                if (paddleX <= 0)
                    paddleX = 0;
                else
                    moveLeft();

            }   break;

        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE && attempts < 3)
        if ( !playing && score > 0)
        {
            playing = true;
            blocksCount = rows * cols;
            score =0;
            attempts++ ;
            ballposX = 11; ballposY = 350; ballpathX = -4; ballpathY = -4;
            map = new Map(rows,cols);
            repaint();
        }

    }

    public void moveLeft() {
        playing = true;
        paddleX -= 20; //لو ضغط السهم اليمين يروح البادل 20 بكسل يمين

    }

    public void moveRight() {
        playing = true;
        paddleX += 20; //لو ضغط السهم اليسار يروح البادل 20 بكسل يسار

    }

    //I don't use them but they must be Overrided
    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {


    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

}


