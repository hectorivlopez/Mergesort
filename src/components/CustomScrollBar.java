package components;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class CustomScrollBar extends JScrollBar {
    public CustomScrollBar(int ori) {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(new Color(64,67,81));
        setBackground(new Color(34,37,51));
        setOrientation(ori);
    }
}
