package fundamentalista.service;

import java.util.List;

import fundamentalista.FundamentoBusinessException;
import fundamentalista.entidade.Papel;
import fundamentalista.entidade.Parametro;
import fundamentalista.entidade.SetorEnum;

public interface PapelService {

	public List<Papel> analizarPapeis(List<Papel> papeis, List<Parametro> parametros) throws FundamentoBusinessException;

	public List<Papel> findBySetor(SetorEnum setor) throws FundamentoBusinessException;

//	public List<Papel> findAll() throws FundamentoBusinessException;

}
