package it.betacom.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.betacom.dao.MovimentoDao;
import it.betacom.model.Conto;
import it.betacom.model.Movimento;

public class MovimentoDaoImpl implements MovimentoDao {

	private static MovimentoDaoImpl instance = null;
	private MovimentoDaoImpl() {}
	public static MovimentoDaoImpl getInstance() {
		if (instance == null ) 
			instance = new MovimentoDaoImpl();
		return instance;
	}
	
	List<Movimento> movimenti;
	
	Statement stm = null;
	@Override
	public void setConnessione(Statement stm) {
		this.stm = stm;
	}
	@Override
	public List<Movimento> getMovimentiByConto(Conto conto) {
		movimenti = new ArrayList<>();
		try {
			ResultSet r = stm.executeQuery("select * from movimenti where id_conto="+conto.getId());
			while (r.next()) {
				Movimento movimento = new Movimento(
					r.getInt("id"),
					r.getInt("id_conto"),
					r.getString("tipo_movimento"),
					r.getDouble("importo"),
					r.getDouble("saldo_precedente"),
					r.getDouble("saldo_successivo"),
					r.getDate("data_movimento").toLocalDate());
				movimenti.add(movimento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movimenti;
	}

	@Override
	public void insertMovimento(Movimento movimento) {
		try {
			int r = stm.executeUpdate("insert into movimenti (id_conto,saldo_precedente,saldo_successivo,data_movimento,tipo_movimento,importo) values "
					+ "("+movimento.getIdConto()+","+movimento.getSaldoP()+","+movimento.getSaldoS()+
					",'"+movimento.getDataM()+"','"+movimento.getTipoMovimento()+ "',"+movimento.getImporto()+")");
			if (r > 0) 
				System.out.println("Record inserito");
			else
				System.out.println("Record non inserito");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMovimento(Movimento movimento) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMovimento(Movimento movimento) {
		// TODO Auto-generated method stub

	}

}
