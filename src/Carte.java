import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.princeton.cs.introcs.StdDraw;

public class Carte {

	private final int largeur = 1643;
	private final int hauteur = 675;
	private final String carte = new String("Carte/risk.jpg");
	private final String carteS = new String("Carte/riskF.jpg");
	private final String choixn = new String("Choix/menuNArmee.jpg");
	private final String choixnRs = new String("Choix/menuNArmeeS.jpg");
	private final String choixCArmee = new String("Choix/menuCArmee.jpg");
	private final String choixCArmeeRs = new String("Choix/lebug.jpg");
	private final String choixCArmeeSs = new String("Choix/menuCArmeeSS.jpg");
	private final String debut = new String("Choix/debut.jpg");
	private final String choixCArmeeCs = new String("Choix/menuCArmeeCS.jpg");
	private final String dep = new String("Choix/Deplacement.jpg");
	private final String depR = new String("Choix/DeplacementR.jpg");
	private final String debutS = new String("Choix/debutS.jpg");
	private final String debutC = new String("Choix/debutC.jpg");
	private final String debutO = new String("Choix/debutO.jpg");
	private final String depD = new String("Choix/DeplacementD.jpg");
	private final String choixArmeeOs = new String("Choix/menuCArmeeOBES.jpg");
	private static Font font = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 20);
	private static Font majuscule = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 55);
	private static Font minuscule = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 17);
	private int m = 3;
	private JOptionPane message = new JOptionPane();
	protected ArrayList<Territoire> territoire;
	private boolean reset = true;
	private ArrayList<String> nterritoire;
	private ArrayList<String> nregion;
	private int ID = 100;
	private int[][] listeregion;
	private ArrayList<int[]> listevoisins;


	public Carte(int[][] r, ArrayList<int[]> v, ArrayList<String> t, ArrayList<String> re) {
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
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.circle(territoire.get(h).getX(), territoire.get(h).getY(), 15);
		}
		StdDraw.setFont(font);
		StdDraw.setPenColor(j.getCouleur());
		StdDraw.filledCircle(1230, 560, 10);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.textLeft(1245, 560, ""+j.getNom() + " Objectif : ");
		StdDraw.setFont(minuscule);
		if(j.getMission().length()>31) {  //Pour éviter que la mission ne sorte de l'écran/canvas
			String[] morceau = {j.getMission().substring(0, 31),j.getMission().substring(31)};
			StdDraw.textLeft(1220, 530, " " + morceau[0]);
			StdDraw.textLeft(1220, 510, " " + morceau[1]);
		} else {
			StdDraw.textLeft(1220, 530, " " + j.getMission());
		}
		StdDraw.setFont(font);
		StdDraw.textLeft(1205, 450, " Placez votre "+ j.getArmyAvailable().get(j.getArmyAvailable().size()-1).getType() );
		int x = j.getArmy().size() - j.getArmyAvailable().size();
		StdDraw.textLeft(1205, 420,  " "+ x +" armée(s) sur " + j.getTerritoire().size() +" territoires" );
		StdDraw.show();
	}

	public void Maj(Partie game, Joueur j, int d) {
		ArrayList<Integer> quickfix = game.selectionPays(j,this.territoire);	//Pays sélectionné
		if(d == 0) {	
			DebutDuTour(game,j);
			Start(j);
			while(j.getArmyAvailable().size()>0) {
				for(int n =  j.getTerritoire().size() - 1; n>=0 ; n--) {
					int[] territoire = j.getTerritoire().get(n).getPos();
					if(territoire[0]-20<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoire[0]+20 && territoire[1]-20<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoire[1]+20) {
						if(StdDraw.isMousePressed()) {
							j.getTerritoire().get(n).addArmy(j.getArmyAvailable().get(j.getArmyAvailable().size()-1)); //Ajoute l'unite sur le territoire
							if(j.getArmyAvailable().get(j.getArmyAvailable().size()-1).getType()=="Soldat") {
								j.getArmyAvailable().get(j.getArmyAvailable().size()-1).setMvtRestants(3);
							}else if(j.getArmyAvailable().get(j.getArmyAvailable().size()-1).getType()=="Cavalier") {
								j.getArmyAvailable().get(j.getArmyAvailable().size()-1).setMvtRestants(2);
							}else {
								j.getArmyAvailable().get(j.getArmyAvailable().size()-1).setMvtRestants(1);
							}
							j.getArmyAvailable().get(j.getArmyAvailable().size()-1).setOnField(true); //La met sur le terrain
							if(j.getArmyAvailable().size()>0) {
								Start(j);   //MAJ la map avec +1 armée (soldat) sur territoire
							} else {
								reset = true;
							}
							while(StdDraw.isMousePressed()) {};
						}
					}
				}

			}
		}
		
		if(quickfix.get(0) != 99) {
			reset = true;
			while(!StdDraw.isMousePressed()) {
					for(int i = 0;i<this.territoire.size();i++) {	//Si le joueur clique sur un pays possédé
						Territoire t = territoire.get(i);
						int[] territoire = t.getPos();
						if(territoire[0]-20<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoire[0]+20 && territoire[1]-20<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoire[1]+20 ) {
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
								StdDraw.setPenColor(Color.BLACK);
								StdDraw.circle(listeregion[k][0], listeregion[k][1], 15);
							}
							for(int n = quickfix.size()-1;n>0;n--) {
								if(this.territoire.get(quickfix.get(n)).getJoueur() == j) {		//Coloration des voisins ennemis en BLACK
									StdDraw.setPenColor(Color.DARK_GRAY);						//Coloration des voisins alliés en DARK_GRAY		
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
							if(j.getMission().length()>31) {  //Pour éviter que la mission ne sorte de l'écran/canvas
								String[] morceau = {j.getMission().substring(0, 31),j.getMission().substring(31)};
								StdDraw.textLeft(1220, 530, " " + morceau[0]);
								StdDraw.textLeft(1220, 510, " " + morceau[1]);
							} else {
								StdDraw.textLeft(1220, 530, " " + j.getMission());
							}
							StdDraw.setFont(font);
							int x = j.getArmy().size() - j.getArmyAvailable().size();
							StdDraw.textLeft(1205, 420,  " "+ x +" armée(s) sur " + j.getTerritoire().size() +" territoires" );

							//Info sur la région survolée
							StdDraw.setPenColor(Color.BLACK);
							StdDraw.textLeft(1205, 270, t.getNom() + "à " + t.getJoueur().getNom());
							StdDraw.textLeft(1205, 240, " en" + t.getRegion());

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
							StdDraw.textLeft(1205, 210, " " + nsoldat +" soldat(s)" );
							StdDraw.textLeft(1205, 180, " " + ncavalier +" cavalier(s)" );
							StdDraw.textLeft(1205, 150, " " + ncanon +" canon(s)" );
							StdDraw.show();
						}
					}
				}
			
			int allie=0;
			Territoire attaquant = territoire.get(quickfix.get(0));
			Territoire defenseur;
			for(int k = quickfix.size()-1; k>0;k--) {
				if(StdDraw.mouseX()>= this.territoire.get(quickfix.get(k)).getX()-20 && StdDraw.mouseX()<= this.territoire.get(quickfix.get(k)).getX()+20 && StdDraw.mouseY()>= this.territoire.get(quickfix.get(k)).getY()-20 && StdDraw.mouseY() <= this.territoire.get(quickfix.get(k)).getY()+20) {
					defenseur = this.territoire.get(quickfix.get(k));
					for(int l = j.getTerritoire().size()-1;l>=0;l--) {
						if(j.getTerritoire().get(l) == this.territoire.get(quickfix.get(k))) {
							allie++;
							break;
						}
					}
					System.out.println("#######################################################################");
					System.out.println();
					ArrayList<Unite> type = new ArrayList<>();
					if(allie!=0) {
						System.out.println("Pays de départ : " + attaquant.getNom());
						System.out.println("Pays allié de destination : " + defenseur.getNom());
						type = choixDeplacement(attaquant); //BATAILLON A DEPLACER OU A FAIRE ATTAQUER
						if(!type.isEmpty()) { 
							 game.deplacementTerritoireAllie(j, type, attaquant, defenseur); // ICI DEPLACEMENT ALLIÉ
						 }
					}else {
						System.out.println("Pays attaquant : " + attaquant.getNom());
						System.out.println("Pays défenseur : " + defenseur.getNom());
						int n =  choixNarmee(attaquant);
						if(n!=-1) { //Le joueur n'appuie pas sur retour
							type = choixTypeArmee(attaquant, n);
							while(type.isEmpty() && n!=-1) { //Sors d'ici si le joueur clique deux fois sur Retour ou alors choisit nombre et type armees
								n = choixNarmee(attaquant);
								type = choixTypeArmee(attaquant, n);
							}
						} else {
							break;
						}
						 if(!type.isEmpty()) {
						  m = game.attaqueEnnemi(j,type, attaquant, defenseur);  // ICI ATTAQUE SUR TERRITOIRE ENNEMI
						  reset = true;
						 }
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
						StdDraw.setPenColor(Color.BLACK);
						StdDraw.circle(listeregion[k][0], listeregion[k][1], 15);
					}
				}
				//Affichage du joueur, de sa couleur et de sa mission pendant qu'il joue
				StdDraw.setFont(font);
				StdDraw.setPenColor(j.getCouleur());
				StdDraw.filledCircle(1230, 560, 10);
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.textLeft(1245, 560, ""+j.getNom() + " Objectif : ");
				StdDraw.setFont(minuscule);
				if(j.getMission().length()>31) {  //Pour éviter que la mission ne sorte de l'écran/canvas
					String[] morceau = {j.getMission().substring(0, 31),j.getMission().substring(31)};
					StdDraw.textLeft(1220, 530, " " + morceau[0]);
					StdDraw.textLeft(1220, 510, " " + morceau[1]);
				} else {
					StdDraw.textLeft(1220, 530, " " + j.getMission());
				}
				//Affichage de l'action à faire ce tour
				int x = j.getArmy().size() - j.getArmyAvailable().size();
				StdDraw.textLeft(1205, 420,  " "+ x +" armée(s) sur " + j.getTerritoire().size() +" territoires" );
				if(m==0) {
					StdDraw.show();
					message.showMessageDialog(null, "Votre attaque a été repoussée !");
					m = 3;
				} else if(m==1) {
					StdDraw.show();
					message.showMessageDialog(null, "Victoire ! Territoire ennemi conquis !");
					m = 3;
				} else if(m==2) {
					StdDraw.show();
					message.showMessageDialog(null, "Vous avez gagné la bataille, mais pas la guerre ! Vainquez les unités restantes !");
					m = 3;
				}
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
		for(int i = t.getArmy().size()-1;i>=0;i--) { //Vérifie la taille des armées avec plus de 1 de mouvements restants
			if(t.getArmy().get(i).getMvtRestants()>=1) {
				condition++;
			}
		}

		while(StdDraw.isMousePressed()) {};
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
	for(int k = t.getArmy().size()-1;k>=0;k--){  //Compte les types d'armées avec >=1 de mvt et récupère leur ID
		if(t.getArmy().get(k).getType() == "Soldat") {
			if(t.getArmy().get(k).getMvtRestants()>=1) {
				soldat.add(t.getArmy().get(k));
			}
		} else if(t.getArmy().get(k).getType() == "Cavalier") {
			if(t.getArmy().get(k).getMvtRestants()>=1) {
				cavalier.add(t.getArmy().get(k));
			}
		} else if(t.getArmy().get(k).getType() == "Canon"){
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
				total.clear();
				choix = true;
				while(StdDraw.isMousePressed()) {};
			}
		} else if(StdDraw.mouseX()>=104 && StdDraw.mouseX()<=349 && StdDraw.mouseY()>=24 && StdDraw.mouseY()<=462 && soldat.size()>0 && t.getArmy().size()-total.size()>1) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, choixCArmeeSs);
			StdDraw.setFont(majuscule);
			StdDraw.textLeft(1302, 557, "deplacer");
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				while(StdDraw.isMousePressed()) {};
				total.add(soldat.get(soldat.size()-1));
				soldat.remove(soldat.size()-1);
			}
		} else if(StdDraw.mouseX()>=583 && StdDraw.mouseX()<=977 && StdDraw.mouseY()>=7 && StdDraw.mouseY()<=440 && cavalier.size()>0 && t.getArmy().size()-total.size()>1){
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, choixCArmeeCs);
			StdDraw.setFont(majuscule);
			StdDraw.textLeft(1302, 557, "deplacer");
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
				while(StdDraw.isMousePressed()) {};
				total.add(cavalier.get(cavalier.size()-1));
				cavalier.remove(cavalier.size()-1);
			}
		} else if(StdDraw.mouseX()>=1290 && StdDraw.mouseX()<=1547 && StdDraw.mouseY()>=15 && StdDraw.mouseY()<=419 && obelix.size()>0 && t.getArmy().size()-total.size()>1) {
			StdDraw.clear();
			StdDraw.picture(largeur/2, hauteur/2, choixArmeeOs);
			StdDraw.setFont(majuscule);
			StdDraw.textLeft(1302, 557, "deplacer");
			StdDraw.show();
			if(StdDraw.isMousePressed()) {
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
		while(StdDraw.isMousePressed()) {};
		ArrayList<Unite> soldat = new ArrayList<>();
		ArrayList<Unite> cavalier = new ArrayList<>();
		ArrayList<Unite> obelix = new ArrayList<>();
		ArrayList<Unite> total = new ArrayList<>();
		for(int k = t.getArmy().size()-1;k>=0;k--){  //Compte les types d'armées avec >=1 de mvt et les récupère dans leur arraylist correspondante
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
