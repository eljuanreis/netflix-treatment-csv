package model;

public class Serie {
	public String major_genre;
	public String title;
	public String subgenre;
	public int premiereYear;
	public String episodes;
	public String status;
	public int imdb_rating;
	
	public Serie(String[] data) {
		this.major_genre = data[0];
		this.title = data[1];
		this.subgenre = data[2];
		this.premiereYear = Integer.valueOf(data[4]);
		this.episodes = data[10];
		this.status = data[6];
		this.imdb_rating = Integer.valueOf(data[11]);
	}

	@Override
	public String toString() {
		return major_genre + ";" + title + ";" + subgenre + ";"+ premiereYear + ";" + episodes + ";" + status + ";" + imdb_rating + "\r\n";
	}
	
	public String getMajorGenre() {
		return major_genre + "/r/n";
	}
	
	@Override
	public int hashCode() {
		if (this.imdb_rating <= 15) {
			return 0;
		}
		
		if (this.imdb_rating > 15 && this.imdb_rating <= 30) {
			return 1;
		}
		
		if (this.imdb_rating > 30 && this.imdb_rating <= 45) {
			return 2;
		}
		
		if (this.imdb_rating > 45 && this.imdb_rating <= 60) {
			return 3;
		}
		
		if (this.imdb_rating > 60 && this.imdb_rating <= 75) {
			return 4;
		}
		
		if (this.imdb_rating > 75 && this.imdb_rating <= 90) {
			return 5;
		}
		
		if (this.imdb_rating > 90) {
			return 6;
		}
		
		return 0;
	}
}
