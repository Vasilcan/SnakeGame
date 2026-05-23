package snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    static final int Screen_Width = 600;
    static final int Screen_Height = 600;
    static final int Unit_Size = 25;

    List<Point> snake = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        for(int i = 0; i < 6;i++) {
            snake.add(new Point(150 - (i * Unit_Size), 50));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        deseneaza(g);
    }

    public void deseneaza(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < Screen_Height / Unit_Size;i++) {
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
