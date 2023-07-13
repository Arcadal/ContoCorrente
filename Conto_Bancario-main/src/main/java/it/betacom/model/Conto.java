package it.betacom.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.betacom.dao.ContoDao;
import it.betacom.dao.impl.ContoDaoImpl;

public abstract class Conto {
	private Integer id;
	private TipoConto tipoConto;
	private Correntista correntista;
	private LocalDate dataApertura;
	public double saldo;
	private LocalDate dataChiusura;
	
	private List<Movimento> listaMovimenti = new ArrayList<>();
	
	ContoDao contoDao = ContoDaoImpl.getInstance();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	Scanner input = new Scanner(System.in);
	
	public Conto(Integer id, TipoConto tipoConto, Correntista corr, LocalDate dataA, double saldo, LocalDate dataC) {
		setId(id);
		setTipoConto(tipoConto);
		setCorrentista(corr);
		setDataApertura(dataA);
		setSaldo(saldo);
		setDataChiusura(dataC);
	}
	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	public TipoConto getTipoConto() {return tipoConto;}
	public void setTipoConto(TipoConto tipoConto) {this.tipoConto = tipoConto;}
	
	public Correntista getCorrentista() {return correntista;}
	public void setCorrentista(Correntista correntista) {this.correntista = correntista;}
	
	public LocalDate getDataApertura() {return dataApertura;}
	public void setDataApertura(LocalDate dataApertura) {this.dataApertura = dataApertura;}
	
	public double getSaldo() {return  (double)((int)(saldo*100))/100;}
	public void setSaldo(double saldo) {this.saldo = (double)((int)(saldo*100))/100;}
	
	public LocalDate getDataChiusura() {return dataChiusura;}
	public void setDataChiusura(LocalDate dataChiusura) {this.dataChiusura = dataChiusura;}
	
	public List<Movimento> getListaMovimenti() {return listaMovimenti;}
	public void setListaMovimenti(List<Movimento> listaMovimenti) {this.listaMovimenti = listaMovimenti;}
	
	public void preleva(double prelievo, LocalDate dataMovimento, Conto conto) {
		double s = getSaldo();
		setSaldo(s - prelievo);
		Movimento mov = new Movimento(null, conto.getId(),"Prelievo",prelievo,s,conto.getSaldo(), dataMovimento);
		getListaMovimenti().add(mov);
		contoDao.updateContoMovimento(conto);
		System.out.println("Prelievo   - Importo: " + prelievo + " Saldo: " + getSaldo());
	}
	public void versa(double versamento, LocalDate dataMovimento, Conto conto) {

		double s = getSaldo();
		setSaldo(s + versamento);
		conto.getListaMovimenti().add(new Movimento(null, conto.getId(),"Versamento",versamento,s,conto.getSaldo(), dataMovimento));
		contoDao.updateContoMovimento(conto);
		System.out.println("Versamento - Importo: " + versamento + " Saldo: " + getSaldo());
	}
	public abstract double generaInteressi(List<Movimento> listaMovimenti, int anno);
}
