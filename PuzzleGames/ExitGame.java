import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ExitGame implements ActionListener {

	MyFrame an;

	public ExitGame(MyFrame an) {
		this.an = an;
	}

	// close game
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}