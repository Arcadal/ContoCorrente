package it.betacom.dao;

import java.sql.Statement;
import java.util.List;

import it.betacom.model.Conto;
import it.betacom.model.Movimento;

public interface MovimentoDao {

	void setConnessione(Statement stm);
	List<Movimento> getMovimentiByConto(Conto conto);
	void insertMovimento(Movimento movimento);
	void deleteMovimento(Movimento movimento);
	void updateMovimento(Movimento movimento); 
}
