package fundamentalista.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fundamentalista.FundamentoBusinessException;
import fundamentalista.entidade.Papel;
import fundamentalista.entidade.Parametro;
import fundamentalista.entidade.SetorEnum;
import fundamentalista.service.PapelService;
import fundamentalista.view.component.JTextFieldValidation;

@Component
public class PapelView extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(PapelView.class);

	@Autowired
	private PapelService papelService;

	private List<Parametro> parametros;

	private SetorEnum setorSelecionado;

	private Color colorPanel = new Color(230, 255, 255);

	public PapelView() {
		this.setTitle("TABELA M�GICA");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		setSize(1200, 600);

		parametros = new ArrayList<>();

		parametros.add(new Parametro("P/L", true, true, 1, 1000));
		parametros.add(new Parametro("P/VP", true, true, 0, 1000));
		parametros.add(new Parametro("PSR", false, true, 0, 1000));
		parametros.add(new Parametro("DIV.YIELD", true, true, 0));
		parametros.add(new Parametro("MRG EBIT", true, true, 0));
		parametros.add(new Parametro("LIQ. CORR.", true, true, 1));
		parametros.add(new Parametro("ROIC", false, true, 0));
		parametros.add(new Parametro("ROE", true, true, 0));
		parametros.add(new Parametro("LIQ. 2MESES", false, true, 100000));
		parametros.add(new Parametro("CRESC.", false, true, 5));

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
		menu = new JMenu("Tabela M�gica");
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
		menuItem = new JMenuItem("Setor Vestu�rio", KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("PapelView.createMenuItemVestuario().new ActionListener() {...}.actionPerformed()");
				setorSelecionado = SetorEnum.VESTUARIO;
				atualizarPapeis();
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
				logger.info("PapelView.createMenuItemFinanceiro().new ActionListener() {...}.actionPerformed()");
				setorSelecionado = SetorEnum.FINANCEIRO;
				atualizarPapeis();
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
				logger.info("PapelView.createMenuItemEnergetico().new ActionListener() {...}.actionPerformed()");
				setorSelecionado = SetorEnum.ENERGETICO;
				atualizarPapeis();

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
				logger.info("PapelView.createMenuItemTodoSetor().new ActionListener() {...}.actionPerformed() ");
				setorSelecionado = SetorEnum.TODOS;
				atualizarPapeis();
			}
		});
		return menuItem;
	}

	private void preparar(List<Papel> papeis) {
		// headers for the table

		Object[][] data = new Object[papeis.size() + 3][12];
		Double somaPL = 0.0;
		Double somaPVP = 0.0;
		Double somaPSR = 0.0;
		Double somaDIVYIELD = 0.0;
		Double somaMAREBIT = 0.0;
		Double somaLIQCOR = 0.0;
		Double somaROIC = 0.0;
		Double somaROE = 0.0;
		Double somaLIQMES = 0.0;
		Double somaCRES = 0.0;

		for (int linha = 0; linha < papeis.size(); linha++) {
			data[linha][0] = papeis.get(linha).getPapel();
			data[linha][1] = papeis.get(linha).getFundamento().getP_l();
			data[linha][2] = papeis.get(linha).getFundamento().getP_vp();
			data[linha][3] = papeis.get(linha).getFundamento().getP_sr();
			data[linha][4] = papeis.get(linha).getFundamento().getDividentoYIELD();
			data[linha][5] = papeis.get(linha).getFundamento().getMargemEBIT();
			data[linha][6] = papeis.get(linha).getFundamento().getLiquidezCorrete();
			data[linha][7] = papeis.get(linha).getFundamento().getRoic();
			data[linha][8] = papeis.get(linha).getFundamento().getRoe();
			data[linha][9] = papeis.get(linha).getFundamento().getLiquidez2Meses();
			data[linha][10] = papeis.get(linha).getFundamento().getCrescimento();
			data[linha][11] = papeis.get(linha).getRank();

			somaPL += papeis.get(linha).getFundamento().getP_l();
			somaPVP += papeis.get(linha).getFundamento().getP_vp();
			somaPSR += papeis.get(linha).getFundamento().getP_sr();
			somaDIVYIELD += papeis.get(linha).getFundamento().getDividentoYIELD();
			somaMAREBIT += papeis.get(linha).getFundamento().getMargemEBIT();
			somaLIQCOR += papeis.get(linha).getFundamento().getLiquidezCorrete();
			somaROIC += papeis.get(linha).getFundamento().getRoic();
			somaROE += papeis.get(linha).getFundamento().getRoe();
			somaLIQMES += papeis.get(linha).getFundamento().getLiquidez2Meses();
			somaCRES += papeis.get(linha).getFundamento().getCrescimento();
		}

		data[papeis.size() + 1][1] = "M�DIA P/L";
		data[papeis.size() + 1][2] = "M�DIA P/VP";
		data[papeis.size() + 1][3] = "M�DIA PSR";
		data[papeis.size() + 1][4] = "M�DIA DIV.YIELD";
		data[papeis.size() + 1][5] = "M�DIA MRG EBIT";
		data[papeis.size() + 1][6] = "M�DIA LIQ. CORR.";
		data[papeis.size() + 1][7] = "M�DIA ROIC";
		data[papeis.size() + 1][8] = "M�DIA ROE";
		data[papeis.size() + 1][9] = "M�DIA M�DIA LIQ. 2MESES";
		data[papeis.size() + 1][10] = "M�DIA M�DIA CRESC.";

		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);

		data[papeis.size() + 2][1] = df.format(somaPL / papeis.size());
		data[papeis.size() + 2][2] = df.format(somaPVP / papeis.size());
		data[papeis.size() + 2][3] = df.format(somaPSR / papeis.size());
		data[papeis.size() + 2][4] = df.format(somaDIVYIELD / papeis.size());
		data[papeis.size() + 2][5] = df.format(somaMAREBIT / papeis.size());
		data[papeis.size() + 2][6] = df.format(somaLIQCOR / papeis.size());
		data[papeis.size() + 2][7] = df.format(somaROIC / papeis.size());
		data[papeis.size() + 2][8] = df.format(somaROE / papeis.size());
		data[papeis.size() + 2][9] = df.format(somaLIQMES / papeis.size());
		data[papeis.size() + 2][10] = df.format(somaCRES / papeis.size());

