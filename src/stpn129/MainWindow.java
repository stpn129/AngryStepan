package stpn129;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame {

    private JTextField Angle1Field;
    private JTextField m1Field;
    private JTextField m2Field;
    private JTextField u1Field;

    public MainWindow() {
        setTitle("Angry Stepan!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = sSize.height;
        int width = sSize.width;
        setSize(width, width - 25);
        setResizable(false);
        setLocation(0, 0);
        JPanel gameField = new GameField();
        add(gameField);


        Angle1Field = new JTextField(15);
        Angle1Field.setToolTipText("Угол разлета птицы и яйца");
        Angle1Field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Angle1 = Angle1Field.getText();
                ((GameField) gameField).setAngle1(Double.parseDouble(Angle1));
            }
        });
        m1Field = new JTextField(15);
        m1Field.setToolTipText("Масса птицы");
        m1Field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m1 = m1Field.getText();
                ((GameField) gameField).setM1(Double.parseDouble(m1));
            }
        });
        m2Field = new JTextField(15);
        m2Field.setToolTipText("Масса яйца");
        m2Field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m2 = m2Field.getText();
                ((GameField) gameField).setM2(Double.parseDouble(m2));
            }
        });
        u1Field = new JTextField(15);
        u1Field.setToolTipText("Скорость птицы после выпуска яйца");
        u1Field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u1 = u1Field.getText();
                ((GameField) gameField).setU1(Double.parseDouble(u1));
            }
        });
        gameField.add(Angle1Field);
        gameField.add(m1Field);
        gameField.add(m2Field);
        gameField.add(u1Field);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
