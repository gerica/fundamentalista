package fundamentalista.entidade;

public class Parametro {

	private String descricao;
	private boolean ativo;
	private boolean aplicarFiltro;

	private Integer min;

	private Integer max;

	public Parametro() {
		super();
	}

	public Parametro(String descricao, boolean ativo) {
		super();
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public Parametro(String descricao, boolean ativo, boolean aplicarFiltro, int min) {
		super();
		this.descricao = descricao;
		this.ativo = ativo;
		this.aplicarFiltro = aplicarFiltro;
		this.min = min;
	}

	public Parametro(String descricao, boolean ativo, boolean aplicarFiltro, int min, int max) {
		super();
		this.descricao = descricao;
		this.ativo = ativo;
		this.aplicarFiltro = aplicarFiltro;
		this.min = min;
		this.max = max;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getMax() {
		return max;
	}

	public Integer getMin() {
		return min;
	}

	public boolean isAplicarFiltro() {
		return aplicarFiltro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAplicarFiltro(boolean aplicarFiltro) {
		this.aplicarFiltro = aplicarFiltro;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

}
