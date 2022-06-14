import javax.swing.*;

public class FlowerTheme implements BoardStyle{
    public ImageIcon getBackground() {
        return new ImageIcon("flowerTheme.png");
    }

    public ImageIcon getXShape() {
        return new ImageIcon("flowerX.png");
    }

    public ImageIcon getOShape() {
        return new ImageIcon("flowerY.png");
    }
}