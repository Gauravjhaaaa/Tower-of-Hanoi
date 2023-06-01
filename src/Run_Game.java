import javax.swing.*;
import java.awt.event.*;

public class Run_Game implements ActionListener {

    private JFrame frame;
    private Tower tower;
    private JMenuItem level;
    private JMenuItem exit;
    private int moveCount;

    public Run_Game(String title) {
        frame = new JFrame(title);
        build();
        moveCount = 0;
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == level) {
            Object[] values = {3, 4, 5, 6, 7, 8, 9};
            Object val = JOptionPane.showInputDialog(frame, "Number of Disks:", "Level",
                    JOptionPane.INFORMATION_MESSAGE, null, values, values[0]);
            if (val != null) {
                int numberOfDisks = (int) val;
                tower.init(numberOfDisks);
                moveCount = 0; // Reset move count when starting a new level
            }
        } else if (ev.getSource() == exit) {
            System.exit(0);
        }
    }

    private void build() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Options");

        level = new JMenuItem("Level");
        exit = new JMenuItem("Exit");

        gameMenu.add(level);
        gameMenu.add(exit);

        menuBar.add(gameMenu);

        level.addActionListener(this);
        exit.addActionListener(this);

        tower = new Tower();
        frame.getContentPane().add(tower);

        frame.setJMenuBar(menuBar);
        frame.setSize(660, 280);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Run_Game game = new Run_Game("Tower Of Hanoi");
        });
    }
}
