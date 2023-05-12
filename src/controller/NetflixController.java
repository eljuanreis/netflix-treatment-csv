package controller;

import java.io.IOException;

import br.edu.fateczl.filaobj.Fila;
import br.edu.fateczl.lista.listaObj.Lista;
import contracts.INetflixController;
import model.Serie;
import service.FileService;

public class NetflixController implements INetflixController {

	private FileService service;
	private Fila filaMajorGenre, filaPremiereYear;
	private Lista[] ratingList;
	
	public NetflixController() {
		this.service = new FileService();
		this.filaMajorGenre = new Fila();
		this.filaPremiereYear = new Fila();
		
		this.ratingList = new Lista[7];
		
		for (int i = 0; i < 7; i++) {
			this.ratingList[i] = new Lista();
		}
	}
	
	@Override
	public void generateGenre(String genere) {
		try {
			this.getCSVGenre(genere);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void getCSVGenre(String genere) {
		String csvData = null;
		try {
			csvData = this.service.readData("netflixSeries");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		String[] csv = csvData.split("\r\n");
		
		for (String line : csv) {
			
			if (line.contains(genere)) {
				// Tratamento de ; atoa
				if (line.contains("\"")) {
					line = this.treatmentComma(line);
				}
				
				// cortando csv
				String[] data = line.split(";");
				
				Serie serie = new Serie(data);
				
				this.filaMajorGenre.insert(serie);

				try {
					this.service.run(data[0], serie.toString());
				} catch (Exception e) {
					System.err.println(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void generatePremiereYear(int year) {
		this.getCSVPremiereYear(year);
		
	}

	private void getCSVPremiereYear (int year) {
		String csvData = null;

		try {
			csvData = this.service.readData("netflixSeries");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		String[] csv = csvData.split("\r\n");
		
		for (String line : csv) {
			String yearString = String.valueOf(year);

			if (line.contains(yearString)) {
	
				// Tratamento de ; atoa
				if (line.contains("\"")) {
					line = this.treatmentComma(line);
				}
	
				String[] data = line.split(";");
							
				Serie serie = new Serie(data);
				
				if (serie.status == "renewed") {				
					this.filaPremiereYear.insert(serie);
				}
				
				try {
					this.service.run(data[4], serie.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void classification() throws Exception {
		String csvData = null;

		try {
			csvData = this.service.readData("netflixSeries");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		String[] csv = csvData.split("\r\n");
		boolean first = true;
		
		for (String line : csv) {

			if (first) {
				first = false;
				continue;
			}
			
			// Tratamento de ; atoa
			if (line.contains("\"")) {
				line = this.treatmentComma(line);
			}
			
			String[] data = line.split(";");

			Serie serie = new Serie(data);
			
			if (this.ratingList[serie.hashCode()].isEmpty()) {
				this.ratingList[serie.hashCode()].addFirst(serie);
			} else {
				this.ratingList[serie.hashCode()].addLast(serie);
			}
		}
		
	}

	@Override
	public void searchByClassification(int classification) throws Exception {
		this.classification();
		Lista l = this.ratingList[classification];
		int size = l.size();

		for (int i = 0 ; i < size; i++) {
			try {
				Serie s = (Serie) l.get(i);
				System.out.println(s.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private String treatmentComma(String line) {
		String[] data = line.split("\"");
		
		for (String d : data) {
			String initialChar = String.valueOf(d.charAt(0));
			String finalChar = String.valueOf(d.charAt(d.length() - 1));
			
			if (initialChar.contains(";") || finalChar.contains(";")) {
				continue;
			}
			
			if (d.contains(";")) {
				String remove = d.replace(";", ",");
				line = line.replace(d, remove);
			}
		}
		
		return line;
	}

}
