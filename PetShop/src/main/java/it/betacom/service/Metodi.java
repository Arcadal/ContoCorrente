package it.betacom.service;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.opencsv.CSVReader;

import it.betacom.model.Animale;
import it.betacom.model.Cliente;

public class Metodi {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public Metodi() {
        emf = Persistence.createEntityManagerFactory("PetShop");
        em = emf.createEntityManager();
    }

    public void inizializza() {
        // Non ci sono modifiche da apportare qui
    }

    public void chiusura() {
        em.close();
        emf.close();
    }

    public void importaCSV(String filePath) {
        Map<String, Cliente> existClienti = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] header = csvReader.readNext();
            String[] header2 = csvReader.readNext();

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String nomeCliente = nextLine[0];
                String cognomeCliente = nextLine[1];
                String key = nomeCliente + " " + cognomeCliente;

                Cliente cliente;
                if (existClienti.containsKey(key)) {
                    cliente = existClienti.get(key);
                } else {
                    String cittaCliente = nextLine[2];
                    String telefonoCliente = nextLine[3];
                    String indirizzoCliente = nextLine[4];

                    cliente = new Cliente();
                    cliente.setNome(nomeCliente);
                    cliente.setCognome(cognomeCliente);
                    cliente.setCitta(cittaCliente);
                    cliente.setTelefono(telefonoCliente);
                    cliente.setIndirizzo(indirizzoCliente);

                    em.getTransaction().begin();
                    em.persist(cliente);
                    em.getTransaction().commit();

                    existClienti.put(key, cliente);
                }

                String tipoAnimale = nextLine[5];
                String nomeAnimale = nextLine[6];
                String matricola = nextLine[7];
                String dataAcquisto = nextLine[8];
                double prezzo = Double.parseDouble(nextLine[9]);

                Animale animale = new Animale();
                animale.setTipoAnimale(tipoAnimale);
                animale.setNomeAnimale(nomeAnimale);
                animale.setMatricola(matricola);
                animale.setDataAcquisto(dataAcquisto);
                animale.setPrezzo(prezzo);
                animale.setCliente(cliente);

                em.getTransaction().begin();
                em.persist(animale);
                em.getTransaction().commit();
            }
            System.out.println("Dati caricati Correttamente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void inserisci () {
        String filePath = ".\\documenti\\PetShop_dati.csv";
        String nuoviDati = "Guido,Curradi,Roma,12345678,Via Roma 10,Gatto,Peppino,123456,12/06/2023,250";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.newLine();
            writer.write(nuoviDati);
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Scrittura Completata");
    }
}
