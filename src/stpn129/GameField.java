package stpn129;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.lang.Math.*;

public class GameField extends JPanel implements ActionListener {
    Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();


    private final int WIDTH = sSize.width;
    private final int HEIGHT = sSize.height;

    private final int X_BORDER = 120;
    private final int Y_BORDER = sSize.height - 100;

    private final double G = 9.8;

    private Image bird;
    private Image egg;

    private Timer timer;

    private boolean launched = false;
    private boolean splitted = false;

    private int t = 0;
    private int tFall1 = 0;
    private int t1 = 0;
    private int tFall2 = 0;

    private int birdX = X_BORDER;
    private int birdY = Y_BORDER;
    private int eggX = X_BORDER;
    private int eggY = Y_BORDER;

    private int SbirdX = 0;
    private int SbirdY = 0;
    private int SeggX = 0;
    private int SeggY = 0;

    private int Angle0 = 45;
    private int V0 = 180;


    public void setM1(double m1) {
        this.m1 = m1;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public void setAngle1(double angle1) {
        Angle1 = angle1;
    }

    public void setU1(double u1) {
        this.u1 = u1;
    }

    private double m1 = 90;
    private double m2 = 10;
    private double Angle1 = 45;
    private double a1 = Angle1;
    private double a2 = 360 - Angle1;
    private double u1 = 120;
    private double u2 = ((m1 + m2) * V0 - m1 * u1) / m2;


    public GameField() {
        setBackground(Color.PINK);

        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    private void initGame() {
        timer = new Timer(60, this);
        timer.start();
    }


    private void loadImages() {
        ImageIcon ibird = new ImageIcon("bird.png");
        bird = ibird.getImage();
        ImageIcon iegg = new ImageIcon("egg.png");
        egg = iegg.getImage();

    }

    private int[] throwSmth(double v, double t, double a) {
        int[] coords = new int[2];
        a = toRadians(a);
        coords[0] = (int) (v * cos(a) * t) / 4;
        coords[1] = (int) (-v * sin(a) * t + (G * t * t) / 2) / 4;

        return coords;
    }

    private double[] justParabole(double x) {
        double[] coords = new double[2];
        coords[0] = x;
        coords[1] = -x * x;
        return coords;
    }

    private double[] justLine(double x, double a) {
        double[] coords = new double[2];
        a = toRadians(a);
        coords[0] = x;
        coords[1] = -x * tan(Math.PI / 2 - a);
        return coords;
    }


    private void move() {

        if (launched & !splitted) {
            t += 1;

            int[] coords = throwSmth(V0, t, Angle0);
            birdX = coords[0] + X_BORDER;
            birdY = coords[1] + Y_BORDER;
            eggX = coords[0] + X_BORDER;
            eggY = coords[1] + Y_BORDER;

            SbirdX = birdX;
            SbirdY = birdY;
            SeggX = eggX;
            SeggY = eggY;
            if (birdY > Y_BORDER) {
                birdY = Y_BORDER + 1;
                eggY = Y_BORDER + 1;
                birdX = X_BORDER;
                eggX = X_BORDER;
                launched = false;
                splitted = false;
                t = 0;
            }
        } else if (splitted) {
            t1 += 1;
            int[] coords01 = throwSmth(V0, t, Angle0);
            int[] coords1 = throwSmth(u1, t1, a1);

            birdX = coords1[0] + SbirdX;
            birdY = coords1[1] + SbirdY;

            int[] coords02 = throwSmth(V0, t, Angle0);
            int[] coords2 = throwSmth(u2, t1, a2);

            eggX = coords2[0] + SeggX;
            eggY = coords2[1] + SeggY;
            if (birdY > Y_BORDER && eggY > Y_BORDER) {
                birdY = Y_BORDER + 1;
                eggY = Y_BORDER + 1;
                birdX = X_BORDER;
                eggX = X_BORDER;
                launched = false;
                splitted = false;
                t1 = 0;
                t = 0;
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.gray);
        g.fill3DRect(0, Y_BORDER + 16, WIDTH, Y_BORDER, true);
        g.draw3DRect(0, Y_BORDER + 16, WIDTH, Y_BORDER, true);
        if (!launched) {
            for (int i = 0; i < 120; i++) {
                g.setColor(Color.gray);
                int[] coords = throwSmth(V0, i, Angle0);
                g.drawOval(coords[0] + X_BORDER, coords[1] + Y_BORDER, 10, 10);
            }
        }
        String strAngle0 = "Угол выпуска птицы: " + Double.toString(Angle0);
        String strV0 = "Начальная скорость птицы: " + Double.toString(V0);

        String strAngle1 = "Угол разлета птицы и яйца: " + Double.toString(Angle1);
        String strm1 = "Масса птицы: " + Double.toString(m1);
        String strm2 = "Масса яйца: " + Double.toString(m2);
        String stru1 = "Скорость птицы после выпуска яйца: " + Double.toString(u1);
        String stru2 = "Скорость яйца после выпуска: " + Double.toString(u2);

        g.drawString(strAngle0, X_BORDER, 100);
        g.drawString(strV0, X_BORDER, 115);
        g.drawString(strAngle1, X_BORDER, 130);
        g.drawString(strm1, X_BORDER, 145);
        g.drawString(strm2, X_BORDER, 160);
        g.drawString(stru1, X_BORDER, 175);
        g.drawString(stru2, X_BORDER, 190);

        g.drawImage(egg, eggX, eggY, this);
        g.drawImage(bird, birdX, birdY, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (launched || splitted) move();
        u2 = ((m1 + m2) * V0 - m1 * u1) / m2;
        repaint();
    }


    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP & Angle0 < 180 & !launched) {
                Angle0++;
            } else if (key == KeyEvent.VK_DOWN & Angle0 > 0 & !launched) {
                Angle0--;
            } else if (key == KeyEvent.VK_LEFT & V0 > 0 & !launched) {
                V0--;
            } else if (key == KeyEvent.VK_RIGHT & !launched) {
                V0++;
            } else if (key == KeyEvent.VK_SPACE) {
                launched = true;

            } else if (key == KeyEvent.VK_SHIFT & launched & !splitted) {
                splitted = true;
            }
        }
    }


}
