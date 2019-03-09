/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {
  Map<String, Integer> brandAndAmountToOrder = new HashMap<>();

  public PDFGenerator(String brand, int amountToOrder) {
    brandAndAmountToOrder.put(brand, amountToOrder);
  }

  public static void createPdf(Map<String, Integer> brandAndAmountToOrder) throws Exception {

    //Creating a PdfDocument object 

    //Creating a Document object 
    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream("mypdftable.pdf"));
    //Opening the document
    doc.open();

    //Creating a table 
    PdfPTable table = new PdfPTable(2);
    //Adding cells to the table 
    for (Map.Entry<String, Integer> entry : brandAndAmountToOrder.entrySet()) {
      table.addCell(entry.getKey());
      table.addCell(String.valueOf(entry.getValue()));
    }

    //Adding Table to document  
    doc.add(table);

    //Closing the document 
    doc.close();
    System.out.println("Table created successfully..");
  }

}
