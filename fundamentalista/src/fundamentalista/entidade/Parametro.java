package fundamentalista.entidade;

public class Parametro {

	private String descricao;
	private boolean ativo;

	public Parametro(String descricao, boolean ativo) {
		super();
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public Parametro() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
