package fundamentalista.view.component;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class JTextFieldValidation extends JTextField {

	public JTextFieldValidation(String string, int i) {
		super(string, i);

	}

	private static final long serialVersionUID = 1L;

	@Override
	public void processKeyEvent(KeyEvent ev) {
		if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == 8) {
			super.processKeyEvent(ev);
		}
		ev.consume();
		return;
	}

	/**
	 * As the user is not even able to enter a dot ("."), only integers (whole
	 * numbers) may be entered.
	 */
	public Long getNumber() {
		Long result = null;
		String text = getText();
		if (text != null && !"".equals(text)) {
			result = Long.valueOf(text);
		}
		return result;
	}

}