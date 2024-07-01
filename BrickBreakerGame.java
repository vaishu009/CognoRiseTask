//BreakBreakerGame.java

package brickbreakergame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener 
{ 
    private boolean play = false;
    public int score = 0;

    private int totalBricks = 40;

    private Timer timer;
    private int delay = 17;

    private int playerX = 300;

    private int ballPosX = 290;
    private int ballPosY = 350;
    private int ballDirX = getRandomNumberForX();
    private int ballDirY = getRandomNumberForY();

    private Play mapPlay;

    public BrickBreakerGame()
    {
        mapPlay = new Play(4, 10);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) 
    {
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        mapPlay.draw((Graphics2D) graphics, Color.WHITE);

        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 1, 3, 592);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Time New Ro", Font.BOLD, 22));
        graphics.drawString("Score: " + score + "/200", 490, 30);

        graphics.setColor(Color.green);
        graphics.fillRect(playerX, 550, 100, 8);

        if (play == false) 
        {
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Press Enter/Left/Right Arrow to start the game!", 90, 350);

            graphics.setColor(Color.black);
            graphics.fillOval(ballPosX, ballPosY, 20, 20);
        } 
        else 
        {
            graphics.setColor(Color.green);
            graphics.fillOval(ballPosX, ballPosY, 20, 20);
        }
        if (score >= 50 && score < 100) 
        {
            graphics.setColor(Color.yellow);
            graphics.fillOval(ballPosX, ballPosY, 21, 21);
        } 
        else if (score >= 100 && score < 150) 
        {
            graphics.setColor(Color.orange);
            graphics.fillOval(ballPosX, ballPosY, 22, 22);
        }
        else if (score >= 150) 
        {
            graphics.setColor(Color.red);
            graphics.fillOval(ballPosX, ballPosY, 23, 23);
        }
        if (totalBricks <= 0) 
        {
            play = false;
            ballDirX = 0;
            ballDirY = 0;

            graphics.setColor(Color.black);
            graphics.fillOval(ballPosX, ballPosY, 23, 23);

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Time New Romans", Font.BOLD, 30));
            graphics.drawString("You Win! Score: " + score, 200, 300);

            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("Time New Romans", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart..", 230, 330);

            graphics.setColor(Color.black);
            graphics.setFont(new Font("Time New Romans", Font.BOLD, 22));
            graphics.drawString("Score: " + score + "/200", 490, 30);

            mapPlay.draw((Graphics2D) graphics, Color.BLACK);

            graphics.setColor(Color.black);
            graphics.fillRect(playerX, 550, 100, 8);

            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Press Enter/Left/Right Arrow to start the game!", 90, 350);
        }

        if (ballPosY > 570)
        { 
            play = false;
            ballDirX = 0;
            ballDirY = 0;

            graphics.setColor(Color.black);
            graphics.fillOval(ballPosX, ballPosY, 23, 23);

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Time New Romans", Font.BOLD, 30));
            graphics.drawString("Game over! Score: " + score, 200, 300);

            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("Time New Romans", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart..", 230, 330);

            graphics.setColor(Color.black);
            graphics.setFont(new Font("Time New Romans", Font.BOLD, 22));
            graphics.drawString("Score: " + score + "/200", 490, 30);

            mapPlay.draw((Graphics2D) graphics, Color.BLACK);

            graphics.setColor(Color.black);
            graphics.fillRect(playerX, 550, 100, 8);

            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Press Enter/Left/Right Arrow to start the game!", 90, 350);
        }
        graphics.dispose();
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) 
        {
            if (playerX >= 600)
            {
                playerX = 600;
            } 
            else
            {
                moveRight();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) 
        {
            if (playerX < 10) 
            {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) 
        {
            if (!play) {
                play = true;
                playerX = 310;
                ballPosX = 290;
                ballPosY = 350;
                ballDirX = getRandomNumberForX();
                ballDirY = getRandomNumberForY();
                totalBricks = 40;

                mapPlay = new Play(4, 10);
                score = 0;

                repaint();
            }
        }
    }
    public void moveRight()
    {
        play = true;
        playerX += 20;
    }
    public void moveLeft()
    {
        play = true;
        playerX -= 20;
    }
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        timer.start();
        if (play) 
        {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) 
            {
                ballDirY = -ballDirY;
            }
            A:
            for (int i = 0; i < mapPlay.map.length; i++) 
            {
                for (int j = 0; j < mapPlay.map[0].length; j++) 
                {
                    if (mapPlay.map[i][j] > 0) {
                        int brickX = j * mapPlay.brickWidth + 80;
                        int brickY = i * mapPlay.brickHeight + 50;
                        int brickWidth = mapPlay.brickWidth;
                        int brickHeight = mapPlay.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) 
                        {
                            mapPlay.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) 
                            {
                                ballDirX = -ballDirX;
                            } 
                            else 
                            {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }
                    }
                }
            }
            ballPosX += ballDirX;
            ballPosY += ballDirY;

            if (ballPosX < 0) 
            {  
                ballDirX = -ballDirX;
            }
            if (ballPosY < 0) 
            { 
                ballDirY = -ballDirY;
            }
            if (ballPosX > 670) 
            { 
                ballDirX = -ballDirX;
            }
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) {}
    public int getRandomNumberForY() 
    {
        Random random = new Random();
        int max = -1;
        int min = -5;
        int randomNumber = min + random.nextInt(max - min + 1);
        return randomNumber;
    }
    public int getRandomNumberForX() 
    {
        Random random = new Random();
        int max = -1;
        int min = -3;
        int randomNumber = min + random.nextInt(max - min + 1);
        return randomNumber;
    }
}


//Main.java

package brickbreakergame;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        BrickBreakerGame gamePlay = new BrickBreakerGame();

        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }
}


//Play.java

package brickbreakergame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Play 
{
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public Play(int row, int col) 
    {
        map = new int[row][col];

        for (int i = 0; i < map.length; i++) 
        {
            for (int j = 0; j < map[0].length; j++) 
            {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D graphics2d, Color colorName)
    {
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {
                if (map[i][j] > 0) 
                {
                    graphics2d.setColor(colorName);
                    graphics2d.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    graphics2d.setStroke(new BasicStroke(4));
                    graphics2d.setColor(Color.black);
                    graphics2d.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) 
    {
        map[row][col] = value;
    }
}
