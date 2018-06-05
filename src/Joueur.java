import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Joueur {
	private ArrayList<Unite> Unites;
	private ArrayList<Territoire> Territoires; 
	private ArrayList<Unite> Tampon;
	private String nom = new String();
	private Color couleur;
	private String mission = new String();
	private boolean ia;
	private int territoiresConquis;
	
	public Joueur(String n, Color c, boolean IA) {
		this.nom = n;
		this.couleur = c;
		this.ia = IA;
		this.Unites = new ArrayList<>();
		this.Territoires = new ArrayList<>();
	}
	
	public void setTerritoiresConquis(int nbr) {
		this.territoiresConquis = nbr;
	}
	
	public int getTerritoiresConquis() {
			return territoiresConquis;
		}

	public void setMission(String m) {
		this.mission = m;
	}
	
	public boolean getIA() { //Temporaire
		return this.ia;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public Color getCouleur() {
		return this.couleur;
	}
	
	public String getMission(){
		return mission;
	}

	public ArrayList<Territoire> getTerritoire() {
		return Territoires;
	}
	
	public void addTerritoire(Territoire territoire) {
		Territoires.add(territoire);
	}
	
	public void addUnite(Unite UNITE) {
		Unites.add(UNITE);
	}
	
	public ArrayList<Unite> getArmy(){
		return Unites;
	}
	
		
	public ArrayList<Unite> getArmyAvailable(){
		ArrayList<Unite> t = new ArrayList<>();
		t.clear();
		t.addAll(Unites);
		for(int i = Unites.size()-1;i>=0;i--) {
			if(Unites.get(i).isOnField()) {
				t.remove(i);
			}
		}
		return t;
	}
	

	
}
