package fundamentalista.entidade;

import java.io.Serializable;

public class Papel implements Serializable, Comparable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String papel;
	private Cotacao cotacoes;
	private Integer rank = new Integer(1);

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	@Override
	public String toString() {
		return "Papel [nome=" + nome + ", papel=" + papel + ", cotacoes=" + cotacoes + "]";
	}

	public Cotacao getCotacoes() {
		return cotacoes;
	}

	public void setCotacoes(Cotacao cotacoes) {
		this.cotacoes = cotacoes;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
