import java.awt.*;

public class Run {

    public static void main(String[] args) {

        MyFrame frame = new MyFrame();
        frame.init();
        frame.setPreferredSize(new Dimension(1400, 1000));

        frame.pack();
        frame.setVisible(true);
    }

}
