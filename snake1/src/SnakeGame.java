import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



public class SnakeGame extends JPanel implements ActionListener,KeyListener{
   

    private class Tile{
        int x;
        int y;

        Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardwidth;
    int boardlength;
    int tileSize = 25;

    Tile SnakeHead;

    Tile food;
    Random random;

    Timer gameLoop;

    int velocityX;
    int velocityY; 

    boolean gameover = false;

    ArrayList<Tile> snakebody;

    SnakeGame(int boardwidth,int boardlength){
        this.boardwidth = boardwidth;
        this.boardlength = boardlength;

        setPreferredSize(new Dimension(this.boardwidth,this.boardlength));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);



        SnakeHead = new Tile(5,5);

        food = new Tile(10,10);

        random = new Random();
        placefood();

        gameLoop = new Timer(100,this);
        gameLoop.start();

        velocityX = 0;
        velocityY = 1;

        snakebody = new ArrayList<Tile>();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g ){
       //food
       g.setColor(Color.red);
       g.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);
       
       
        //snake
        g.setColor(Color.GREEN);
        g.fill3DRect(SnakeHead.x*tileSize, SnakeHead.y*tileSize, tileSize, tileSize,true);

        //snakebody
        for(int i = 0; i < snakebody.size(); i++){
            Tile snakepart = snakebody.get(i);
            g.fill3DRect(snakepart.x*tileSize,snakepart.y*tileSize,tileSize,tileSize,true);
        }

        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameover){
            g.setColor(Color.red);
            g.drawString("Game Over : " + String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }
        else {
            g.drawString("Score : " + String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }

    }
    public void placefood(){
        food.x = random.nextInt(boardwidth/tileSize);
        food.y = random.nextInt(boardlength/tileSize);

    }


    public boolean collision(Tile tile1,Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }


    public void move(){

        if(collision(SnakeHead, food)){
            snakebody.add(new Tile(food.x , food.y));
            placefood();
        }

        for(int i = snakebody.size()-1; i >=0 ; i--){
            Tile snakepart = snakebody.get(i);
            if(i == 0){
                snakepart.x = SnakeHead.x;
                snakepart.y = SnakeHead.y;
            }
            else  {
                Tile prevsnakepart = snakebody.get(i-1);
                snakepart.x =  prevsnakepart.x;
                snakepart.y = prevsnakepart.y;

            }
        }

        SnakeHead.x += velocityX;
        SnakeHead.y += velocityY;

        for(int i = 0 ; i< snakebody.size(); i++ ){
            Tile snakepart = snakebody.get(i);
            if(collision(SnakeHead, snakepart)){
                gameover = true;
            }
        }
        if(SnakeHead.x*tileSize < 0 || SnakeHead.x*tileSize >boardwidth ||SnakeHead.y*tileSize < 0 || SnakeHead.y*tileSize > boardlength){
            gameover = true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameover){
            gameLoop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
