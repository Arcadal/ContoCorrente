package it.betacom.service;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		Metodi metodo = new Metodi();
		metodo.inizializza();
		
		
		//metodo.inserisci();
		//metodo.importaCSV("C:\\Users\\Betacom\\Documents\\Workspace-Eclipse\\PetShop\\documenti\\PetShop_dati.csv");
		metodo.generaReport1();
		metodo.generaReport2();
		
		metodo.chiusura();
		
	}

}