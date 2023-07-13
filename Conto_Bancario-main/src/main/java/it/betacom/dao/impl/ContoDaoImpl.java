package it.betacom.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.betacom.dao.ContoDao;
import it.betacom.dao.CorrentistaDao;
import it.betacom.dao.MovimentoDao;
import it.betacom.dao.TipoContoDao;
import it.betacom.model.Conto;
import it.betacom.model.ContoCorrente;
import it.betacom.model.ContoDeposito;
import it.betacom.model.ContoInvestimento;
import it.betacom.model.Correntista;
import it.betacom.model.TipoConto;

public class ContoDaoImpl implements ContoDao {

	private static ContoDaoImpl instance = null;
	private ContoDaoImpl() {}
	public static ContoDaoImpl getInstance() {
		if (instance == null ) 
			instance = new ContoDaoImpl();
		return instance;
	}
	
	List<Conto> conti = new ArrayList<>();
	MovimentoDao movimentoDao = MovimentoDaoImpl.getInstance();
	CorrentistaDao correntistaDao = CorrentistaDaoImpl.getInstance();
	TipoContoDao tipoContoDao = TipoContoDaoImpl.getInstance();

	Statement stm = null;
	@Override
	public void setConnessione(Statement stm) {
		this.stm = stm;
	}
	@Override
	public List<Conto> getConti() {
		try {
			ResultSet r = stm.executeQuery("select * from conti");
			while (r.next()) {
				int id = r.getInt("id");
				int t =r.getInt("id_tipo");
				int c = r.getInt("id_correntista");
				LocalDate dataA = r.getDate("data_apertura").toLocalDate();
				double saldo = r.getDouble("saldo");
				LocalDate dataC;
				try {dataC = r.getDate("data_chiusura").toLocalDate();} 
				catch(NullPointerException e) {dataC = null;}
				TipoConto tipoConto = tipoContoDao.getTipoContoById(t);
				Correntista correntista = correntistaDao.getCorrentistaById(c);
				Conto conto = null;
				if (t == 1)
					conto = new ContoCorrente(id, tipoConto, correntista, dataA, saldo, dataC);
				else if (t == 2)
					conto = new ContoDeposito(id, tipoConto, correntista, dataA, saldo, dataC);
				else if (t == 3)
					conto = new ContoInvestimento(id, tipoConto, correntista, dataA, saldo, dataC);
				conto.setListaMovimenti(movimentoDao.getMovimentiByConto(conto));
				conti.add(conto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conti;
	}

	@Override
	public Conto getContoByCorrentista(String nome, String cognome) {
		Conto conto = null;
		try {
			Correntista correntista = correntistaDao.getCorrentistaByName(nome, cognome);
			
			ResultSet r = stm.executeQuery("select * from conti where id_correntista = "+correntista.getId());
			if (r.next()) {
				int id = r.getInt("id");
				int t =r.getInt("id_tipo");
				LocalDate dataA = r.getDate("data_apertura").toLocalDate();
				double saldo = r.getDouble("saldo");
				LocalDate dataC;
				try {dataC = r.getDate("data_chiusura").toLocalDate();} 
				catch(NullPointerException e) {dataC = null;}
				TipoConto tipoConto = tipoContoDao.getTipoContoById(t);
				
				if (t == 1)
					conto = new ContoCorrente(id, tipoConto, correntista, dataA, saldo, dataC);
				else if (t == 2)
					conto = new ContoDeposito(id, tipoConto, correntista, dataA, saldo, dataC);
				else if (t == 3)
					conto = new ContoInvestimento(id, tipoConto, correntista, dataA, saldo, dataC);
				conto.setListaMovimenti(movimentoDao.getMovimentiByConto(conto));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conto;
	}
	@Override
	public Conto getContoById(int id) {
		Conto conto = null;
		try {
			ResultSet r = stm.executeQuery("select * from conti where id = "+id);
			if (r.next()) {
				int t = r.getInt("id_tipo");
				int c = r.getInt("id_correntista");
				LocalDate dataA = r.getDate("data_apertura").toLocalDate();
				double saldo = r.getDouble("saldo");
				LocalDate dataC;
				try {dataC = r.getDate("data_chiusura").toLocalDate();} 
				catch(NullPointerException e) {dataC = null;}
				TipoConto tipoConto = tipoContoDao.getTipoContoById(t);
				Correntista correntista = correntistaDao.getCorrentistaById(c);
				
				if (t == 1)
					conto = new ContoCorrente(id, tipoConto, correntista, dataA, saldo, dataC);
				else if (t == 2)
					conto = new ContoDeposito(id, tipoConto, correntista, dataA, saldo, dataC);
				else if (t == 3)
					conto = new ContoInvestimento(id, tipoConto, correntista, dataA, saldo, dataC);
				conto.setListaMovimenti(movimentoDao.getMovimentiByConto(conto));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conto;
	}

	@Override
	public void insertConto(Conto conto) {
		correntistaDao.insertCorrentista(conto.getCorrentista());
		try {
			int r = stm.executeUpdate("insert into conti "
					+ "(id_tipo,data_apertura,saldo,id_correntista) values "
					+ "("+conto.getTipoConto().getId()+ ",'"+conto.getDataApertura()+"',"+conto.getSaldo()
					+","+correntistaDao.getCorrentistaByName(conto.getCorrentista().getNome(), conto.getCorrentista().getCognome()).getId()+")");
			if (r > 0) 
				System.out.println("Conto registrato");
			else
				System.out.println("Conto non registrato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteConto(Conto conto) {
		try {
			int r = stm.executeUpdate("delete from conti where id = "+conto.getId()+")");
			if (r > 0) 
				System.out.println("Record eliminato");
			else
				System.out.println("Record non eliminato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateContoMovimento(Conto conto) {
		try {
			int r = stm.executeUpdate("update conti set saldo = " +conto.getSaldo() +" "
					+ "where id = " +conto.getId());
			if (r > 0)
				movimentoDao.insertMovimento(conto.getListaMovimenti().get(conto.getListaMovimenti().size()-1));
			else 
				System.out.println("Errore movimento non registrato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
