package it.betacom.service;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfBot {
	int i = 1;
	PdfWriter writer = null;
	Document doc = null;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	public void creaPDF(String c, String t,LocalDate ld) {
		try {
			String path = "C:/Users/Betacom/Documents/Workspace-Eclipse/Conto_Bancario-main/Movimenti/EC_"+t+"_"+c+"_"+i+"_"+ld+".pdf";
			while(Files.exists(Paths.get("c:/esempi/Movimenti/EC_"+t+"_"+c+"_"+i+"_"+ld+".pdf"))){
				i++;
				path = "C:/Users/Betacom/Documents/Workspace-Eclipse/Conto_Bancario-main/Movimenti/EC_"+t+"_"+c+"_"+i+"_"+ld+".pdf";
			}
			doc  = new Document();
			writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
			doc.open();
			doc.add(new Paragraph("Elenco movimenti"));
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		
	}
	public void scriviParagrafo(String paragrafo) {
		try {
			doc.add(new Paragraph(paragrafo));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public void stampaPDF() {
		try {
			doc.add(new Paragraph("****************************"));
			doc.close();
			writer.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
