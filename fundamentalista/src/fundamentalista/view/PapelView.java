package fundamentalista.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import fundamentalista.entidade.Papel;

public class PapelView extends JFrame {

	public PapelView() {
		setPreferredSize(new Dimension(1200, 600));
	}

	public void preparar(List<Papel> papeis) {
		// headers for the table
		String[] columns = new String[] { "PAPEL", "P/L", "P/VP", "DIV.YIELD", "MRG EBIT", "LIQ. CORR.", "ROE", "LIQ. 2MESES", "CRESC.", "RANK" };

		Object[][] data = new Object[papeis.size() + 3][10];
		Double somaPL = 0.0;
		Double somaPVP = 0.0;
		Double somaDIVYIELD = 0.0;
		Double somaMAREBIT = 0.0;
		Double somaLIQCOR = 0.0;
		Double somaROE = 0.0;
		Double somaLIQMES = 0.0;
		Double somaCRES = 0.0;

		for (int linha = 0; linha < papeis.size(); linha++) {
			data[linha][0] = papeis.get(linha).getPapel();
			data[linha][1] = papeis.get(linha).getCotacoes().getP_l();
			data[linha][2] = papeis.get(linha).getCotacoes().getP_vp();
			data[linha][3] = papeis.get(linha).getCotacoes().getDividentoYIELD();
			data[linha][4] = papeis.get(linha).getCotacoes().getMargemEBIT();
			data[linha][5] = papeis.get(linha).getCotacoes().getLiquidezCorrete();
			data[linha][6] = papeis.get(linha).getCotacoes().getRoe();
			data[linha][7] = papeis.get(linha).getCotacoes().getLiquidez2Meses();
			data[linha][8] = papeis.get(linha).getCotacoes().getCrescimento();
			data[linha][9] = papeis.get(linha).getRank();

			somaPL += papeis.get(linha).getCotacoes().getP_l();
			somaPVP += papeis.get(linha).getCotacoes().getP_vp();
			somaDIVYIELD += papeis.get(linha).getCotacoes().getDividentoYIELD();
			somaMAREBIT += papeis.get(linha).getCotacoes().getMargemEBIT();
			somaLIQCOR += papeis.get(linha).getCotacoes().getLiquidezCorrete();
			somaROE += papeis.get(linha).getCotacoes().getRoe();
			somaLIQMES += papeis.get(linha).getCotacoes().getLiquidez2Meses();
			somaCRES += papeis.get(linha).getCotacoes().getCrescimento();
		}

		data[papeis.size() + 1][1] = "MÉDIA P/L";
		data[papeis.size() + 1][2] = "MÉDIA P/VP";
		data[papeis.size() + 1][3] = "MÉDIA DIV.YIELD";
		data[papeis.size() + 1][4] = "MÉDIA MRG EBIT";
		data[papeis.size() + 1][5] = "MÉDIA LIQ. CORR.";
		data[papeis.size() + 1][6] = "MÉDIA ROE";
		data[papeis.size() + 1][7] = "MÉDIA MÉDIA LIQ. 2MESES";
		data[papeis.size() + 1][8] = "MÉDIA MÉDIA CRESC.";

		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);

		data[papeis.size() + 2][1] = df.format(somaPL / papeis.size());
		data[papeis.size() + 2][2] = df.format(somaPVP / papeis.size());
		data[papeis.size() + 2][3] = df.format(somaDIVYIELD / papeis.size());
		data[papeis.size() + 2][4] = df.format(somaMAREBIT / papeis.size());
		data[papeis.size() + 2][5] = df.format(somaLIQCOR / papeis.size());
		data[papeis.size() + 2][6] = df.format(somaROE / papeis.size());
		data[papeis.size() + 2][7] = df.format(somaLIQMES / papeis.size());
		data[papeis.size() + 2][8] = df.format(somaCRES / papeis.size());
		

		// create table with data
		JTable table = new JTable(data, columns);
		table.setRowHeight(0, 30);
		table.setRowHeight(1, 30);
		table.setRowHeight(2, 30);
		table.setRowHeight(3, 30);
		table.setRowHeight(4, 30);
		// add the table to the frame

		this.setTitle("TABELA MÁGICA");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		// JButton print = new JButton("Fechar");
		// print.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // try {
		// // DefaultTableModel viewModel = (DefaultTableModel)
		// table.getModel();
		// // DefaultTableModel printModel = new DefaultTableModel(0,
		// viewModel.getColumnCount());
		// // for (int row : table.getSelectedRows()) {
		// // printModel.addRow((Vector) viewModel.getDataVector().get(row));
		// // }
		// // JTable toPrint = new JTable(printModel);
		// // toPrint.setSize(toPrint.getPreferredSize());
		// // JTableHeader tableHeader = toPrint.getTableHeader();
		// // tableHeader.setSize(tableHeader.getPreferredSize());
		// // toPrint.print(JTable.PrintMode.FIT_WIDTH);
		// // } catch (PrinterException ex) {
		// // ex.printStackTrace();
		// // }
		// }
		// });

		this.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void display() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PapelView();
			}
		});
	}

}
