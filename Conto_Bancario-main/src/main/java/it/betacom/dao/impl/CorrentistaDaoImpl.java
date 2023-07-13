package it.betacom.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.betacom.dao.CorrentistaDao;
import it.betacom.model.Correntista;

public class CorrentistaDaoImpl implements CorrentistaDao {

	private static CorrentistaDaoImpl instance = null;
	private CorrentistaDaoImpl() {}
	public static CorrentistaDaoImpl getInstance() {
		if (instance == null ) 
			instance = new CorrentistaDaoImpl();
		return instance;
	}
	
	List<Correntista> correntisti = new ArrayList<>();
	Statement stm = null;
	
	@Override
	public void setConnessione(Statement stm) {
		this.stm = stm;
	}
	@Override
	public List<Correntista> getCorrentisti() {
		try {
			ResultSet r = stm.executeQuery("select * from correntisti");
			while (r.next()) {
				correntisti.add(new Correntista(
					r.getInt("id"), 
					r.getString("nome"), 
					r.getString("cognome"),
					r.getString("citta"),
					r.getString("nazione"),
					r.getString("telefono")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return correntisti;
	}

	@Override
	public Correntista getCorrentistaById(int id) {
		Correntista correntista = null;
		try {
			ResultSet r = stm.executeQuery("select * from correntisti where id = "+id);
			if (r.next()) {
				correntista = new Correntista(
						r.getInt("id"), 
						r.getString("nome"), 
						r.getString("cognome"),
						r.getString("citta"),
						r.getString("nazione"),
						r.getString("telefono"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(correntista.getNome());
		return correntista;
	}
	
	@Override
	public Correntista getCorrentistaByName(String nome, String cognome) {
		Correntista correntista = null;
		try {
			ResultSet r = stm.executeQuery("select * from correntisti where nome = '"+nome+"' and cognome = '"+cognome+"'");
			if (r.next()) {
				correntista = new Correntista(
						r.getInt("id"), 
						r.getString("nome"), 
						r.getString("cognome"),
						r.getString("citta"),
						r.getString("nazione"),
						r.getString("telefono"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return correntista;
		
	}

	@Override
	public void insertCorrentista(Correntista correntista) {
		try {
			int r = stm.executeUpdate("insert into correntisti "
					+ "(nome,cognome,citta,nazione,telefono) values "
					+ "('"+correntista.getNome()+"','"+correntista.getCognome()
					+ "','"+correntista.getCitta()+"','"+correntista.getNazione()
					+"','"+correntista.getTelefono()+"')");
			if (r > 0) 
				System.out.println("Correntista registrato");
			else
				System.out.println("Correntista non registrato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCorrentista(Correntista correntista) {
		try {
			int r = stm.executeUpdate("delete from correntisti where id = "+correntista.getId()+")");
			if (r > 0) 
				System.out.println("Record eliminato");
			else
				System.out.println("Record non eliminato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCorrentista(Correntista correntista) {
		// TODO Auto-generated method stub

	}
}
