import javax.swing.*;

public class SpaceTheme implements BoardStyle {
    public ImageIcon getBackground() {
        return new ImageIcon("spaceTheme.png");
    }

    public ImageIcon getXShape() {
        return new ImageIcon("spaceX.png");
    }

    public ImageIcon getOShape() {
        return new ImageIcon("spaceY.png");
    }
}