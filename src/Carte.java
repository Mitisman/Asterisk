import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdDraw;

public class Carte {

	private final int largeur = 1643;
	private final int hauteur = 675;
	private final String carte = new String("risk.jpg");
	private final String carteS = new String("riskF.jpg");
	private final String choixn = new String("menuNArmee.jpg");
	private final String choixnRs = new String("menuNArmeeS.jpg");
	private final String choixCArmee = new String("menuCArmee.jpg");
	private final String choixCArmeeRs = new String("lebug.jpg");
	private final String choixCArmeeSs = new String("menuCArmeeSS.jpg");
	private final String debut = new String("debut.jpg");
	private final String choixCArmeeCs = new String("menuCArmeeCS.jpg");
	private final String dep = new String("Deplacement.jpg");
	private final String depR = new String("DeplacementR.jpg");
	private final String debutS = new String("debutS.jpg");
	private final String debutC = new String("debutC.jpg");
	private final String debutO = new String("debutO.jpg");
	private final String depD = new String("DeplacementD.jpg");
	private final String choixArmeeOs = new String("menuCArmeeOBES.jpg");
	private static Font font = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 20);
	private static Font majuscule = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 55);
	private static Font minuscule = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 17);
	protected ArrayList<Territoire> territoire;
	private boolean reset = true;
	private ArrayList<String> nterritoire;
	private ArrayList<String> nregion;
	private int ID = 100;

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
	
	public void Start(Joueur j) {
		StdDraw.clear();
		StdDraw.picture(largeur/2, hauteur/2, carte);
		StdDraw.setFont();
		for(int h = territoire.size() - 1; h>=0;h--) {
			StdDraw.setPenColor(territoire.get(h).getJoueur().getCouleur());
			StdDraw.filledCircle(territoire.get(h).getX(), territoire.get(h).getY(), 15);
			StdDraw.setPenColor(Color.WHITE);
			StdDraw.text(territoire.get(h).getX()-0.1, territoire.get(h).getY()-0.8, ""+ territoire.get(h).getArmy().size());
		}
		StdDraw.setFont(font);
		StdDraw.setPenColor(j.getCouleur());
		StdDraw.filledCircle(1230, 560, 10);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.textLeft(1245, 560, ""+j.getNom() + " Objectif : ");
		StdDraw.setFont(minuscule);
		if(j.getMission().length()>31) {  //Pour éviter que la mission sorte du canvas :o
			String[] morceau = {j.getMission().substring(0, 31),j.getMission().substring(31)};
			StdDraw.textLeft(1220, 530, " " + morceau[0]);
			StdDraw.textLeft(1220, 510, " " + morceau[1]);
		} else {
			StdDraw.textLeft(1220, 530, " " + j.getMission());
		}
		StdDraw.setFont(font);
		StdDraw.textLeft(1205, 450, " Place ton "+ j.getArmyAvailable().get(j.getArmyAvailable().size()-1).getType() );
		int x = j.getArmy().size() - j.getArmyAvailable().size();
		StdDraw.textLeft(1205, 420,  " "+ x +" armée(s) sur " + j.getTerritoire().size() +" Terrritoires" );
		StdDraw.show();
	}
	
	//MODIFIER MAJ() POUR QUE LORSQUE L'UTILISATEUR CLIQUE SUR UN PAYS, LES PAYS VOISINS SE COLORENT EN NOIR; SI LE JOUEUR
	//CLIQUE JUSTE ENSUITE SUR UN DE CES PAYS NOIRS, IL LANCE UNE ATTAQUE (SI IL CLIQUE AILLEURS, LES VOISINS REPRENNENT
	//LEUR COULEUR ET ON REVIENT COMME AVANT). C'EST CLAIR :) ?
	
	public void Maj(Partie game, Joueur j, int d) {
		ArrayList<Integer> quickfix = game.selectionPays(j,this.territoire);
		if(d == 0) {
			DebutDuTour(game,j);
			Start(j);
			while(j.getArmyAvailable().size()>0) {
				for(int n =  j.getTerritoire().size() - 1; n>=0 ; n--) {
					int[] territoire = j.getTerritoire().get(n).getPos();
					if(territoire[0]-10<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoire[0]+10 && territoire[1]-10<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoire[1]+10) {
						if(StdDraw.isMousePressed()) {
							j.getTerritoire().get(n).addArmy(j.getArmyAvailable().get(j.getArmyAvailable().size()-1)); //Ajoute l'unite sur le territoire
							j.getArmyAvailable().get(j.getArmyAvailable().size()-1).setMvtRestants(3);
							j.getArmyAvailable().get(j.getArmyAvailable().size()-1).setOnField(true); //Le met sur le terrain
							if(j.getArmyAvailable().size()>0) {
								Start(j);   //MAJ la map avec +1 armée sur territoire
							} else {
								reset = true;
							}
							while(StdDraw.isMousePressed()) {};
						}
					}
				}
				
			}
		}
		if(quickfix.get(0) != 0) {
			reset = true;
			while(!StdDraw.isMousePressed()) {
					for(int i = 0;i<this.territoire.size();i++) {
						Territoire t = territoire.get(i);
						int[] territoire = t.getPos();
						if(territoire[0]-10<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoire[0]+10 && territoire[1]-10<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoire[1]+10 ) {
							StdDraw.clear();
							if(StdDraw.mouseX()>=1233 && StdDraw.mouseY()<=1620 && StdDraw.mouseY()>=84 && StdDraw.mouseY()<=125) {
								StdDraw.picture(largeur/2, hauteur/2, carteS);
								
							} else {
								StdDraw.picture(largeur/2, hauteur/2, carte);
							}
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
							StdDraw.setPenColor(j.getCouleur());
							StdDraw.filledCircle(1230, 560, 10);
							StdDraw.setPenColor(Color.BLACK);
							StdDraw.textLeft(1245, 560, ""+j.getNom() + " Objectif : ");
							StdDraw.setFont(minuscule);
							if(j.getMission().length()>31) {  //Pour éviter que la mission sorte du canvas :o
								String[] morceau = {j.getMission().substring(0, 31),j.getMission().substring(31)};
								StdDraw.textLeft(1220, 530, " " + morceau[0]);
								StdDraw.textLeft(1220, 510, " " + morceau[1]);
							} else {
								StdDraw.textLeft(1220, 530, " " + j.getMission());
							}
							StdDraw.setFont(font);
							int x = j.getArmy().size() - j.getArmyAvailable().size();
							StdDraw.textLeft(1205, 420,  " "+ x +" armée(s) sur " + j.getTerritoire().size() +" Terrritoires" );
							
							//Info sur la région survole
							StdDraw.setPenColor(Color.BLACK);
							StdDraw.textLeft(1205, 270, t.getNom() +"à " + t.getJoueur().getNom());
							StdDraw.textLeft(1205, 240, " En" + t.getRegion());
							
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
							StdDraw.textLeft(1205, 210, " Avec " + nsoldat +" Soldat" );
							StdDraw.textLeft(1205, 180, " Avec " + ncavalier +" Cavalier" );
							StdDraw.textLeft(1205, 150, " Avec " + ncanon +" Canon" );
							StdDraw.show();
						}
					}
				}
			int allie=0;
			Territoire attaquant = territoire.get(quickfix.get(0));
			Territoire defenseur;
			for(int k = quickfix.size()-1; k>0;k--) {
				if(StdDraw.mouseX()>= this.territoire.get(quickfix.get(k)).getX()-10 && StdDraw.mouseX()<= this.territoire.get(quickfix.get(k)).getX()+10 && StdDraw.mouseY()>= this.territoire.get(quickfix.get(k)).getY()-10 && StdDraw.mouseY() <= this.territoire.get(quickfix.get(k)).getY()+10) {
					defenseur = this.territoire.get(quickfix.get(k));
					for(int l = j.getTerritoire().size()-1;l>=0;l--) {
						if(j.getTerritoire().get(l) == this.territoire.get(quickfix.get(k))) {
							allie++;
							break;
						}
					}
					
					ArrayList<Unite> type = new ArrayList<>(); //Type == Liste des armees qui attaquent ou que tu dep
					if(allie!=0) {
						System.out.println("PAYS DEPART : " + attaquant.getNom());
						System.out.println("PAYS ALLIE DESTINATION : " + defenseur.getNom());
						System.out.println("DEFENSE"); // ICI DEPLACEMENT ALLIÉ
						//game.getUnitesADeplacer(j, attaquant, defenseur);
						
						type = choixDeplacement(attaquant);
						
						//Recupere type HERE IF NOT EMPTY POUR AVOIR LES UNITES QUE LE MEC DEPLACE 
						
					}else {
						System.out.println("PAYS ATTAQUANT : " + attaquant.getNom());
						System.out.println("PAYS DEFENSEUR : " + defenseur.getNom());
						System.out.println("ATTAQUE"); // ICI ATTAQUE
						//game.getBattaillonAttaquant(j, attaquant, defenseur);
						
						int n =  choixNarmee(attaquant);
						if(n!=-1) { //Le mec appuie pas sur retour
							type = choixTypeArmee(attaquant, n);
							while(type.isEmpty() && n!=-1) { //Sors de ça si le mec clique deux fois sur Retour ou alors choisis nombre et type armeess
								n = choixNarmee(attaquant);
								type = choixTypeArmee(attaquant, n);
							}
						} else {
							break;
						} 
						
						// HEEEEEEEEEEEEEEEEEEERE RECUPERE TYPE IF NOT EMPTY
						

					}
				}
			}
			} else {
				for(int i = 0;i<this.territoire.size();i++) {
					Territoire t = this.territoire.get(i);
					StdDraw.clear();
					if(StdDraw.mouseX()>=1233 && StdDraw.mouseY()<=1620 && StdDraw.mouseY()>=84 && StdDraw.mouseY()<=125) {
						StdDraw.picture(largeur/2, hauteur/2, carteS);
					} else {
						StdDraw.picture(largeur/2, hauteur/2, carte);
					}
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
				//Affichage du jouer pendant son tour ainsi que sa mission
				StdDraw.setFont(font);
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(1230, 560, 10);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.textLeft(1245, 560, ""+j.getNom() + " Objectif : ");
				StdDraw.setFont(minuscule);
				if(j.getMission().length()>31) {  //Pour éviter que la mission sorte du canvas :o
					String[] morceau = {j.getMission().substring(0, 31),j.getMission().substring(31)};
					StdDraw.textLeft(1220, 530, " " + morceau[0]);
					StdDraw.textLeft(1220, 510, " " + morceau[1]);
				} else {
					StdDraw.textLeft(1220, 530, " " + j.getMission());
				}
				//Affichage de l'action à faire ce tour
				int x = j.getArmy().size() - j.getArmyAvailable().size();
				StdDraw.textLeft(1205, 420,  " "+ x +" armée(s) sur " + j.getTerritoire().size() +" Terrritoires" );
			reset = false;
		}
		StdDraw.show();	
	}
	
	public ArrayList<Territoire> getTerritoire() {
		return territoire;
	}

	public void setRegion(ArrayList<Territoire> territoire) {
		this.territoire = territoire;
	}

	public int choixNarmee(Territoire t) {
		int condition = 0;
		boolean uneUniteRestanteChoixUN=false;
		boolean uneUniteRestanteChoixDEUX=false;
		boolean uneUniteRestanteChoixTROIS=false;
		for(int i = t.getArmy().size()-1;i>=0;i--) { //Vérifie la taille des armées avec plus de 1 de mouvement
			if(t.getArmy().get(i).getMvtRestants()>=1) {
				condition++;
			}
		}
		
		if(t.getArmy().size()-1>0) {
			uneUniteRestanteChoixUN=true;
		}
		if(t.getArmy().size()-2>0) {
			uneUniteRestanteChoixDEUX=true;
		}
		if(t.getArmy().size()-3>0) {
			uneUniteRestanteChoixTROIS=true;
		}
		
		while(true) {
			if(StdDraw.mouseX()>=682 && StdDraw.mouseX()<= 941 && StdDraw.mouseY()>=13 && StdDraw.mouseY()<=55) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixnRs);
				StdDraw.text(526, 370, "1");
				StdDraw.text(765, 370, "2");
				StdDraw.text(1007, 370, "3");
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					return -1;
				}
			} else if(StdDraw.mouseX()>=520 && StdDraw.mouseY()<=394 && StdDraw.mouseX()<=535 && StdDraw.mouseY()>=354 && uneUniteRestanteChoixUN){
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixn);
				StdDraw.setPenColor(Color.RED);
				StdDraw.text(526, 370, "1");
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.text(765, 370, "2");
				StdDraw.text(1007, 370, "3");
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					return 1;
				}
			} else if(StdDraw.mouseX()>=747 && StdDraw.mouseX()<=785 && StdDraw.mouseY()<=391 && StdDraw.mouseY()>=347 && condition>1 && uneUniteRestanteChoixDEUX) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixn);
				StdDraw.text(526, 370, "1");
				StdDraw.setPenColor(Color.RED);
				StdDraw.text(765, 370, "2");
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.text(1007, 370, "3");
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					return 2;
				}
			} else if(StdDraw.mouseX()>=982 && StdDraw.mouseX()<=1029 && StdDraw.mouseY()<=392 && StdDraw.mouseY()>=350 && condition>2 && uneUniteRestanteChoixTROIS) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixn);
				StdDraw.text(526, 370, "1");
				StdDraw.text(765, 370, "2");
				StdDraw.setPenColor(Color.RED);
				StdDraw.text(1007, 370, "3");
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					return 3;
				}
			} else {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixn);
				StdDraw.setFont(majuscule);
				StdDraw.text(526, 370, "1");
				StdDraw.text(765, 370, "2");
				StdDraw.text(1007, 370, "3");
				StdDraw.show();
			}
		}
	}
	
	public ArrayList<Unite> choixDeplacement(Territoire t){ //Utilisateur choisis les unites qu'il veut deplacer
	boolean choix = false;
	ArrayList<Unite> soldat = new ArrayList<>();
	ArrayList<Unite> cavalier = new ArrayList<>();
	ArrayList<Unite> obelix = new ArrayList<>();
	ArrayList<Unite> total = new ArrayList<>();
	for(int k = t.getArmy().size()-1;k>=0;k--){  //Compte les types d'armées avec >=1 de mvt et récupère leurs ID
		if(t.getArmy().get(k).getType() == "Soldat") {
			if(t.getArmy().get(k).getMvtRestants()>=1) {
				soldat.add(t.getArmy().get(k));
			}
		} else if(t.getArmy().get(k).getType() == "Canon") {
			if(t.getArmy().get(k).getMvtRestants()>=1) {
				cavalier.add(t.getArmy().get(k));
			}
		} else {
			if(t.getArmy().get(k).getMvtRestants()>=1) {
				obelix.add(t.getArmy().get(k));
			}
		}
	}
	while(!choix) {
		if(StdDraw.mouseX()>=1300 && StdDraw.mouseX()<=1636 && StdDraw.mouseY()<=584 && StdDraw.mouseY()>=540) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, depD);
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				choix = true;
				while(StdDraw.isMousePressed()) {};
			}
		} else if(StdDraw.mouseX()<=1635 && StdDraw.mouseX()>=1374 && StdDraw.mouseY()<=659 && StdDraw.mouseY()>=617) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, depR);
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				choix = true;
				while(StdDraw.isMousePressed()) {}; 
			}
		} else if(StdDraw.mouseX()>=104 && StdDraw.mouseX()<=349 && StdDraw.mouseY()>=24 && StdDraw.mouseY()<=462 && soldat.size()>0) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, choixCArmeeSs);
			StdDraw.setFont(majuscule);
			StdDraw.textLeft(1302, 557, "deplacer");
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				choix = true;
				while(StdDraw.isMousePressed()) {};
				total.add(soldat.get(soldat.size()-1));
				soldat.remove(soldat.size()-1);
			}
		} else if(StdDraw.mouseX()>=583 && StdDraw.mouseX()<=977 && StdDraw.mouseY()>=7 && StdDraw.mouseY()<=440 && cavalier.size()>0){
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, choixCArmeeCs);
			StdDraw.setFont(majuscule);
			StdDraw.textLeft(1302, 557, "deplacer");
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				choix = true;
				while(StdDraw.isMousePressed()) {};
				total.add(cavalier.get(cavalier.size()-1));
				cavalier.remove(cavalier.size()-1);
			}
		} else if(StdDraw.mouseX()>=1290 && StdDraw.mouseX()<=1547 && StdDraw.mouseY()>=15 && StdDraw.mouseY()<=419 && obelix.size()>0) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, choixArmeeOs);
			StdDraw.setFont(majuscule);
			StdDraw.textLeft(1302, 557, "deplacer");
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				choix = true;
				while(StdDraw.isMousePressed()) {};
				total.add(obelix.get(obelix.size()-1));
				obelix.remove(obelix.size()-1);
			}
		} else {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, dep);
			StdDraw.show();
		}
	}
	return total;
	}
	
	public void DebutDuTour(Partie g, Joueur j){
		int renforts = g.repartitionArmees(j);
		while(StdDraw.isMousePressed()) {};
		while(renforts!=0) {
			if(StdDraw.mouseX()>=104 && StdDraw.mouseX()<=349 && StdDraw.mouseY()>=24 && StdDraw.mouseY()<=462) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, debutS);
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(600, 595, 25);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setFont(majuscule);
				StdDraw.textLeft(635, 595, ""+j.getNom());
				StdDraw.textLeft(1230, 559, "Renfort " + renforts);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					Unite UNITE = new Unite(false,"Soldat",ID,0,1,2,1);
					ID++;
					j.addUnite(UNITE);
					renforts--;
					while(StdDraw.isMousePressed()) {};
				}
			} else if(StdDraw.mouseX()>=583 && StdDraw.mouseX()<=977 && StdDraw.mouseY()>=7 && StdDraw.mouseY()<=440 && renforts>=3) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, debutC);
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(600, 595, 25);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setFont(majuscule);
				StdDraw.textLeft(635, 595, ""+j.getNom());
				StdDraw.textLeft(1230, 559, "Renfort " + renforts);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					Unite UNITE = new Unite(false,"Cavalier",ID,0,3,1,3);
					ID++;
					j.addUnite(UNITE);
					renforts = renforts - 3;
					while(StdDraw.isMousePressed()) {};
				}
			} else if(StdDraw.mouseX()>=1290 && StdDraw.mouseX()<=1547 && StdDraw.mouseY()>=15 && StdDraw.mouseY()<=419 && renforts>=7) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, debutO);
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(600, 595, 25);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setFont(majuscule);
				StdDraw.textLeft(635, 595, ""+j.getNom());
				StdDraw.textLeft(1230, 559, "Renfort " + renforts);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					Unite UNITE = new Unite(false,"Canon",ID,0,2,3,7);
					ID++;
					j.addUnite(UNITE);
					renforts = renforts - 7;
					while(StdDraw.isMousePressed()) {};
				}
			} else {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, debut);
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(600, 595, 25);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setFont(majuscule);
				StdDraw.textLeft(635, 595, ""+j.getNom());
				StdDraw.textLeft(1230, 559, "Renfort " + renforts);
				StdDraw.show();
			}
		}
	}
	
	public ArrayList<Unite> choixTypeArmee(Territoire t, int n) {
		ArrayList<Unite> soldat = new ArrayList<>();
		ArrayList<Unite> cavalier = new ArrayList<>();
		ArrayList<Unite> obelix = new ArrayList<>();
		ArrayList<Unite> total = new ArrayList<>();
		for(int k = t.getArmy().size()-1;k>=0;k--){  //Compte les types d'armées avec >=1 de mvt et les recup dans leur arraylist correspondante
			if(t.getArmy().get(k).getType().equals("Soldat")) {
				if(t.getArmy().get(k).getMvtRestants()>=1) {
					soldat.add(t.getArmy().get(k));
				}
			} else if(t.getArmy().get(k).getType().equals("Cavalier")) {
				if(t.getArmy().get(k).getMvtRestants()>=1) {
					cavalier.add(t.getArmy().get(k));
				}
			} else {
				if(t.getArmy().get(k).getMvtRestants()>=1) {
					obelix.add(t.getArmy().get(k));
				}
			}
		}
		while(total.size()<n) {   //Affichage graphique
			if(StdDraw.mouseX()>=104 && StdDraw.mouseX()<=349 && StdDraw.mouseY()>=24 && StdDraw.mouseY()<=462 && soldat.size()>0) {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixCArmeeSs);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					total.add(soldat.get(soldat.size()-1));
					soldat.remove(soldat.size()-1);
					while(StdDraw.isMousePressed()) {};
				}
			} else if(StdDraw.mouseX()>=583 && StdDraw.mouseX()<=977 && StdDraw.mouseY()>=7 && StdDraw.mouseY()<=440 && cavalier.size()>0){
				StdDraw.clear();
				StdDraw.picture(largeur/2,  hauteur/2, choixCArmeeCs);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					total.add(cavalier.get(cavalier.size()-1));
					cavalier.remove(cavalier.size()-1);
					while(StdDraw.isMousePressed()) {};
				}
			} else if(StdDraw.mouseX()>=1290 && StdDraw.mouseX()<=1547 && StdDraw.mouseY()>=15 && StdDraw.mouseY()<=419 && obelix.size()>0){
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixArmeeOs);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					total.add(obelix.get(obelix.size()-1));
					obelix.remove(obelix.size()-1);
					while(StdDraw.isMousePressed()) {};
				}
			} else if(StdDraw.mouseX()<=1635 && StdDraw.mouseX()>=1374 && StdDraw.mouseY()<=659 && StdDraw.mouseY()>=617){
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixCArmeeRs);
				StdDraw.show();
				if(StdDraw.isMousePressed()) {
					total.clear();
					while(StdDraw.isMousePressed()) {};
					return total;
				}
			} else {
				StdDraw.clear();
				StdDraw.picture(largeur/2, hauteur/2, choixCArmee);
				StdDraw.show();
			}
		}
		return total;
	}
	
	public double[] Souris() {
		double pos[] = {StdDraw.mouseX(),StdDraw.mouseY()};
		return pos;
	}
	
	public boolean click() {
		return StdDraw.isMousePressed();
	}
	
}
