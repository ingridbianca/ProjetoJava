package ReadTable;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import com.opencsv.CSVWriter;

public class OscarMovie {
	private static final String CSV_FILE_PATH
    = "./files/movies.csv";
	
	public static void main(String[] args) throws IOException{
		addDataToCSV(CSV_FILE_PATH);
	}


	public static void addDataToCSV(String output)
    {

        File file = new File(output);
        Scanner sc = new Scanner(System.in);
        
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);
  
            // create CSVWriter with ';' as separator
            CSVWriter writer = new CSVWriter(outputfile, ';',
                                             CSVWriter.NO_QUOTE_CHARACTER,
                                             CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                             CSVWriter.DEFAULT_LINE_END);
            
            Document doc = Jsoup.connect("https://www.imdb.com/chart/top")
    				.timeout(6000).get();
    		Elements body = doc.select("tbody.lister-list");
            
            // create a List which contains Data
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Titulo", "Ano", "Ranking", "Imagem" });         

        	for(Element e: body.select("tr")) {
        		String img = e.select("td.posterColumn img").attr("src");
        	    String title = e.select("td.posterColumn img").attr("alt");
        	    String year = e.select("td.titleColumn span.secondaryInfo").text().replaceAll("[^\\d]", "");
        	    String rate = e.select("td.ratingColumn.imdbRating").text().trim();
       
        	    data.add(new String[] {title, year, rate, img});
                
                
        	}
        	writer.writeAll(data); 
            writer.close();
            System.out.println("Fim da execução com sucesso!!");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
}
	
