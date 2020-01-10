package fundamentalista.entidade;

import java.io.Serializable;

public class Fundamento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double p_l; // entre 1 e 30
	private Double p_vp; // entre 0 e 20
	private Double p_sr; // entre 0 e 50
	private Double dividentoYIELD; // maior que 0
	private Double margemEBIT; // TERÁ QUE SER >0
	private Double liquidezCorrete; // maior que 1
	private Double roic; // maior que 0%
	private Double roe; // maior que 0%
	private Double liquidez2Meses; // maior que 100.000
	private Double crescimento; // maior que 5%

	@Override
	public String toString() {
		return "Cotacao [p_l=" + p_l + ", p_vp=" + p_vp + ", roe=" + roe + ", margemEBIT=" + margemEBIT
				+ ", liquidezCorrete=" + liquidezCorrete + ", crescimento=" + crescimento + ", dividentoYIELD="
				+ dividentoYIELD + ", liquidez2Meses=" + liquidez2Meses + "]";
	}

	public Double getP_l() {
		return p_l;
	}

	public void setP_l(Double p_l) {
		this.p_l = p_l;
	}

	public Double getP_vp() {
		return p_vp;
	}

	public void setP_vp(Double p_vp) {
		this.p_vp = p_vp;
	}

	public Double getDividentoYIELD() {
		return dividentoYIELD;
	}

	public void setDividentoYIELD(Double dividentoYIELD) {
		this.dividentoYIELD = dividentoYIELD;
	}

	public Double getMargemEBIT() {
		return margemEBIT;
	}

	public void setMargemEBIT(Double margemEBIT) {
		this.margemEBIT = margemEBIT;
	}

	public Double getLiquidezCorrete() {
		return liquidezCorrete;
	}

	public void setLiquidezCorrete(Double liquidezCorrete) {
		this.liquidezCorrete = liquidezCorrete;
	}

	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getLiquidez2Meses() {
		return liquidez2Meses;
	}

	public void setLiquidez2Meses(Double liquidez2Meses) {
		this.liquidez2Meses = liquidez2Meses;
	}

	public Double getCrescimento() {
		return crescimento;
	}

	public void setCrescimento(Double crescimento) {
		this.crescimento = crescimento;
	}

	public Double getP_sr() {
		return p_sr;
	}

	public void setP_sr(Double p_sr) {
		this.p_sr = p_sr;
	}

	public Double getRoic() {
		return roic;
	}

	public void setRoic(Double roic) {
		this.roic = roic;
	}

}
