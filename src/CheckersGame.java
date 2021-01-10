import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class CheckersGame extends JFrame implements ActionListener {

    private int[][] occupied = new int[11][6];
    private String turn = "red";
    private int storeX = -1, storeY = -1;
    private boolean gameOver = false;
    public boolean restart;

    JLabel l = new JLabel(), prompt = new JLabel(),  whoDisplay = new JLabel("Red players turn");

    JFrame result = new JFrame();
    JButton gb[][] = new JButton [11][6];
    JButton replay = new JButton();

    public CheckersGame() {

            JPanel panel = new JPanel(new GridLayout(11, 6, 0, 0));
            panel.setBorder(new LineBorder(Color.blue, 1));
            whoDisplay.setBorder(new LineBorder(Color.red, 2));
            add(panel, BorderLayout.CENTER);
            result.setBounds(300, 500, 300, 100);

            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 6; j++) {
                    gb[i][j] = new JButton();
                    panel.add(gb[i][j]);
                    gb[i][j].addActionListener(this);
                    occupied[i][j] = 0;
                }
            }
            for (int i = 0; i < 6; i++) {
                occupied[0][i] = 1;
                gb[0][i].setText("R");
                occupied[10][i] = -1;
                gb[10][i].setText("G");
            }

            add(whoDisplay, BorderLayout.SOUTH);
        }



    public void actionPerformed(ActionEvent ae) {
        Font myFont = new Font("Default", Font.PLAIN, 56);
        int clickedX, clickedY;
        if (!gameOver) {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 6; j++) {
                    if (ae.getSource() == gb[i][j]) {
                        clickedX = j;
                        clickedY = i;

                        if (turn.equals("red")) {
                            if (occupied[clickedY][clickedX] == 1) {
                                storeX = clickedX;
                                storeY = clickedY;
                            }
                            if (occupied[clickedY][clickedX] == 0 && storeX != -1) {
                                if (((clickedX == storeX - 1 || clickedX == storeX + 1) && clickedY == storeY + 1) ||
                                        (clickedX == storeX - 2 && clickedY == storeY + 2 && occupied[clickedY - 1][clickedX + 1] != 0) ||
                                        (clickedX == storeX + 2 && clickedY == storeY + 2 && occupied[clickedY - 1][clickedX - 1] != 0)) {
                                    occupied[storeY][storeX] = 0;
                                    occupied[clickedY][clickedX] = 1;
                                    gb[storeY][storeX].setText("");
                                    gb[clickedY][clickedX].setText("R");
                                    turn = "green";
                                    whoDisplay.setText("Green players turn");
                                    storeX = -1; storeY = -1;
                                }
                            }
                        }
                        if (turn.equals("green")) {                         // if it's red's turn
                            if (occupied[clickedY][clickedX] == -1) {      //if piece selected is a red piece, store it as selected
                                storeX = clickedX;
                                storeY = clickedY;
                            }
                            if (occupied[clickedY][clickedX] == 0 && storeX != -1) {
                                if (((clickedX == storeX - 1 || clickedX == storeX + 1) && clickedY == storeY - 1) ||
                                        (clickedX == storeX - 2 && clickedY == storeY - 2 && occupied[clickedY + 1][clickedX + 1] != 0) ||
                                        (clickedX == storeX + 2 && clickedY == storeY - 2 && occupied[clickedY + 1][clickedX - 1] != 0)) {
                                    occupied[storeY][storeX] = 0;
                                    occupied[clickedY][clickedX] = -1;
                                    gb[storeY][storeX].setText("");
                                    gb[clickedY][clickedX].setText("G");
                                    turn = "red";
                                    whoDisplay.setText("Red players turn");
                                    storeX = -1; storeY = -1;
                                }
                            }
                        }
                        if (DoesRedWin()) {
                            gameOver = true;
                            whoDisplay.setText("gameover red wins");
                        }
                        if (DoesGreenWin()) {
                            gameOver = true;
                            whoDisplay.setText("gameover green wins");
                        }
                    }
                }
            }
        }
        if (gameOver || occupied[1][1]==1){

            JDialog d = new JDialog(result, "GAMEOVER");
            d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel p = new JPanel(new GridLayout(0,3,50,50));
            p.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
            replay.addActionListener(this);
            replay.setText("replay?");
            l.setText("GAMEOVER!!! RED PLAYER WINS");
            if (DoesRedWin())
                l.setText("GAMEOVER!!! RED PLAYER WINS");
            if (DoesGreenWin())
                l.setText("GAMEOVER!!! GREEN PLAYER WINS");
            p.add(l);
            p.add(prompt);
            p.add(replay);
            d.add(p,BorderLayout.CENTER);
            d.pack();
            d.setLocationRelativeTo(null);
            d.setVisible(true);

        }

        if (ae.getSource().equals(replay)){
            setRestart(true);
            gameOver = false;

            JFrame ttt = new CheckersGame();
            ttt.setTitle("Alternative Checkers");
            ttt.setBounds(200, 100, 600, 1100);
            ttt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ttt.setVisible(true);
        }


    }
    public void setRestart(boolean input){
        restart = input;
    }
    public boolean isRestart(){
        return restart;
    }

    private Boolean DoesRedWin() {
        boolean result = false;
        for (int i = 0; i < 6; i++) {
            if (occupied[10][i] == 1)
                result = true;
            else {
                result = false;
                break;
            }
            if (result)
                return true;
        }

        for (int i=1; i<11; i++){
            for (int j=0; j<6; j++){
                if (occupied[i][j] == -1){
                    int x = j, y = i;
                    if (j==0) {
                        if (occupied[y-1][x+1]!=0 && occupied[y-2][x+2]!=0)
                            result = true;
                    } else if (j==1){
                        if (occupied[y-1][x-1]!=0 && occupied[y-1][x+1]!=0 && occupied[y-2][x+2]!=0)
                            result = true;
                    } else if (j==4){
                        if (occupied[y-1][x-1]!=0 && occupied[y-1][x+1]!=0 && occupied[y-2][x-2]!=0)
                            result = true;
                    } else if (j==5){
                        if (occupied[y-1][x-1]!=0 && occupied[y-2][x-2]!=0)
                            result = true;
                    } else if (occupied[y-1][x-1]!=0 && occupied[y-1][x+1]!=0 && occupied[y-2][x-2]!=0 && occupied[y-2][x+2]!=0)
                        result = true;
                    else {
                        return false;
                    }
                }
            }
        }
        return result;
    }

    private Boolean DoesGreenWin() {
        boolean result = false;
        for (int i = 0; i < 6; i++) {
            if (occupied[0][i] == -1)
                result = true;
            else {
                result = false;
                break;
            }
            if (result)
                return true;
        }

        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                if (occupied[i][j] == 1) {
                    int x = j, y = i;
                    if (j == 0) {
                        if (occupied[y + 1][x + 1] != 0 && occupied[y + 2][x + 2] != 0)
                            result = true;
                    } else if (j == 1) {
                        if (occupied[y + 1][x - 1] != 0 && occupied[y + 1][x + 1] != 0 && occupied[y + 2][x + 2] != 0)
                            result = true;
                    } else if (j == 4) {
                        if (occupied[y + 1][x - 1] != 0 && occupied[y + 1][x + 1] != 0 && occupied[y + 2][x - 2] != 0)
                            result = true;
                    } else if (j == 5) {
                        if (occupied[y + 1][x - 1] != 0 && occupied[y + 2][x - 2] != 0)
                            result = true;
                    } else if (occupied[y + 1][x - 1] != 0 && occupied[y + 1][x + 1] != 0 && occupied[y + 2][x - 2] != 0 && occupied[y + 2][x + 2] != 0)
                        result = true;
                    else {
                        return false;
                        }
                    }
                }
            }
            return result;
    }

    public static void main (String [] args) {

            JFrame ttt = new CheckersGame();
            ttt.setTitle("Alternative Checkers");
            ttt.setBounds(200, 100, 600, 1100);
            ttt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ttt.setVisible(true);

        }
    }
