package it.betacom.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the animale database table.
 * 
 */
@Entity
@Table(name = "animali")
public class Animale {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idAnimale;

	@ManyToOne
	@JoinColumn(name = "IDCLIENTE")
	private Cliente cliente;

	private String tipoAnimale;
	private String nomeAnimale;
	private String matricola;
	private String dataAcquisto;
	private double prezzo;

	public Animale() {
	}

	public Animale(Long idAnimale, Cliente cliente, String tipoAnimale, String nomeAnimale, String matricola,
			String dataAcquisto, double prezzo) {
		super();
		this.idAnimale = idAnimale;
		this.cliente = cliente;
		this.tipoAnimale = tipoAnimale;
		this.nomeAnimale = nomeAnimale;
		this.matricola = matricola;
		this.dataAcquisto = dataAcquisto;
		this.prezzo = prezzo;
	}

	public Long getIdAnimale() {
		return idAnimale;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getTipoAnimale() {
		return tipoAnimale;
	}

	public String getNomeAnimale() {
		return nomeAnimale;
	}

	public String getMatricola() {
		return matricola;
	}

	public String getDataAcquisto() {
		return dataAcquisto;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setIdAnimale(Long idAnimale) {
		this.idAnimale = idAnimale;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setTipoAnimale(String tipoAnimale) {
		this.tipoAnimale = tipoAnimale;
	}

	public void setNomeAnimale(String nomeAnimale) {
		this.nomeAnimale = nomeAnimale;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public void setDataAcquisto(String dataAcquisto2) {
		this.dataAcquisto = dataAcquisto2;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

}