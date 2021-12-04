package BBgame;
import javax.swing.*;

public class Frame extends JFrame {

    public Frame() {

        GamePlay panel = new GamePlay();

        setTitle("Brick Breaker Game");
        setBounds(600, 150, 800, 800); //(X,Y,طول,عرض)
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);

    }
}
