package it.betacom.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name = "Clienti")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idCliente;

	private String nome;
	private String cognome;
	private String citta;
	private String telefono;
	private String indirizzo;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Animale> animali;

	public Cliente() {
	}

	public Cliente(Long idCliente, String nome, String cognome, String citta, String telefono, String indirizzo,
			List<Animale> animali) {
		super();
		this.idCliente = idCliente;
		this.nome = nome;
		this.cognome = cognome;
		this.citta = citta;
		this.telefono = telefono;
		this.indirizzo = indirizzo;
		this.animali = animali;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCitta() {
		return citta;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public List<Animale> getAnimali() {
		return animali;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public void setTelefono(String telefonoCliente) {
		this.telefono = telefonoCliente;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void setAnimali(List<Animale> animali) {
		this.animali = animali;
	}

	// Metodi di utilità per gestire la reazione con Animale

	public void addAnimale(Animale animale) {
		animali.add(animale);
	}

	public void removeAnimale(Animale animale) {
		animali.remove(animale);
	}

}