//		String[] columns = new String[] { "PAPEL", "P/L", "P/VP", "DIV.YIELD", "MRG EBIT", "LIQ. CORR.", "ROIC", "ROE",
//				"LIQ. 2MESES", "CRESC.", "RANK" };

		List<String> columns = new ArrayList<>();
		columns.add("PAPEL");
		for (Parametro parametro : parametros) {
			columns.add(parametro.getDescricao());
		}
		columns.add("RANK");

		// create table with data
		JTable jTtable = new JTable(data, columns.toArray());
		jTtable.setRowHeight(0, 30);
		jTtable.setRowHeight(1, 30);
		jTtable.setRowHeight(2, 30);
		jTtable.setRowHeight(3, 30);
		jTtable.setRowHeight(4, 30);

		jTtable.setFillsViewportHeight(true);

		JPanel panelTable = new JPanel(new BorderLayout()); // PREFERRED!
		panelTable.setBackground(colorPanel);
		panelTable.add(new JScrollPane(jTtable));

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(panelFiltro());
		container.add(panelInfo());
		container.add(panelTable);

		this.getContentPane().removeAll();
		this.getContentPane().add(container);

		SwingUtilities.updateComponentTreeUI(this);
		// this.repaint();
	}

	private JPanel panelInfo() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		// setbackground of panel
		panel.setBackground(colorPanel);
//		panel.add(new JLabel("Par�metros:"));

//		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Par�metros"));

		for (Parametro parametro : parametros) {
			JCheckBox jCheckBox = new JCheckBox(parametro.getDescricao(), parametro.isAtivo());
//
//			jCheckBox.addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					System.out.println(e.getID() == ActionEvent.ACTION_PERFORMED ? "ACTION_PERFORMED" : e.getID());
//				}
//			});
			jCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox item = (JCheckBox) e.getItem();
					mudarParametros(item, e.getStateChange() == ItemEvent.SELECTED);
					atualizarPapeis();
//					System.out.println(e.getStateChange() == ItemEvent.SELECTED ? "SELECTED" : "DESELECTED");
				}
			});
			jCheckBox.setBackground(colorPanel);
			panel.add(jCheckBox);
		}

		return panel;
	}

	private JPanel panelFiltro() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel.setBackground(colorPanel);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Filtros:"));

		for (Parametro parametro : parametros) {
			JCheckBox jCheckBox = new JCheckBox(parametro.getDescricao(), parametro.isAplicarFiltro());
			jCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox item = (JCheckBox) e.getItem();
					mudarFiltro(item, e.getStateChange() == ItemEvent.SELECTED);
					atualizarPapeis();
				}
			});
			jCheckBox.setBackground(colorPanel);

//			JTextField text = new JTextField(parametro.getMin().toString(), 5);
			JTextFieldValidation text = new JTextFieldValidation(parametro.getMin().toString(), 5);

//			text.addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					System.out.println(e.getSource());
//				}
//			});

//			text.addPropertyChangeListener(new PropertyChangeListener() {
//				public void propertyChange(PropertyChangeEvent evt) {
//					System.out.println(evt);
//				}
//			});
			text.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					updateTextField();
				}

				public void removeUpdate(DocumentEvent e) {
					updateTextField();
				}

				public void insertUpdate(DocumentEvent e) {
					updateTextField();
				}

				public void updateTextField() {
					if (text != null && !text.getText().trim().equals("") && Integer.parseInt(text.getText()) > 0) {
						int min = Integer.parseInt(text.getText());
						mudarFiltro(parametro, min);
						atualizarPapeis();
					}
				}
			});

			JPanel iPanel = new JPanel();
			iPanel.setBackground(colorPanel);
			iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.Y_AXIS));
			iPanel.setBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), parametro.getDescricao()));

			iPanel.add(jCheckBox);
			iPanel.add(text);
			panel.add(iPanel);
		}

		return panel;
	}

	public void refresh() {
		// SwingUtilities.invokeLater(new Runnable() {
		// @Override
		// public void run() {
		// // new PapelView();
		// }
		// });
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void mudarFiltro(JCheckBox item, boolean filtro) {
		for (Parametro parametro : parametros) {
			if (parametro.getDescricao().equalsIgnoreCase(item.getText())) {
				parametro.setAplicarFiltro(filtro);
			}
		}
	}

	private void mudarFiltro(Parametro parametro, Integer min) {
		parametro.setMin(min);
	}

	private void mudarParametros(JCheckBox item, boolean ativo) {
		for (Parametro parametro : parametros) {
			if (parametro.getDescricao().equalsIgnoreCase(item.getText())) {
				parametro.setAtivo(ativo);
			}
		}
	}

	private void atualizarPapeis() {
		List<Papel> papeis;

		try {
			papeis = papelService.findBySetor(setorSelecionado);
			preparar(papelService.analizarPapeis(papeis, parametros));
		} catch (FundamentoBusinessException e1) {
			e1.printStackTrace();
		}
	}

}
