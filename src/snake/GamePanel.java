package snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class GamePanel extends JPanel {
    static final int Screen_Width = 600;
    static final int Screen_Height = 600;


    public GamePanel() {
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }
}
