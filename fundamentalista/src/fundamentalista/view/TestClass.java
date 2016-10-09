package fundamentalista.view;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class TestClass extends JPanel {
	public TestClass() {
		Vector columnNames = new Vector();
		Vector data = new Vector();

		int columns = 3;

		// Get column names

		columnNames.addElement("Id");
		columnNames.addElement("Name");
		columnNames.addElement("Age");

		// Get row data

		Vector row = new Vector(columns);
		row.addElement("1");
		row.addElement("Moshi");
		row.addElement("22");

		data.addElement(row);

		// Create table with database data

		JTable table = new JTable(data, columnNames) {
			public Class getColumnClass(int column) {
				for (int row = 0; row < getRowCount(); row++) {
					Object o = getValueAt(row, column);

					if (o != null) {
						return o.getClass();
					}
				}

				return Object.class;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

	}

	public static void main(String[] args) {
		TestClass testClass = new TestClass(); // **JPanel**
		JFrame frame = new JFrame();
		frame.setSize(500, 600);
		frame.getContentPane().add(testClass); // **add jpanel to frame**
		frame.setVisible(true);
		frame.setLayout(new GridLayout());
	}
}
