package it.betacom.dao;
import java.sql.Statement;
import java.util.List;
import it.betacom.model.Conto;

public interface ContoDao {

	void setConnessione(Statement stm);
	List<Conto> getConti();
	Conto getContoById(int id);
	Conto getContoByCorrentista(String nome, String cognome);
	void insertConto(Conto conto);
	void deleteConto(Conto conto);
	void updateContoMovimento(Conto conto); 
}
