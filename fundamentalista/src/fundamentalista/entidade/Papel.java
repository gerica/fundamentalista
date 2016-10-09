package fundamentalista.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "papel")
public class Papel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "papel")
	private String papel;

	// @OneToMany(fetch = FetchType.EAGER, mappedBy = "papel", cascade = {
	// javax.persistence.CascadeType.ALL })
	// @OrderBy("data ASC")
	// private Set<Cotacoes> cotacoes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fundamento", nullable = false)
	private Fundamento fundamento;

	@Column(name = "setor")
	private Integer setor;

	@Transient
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
		return "Papel [id=" + id + ", nome=" + nome + ", papel=" + papel + ", fundamento=" + fundamento + ", rank=" + rank + "]";
	}

	public Integer getSetor() {
		return setor;
	}

	public void setSetor(Integer setor) {
		this.setor = setor;
	}

}
