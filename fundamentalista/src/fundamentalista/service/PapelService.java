package fundamentalista.service;

import java.util.List;

import fundamentalista.FundamentoBusinessException;
import fundamentalista.entidade.Papel;

public interface PapelService {

	public List<Papel> analizarPapeis(List<Papel> papeis) throws FundamentoBusinessException;

	public List<Papel> findBySetor(Integer setor) throws FundamentoBusinessException;

	public List<Papel> findAll() throws FundamentoBusinessException;

}
