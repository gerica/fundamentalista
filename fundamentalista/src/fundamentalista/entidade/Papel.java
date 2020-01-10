package fundamentalista.entidade;

import java.io.Serializable;

public class Papel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;

	private String nome;

	private String papel;
	private Fundamento fundamento;

	private Integer setor;

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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fundamento getFundamento() {
		return fundamento;
	}

	public void setFundamento(Fundamento fundamento) {
		this.fundamento = fundamento;
	}

	@Override
	public String toString() {
		return "Papel [id=" + id + ", nome=" + nome + ", papel=" + papel + ", fundamento=" + fundamento + ", rank="
				+ rank + "]";
	}

	public Integer getSetor() {
		return setor;
	}

	public void setSetor(Integer setor) {
		this.setor = setor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((papel == null) ? 0 : papel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Papel other = (Papel) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (papel == null) {
			if (other.papel != null)
				return false;
		} else if (!papel.equals(other.papel))
			return false;
		return true;
	}

}
