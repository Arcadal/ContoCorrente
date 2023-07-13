package it.betacom.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import it.betacom.dao.ContoDao;
import it.betacom.dao.CorrentistaDao;
import it.betacom.dao.MovimentoDao;
import it.betacom.dao.TipoContoDao;
import it.betacom.dao.impl.ContoDaoImpl;
import it.betacom.dao.impl.CorrentistaDaoImpl;
import it.betacom.dao.impl.MovimentoDaoImpl;
import it.betacom.dao.impl.TipoContoDaoImpl;
import it.betacom.model.Conto;
import it.betacom.model.ContoCorrente;
import it.betacom.model.ContoDeposito;
import it.betacom.model.ContoInvestimento;
import it.betacom.model.Correntista;
import it.betacom.model.Movimento;
import it.betacom.singleton.DBHandler;

public class BancaService {
	int idTipo = 0;
	CorrentistaDao corrDao = CorrentistaDaoImpl.getInstance();
	ContoDao contoDao = ContoDaoImpl.getInstance();
	MovimentoDao movimentoDao = MovimentoDaoImpl.getInstance();
	TipoContoDao tipoContoDao = TipoContoDaoImpl.getInstance();
	LocalDate dataMovimento;
	Statement stm = null;
	DBHandler db = null;
	ResultSet result;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	Random random = new Random();
	LocalDate dataA = LocalDate.parse("2021-01-01");
	
