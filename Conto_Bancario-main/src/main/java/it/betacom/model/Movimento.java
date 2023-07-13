package it.betacom.model;

import java.time.LocalDate;

public class Movimento {
	private Integer id, idConto;
	private String tipoMovimento;
	private double importo, saldoP, saldoS;
	private LocalDate dataM;
	public Movimento(Integer id, int idConto, String tipoMovimento, double importo, double saldoP, double saldoS,
			LocalDate dataM) {
		super();
		this.id = id;
		this.idConto = idConto;
		this.tipoMovimento = tipoMovimento;
		this.importo = importo;
		this.saldoP = saldoP;
		this.saldoS = saldoS;
		this.dataM = dataM;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getIdConto() {
		return idConto;
	}
	public void setIdConto(int idConto) {
		this.idConto = idConto;
	}
	public String getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public double getImporto() {
		return importo;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public double getSaldoP() {
		return saldoP;
	}
	public void setSaldoP(double saldoP) {
		this.saldoP = saldoP;
	}
	public double getSaldoS() {
		return saldoS;
	}
	public void setSaldoS(double saldoS) {
		this.saldoS = saldoS;
	}
	public LocalDate getDataM() {
		return dataM;
	}
	public void setDataM(LocalDate dataM) {
		this.dataM = dataM;
	}
}
