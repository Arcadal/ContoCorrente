package it.betacom.dao;

import java.sql.Statement;

import it.betacom.model.TipoConto;

public interface TipoContoDao {

	void setConnessione(Statement stm);
	TipoConto getTipoContoById(int id);
	void updateTipoConto(TipoConto tipoConto);
}