	public int controlloErrore(Scanner input) {
		boolean ancora = true;
		int scelta = 0;
		while (ancora) {
			try {
				scelta = input.nextInt();
				ancora = false;
			} catch (InputMismatchException e) {
				System.out.println("Errore: inserire nuovamente");
				input.nextLine();
			}
		}
		return scelta;
	}
	private void getMappa(boolean bl){
		int i = 0;
		Map<Integer,String> map=new HashMap<Integer,String>(); 
		if (bl) 
			map.put(1,"Aprire un nuovo conto corrente");  
		else 
			i = 1;
		map.put(2-i,"Prelevare");  
		map.put(3-i,"Versare");  
		map.put(4-i,"Visualizzare saldo e ultimi movimenti");  
		map.put(5-i,"Logout"); 
		for(@SuppressWarnings("rawtypes") Map.Entry m:map.entrySet()) 
			System.out.println(m.getKey()+": "+m.getValue());  
	}
	public void sceltaOperazione() {
		System.out.println("Inizio Applicazione");
		if (stm == null && db == null) {
			try {
				db = DBHandler.getInstance();
				stm = db.getConnection().createStatement();
				contoDao.setConnessione(stm);
				corrDao.setConnessione(stm);
				movimentoDao.setConnessione(stm);
				tipoContoDao.setConnessione(stm);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Scanner input = new Scanner(System.in);
		boolean ancora = true;
		while (ancora) {
			System.out.println("Benvenuto, che operazione vuoi fare?");
			getMappa(true);
			int operazione = controlloErrore(input);
			switch (operazione) {
			case 1:aggiungiConto(input);break;
			case 2:checkID(input, operazione);break;
			case 3:checkID(input, operazione);break;
			case 4:checkID(input, operazione);break;
			case 5:ancora=false;break;
			default:
				System.out.println("Operazione inesistente");
			}
		}
		db.closeConnection();
		System.out.println("Fine Applicazione");
		input.close();
	}
	private void aggiungiConto(Scanner input) {
		Correntista corr = new Correntista();
		Conto conto = null;
		input.nextLine();
		System.out.println("Inserire il nome");
		corr.setNome(input.nextLine());
		System.out.println("Inserire il cognome");
		corr.setCognome(input.nextLine());
		System.out.println("Inserire la città");
		corr.setCitta(input.nextLine());
		System.out.println("Inserire la nazione");
		corr.setNazione(input.nextLine());
		System.out.println("Inserire il numero");
		corr.setTelefono(input.nextLine());
		System.out.println("Che tipo di conto vuoi aprire?");
		System.out.println("1. Conto Corrente\n2. Conto Deposito\n3. Conto Investimento");
		int scelta = controlloErrore(input);
		boolean ancora = true;
		while (ancora) {
			if (scelta == 1) {
				conto = new ContoCorrente(null, tipoContoDao.getTipoContoById(1), corr, dataA, 1000, null);
				contoDao.insertConto(conto);
				Movimento movimento = new Movimento(null,contoDao.getContoByCorrentista(corr.getNome(), corr.getCognome()).getId(),"Apertura", 1000, 0, 1000, dataA);
				movimentoDao.insertMovimento(movimento);
				ancora = false;
			} else if (scelta == 2) {
				contoDao.insertConto(new ContoDeposito(null, tipoContoDao.getTipoContoById(2), corr, dataA, 1000, null));
				ancora = false;
			}else if (scelta == 3) {
				contoDao.insertConto(new ContoInvestimento(null, tipoContoDao.getTipoContoById(3), corr, dataA, 1000, null));
				ancora = false;
			} else {
				System.out.println("Errore: scelta non consentita, riprovare");
			}
		}
	}
	private void checkID(Scanner input, int operazione) {
		input.nextLine();
		System.out.println("Inserire dati di accesso");
		System.out.println("Nome");
		String nome = input.nextLine();
		System.out.println("Cognome");
		String cognome = input.nextLine();
		Conto conto = contoDao.getContoByCorrentista(nome, cognome);
		LocalDate data = conto.getListaMovimenti().get(conto.getListaMovimenti().size()-1).getDataM();
		dataMovimento = LocalDate.now().minusDays(random.nextInt((int) ChronoUnit.DAYS.between(data, LocalDate.now())));
		System.out.println(dataMovimento.format(formatter) + " - Accesso Effettuato\n"
				+ "	   - Bentornato: "+conto.getCorrentista().getNome()+" "+conto.getCorrentista().getCognome());
		Integer genera = null;
		if (data.getYear() < dataMovimento.getYear()) {
			genera = data.getYear() - dataMovimento.getYear();
		}
		if (operazione == 4) 
			stampaMovimenti(conto, dataMovimento);
		else 
			operazione(input,conto,operazione,genera);
	}
	private void operazione(Scanner input, Conto conto, int operazione, Integer genera) {

		while (genera != null) {
			int anno = dataMovimento.getYear()+genera;
			double interessi = conto.generaInteressi(conto.getListaMovimenti(),anno);
			double s = conto.getSaldo();
			conto.setSaldo(s+interessi);
			genera ++;
			conto.getListaMovimenti().add(new Movimento(null, conto.getId(),"Interessi",interessi,s,conto.getSaldo(), LocalDate.parse((anno+1)+"-01-01")));
			contoDao.updateContoMovimento(conto);
			System.out.println("	   - Saldo attuale: "+conto.getSaldo()+" di cui interessi maturati: "+interessi);
			if (genera == 0)
				genera = null;
		}	

		double risultato = 0;
		String tipoMovimento = (operazione == 2) ? "prelevare" : "versare";
			System.out.println("Quanto vuoi " + tipoMovimento + "?");
			risultato = controlloErrore(input);
		if (operazione == 2) 
			conto.preleva(risultato, dataMovimento, conto);
		else 
			conto.versa(risultato, dataMovimento, conto);
	}
	private void stampaMovimenti(Conto conto, LocalDate oggi) {
		PdfBot pdf = new PdfBot();
		String t = conto.getTipoConto().getTipo();
		String c = conto.getCorrentista().getCognome();
		System.out.println("Elenco movimenti - "+conto.getCorrentista().getCognome());
		pdf.creaPDF(c, t, oggi);
		pdf.scriviParagrafo("****************************");

		for (Movimento mov : conto.getListaMovimenti()) {
			String s = mov.getDataM().format(formatter)+" - Saldo: "+mov.getSaldoP()+" - "+mov.getTipoMovimento()+": "
					+mov.getImporto()+" Saldo attuale "+mov.getSaldoS();
					System.out.println(s);
					pdf.scriviParagrafo(s);
		}
		pdf.stampaPDF();

	}
}