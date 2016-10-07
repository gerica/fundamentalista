package fundamentalista.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fundamentalista.entidade.Papel;

public class PapelService {

	public List<Papel> analizarPapeis(List<Papel> papeis) {
		List<Papel> papeisCantidatos = new ArrayList<Papel>();

		for (Papel papel : papeis) {
			// System.out.println(papel);
			if (papel.getCotacoes().getP_l() < 1 || papel.getCotacoes().getP_l() > 30) {
				continue;
			}
			if (papel.getCotacoes().getP_vp() < 0 || papel.getCotacoes().getP_vp() > 20) {
				continue;
			}
			if (papel.getCotacoes().getRoe() < 0) {
				continue;
			}
			if (papel.getCotacoes().getDividentoYIELD() <= 0) {
				continue;
			}
			if (papel.getCotacoes().getLiquidezCorrete() < 1) {
				continue;
			}
			if (papel.getCotacoes().getMargemEBIT() < 0) {
				continue;
			}
			if (papel.getCotacoes().getCrescimento() < 5) {
				continue;
			}
			if (papel.getCotacoes().getLiquidez2Meses() < 100000) {
				continue;
			}
			papeisCantidatos.add(papel);

		}
		System.out.println("Antes de aplicar as regras:  " + papeis.size());
		System.out.println("Depois de aplicar as regras: " + papeisCantidatos.size());

		ordenarPorPL(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordenarPorPVP(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorROE(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorDividendoYIELD(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorLiquidezCorrente(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorMargemEBIT(papeisCantidatos);
		calcularRank(papeisCantidatos);

//		ordernarPorCresimento(papeisCantidatos);
//		calcularRank(papeisCantidatos);
//
//		ordernarPorLiquides2Meses(papeisCantidatos);
//		calcularRank(papeisCantidatos);

		ordenarPorRank(papeisCantidatos);
		organizarRank(papeisCantidatos);

		return papeisCantidatos;

	}

	private void organizarRank(List<Papel> papeisCantidatos) {
		for (int i = 0; i < papeisCantidatos.size(); i++) {
			papeisCantidatos.get(i).setRank(i+1);
		}

	}

	private void calcularRank(List<Papel> papeisCantidatos) {
		for (int i = 0; i < papeisCantidatos.size(); i++) {
			Papel papel = papeisCantidatos.get(i);
			papel.setRank(papel.getRank() + i);
		}

	}

	private void ordenarPorRank(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getRank().compareTo(p2.getRank());
			}

		});

	}

	/**
	 * QUANTO MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorLiquides2Meses(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getCotacoes().getLiquidez2Meses().compareTo(p1.getCotacoes().getLiquidez2Meses());
			}

		});

	}

	/**
	 * Regra maior que 5%, QUANTO MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorCresimento(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getCotacoes().getCrescimento().compareTo(p1.getCotacoes().getCrescimento());
			}

		});

	}

	/**
	 * Regra TERÁ QUE SER >0, QUANTO MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorMargemEBIT(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getCotacoes().getMargemEBIT().compareTo(p1.getCotacoes().getMargemEBIT());
			}

		});

	}

	/**
	 * Regra maior que 1. Quanto MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorLiquidezCorrente(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getCotacoes().getLiquidezCorrete().compareTo(p1.getCotacoes().getLiquidezCorrete());
			}

		});
	}

	/**
	 * Regra,maior que 0% e quanto MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorROE(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getCotacoes().getRoe().compareTo(p1.getCotacoes().getRoe());
			}

		});

	}

	/**
	 * Regra, maior que 0. Quanto MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorDividendoYIELD(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getCotacoes().getDividentoYIELD().compareTo(p1.getCotacoes().getDividentoYIELD());
			}

		});

	}

	/**
	 * O valor estará entre entre 0 e 20 e quanto MENOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordenarPorPVP(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getCotacoes().getP_vp().compareTo(p2.getCotacoes().getP_vp());
			}

		});
	}

	/**
	 * O valor estrará entre entre 1 e 30, e quanto MENOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordenarPorPL(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getCotacoes().getP_l().compareTo(p2.getCotacoes().getP_l());
			}

		});

	}

}
