package snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GamePanel extends JPanel implements ActionListener {
    static final int Screen_Width = 600;
    static final int Screen_Height = 600;
    static final int Unit_Size = 25;
    static final int Delay = 150;

    List<Point> snake = new ArrayList<>();
    char direction = 'R';
    boolean running = false;
    Timer timer;

    public GamePanel() {
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        startGame();


    }

    public void startGame() {
        for (int i = 0; i < 6; i++) {
            snake.add(new Point(150 - (i * Unit_Size), 50));
        }

        running = true;

        timer = new Timer(Delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        deseneaza(g);
    }

    public void deseneaza(Graphics g) {

        if (running) {
            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i < Screen_Height / Unit_Size; i++) {
                g.drawLine(i * Unit_Size, 0, i * Unit_Size, Screen_Height);
                g.drawLine(0, i * Unit_Size, Screen_Width, i * Unit_Size);
            }


            for (int i = 0; i < snake.size(); i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(snake.get(i).x, snake.get(i).y, Unit_Size, Unit_Size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(snake.get(i).x, snake.get(i).y, Unit_Size, Unit_Size);
                }
            }
        }
    }

    public void move() {
        Point capCurent = snake.get(0);

        Point capNou = new Point(capCurent.x, capCurent.y);

        switch (direction) {
            case 'U':
                capNou.y = capNou.y - Unit_Size;
                break;
            case 'D':
                capNou.y = capNou.y + Unit_Size;
                break;
            case 'L':
                capNou.x = capNou.x - Unit_Size;
                break;
            case 'R':
                capNou.x = capNou.x + Unit_Size;
                break;
        }

        snake.add(0, capNou);

        snake.remove(snake.size() - 1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }
}


