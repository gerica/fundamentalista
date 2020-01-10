package fundamentalista.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import fundamentalista.entidade.Fundamento;
import fundamentalista.entidade.Papel;
import fundamentalista.entidade.SetorEnum;
import fundamentalista.service.HTMLParseService;

@Service("htmlParseService")
public class HTMLParseServiceImpl implements HTMLParseService {

	public Set<Papel> parse(SetorEnum setor) {
		if (SetorEnum.TODOS.equals(setor)) {
			return parse("http://www.fundamentus.com.br/resultado.php");
		} else {
			return parse("http://www.fundamentus.com.br/resultado.php?setor=" + setor.getId());
		}
	}

	private Set<Papel> parse(String URL) {
		Set<Papel> papeis = new HashSet<Papel>();
//		AutenticarProxy proxy = new AutenticarProxy();
//		proxy.autenticar();

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
				papel.setNome(cols.get(0).select("span").attr("title"));

				cotacao.setP_l(Double.parseDouble(cols.get(2).text().replace(".", "").replace(",", ".")));
				cotacao.setP_vp(Double.parseDouble(cols.get(3).text().replace(".", "").replace(",", ".")));
				cotacao.setP_sr(Double.parseDouble(cols.get(4).text().replace(".", "").replace(",", ".")));
				cotacao.setDividentoYIELD(
						Double.parseDouble(cols.get(5).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setMargemEBIT(
						Double.parseDouble(cols.get(12).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setLiquidezCorrete(Double.parseDouble(cols.get(14).text().replace(".", "").replace(",", ".")));
				cotacao.setRoic(
						Double.parseDouble(cols.get(15).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setRoe(
						Double.parseDouble(cols.get(16).text().replace(".", "").replace(",", ".").replace("%", "")));
				cotacao.setLiquidez2Meses(Double.parseDouble(cols.get(17).text().replace(".", "").replace(",", ".")));
				cotacao.setCrescimento(
						Double.parseDouble(cols.get(20).text().replace(".", "").replace(",", ".").replace("%", "")));

				papel.setFundamento(cotacao);
				papeis.add(papel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return papeis;
	}

	public static void main(String[] args) {
		HTMLParseServiceImpl a = new HTMLParseServiceImpl();
		Set<Papel> papeis = a.parse(SetorEnum.TODOS);
		System.out.println(papeis.size());
//		for (Papel papel : papeis) {
//			System.out.println(papel);
//		}

	}

}