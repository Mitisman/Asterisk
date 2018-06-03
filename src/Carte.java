import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdDraw;

public class Carte {

	private final int largeur = 1643;
	private final int hauteur = 675;
	private final String carte = new String("risk.jpg");
	private final String carteS = new String("riskF.jpg");
	private static Font font = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 20);
	protected ArrayList<Territoire> territoire;
	private boolean draw = false;
	private ArrayList<String> nterritoire;
	private ArrayList<String> nregion;

	private int[][] listeregion;
	private ArrayList<int[]> listevoisins;
	//private int N_joueur;
	
	
	public Carte(int[][] r, ArrayList<int[]> v, ArrayList<String> t, ArrayList<String> re) {
	//	this.N_joueur = n;
		this.listeregion = r;
		this.listevoisins = v;
		this.nterritoire = t;
		this.nregion = re;
		this.territoire = new ArrayList<Territoire>();
	}
	
	public void initialisation() {
		for(int i=0;i<listeregion.length;i++) {
			Territoire t = new Territoire(i, listeregion[i][0], listeregion[i][1], listevoisins.get(i), nterritoire.get(i), nregion.get(i));
			territoire.add(t);
		}
	}
	
	public void Start() {
		StdDraw.clear();
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(largeur, hauteur);
		StdDraw.setXscale(0, largeur);
		StdDraw.setYscale(0, hauteur);
		StdDraw.picture(largeur/2, hauteur/2, carte);
		for(int i=0;i<listeregion.length;i++) {
			Territoire t = territoire.get(i);
			Joueur j = t.getJoueur();
			StdDraw.setPenColor(j.getCouleur());
			StdDraw.filledCircle(listeregion[i][0], listeregion[i][1], 15);
			StdDraw.setPenColor(Color.WHITE);
			StdDraw.text(listeregion[i][0]-0.1, listeregion[i][1]-0.8, ""+ t.getArmy().size());
		}
		StdDraw.show();
	}
	
	//MODIFIER MAJ() POUR QUE LORSQUE L'UTILISATEUR CLIQUE SUR UN PAYS, LES PAYS VOISINS SE COLORENT EN NOIR; SI LE JOUEUR
	//CLIQUE JUSTE ENSUITE SUR UN DE CES PAYS NOIRS, IL LANCE UNE ATTAQUE (SI IL CLIQUE AILLEURS, LES VOISINS REPRENNENT
	//LEUR COULEUR ET ON REVIENT COMME AVANT). C'EST CLAIR :) ?
	
	public void Maj(Partie game, Joueur j) {
		System.out.println(StdDraw.isMousePressed());
		ArrayList<Integer> quickfix = game.selectionPays(j,this.territoire);
		if(quickfix.get(0) == 1) {
			while(!StdDraw.isMousePressed()) {
				for(int i = 0;i<this.territoire.size();i++) {
					Territoire t = territoire.get(i);
					int[] territoire = t.getPos();
					if(territoire[0]-10<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoire[0]+10 && territoire[1]-10<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoire[1]+10 ) {
						StdDraw.clear();
						StdDraw.picture(largeur/2, hauteur/2, carte);
						StdDraw.setFont();
						for(int k=0;k<listeregion.length;k++) {
							Territoire ta = this.territoire.get(k);
							Joueur joueur = ta.getJoueur();
							StdDraw.setPenColor(joueur.getCouleur());
							StdDraw.filledCircle(listeregion[k][0], listeregion[k][1], 15);
							StdDraw.setPenColor(Color.WHITE);
							StdDraw.text(listeregion[k][0]-0.1, listeregion[k][1]-0.8, ""+ ta.getArmy().size());
						}
						for(int n = quickfix.size()-1;n>0;n--) {
							if(this.territoire.get(quickfix.get(n)).getJoueur() == j) {
								StdDraw.setPenColor(Color.DARK_GRAY);
								this.territoire.get(quickfix.get(n)).setDeplacable(true);
								StdDraw.filledCircle(this.territoire.get(quickfix.get(n)).getPos()[0], this.territoire.get(quickfix.get(n)).getPos()[1] , 15);
								StdDraw.setPenColor(Color.WHITE);
								StdDraw.text(this.territoire.get(quickfix.get(n)).getPos()[0]-0.1, this.territoire.get(quickfix.get(n)).getPos()[1]-0.8, "" + this.territoire.get(quickfix.get(n)).getArmy().size());
							} else {
								StdDraw.setPenColor(Color.BLACK);
								this.territoire.get(quickfix.get(n)).setAttaquable(true);
								StdDraw.filledCircle(this.territoire.get(quickfix.get(n)).getPos()[0], this.territoire.get(quickfix.get(n)).getPos()[1] , 15);
								StdDraw.setPenColor(Color.WHITE);
								StdDraw.text(this.territoire.get(quickfix.get(n)).getPos()[0]-0.1, this.territoire.get(quickfix.get(n)).getPos()[1]-0.8, "" + this.territoire.get(quickfix.get(n)).getArmy().size());
							}
						}
						
						StdDraw.setFont(font);
						StdDraw.setPenColor(Color.BLACK);
						StdDraw.textLeft(1205, 560, t.getNom());
						StdDraw.textLeft(1205, 540, " à " + t.getJoueur().getNom());
						StdDraw.textLeft(1205, 520, " En" + t.getRegion());
						
						int ncanon = 0;
						int ncavalier = 0;
						int nsoldat = 0;
						for(int k = t.getArmy().size()-1;k>=0;k--) {
							Unite u = t.getArmy().get(k);
							if(u.type=="Soldat") {
								nsoldat++;
							} else if(u.type == "Canon") {
								ncanon++;
							} else {
								ncavalier++;
							}
						}
						StdDraw.textLeft(1205, 460, " Avec " + nsoldat +" Soldat" );
						StdDraw.textLeft(1205, 430, " Avec " + ncavalier +" Cavalier" );
						StdDraw.textLeft(1205, 400, " Avec " + ncanon +" Canon" );
						StdDraw.textLeft(1205, 370, " Encore "+ t.getJoueur().getArmyAvailable().size()+ " armée(s) à placer");
						int x = t.getJoueur().getArmy().size() - t.getJoueur().getArmyAvailable().size();
						StdDraw.textLeft(1205, 340,  " "+ x +" armée(s) sur " + t.getJoueur().getTerritoire().size() +" Terrritoires" );
						StdDraw.textLeft(1205, 310, " Pour " + t.getJoueur().getNom());
						StdDraw.show();
					}
				}
			}
		} else {
			for(int i = 0;i<this.territoire.size();i++) {
				Territoire t = territoire.get(i);
				int[] territoire = t.getPos();
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, carte);
				StdDraw.setFont();
				for(int k=0;k<listeregion.length;k++) {
					Territoire ta = this.territoire.get(k);
					Joueur a = ta.getJoueur();
					StdDraw.setPenColor(a.getCouleur());
					StdDraw.filledCircle(listeregion[k][0], listeregion[k][1], 15);
					StdDraw.setPenColor(Color.WHITE);
					StdDraw.text(listeregion[k][0]-0.1, listeregion[k][1]-0.8, ""+ ta.getArmy().size());
				}
			}
		}
	
		
		StdDraw.show();	
		/*
		if(StdDraw.mouseX()>=1233 && StdDraw.mouseY()<=1620 && StdDraw.mouseY()>=84 && StdDraw.mouseY()<=125) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, carteS);
			StdDraw.setFont();
			for(int k=0;k<listeregion.length;k++) {
				Territoire ta = this.territoire.get(k);
				Joueur j = ta.getJoueur();
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(listeregion[k][0], listeregion[k][1], 15);
				StdDraw.setPenColor(Color.WHITE);
				StdDraw.text(listeregion[k][0]-0.1, listeregion[k][1]-0.8, ""+ ta.getArmy().size());
			}
			draw = true;
		} else if(draw){
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, carte);
			StdDraw.setFont();
			for(int k=0;k<listeregion.length;k++) {
				Territoire ta = this.territoire.get(k);
				Joueur j = ta.getJoueur();
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(listeregion[k][0], listeregion[k][1], 15);
				StdDraw.setPenColor(Color.WHITE);
				StdDraw.text(listeregion[k][0]-0.1, listeregion[k][1]-0.8, ""+ ta.getArmy().size());
			}
			draw = false;
		}
		for(int i = 0;i<this.territoire.size();i++) {
			Territoire t = territoire.get(i);
			int[] territoire = t.getPos();
			if(territoire[0]-10<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoire[0]+10 && territoire[1]-10<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoire[1]+10 ) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, carte);
				StdDraw.setFont();
				for(int k=0;k<listeregion.length;k++) {
					Territoire ta = this.territoire.get(k);
					Joueur j = ta.getJoueur();
					StdDraw.setPenColor(j.getCouleur());
					StdDraw.filledCircle(listeregion[k][0], listeregion[k][1], 15);
					StdDraw.setPenColor(Color.WHITE);
					StdDraw.text(listeregion[k][0]-0.1, listeregion[k][1]-0.8, ""+ ta.getArmy().size());
				}
				StdDraw.setFont(font);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.textLeft(1205, 560, t.getNom());
				StdDraw.textLeft(1205, 540, " à " + t.getJoueur().getNom());
				StdDraw.textLeft(1205, 520, " En" + t.getRegion());
				
				int ncanon = 0;
				int ncavalier = 0;
				int nsoldat = 0;
				for(int k = t.getArmy().size()-1;k>=0;k--) {
					Unite u = t.getArmy().get(k);
					if(u.type=="Soldat") {
						nsoldat++;
					} else if(u.type == "Canon") {
						ncanon++;
					} else {
						ncavalier++;
					}
				}
				StdDraw.textLeft(1205, 460, " Avec " + nsoldat +" Soldat" );
				StdDraw.textLeft(1205, 430, " Avec " + ncavalier +" Cavalier" );
				StdDraw.textLeft(1205, 400, " Avec " + ncanon +" Canon" );
				StdDraw.textLeft(1205, 370, " Encore "+ t.getJoueur().getArmyAvailable().size()+ " armée(s) à placer");
				int x = t.getJoueur().getArmy().size() - t.getJoueur().getArmyAvailable().size();
				StdDraw.textLeft(1205, 340,  " "+ x +" armée(s) sur " + t.getJoueur().getTerritoire().size() +" Terrritoires" );
				StdDraw.textLeft(1205, 310, " Pour " + t.getJoueur().getNom());
				StdDraw.show();
			}
		
			 */	
		}
		
	//}

	
	public ArrayList<Territoire> getTerritoire() {
		return territoire;
	}

	public void setRegion(ArrayList<Territoire> territoire) {
		this.territoire = territoire;
	}

	
	public double[] Souris() {
		double pos[] = {StdDraw.mouseX(),StdDraw.mouseY()};
		return pos;
	}
	
	public boolean click() {
		return StdDraw.isMousePressed();
	}
	
}
