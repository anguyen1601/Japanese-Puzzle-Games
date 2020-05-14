import java.awt.*;
import javax.swing.*;

public interface MyGame {

    void makeGrid(Container contentPane, JPanel center, JPanel bottom);

    void reset();

    void fill();

    void setElement(int row, int col);

    boolean selected(int row, int col);

    boolean check();

    boolean isSolved();
}
