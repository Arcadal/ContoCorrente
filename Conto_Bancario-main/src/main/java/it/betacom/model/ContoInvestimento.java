package it.betacom.model;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

public class ContoInvestimento extends Conto{

	public ContoInvestimento(Integer id, TipoConto tipoConto, Correntista corr, 
			LocalDate dataA, double saldo,LocalDate dataC) {
		super(id, tipoConto, corr, dataA, saldo, dataC);
	}

	Random random = new Random();
	@Override
	public double generaInteressi(List<Movimento> listaMovimenti, int anno) {
		getTipoConto().setTasso(random.nextInt(201)-100);
		
		LocalDate ini = LocalDate.parse(anno+"-01-01");
		double interessi = 0;
		System.out.println("Aggiornamento Saldo, hai maturato interessi in data: " + ini.plusYears(1).format(formatter));
		System.out.println("	   - Lista movimenti anno: " + (anno));
		for (int i = 0; i < listaMovimenti.size(); i++) {
			if (listaMovimenti.get(i).getDataM().getYear() == anno) {
				LocalDate dataM = listaMovimenti.get(i).getDataM();
				if (listaMovimenti.size()==i+1) 
					dataM = LocalDate.parse((anno+1)+"-01-01");
				long giorni = ChronoUnit.DAYS.between(ini, dataM);
				int giorniAnnoCorrente = (Year.of(anno).isLeap()) ? 366 : 365;
				double parziale = (listaMovimenti.get(i).getSaldoS()*((getTipoConto().getTasso()/giorniAnnoCorrente*giorni) / 100));
				System.out.println(dataM.format(formatter)+" - ("+giorni+") Tasso "+getTipoConto().getTasso()+"% Interesse accumulato: "+parziale);
				interessi += parziale;
				ini = dataM;
			}	
		}
		while (getSaldo() + interessi < 0) {
			System.out.println(" ** ** **  - Anno chiuso in negativo effettuare versamento. Debito: " + getSaldo());
			System.out.println("Prima di procedere effettuare un versamento di almeno" + getSaldo());
			
			double importoMancante = input.nextDouble();
			setSaldo(getSaldo()+importoMancante);
			System.out.println("	   - Versamento effettuato: " + importoMancante + " Saldo: " + getSaldo());
		}
		return (double)((int)(interessi*100))/100;
	}
}
