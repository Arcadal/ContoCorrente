package it.betacom.main;

import it.betacom.service.BancaService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BancaService bs = new BancaService();
        bs.sceltaOperazione();
    }
}
