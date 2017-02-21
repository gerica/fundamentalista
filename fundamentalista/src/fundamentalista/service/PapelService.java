package fundamentalista.service;

import java.util.List;

import fundamentalista.FundamentoBusinessException;
import fundamentalista.entidade.Papel;
import fundamentalista.entidade.SetorEnum;

public interface PapelService {

	public List<Papel> analizarPapeis(List<Papel> papeis) throws FundamentoBusinessException;

	public List<Papel> findBySetor(SetorEnum setor) throws FundamentoBusinessException;

	public List<Papel> createPapeis(SetorEnum setor);

	// public List<Papel> findAll() throws FundamentoBusinessException;

}
