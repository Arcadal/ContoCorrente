package it.betacom.model;

public class TipoConto {

	private int id;
	private double tasso;
	private String tipo;
	public TipoConto(int id, double tasso, String tipo) {
		super();
		this.id = id;
		this.tasso = tasso;
		this.tipo = tipo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getTasso() {
		return tasso;
	}
	public void setTasso(double tasso) {
		this.tasso = tasso;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
