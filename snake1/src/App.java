import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth = 600;
        int boardlength = boardwidth;

        JFrame frame = new JFrame("snake");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardlength);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        SnakeGame snakegame = new SnakeGame(boardwidth,boardlength);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();
    }
}
