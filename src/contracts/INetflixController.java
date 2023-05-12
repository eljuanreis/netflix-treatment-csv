package contracts;

public interface INetflixController {
	public void generateGenre(String genere);
	public void generatePremiereYear(int year);
	public void classification() throws Exception;
	public void searchByClassification(int classification) throws Exception;
}
