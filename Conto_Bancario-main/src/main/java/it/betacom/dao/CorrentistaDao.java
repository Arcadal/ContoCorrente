package it.betacom.dao;
import java.sql.Statement;
import java.util.List;
import it.betacom.model.Correntista;

public interface CorrentistaDao {

	void setConnessione(Statement stm);
	List<Correntista> getCorrentisti();
	Correntista getCorrentistaById(int id);
	Correntista getCorrentistaByName(String nome, String cognome);
	void insertCorrentista(Correntista correntista);
	void deleteCorrentista(Correntista correntista);
	void updateCorrentista(Correntista correntista); 
}
