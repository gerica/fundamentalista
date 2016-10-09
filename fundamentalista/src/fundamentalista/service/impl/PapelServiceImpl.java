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
import fundamentalista.entidade.Papel;
import fundamentalista.entidade.SetorEnum;
import fundamentalista.repository.PapelRepository;
import fundamentalista.service.HTMLParseService;
import fundamentalista.service.PapelService;

@Service("papelService")
public class PapelServiceImpl implements PapelService {
	private static final Logger logger = LoggerFactory.getLogger(PapelServiceImpl.class);

	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private HTMLParseService htmlParseService;

	public List<Papel> analizarPapeis(List<Papel> papeis) throws FundamentoBusinessException {
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

		// ordernarPorCresimento(papeisCantidatos);
		// calcularRank(papeisCantidatos);
		//
		// ordernarPorLiquides2Meses(papeisCantidatos);
		// calcularRank(papeisCantidatos);

		ordenarPorRank(papeisCantidatos);
		organizarRank(papeisCantidatos);

		return papeisCantidatos;

	}

	@Override
	public List<Papel> findBySetor(SetorEnum setor) throws FundamentoBusinessException {
		logger.info("PapelServiceImpl.findBySetor() " + setor.getDesc());
		List<Papel> papeis = null;
		if (SetorEnum.TODOS.equals(setor)) {
			papeis = (List<Papel>) papelRepository.findAll();
		} else {
			papeis = (List<Papel>) papelRepository.findBySetor(setor.getId());
		}

		if (papeis == null || papeis.isEmpty()) {
			papeis = createPapeis(setor);
		}
		return papeis;

	}

	private List<Papel> createPapeis(SetorEnum setor) {
		logger.info("PapelServiceImpl.createPapeis() " + setor.getDesc());

		Set<Papel> papeis = htmlParseService.parse(setor);

		if (!SetorEnum.TODOS.equals(setor)) {
			for (Papel papel : papeis) {
				Papel tempPapel = null;
				tempPapel = papelRepository.findByNomeAndPapel(papel.getNome(), papel.getPapel());
				if (tempPapel != null) {
					papel.setId(tempPapel.getId());
					papel.setSetor(setor.getId());
				}
			}
		}
		papelRepository.save(papeis);

		return new ArrayList<Papel>(papeis);

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
