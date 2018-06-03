import java.util.ArrayList;

public class Region {
	private String nom = new String();
	private ArrayList<Territoire> territoires;
	
	public Region(String n) {
		this.nom = n;
		this.territoires = new ArrayList<>();
	}
	
	public void addTerritoire(Territoire t) {
		territoires.add(t);
	}
	
	public int getSize() {
		return territoires.size();
	}
	
	public String getNom() {
		return nom;
	}
}
