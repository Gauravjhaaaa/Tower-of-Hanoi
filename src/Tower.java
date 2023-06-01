import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Tower extends JPanel implements MouseListener, MouseMotionListener {

    public static final long serialVersionUID = 0xff;

    private Stack<Rectangle2D.Double> s[] = new Stack[3];
    private Stack<Color> disk_color[] = new Stack[3];
    private static Rectangle2D.Double top = null;
    private Color top_color = null;
    private double ax, ay, w, h;
    private boolean draggable = false, firstTime = false;
    private int moveCount = 0;
    private boolean success = false;
    private JLabel movesLabel;
    private int current_tower = -1;

    public Tower() {
        setLayout(null);
        firstTime = true;
        init(4);
        addMouseListener(this);
        addMouseMotionListener(this);
        movesLabel = new JLabel("Moves: 0");
        movesLabel.setForeground(Color.white); // Set color
        movesLabel.setBounds(10, 10, 1000, 50); // Set position
        add(movesLabel);
    }

    public void init(int val) {
        Color c[] = { Color.yellow, Color.red, Color.blue, Color.pink, Color.cyan, Color.magenta, Color.green,
                Color.orange, Color.lightGray };

        s[0] = new Stack<Rectangle2D.Double>();
        s[1] = new Stack<Rectangle2D.Double>();
        s[2] = new Stack<Rectangle2D.Double>();

        disk_color[0] = new Stack<Color>();
        disk_color[1] = new Stack<Color>();
        disk_color[2] = new Stack<Color>();

        for (int i = 0; i < val; i++) {
            Rectangle2D.Double r = new Rectangle2D.Double();

            double x = getWidth() / 6;
            x = (x == 0) ? 109 : x;
            double wr = val * 25 - 20 * i;
            r.setFrame(x - wr / 2, 190 - i * 20, wr, 20);
            s[0].push(r);
            disk_color[0].push(c[i]);
        }

        top = null;
        top_color = null;
        ax = 0.0;
        ay = 0.0;
        w = 0.0;
        h = 0.0;
        draggable = false;
        repaint();
    }

    public void mouseClicked(MouseEvent ev) {
    }

    public void mousePressed(MouseEvent ev) {
        Point pos = ev.getPoint();
        current_tower = current_tower(pos); // Set the current tower of the disk
        if (!s[current_tower].empty()) {
            top = s[current_tower].peek();
            if (top.contains(pos)) {
                top = s[current_tower].pop();
                top_color = disk_color[current_tower].pop();
                ax = top.getX();
                ay = top.getY();
                w = pos.getX() - ax;
                h = pos.getY() - ay;
                draggable = true; // Allowing dragging if current mouse position is in top disk
            } else {
                top = null;
                top_color = Color.black;
                draggable = false;
            }
        }
    }

    public void mouseReleased(MouseEvent ev) {
    if (top != null && draggable == true) {
        int tower = current_tower(ev.getPoint());
        double x, y;
        if (!s[tower].empty()) {
            if (s[tower].peek().getWidth() > top.getWidth())
                y = s[tower].peek().getY() - 20;
            else {
                JOptionPane.showMessageDialog(this, "Wrong Move", "Tower Of Hanoi",
                        JOptionPane.ERROR_MESSAGE);
                restoreDisk(); // Restore the disk to its original position
                return; // Return without updating move count if wrong move is made
            }
        } else
            y = getHeight() - 40;

        x = (int) (getWidth() / 6 + (getWidth() / 3) * tower - top.getWidth() / 2);
        top.setFrame(x, y, top.getWidth(), top.getHeight());
        s[tower].push(top);
        disk_color[tower].push(top_color);

        top = null;
        top_color = Color.black;
        draggable = false;

        if (s[0].empty() && s[1].empty()) { // Check if both rod 1 and rod 2 are empty
            success = true;
            JOptionPane.showMessageDialog(this, "Congratulations! Puzzle Solved in " + moveCount + " moves.",
                    "Tower Of Hanoi", JOptionPane.INFORMATION_MESSAGE);
        }

        if (tower != current_tower) { // Check if the disk has changed its place to a different rod
            moveCount++; // Increment move count only if a valid move is made
            movesLabel.setText("Moves: " + moveCount);
        }

        repaint();
    }
}

private void restoreDisk() {
    top.setFrame(ax, ay, top.getWidth(), top.getHeight()); // Restore the disk to its original position
    s[current_tower].push(top);
    disk_color[current_tower].push(top_color);
    top = null;
    top_color = Color.black;
    draggable = false;
    repaint();
}


    public void mouseEntered(MouseEvent ev) {
    }

    public void mouseExited(MouseEvent ev) {
    }

    public void mouseMoved(MouseEvent ev) {
    }

    public void mouseDragged(MouseEvent ev) {
        int cx = ev.getX(); // getting current mouse position
        int cy = ev.getY();
        if (top != null && draggable == true) {
            top.setFrame(cx - w, cy - h, top.getWidth(), top.getHeight());
            repaint(); // repainting if dragging a disk
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int holder_x = getWidth() / 6;
        int holder_y1 = getHeight() - 10 * 20, holder_y2 = getHeight() - 20;

        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(holder_x, holder_y1, holder_x, holder_y2);
        g2d.drawLine(3 * holder_x, holder_y1, 3 * holder_x, holder_y2);
        g2d.drawLine(5 * holder_x, holder_y1, 5 * holder_x, holder_y2);
        g2d.drawLine(0, holder_y2, getWidth(), holder_y2);

        g2d.setStroke(new BasicStroke(1));

        g2d.setColor(top_color);

        if (draggable == true && top != null)
            g2d.fill(top); // drawing dragged disk

        drawtower(g2d, 0); // drawing disks of each tower
        drawtower(g2d, 1);
        drawtower(g2d, 2);

        if (success) {
            g2d.setColor(Color.white);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Puzzle Solved!", getWidth() / 2 - 80, 30);
        }
    }

    private void drawtower(Graphics2D g2d, int n) {
        if (!s[n].empty()) {
            for (int i = 0; i < s[n].size(); i++) {
                g2d.setColor(disk_color[n].get(i));
                g2d.fill(s[n].get(i));
            }
        }
    }

    private int current_tower(Point p) {
        int holder_x = getWidth() / 6;
        int holder_y1 = getHeight() - 10 * 20, holder_y2 = getHeight() - 20;

        if (p.x >= holder_x - 50 && p.x <= holder_x + 50)
            return 0;
        else if (p.x >= 3 * holder_x - 50 && p.x <= 3 * holder_x + 50)
            return 1;
        else if (p.x >= 5 * holder_x - 50 && p.x <= 5 * holder_x + 50)
            return 2;
        else
            return -1;
    }
}
