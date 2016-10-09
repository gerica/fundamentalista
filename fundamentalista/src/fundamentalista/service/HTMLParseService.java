package fundamentalista.service;

import java.util.Set;

import fundamentalista.entidade.Papel;
import fundamentalista.entidade.SetorEnum;

public interface HTMLParseService {

	public Set<Papel> parse(SetorEnum setor);

}
