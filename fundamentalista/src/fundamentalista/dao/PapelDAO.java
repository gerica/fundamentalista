package fundamentalista.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import fundamentalista.entidade.Papel;

public class PapelDAO {

	private static final String BASE = "papel.ser";

	public void gravar(List<Papel> papel) {
		try {

			FileOutputStream fs = new FileOutputStream(BASE);
			// fs encadeado ao fluxo de conexão
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(papel);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Papel> findAll() {
		verificarDataCreiacao();
		FileInputStream inFile;
		try {
			File file = new File(BASE);
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

	private void verificarDataCreiacao() {
		File file = new File(BASE);
		file.delete();
		// Date hoje = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		// Date modificacao = new Date(sdf.format(file.lastModified()));
		// System.out.println(modificacao.before(hoje));
		// System.out.println(modificacao);
		// System.out.println(hoje);

		// if (file.lastModified()) {
		//
		// }

	}

}
