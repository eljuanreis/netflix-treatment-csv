package view;

import controller.NetflixController;

public class Main {

	public static void main(String[] args) {
		NetflixController nc = new NetflixController();
		
		nc.generateGenre("Comedy");
		
		nc.generatePremiereYear(2016);
		
		try {
			nc.searchByClassification(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
