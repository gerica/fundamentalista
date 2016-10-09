package fundamentalista.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fundamentalista.entidade.Fundamento;
import fundamentalista.entidade.Papel;

/**
 * Java Program to parse/read HTML documents from File using Jsoup library.
 * Jsoup is an open source library which allows Java developer to parse HTML
 * files and extract elements, manipulate data, change style using DOM, CSS and
 * JQuery like method.
 *
 * @author Javin Paul
 */
public class HTMLParser {

	public List<Papel> parteAll() {
		return parse("http://www.fundamentus.com.br/resultado.php");
	}

	public List<Papel> parteSetorEnergetico() {
		return parse("http://www.fundamentus.com.br/resultado.php?setor=32");
	}

	public List<Papel> parteSetorFinanceiro() {
		return parse("http://www.fundamentus.com.br/resultado.php?setor=35");
	}
	
	public List<Papel> parteSetorVestuario() {
		return parse("http://www.fundamentus.com.br/resultado.php?setor=21");
	}

	private List<Papel> parse(String URL) {
		List<Papel> papeis = new ArrayList<Papel>();
		AutenticarProxy proxy = new AutenticarProxy();
		proxy.autenticar();

		Document doc;
		Papel papel = null;
		Fundamento cotacao = null;
		try {

			doc = Jsoup.connect(URL).get(); // TODOS

			Element table = doc.select("table").get(0); // select the first
														// table.
			Elements rows = table.select("tr");

			for (int i = 1; i < rows.size(); i++) { // first row is the col
													// names so skip it.
				Element row = rows.get(i);
				Elements cols = row.select("td");

				papel = new Papel();
				cotacao = new Fundamento();

				papel.setPapel(cols.get(0).text());

				cotacao.setP_l(Double.parseDouble(cols.get(2).text().replace(".", "").replace(",", ".")));
				cotacao.setP_vp(Double.parseDouble(cols.get(3).text().replace(".", "").replace(",", ".")));
				cotacao.setDividentoYIELD(Double.parseDouble(cols.get(5).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setMargemEBIT(Double.parseDouble(cols.get(11).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setLiquidezCorrete(Double.parseDouble(cols.get(13).text().replace(".", "").replace(",", ".")));
				cotacao.setRoe(Double.parseDouble(cols.get(15).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setLiquidez2Meses(Double.parseDouble(cols.get(16).text().replace(".", "").replace(",", ".")));
				cotacao.setCrescimento(Double.parseDouble(cols.get(19).text().replace(".", "").replace(",", ".").replace("%", "")));

				papel.setFundamento(cotacao);
				papeis.add(papel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return papeis;
	}

}