package fundamentalista;

import java.util.List;

import fundamentalista.dao.PapelDAO;
import fundamentalista.entidade.Papel;
import fundamentalista.service.PapelService;
import fundamentalista.util.HTMLParser;
import fundamentalista.view.PapelView;

public class Executar {

	public static void main(String[] args) {
		PapelView view = new PapelView();
		PapelDAO dao = new PapelDAO();
		PapelService service = new PapelService();
		List<Papel> papeis = dao.findAll();
		if (papeis == null) {
			HTMLParser parse = new HTMLParser();
			papeis = parse.parse();
			dao.gravar(papeis);
		}
		view.preparar(service.analizarPapeis(papeis));
		view.display();

	}

}
