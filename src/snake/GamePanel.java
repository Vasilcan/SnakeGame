package snake;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.event.ActionEvent;
//Lucru cu fisiere


public class GamePanel extends JPanel implements ActionListener {
    static final int Screen_Width = 600;
    static final int Screen_Height = 600;
    static final int Unit_Size = 25;
    static final int Delay = 150;

    List<Point> snake = new ArrayList<>();
    char direction = 'R';
    boolean running = false;
    Timer timer;

    int appleX;
    int appleY;

    int applesEaten = 0;
    int highScore = 0;
    Random random = new Random();

    public GamePanel() {
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.addKeyListener(new CompasTastatura());

        loadHighScore();

        startGame();


    }

    public void startGame() {
        newApple();

        for (int i = 0; i < 6; i++) {
            snake.add(new Point(150 - (i * Unit_Size), 50));
        }

        running = true;

        timer = new Timer(Delay, this);
        timer.start();
    }

    public void newApple() {
        appleX = random.nextInt((int)(Screen_Width / Unit_Size)) * Unit_Size;

        appleY = random.nextInt((int)(Screen_Height / Unit_Size)) * Unit_Size;
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

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, Unit_Size, Unit_Size);


            for (int i = 0; i < snake.size(); i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(snake.get(i).x, snake.get(i).y, Unit_Size, Unit_Size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(snake.get(i).x, snake.get(i).y, Unit_Size, Unit_Size);
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Scor: " + applesEaten, (Screen_Width - metrics.stringWidth("Scor: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Scor final: " + applesEaten, (Screen_Width - metrics1.stringWidth("Scor Final: " + applesEaten)) / 2, g.getFont().getSize());


        g.setColor(Color.YELLOW);
        g.drawString("High Score: " + highScore, (Screen_Width - metrics1.stringWidth("High Score: " + highScore)) / 2, g.getFont().getSize() + 50);


        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 =  getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (Screen_Width - metrics2.stringWidth("GAME OVER")) / 2, Screen_Height / 2);
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

    public void checkApple() {
        Point cap = snake.get(0);
        if ((cap.x == appleX) && (cap.y == appleY)) {
            Point coada = snake.get(snake.size() - 1);
            snake.add(new Point(coada.x, coada.y));

            applesEaten++;

            newApple();
        }
    }

    public void checkCollisions() {
        Point cap = snake.get(0);

        for (int i = 1; i < snake.size(); i++) {
            if ((cap.x == snake.get(i).x) && (cap.y == snake.get(i).y)) {
                running = false;
                break;
            }
        }

        if (cap.x < 0 || cap.x >= Screen_Width || cap.y < 0 || cap.y >= Screen_Height) {
            running = false;
        }

        if (!running) {
            timer.stop();

            checkAndSaveHighScore();
        }
    }

    public void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException e) {

            System.out.println("Fisierul de High Score nu exista inca. Se va crea la final.");
        }
    }

    public void checkAndSaveHighScore() {
        if (applesEaten > highScore) {
            highScore =  applesEaten;


            try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
                writer.write(String.valueOf(highScore));
            } catch (IOException e) {
                System.out.println("Eroare la salvarea fisierului: " + e.getMessage());
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    public class CompasTastatura extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}


