package fundamentalista.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fundamentalista.FundamentoBusinessException;
import fundamentalista.entidade.Papel;
import fundamentalista.service.PapelService;

@Component
public class PapelView extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(PapelView.class);

	@Autowired
	private PapelService papelService;

	public PapelView() {
		this.setTitle("TABELA MÁGICA");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		setSize(1200, 600);
		// setPreferredSize(new Dimension(1200, 600));
		createMenu();
	}

	private void createMenu() {
		// Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("Tabela Mágica");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);

		menu.add(createMenuItemTodoSetor());
		menu.add(createMenuItemEnergetico());
		menu.add(createMenuItemFinanceiro());
		menu.add(createMenuItemVestuario());

		// a submenu
		menu.addSeparator();

		menu.add(createMenuItemSair());

		setJMenuBar(menuBar);

	}

	private JMenuItem createMenuItemSair() {
		JMenuItem menuItem;
		menuItem = new JMenuItem("Sair", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		return menuItem;
	}

	private JMenuItem createMenuItemVestuario() {
		JMenuItem menuItem;
		menuItem = new JMenuItem("Setor Vestuário", KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Papel> papeis;
				try {
					papeis = papelService.findBySetor(21);
					preparar(papelService.analizarPapeis(papeis));
				} catch (FundamentoBusinessException e1) {
					e1.printStackTrace();
				}
			}
		});
		return menuItem;
	}

	private JMenuItem createMenuItemFinanceiro() {
		JMenuItem menuItem;
		menuItem = new JMenuItem("Setor Financeiro", KeyEvent.VK_F);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Papel> papeis;
				try {
					papeis = papelService.findBySetor(35);
					preparar(papelService.analizarPapeis(papeis));
				} catch (FundamentoBusinessException e1) {
					e1.printStackTrace();
				}

			}
		});
		return menuItem;
	}

	private JMenuItem createMenuItemEnergetico() {
		JMenuItem menuItem;
		menuItem = new JMenuItem("Setor Energetico", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Papel> papeis;
				try {
					papeis = papelService.findBySetor(32);
					preparar(papelService.analizarPapeis(papeis));
				} catch (FundamentoBusinessException e1) {
					e1.printStackTrace();
				}

			}
		});
		return menuItem;
	}

	private JMenuItem createMenuItemTodoSetor() {
		JMenuItem menuItem;
		menuItem = new JMenuItem("Todo o Setor", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Papel> papeis;
				try {
					logger.info("Service "+papelService);
					papeis = papelService.findAll();
					preparar(papelService.analizarPapeis(papeis));
				} catch (FundamentoBusinessException e1) {
					e1.printStackTrace();
				}

			}
		});
		return menuItem;
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
			data[linha][1] = papeis.get(linha).getFundamento().getP_l();
			data[linha][2] = papeis.get(linha).getFundamento().getP_vp();
			data[linha][3] = papeis.get(linha).getFundamento().getDividentoYIELD();
			data[linha][4] = papeis.get(linha).getFundamento().getMargemEBIT();
			data[linha][5] = papeis.get(linha).getFundamento().getLiquidezCorrete();
			data[linha][6] = papeis.get(linha).getFundamento().getRoe();
			data[linha][7] = papeis.get(linha).getFundamento().getLiquidez2Meses();
			data[linha][8] = papeis.get(linha).getFundamento().getCrescimento();
			data[linha][9] = papeis.get(linha).getRank();

			somaPL += papeis.get(linha).getFundamento().getP_l();
			somaPVP += papeis.get(linha).getFundamento().getP_vp();
			somaDIVYIELD += papeis.get(linha).getFundamento().getDividentoYIELD();
			somaMAREBIT += papeis.get(linha).getFundamento().getMargemEBIT();
			somaLIQCOR += papeis.get(linha).getFundamento().getLiquidezCorrete();
			somaROE += papeis.get(linha).getFundamento().getRoe();
			somaLIQMES += papeis.get(linha).getFundamento().getLiquidez2Meses();
			somaCRES += papeis.get(linha).getFundamento().getCrescimento();
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

		table.setFillsViewportHeight(true);

		JPanel p = new JPanel(new BorderLayout()); // PREFERRED!
		p.add(new JScrollPane(table));
		// this.add(p);
		this.getContentPane().removeAll();
		this.getContentPane().add(p);

		SwingUtilities.updateComponentTreeUI(this);
		// this.repaint();
	}

	public void refresh() {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
////				new PapelView();
//			}
//		});
		SwingUtilities.updateComponentTreeUI(this);
	}

}
