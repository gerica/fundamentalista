package fundamentalista.service;

import java.io.FileNotFoundException;
import java.util.List;

import fundamentalista.entidade.Papel;

public interface FireBaseService {

	void savePapeis(List<Papel> papeis) throws FileNotFoundException;

	public List<Papel> recuperarPapeis() throws FileNotFoundException;

}
