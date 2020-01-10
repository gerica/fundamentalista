package fundamentalista.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fundamentalista.FundamentoBusinessException;
import fundamentalista.dao.PapelDAO;
import fundamentalista.entidade.Papel;
import fundamentalista.entidade.Parametro;
import fundamentalista.entidade.SetorEnum;
import fundamentalista.service.HTMLParseService;
import fundamentalista.service.PapelService;

@Service("papelService")
public class PapelServiceImpl implements PapelService {
	private static final Logger logger = LoggerFactory.getLogger(PapelServiceImpl.class);

	@Autowired
	private HTMLParseService htmlParseService;

	@Autowired
	private PapelDAO papelDao;

	public List<Papel> analizarPapeis(List<Papel> papeis, List<Parametro> parametros)
			throws FundamentoBusinessException {
		logger.info("PapelServiceImpl.analizarPapeis()");
		List<Papel> papeisCantidatos = new ArrayList<Papel>();

		for (Papel papel : papeis) {
			// System.out.println(papel);
			if (papel.getFundamento().getP_l() < 1 || papel.getFundamento().getP_l() > 30) {
				continue;
			}
			if (papel.getFundamento().getP_vp() < 0 || papel.getFundamento().getP_vp() > 20) {
				continue;
			}
			if (papel.getFundamento().getP_sr() < 0 || papel.getFundamento().getP_sr() > 50) {
				continue;
			}
			if (papel.getFundamento().getRoic() < 0) {
				continue;
			}
			if (papel.getFundamento().getRoe() < 0) {
				continue;
			}
			if (papel.getFundamento().getDividentoYIELD() <= 0) {
				continue;
			}
			if (papel.getFundamento().getLiquidezCorrete() < 1) {
				continue;
			}
			if (papel.getFundamento().getMargemEBIT() < 0) {
				continue;
			}
			if (papel.getFundamento().getCrescimento() < 5) {
				continue;
			}
			if (papel.getFundamento().getLiquidez2Meses() < 100000) {
				continue;
			}
			papeisCantidatos.add(papel);

		}
		System.out.println("Antes de aplicar as regras:  " + papeis.size());
		System.out.println("Depois de aplicar as regras: " + papeisCantidatos.size());

		ordenar(parametros, papeisCantidatos);

		return papeisCantidatos;

	}

	@Override
	public List<Papel> findBySetor(SetorEnum setor) throws FundamentoBusinessException {
		logger.info("PapelServiceImpl.findBySetor() " + setor.getDesc());
		List<Papel> papeis = null;
//		if (SetorEnum.TODOS.equals(setor)) {
//			papeis = (List<Papel>) papelRepository.findAll();
//		} else {
//			papeis = (List<Papel>) papelRepository.findBySetor(setor.getId());
//		}

		if (papeis == null || papeis.isEmpty()) {
			papeis = createPapeis(setor);
		}
		return papeis;

	}

	private void ordenar(List<Parametro> parametros, List<Papel> papeisCantidatos) {

		for (Parametro parametro : parametros) {
			if (parametro.isAtivo()) {
				switch (parametro.getDescricao()) {
				case "P/L":
					ordenarPorPL(papeisCantidatos);
					break;
				case "P/VP":
					ordenarPorPVP(papeisCantidatos);
					break;
				case "PSR":
					ordenarPorPSR(papeisCantidatos);
					break;
				case "ROIC":
					ordernarPorROIC(papeisCantidatos);
					break;
				case "ROE":
					ordernarPorROE(papeisCantidatos);
					break;
				case "DIV.YIELD":
					ordernarPorDividendoYIELD(papeisCantidatos);
					break;
				case "LIQ. CORR.":
					ordernarPorLiquidezCorrente(papeisCantidatos);
					break;
				case "MRG EBIT":
					ordernarPorMargemEBIT(papeisCantidatos);
					break;
				case "CRESC.":
					ordernarPorCresimento(papeisCantidatos);
					break;
				case "LIQ. 2MESES":
					ordernarPorLiquides2Meses(papeisCantidatos);
					break;

				default:
					break;
				}
				calcularRank(papeisCantidatos);
			}
		}

		ordenarPorRank(papeisCantidatos);
		organizarRank(papeisCantidatos);
	}

	private List<Papel> createPapeis(SetorEnum setor) {
		logger.info("PapelServiceImpl.createPapeis() " + setor.getDesc());

		if (SetorEnum.TODOS.equals(setor)) {
			List<Papel> allPapel = papelDao.findAll();

			if (allPapel == null || allPapel.isEmpty()) {
				Set<Papel> papeis = htmlParseService.parse(setor);
				allPapel = new ArrayList<Papel>(papeis);
				papelDao.gravarAll(allPapel);

			}
			return allPapel;
		} else if (SetorEnum.ENERGETICO.equals(setor)) {
			List<Papel> allPapel = papelDao.findEnergetico();

			if (allPapel == null || allPapel.isEmpty()) {
				Set<Papel> papeis = htmlParseService.parse(setor);
				allPapel = new ArrayList<Papel>(papeis);
				papelDao.gravarEnergetico(allPapel);

			}
			return allPapel;
		}

		return new ArrayList<>();

	}

	private void organizarRank(List<Papel> papeisCantidatos) {
		for (int i = 0; i < papeisCantidatos.size(); i++) {
			papeisCantidatos.get(i).setRank(i + 1);
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
				return p2.getFundamento().getLiquidez2Meses().compareTo(p1.getFundamento().getLiquidez2Meses());
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
				return p2.getFundamento().getCrescimento().compareTo(p1.getFundamento().getCrescimento());
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
				return p2.getFundamento().getMargemEBIT().compareTo(p1.getFundamento().getMargemEBIT());
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
				return p2.getFundamento().getLiquidezCorrete().compareTo(p1.getFundamento().getLiquidezCorrete());
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
				return p2.getFundamento().getRoe().compareTo(p1.getFundamento().getRoe());
			}
		});
	}

	/**
	 * Regra,maior que 0% e quanto MAIOR MELHOR
	 * 
	 * ROIC: Retorno sobre o capital investido -> Informe
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorROIC(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getRoic().compareTo(p1.getFundamento().getRoic());
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
				return p2.getFundamento().getDividentoYIELD().compareTo(p1.getFundamento().getDividentoYIELD());
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
				return p1.getFundamento().getP_vp().compareTo(p2.getFundamento().getP_vp());
			}

		});
	}

	/**
	 * O valor estará entre entre 0 e 50 e quanto MENOR MELHOR
	 * 
	 * Preço da ação dividido pela receita liquida
	 * 
	 * @param papeisCantidatos
	 */
	private void ordenarPorPSR(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getFundamento().getP_sr().compareTo(p2.getFundamento().getP_sr());
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
				return p1.getFundamento().getP_l().compareTo(p2.getFundamento().getP_l());
			}

		});

	}

}
