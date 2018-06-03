import java.awt.Color;
import java.util.ArrayList;
import edu.princeton.cs.introcs.StdDraw;

public class Territoire {

	public Territoire(int id, int x, int y,int[] voisins, String n, String r) {
		super();
		this.id = id;
		this.nom = n;
		this.region = r;
		this.x = x;
		this.y = y;
		this.voisins = voisins;
	}

	public int getId() {
		return id;
	}
	
	public void setJoueur(Joueur j) {
		this.j = j;
	}
	
	public Joueur getJoueur() {
		return j;
	}
	
	public void setAttaquable(boolean a) {
		this.attaquable = a;
	}
	
	public boolean getAttaquable() {
		return this.attaquable;
	}
	
	public void setDeplacable(boolean a) {
		this.deplacable = a;
	} 
	
	public boolean getDeplacable() {
		return this.deplacable;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getArmee() {
		return armee;
	}
	public void setArmee(int armee) {
		this.armee = armee;
	}
	
	public String getRegion() {
		return region;
	}

	public String getNom() {
		return nom; 
	}
	public void setNom(String n) {
		this.nom = n;
	}
	public int[] getVoisins() {
		return voisins;
	}
	public void setVoisins(int[] voisins) {
		this.voisins = voisins;
	}
	public ArrayList<Unite> getArmy() {
		return army;
	}
	public void setArmy(ArrayList<Unite> army) {
		this.army = army;
	}
	
	public void addArmy(Unite army) {
		this.army.add(army);
	}
	
	public void deleteArmy(Unite army) {
		this.army.remove(army);
	}
	
	public int getRayon() {
		return rayon;
	}
	
	public int[] getPos(){
		int[] pos = {x,y};
		return pos;
	}
	

	private int id; 
	private final int rayon = 10;
	private int x;
	private int y;
	private int armee = 0;
	private String nom = new String();
	private int[] voisins;
	private String region = new String();
	private boolean attaquable = false;
	private boolean deplacable = false;
	private ArrayList<Unite> army = new ArrayList<Unite>(0);
	private Joueur j;

	
}

