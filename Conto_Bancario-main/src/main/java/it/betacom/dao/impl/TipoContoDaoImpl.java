package it.betacom.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.betacom.dao.TipoContoDao;
import it.betacom.model.TipoConto;

public class TipoContoDaoImpl implements TipoContoDao {

	private static TipoContoDaoImpl instance = null;
	private TipoContoDaoImpl() {}
	public static TipoContoDaoImpl getInstance() {
		if (instance == null ) 
			instance = new TipoContoDaoImpl();
		return instance;
	}
	Statement stm = null;
	@Override
	public void setConnessione(Statement stm) {
		this.stm = stm;
	}
	@Override
	public TipoConto getTipoContoById(int id) {
		TipoConto tipoConto = null;
		try {
			ResultSet r = stm.executeQuery("select * from tipo_conti where id = "+id);
			if (r.next()) 
				tipoConto =	new TipoConto(
						r.getInt("id"), 
						r.getInt("tasso"),
						r.getString("tipo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return tipoConto;
	}
	@Override
	public void updateTipoConto(TipoConto tipoConto) {
		try {
			int res = stm.executeUpdate("UPDATE tipo_conti SET tasso = '"+tipoConto.getTasso()+"' WHERE (id = '"+tipoConto.getId()+"')");
			if (res < 0) 
				System.out.println("Modifica non apportata");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
