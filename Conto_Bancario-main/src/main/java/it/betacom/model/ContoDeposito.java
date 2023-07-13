package it.betacom.model;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ContoDeposito extends Conto{

	public ContoDeposito(Integer id, TipoConto tipoConto, Correntista corr,
			LocalDate dataA, double saldo,LocalDate dataC) {
		super(id, tipoConto, corr, dataA, saldo, dataC);
	}

	public void preleva(double prelievo, LocalDate dataMovimento, Conto conto) {
		
		if (prelievo>1000) {
			System.out.println("Non puoi prelevate un importo superiore a 1000");
			preleva(input.nextDouble(), dataMovimento, conto);
		} else {
			super.preleva(prelievo, dataMovimento, conto);
		}
	}
	
	@Override
	public double generaInteressi(List<Movimento> listaMovimenti, int anno) {
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
		return (double)((int)(interessi*100))/100;
	}
}
