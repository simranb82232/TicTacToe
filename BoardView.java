import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class BoardView extends JPanel {
    private BoardModel model;
    private BoardStyle style;
    private JButton buttons[] = new JButton[9];
    private ImageIcon background;
    private ImageIcon xShape;
    private ImageIcon oShape;
    private int counter = 0;
    private int lastIndex;
    private int[] buttonVal = new int[9];
    boolean win;
    int determinant;                                    //Used to determine current player
    int undoCount = 0;                                  //Keeps track of amount of undo's for that turn
    int alt = 0;

    JFrame frame2 = new JFrame();
    JPanel panel3 = new JPanel();
    JButton undoButton = new JButton("Undo");
    JLabel backgroundLabel = new JLabel();

    JLabel xlabel = new JLabel();
    JLabel olabel = new JLabel();

    public BoardView(BoardModel m, BoardStyle s) {
        model = m;
        style = s;
        counter = m.getCounter();
        lastIndex = m.getLastIndex();
        buttonVal = m.getButtonVal();
        background = s.getBackground();                  //BoardStyle will return ImageIcons
        xShape = s.getXShape();
        oShape = s.getOShape();
    }

    public void buttons() {

        ImageIcon backgroundImage = background;
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setLayout(new GridLayout(3, 3));


        panel3.setLayout(new GridBagLayout());
        panel3.setLocation(-300, 100);
        frame2.setSize(600, 700);
        for (int i = 0; i < model.getButtonVal().length; i++) {
            buttons[i] = new JButton();
            buttons[i].getPreferredSize();

            buttons[i].setBorderPainted(false);          //removes border color
            buttons[i].setContentAreaFilled(false);      //this will make the border transparent, by removing the content areas color

            backgroundLabel.add(buttons[i]);
            backgroundLabel.add(buttons[i], new GridBagConstraints());
            buttons[i].addActionListener(this::actionPerformed);
        }

        undoButton.addActionListener(this::actionPerformed);
        frame2.setVisible(true);
        //frame2.add(view);

        ImageIcon xImage = xShape;                      //Set x shape
        xlabel.setIcon(xImage);

        ImageIcon oImage = oShape;                      //Set o shape
        olabel.setIcon(oImage);

        panel3.add(backgroundLabel);
        panel3.add(undoButton);
        frame2.add(panel3);

        undoButton.setEnabled(false);
    }

    public void actionPerformed(ActionEvent a) {        //Controller
        if (alt == counter) {
            alt++;
        }
        if (counter - alt == 3 || counter - alt == 6 || counter - alt == 9) {   //Keep track of how many undo's for that turn
            undoCount = 0;
            alt = counter + 1;
        } else if (counter - alt >= 10) {
            alt = counter;
        } else if (counter - alt == 8 && undoCount == 3) {
            undoCount = 0;
            alt = counter + 1;
        }
        if (undoCount < 3) {                                                  //Set undoButton to disabled after 3 undo's in that turn
            undoButton.setEnabled(true);
        }
        counter++;
        determinant = counter % 2;
        for (int i = 0; i < 9; i++) {
            if (a.getSource() == buttons[i]) {
                if (determinant == 0 && buttonVal[i] == 0) {                 //If counter is even, it is player 2's icon
                    buttons[i].setIcon(oShape);
                    model.add(i,2);                                        //mutator
                    buttons[i].setDisabledIcon(oShape);
//                    buttonVal[i] = 2;
                } else if (determinant == 1 && buttonVal[i] == 0) {         //else, it is plater 1's icon
                    buttons[i].setIcon(xShape);
                    model.add(i,1);                                     //mutator
                    buttons[i].setDisabledIcon(xShape);
//                    buttonVal[i] = 1;
                }
                lastIndex = i;
                buttons[i].setEnabled(false);
            } else if (a.getSource() == undoButton) {                       //If undoButton is clicked, reset buttons
                if (undoCount < 3 && i == 0) {
                    buttons[lastIndex].setIcon(null);
                    model.add(lastIndex,0);                              //mutator
//                    buttonVal[lastIndex] = 0;
                    buttons[lastIndex].setEnabled(true);
                    undoCount++;
                    undoButton.setEnabled(false);
                    alt--;
                    //System.out.print("undo");
                } else if (undoCount == 3 && i == 0) {
                    counter--;
                    if (alt == counter) {
                        alt--;
                    }
                }
            }
        }
        boardChecker();
        checkWinner();
        //System.out.println("Counter:" + counter);
    }

    public void boardChecker() {
        //check rows by first checking value associated with one button, then checking to see if the other 2 are equal
        if ((buttonVal[0] == 1 || buttonVal[0] == 2) && buttonVal[0] == buttonVal[1] && buttonVal[1] == buttonVal[2]) {
            win = true;
            return;
        } else if ((buttonVal[3] == 1 || buttonVal[3] == 2) && buttonVal[3] == buttonVal[4] && buttonVal[4] == buttonVal[5]) {
            win = true;
            return;
        } else if ((buttonVal[6] == 1 || buttonVal[6] == 2) && buttonVal[6] == buttonVal[7] && buttonVal[7] == buttonVal[8]) {
            win = true;
            return;
        }

        //check columns
        else if ((buttonVal[0] == 1 || buttonVal[0] == 2) && buttonVal[0] == buttonVal[3] && buttonVal[3] == buttonVal[6]) {
            win = true;
            return;
        } else if ((buttonVal[1] == 1 || buttonVal[1] == 2) && buttonVal[1] == buttonVal[4] && buttonVal[4] == buttonVal[7]) {
            win = true;
            return;
        } else if ((buttonVal[2] == 1 || buttonVal[2] == 2) && buttonVal[2] == buttonVal[5] && buttonVal[5] == buttonVal[8]) {
            win = true;
            return;
        }

        //check diagonals
        else if ((buttonVal[0] == 1 || buttonVal[0] == 2) && buttonVal[0] == buttonVal[4] && buttonVal[4] == buttonVal[8]) {
            win = true;
            return;
        } else if ((buttonVal[2] == 1 || buttonVal[2] == 2) && buttonVal[2] == buttonVal[4] && buttonVal[4] == buttonVal[6]) {
            win = true;
            return;
        }
        win = false;
    }

    public void checkWinner() {
        int player;
        if (determinant == 0)           //If determinant is 0, player 2 is winner
            player = 2;
        else                            //else, player 1 is the winner
            player = 1;
        int total = 0;
        for (int i : buttonVal) {
            total += i;
        }
        if (win == true) {
            JOptionPane.showMessageDialog(null, "Player " + player + " wins!");
            for (int i = 0; i < 9; i++) {
                // buttons[i].setIcon(null);
                buttons[i].setEnabled(false);
            }
            undoButton.setEnabled(false);
        } else if (total == 13 && win == false) {
            JOptionPane.showMessageDialog(null, "Draw!");
            for (int i = 0; i < 9; i++) {
                // buttons[i].setIcon(null);
                buttons[i].setEnabled(false);
            }
            undoButton.setEnabled(false);
        }
    }
}