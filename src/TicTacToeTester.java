import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeTester {
    public static void main(String[] args) {
        int counter = 0;
        int lastIndex = 0;
        int[] buttonVal = new int[9];

        BoardModel model = new BoardModel(counter, lastIndex, buttonVal);

        JFrame frame = new JFrame();

        JPanel panel = new JPanel(new BorderLayout());

        frame.setSize(600, 700);
        frame.setVisible(true);

        JPanel panel2 = new JPanel(new FlowLayout());

        JButton spaceButton = new JButton("Space Theme");

        spaceButton.setEnabled(true);
        spaceButton.setBounds(60, 80, 30, 10);

        JButton flowerButton = new JButton("Flower Theme");
        flowerButton.setEnabled(true);
        flowerButton.setBounds(60, 80, 30, 10);
        JLabel text = new JLabel("Choose a theme:");

        spaceButton.setVisible(true);
        flowerButton.setVisible(true);
        text.setVisible(true);

        spaceButton.addActionListener(new ActionListener() {        //Add actionListener to spaceButton
            public void actionPerformed(ActionEvent e) {
                BoardStyle theme = new SpaceTheme();
                BoardView view = new BoardView(model, theme);

                frame.setVisible(false);
                view.buttons();
            }
        });


        flowerButton.addActionListener(new ActionListener() {       //Add actionListener to flowerButton
            public void actionPerformed(ActionEvent e) {
                BoardStyle theme = new FlowerTheme();
                BoardView view = new BoardView(model, theme);

                frame.setVisible(false);
                view.buttons();
            }
        });

        panel.add(spaceButton, BorderLayout.WEST);
        panel.add(text, BorderLayout.NORTH);
        panel2.add(panel);
        panel.add(flowerButton, BorderLayout.EAST);
        frame.add(panel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}