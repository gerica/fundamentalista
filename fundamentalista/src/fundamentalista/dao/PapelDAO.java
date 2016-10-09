package fundamentalista.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import fundamentalista.entidade.Papel;

public class PapelDAO {

	private static final String BASE = "papel.ser";
	private static final String SETOR_ENERGETICO = "energetico_";
	private static final String SETOR_FINANCEIRO = "financeiro_";
	private static final String SETOR_VESTUARIO = "vestuario_";

	public void gravarAll(List<Papel> papel) {
		gravar(BASE, papel);
	}

	public void gravarEnergetico(List<Papel> papel) {
		gravar(SETOR_ENERGETICO + BASE, papel);
	}

	public void gravarFinaneiro(List<Papel> papel) {
		gravar(SETOR_FINANCEIRO + BASE, papel);
	}

	public void gravarVestuario(List<Papel> papel) {
		gravar(SETOR_VESTUARIO + BASE, papel);
	}

	public List<Papel> findAll() {
		return find(BASE);
	}

	public List<Papel> findEnergetico() {
		return find(SETOR_ENERGETICO + BASE);
	}

	public List<Papel> findFinanceiro() {
		return find(SETOR_FINANCEIRO + BASE);
	}

	public List<Papel> findVestuario() {
		return find(SETOR_VESTUARIO + BASE);
	}

	private List<Papel> find(String nomeFile) {
		verificarDataCreiacao(nomeFile);
		FileInputStream inFile;
		try {
			File file = new File(nomeFile);
			if (file.exists()) {

				inFile = new FileInputStream(file);

				ObjectInputStream d = new ObjectInputStream(inFile);
				Object o = d.readObject();
				d.close();
				return (List<Papel>) o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void verificarDataCreiacao(String nomeFile) {
		File file = new File(nomeFile);
		long diff = new Date().getTime() - file.lastModified();

		if (diff > 24 * 60 * 60 * 1000) {
			file.delete();
		}

	}

	private void gravar(String nomeFile, List<Papel> papel) {
		try {

			FileOutputStream fs = new FileOutputStream(nomeFile);
			// fs encadeado ao fluxo de conexão
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(papel);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
