import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

public class Partie {

	boolean gameOver;
	Joueur joueurActuel;
	
	public Partie(Joueur joueurActuel, boolean gameOver) {
		super();
		this.joueurActuel = joueurActuel;
		this.gameOver = gameOver;
	}
	
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}

	public void setJoueurActuel(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void deroulementPrincipal(Joueur player,int count){
		if(count==0) {
			System.out.println("RECEPTION DE RENFORTS");
			repartitionArmees(player);
		}else if(player.getArmyAvailable().size()!=0){
			System.out.println("PLACEMENT ARMEES");
			placerArmees(player);
		}
	}
	
	//CAS D'UNE ATTAQUE
	public void getBattaillonAttaquant(Joueur player,Territoire territoireDepart, Territoire territoireDestination) {
		//ArrayList<Unite> bataillonAttaquant = //OBTENIR CHOIX JOUEUR;
		//mêmes conditions à vérifier que dans setArmyAndPower();
		attaqueEnnemi(player,bataillonAttaquant,territoireDepart,territoireDestination);
	}
	
	//CAS D'UN DEPLACEMENT ALLIE
	public void getUnitesADeplacer(Joueur player,Territoire territoireDepart, Territoire territoireDestination) {
		//ArrayList<Unite> unitesADeplacer = //OBTENIR CHOIX JOUEUR;
		//mêmes conditions à vérifier que dans setArmyAndPower();
		deplacementTerritoireAllie(player,unitesADeplacer,territoireDepart,territoireDestination);
	}
	
	public void attaqueEnnemi(Joueur player, ArrayList<Unite> bataillonAttaquant, Territoire territoireDepart, Territoire territoireDestination) {
		
		ArrayList<Unite> bataillonDefendant = new ArrayList<Unite>();
		ArrayList<Unite> armeesDefenseur = territoireDestination.getArmy();
		
		bataillonDefendant = setArmyAndPower(armeesDefenseur,false); //choisit automatiquement les unités pour la défense
		bataillonAttaquant = setArmyAndPower(bataillonAttaquant,true);
		
		Joueur playerDef = territoireDestination.getJoueur();
		Joueur playerAtt = player;
		
		affrontement(bataillonAttaquant,bataillonDefendant,playerAtt,playerDef,territoireDepart,territoireDestination);
		
	}
	
	public void deplacementTerritoireAllie(Joueur player, ArrayList<Unite> unitesADeplacer, Territoire territoireDepart,Territoire territoireDestination){
		for(Unite unit : unitesADeplacer) {
			territoireDepart.deleteArmy(unit);
			for(int o=1;o<=player.getArmy().size();o++) {
				player.getArmy().get(o).setID(o);	//Devrait résoudre les problèmes d'ID
			}
			territoireDestination.addArmy(unit);
			unit.setOnField(true); //AU CAS OU C'EST OU C'ETAIT PAS FAIT, PRECAUTION
			unit.setMvtRestants(unit.getMvtRestants()-1);
			for(int p=1;p<=player.getArmy().size();p++) {
				territoireDestination.getJoueur().getArmy().get(p).setID(p); //Devrait résoudre les problèmes d'ID
			}
		
		}
	}
	
	public void placerArmees(Joueur player) {
		
		//ALED	
		
		//NOMBRE D'UNITES A DEPLACER
		for(Unite unit : player.getArmy()) {
			if(unit.getType()=="Soldat") {
				unit.setMvtRestants(mvtsParTourSoldat);
			}else if(unit.getType()=="Cavalier") {
				unit.setMvtRestants(mvtsParTourCavalier);
			}else if(unit.getType()=="Canon") {
				unit.setMvtRestants(mvtsParTourCanon);
			}else {
				System.out.println("PROBLEME ENCORE");
			}
			
		}
	}
	
	public void repartitionArmees(Joueur player) {
		
		int firstCondition = player.getTerritoire().size();
		firstCondition = (int) Math.floor(firstCondition/3);
		
		int secondCondition = regionComplete(player);
		
		int thirdCondition = player.getTerritoiresConquis();
		
		int renfortsJoueur = Math.max(firstCondition + secondCondition + thirdCondition,2);
		
		while(renfortsJoueur!=0) {
			System.out.println("RENFORTS = " + renfortsJoueur);
			renfortsJoueur = askRenfort(player,renfortsJoueur);
		}
	}
	
	public int askRenfort(Joueur player,int renfortsJoueur) {
		int IDcanon=0;
		int IDcavalier=0;
		int IDsoldat=0;
		ArrayList<Unite> unites = new ArrayList<Unite>();
		unites = player.getArmy();
		for(Unite unite : unites) {
			if(unite.getType()=="Canon") {
				IDcanon++;
			}else if(unite.getType()=="Cavalier") {
				IDcavalier++;
			}else if(unite.getType()=="Soldat") {
				IDsoldat++;
			}else {
				System.out.println("Erroooor");                                                                                                                            
			}
		}
		System.out.println(IDcanon + " et " + IDcavalier + " et " + IDsoldat);
		if(renfortsJoueur >= 11 ) {
			//AFFICHER IMAGE CANON ROUGE OU CAVALIER ROUGE OU SOLDAT ROUGE
			
			//IF CHOIX = CANON
			Unite newCanon = new Unite(false,"Canon",IDcanon+1,0,2,3,7); 
			player.addUnite(newCanon);
			newCanon.setMvtRestants(1);
			renfortsJoueur = renfortsJoueur - 7;
			
			//IF CHOIX = CAVALIER
			Unite newCavalier = new Unite(false,"Cavalier",IDcavalier+1,0,3,1,3);
			player.addUnite(newCavalier);
			newCavalier.setMvtRestants(2);
			renfortsJoueur = renfortsJoueur - 3;
			
			//IF CHOIX = SOLDAT
			Unite newSoldat = new Unite(false,"Soldat",IDsoldat+1,0,1,2,1);
			player.addUnite(newSoldat);
			newSoldat.setMvtRestants(3);
			renfortsJoueur = renfortsJoueur - 1;
			
		}else if(renfortsJoueur >= 4) {
			//AFFICHER CAVALIER ROUGE OU SOLDAT ROUGE
			
			//IF CHOIX = CAVALIER
			Unite newCavalier = new Unite(false,"Cavalier",IDcavalier+1,0,3,1,3); //CHOSIR UN DES DEUX CHOIX
			player.addUnite(newCavalier);
			newCavalier.setMvtRestants(2);
			renfortsJoueur = renfortsJoueur - 3;
			
			//IF CHOIX = SOLDAT
			Unite newSoldat = new Unite(false,"Soldat",IDsoldat+1,0,1,2,1);
			player.addUnite(newSoldat);
			newSoldat.setMvtRestants(3);
			renfortsJoueur = renfortsJoueur - 1;
			
		}else if(renfortsJoueur >=1) {
			//AFFICHER SOLDAT ROUGE
			
			//IF CHOIX = SOLDAT
			Unite newSoldat = new Unite(false,"Soldat",IDsoldat+1,0,1,2,1);
			player.addUnite(newSoldat);
			newSoldat.setMvtRestants(3);
			renfortsJoueur = renfortsJoueur - 1;
			
		}else {
			System.out.println("Erreur : renfortsJoueur <= 0 ?");
		}
		return renfortsJoueur;
	}
	
	public int regionComplete(Joueur player) {
		int AdN=0;
		int AdS=0;
		int Asie=0;
		int Europe=0;
		int Oceanie=0;
		int Afrique=0;
		int one=0;
		int two=0;
		int three=0;
		int four=0;
		int five=0;
		int six=0;

		ArrayList<Territoire> territoires = player.getTerritoire();
		for(Territoire pays : territoires) {
			if(pays.getRegion().trim().equals("Amérique du nord")) {
				AdN++;
			}else if(pays.getRegion().trim().equals("Amérique du sud")) {
				AdS++;
			}else if(pays.getRegion().trim().equals("Asie")) {
				Asie++;
			}else if(pays.getRegion().trim().equals("Europe")) {
				Europe++;
			}else if(pays.getRegion().trim().equals("Océanie")) {
				Oceanie++;
			}else if(pays.getRegion().trim().equals("Afrique")){
				Afrique++;
			}else {
				System.out.println("There might be a problem");
			}
		}
		if(AdN==9) {
			one = (int) Math.floor(AdN/2);
		}
		if(AdS==4) {
			two = (int) Math.floor(AdS/2);
		}
		if(Asie==12) {
			three = (int) Math.floor(Asie/2);
		}
		if(Europe==7) {
			four = (int) Math.floor(Europe/2);
		}
		if(Oceanie==4) {
			five = (int) Math.floor(Oceanie/2);
		}
		if(Afrique==6) {
			six = (int) Math.floor(Afrique/2);
		}
		return one + two + three + four + five + six;
	}

	public ArrayList<Integer> selectionPays(Joueur player, ArrayList<Territoire> territoires) {
		System.out.println(player.getNom());
		ArrayList<Integer> deatharray = new ArrayList<>();
		if(StdDraw.isMousePressed()){
			ArrayList<Territoire> territoire = player.getTerritoire();
			for(int i = 0;i<territoire.size();i++) {
				Territoire pays = territoire.get(i);
				int[] territoirep = pays.getPos();
				if(territoirep[0]-15<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoirep[0]+15 && territoirep[1]-15<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoirep[1]+15) {
					int[] listeVoisins = pays.getVoisins();
					System.out.println(pays.getNom());
					System.out.println("TAILLE "+listeVoisins.length);
					/* Fonction qui affiche les données du territoire */
					/* AFFICHER INFOS DE TOUS LES VOISINS ? OU SEULEMENT LE PAYS ATTAQUANT ?*/
					for(int j=0;j<listeVoisins.length;j++) {
						if(territoires.get(listeVoisins[j]).getJoueur() == player) {
							System.out.println(listeVoisins[j]);
							/* 2 lignes qui colorient les cercles des voisins en )noir */
							StdDraw.setPenColor(Color.DARK_GRAY);
							StdDraw.filledCircle(territoires.get(listeVoisins[j]).getPos()[0],territoires.get(listeVoisins[j]).getPos()[1], 15);	
							StdDraw.setPenColor(Color.WHITE);
							StdDraw.setFont();
							StdDraw.text(territoires.get(listeVoisins[j]).getPos()[0]-0.1, territoires.get(listeVoisins[j]).getPos()[1]-0.8,  ""+ territoires.get(listeVoisins[j]).getArmy().size());
							StdDraw.show();							
						} else {
							System.out.println(listeVoisins[j]);
							/* 2 lignes qui colorient les cercles des voisins en )noir */
							StdDraw.setPenColor(Color.BLACK);
							StdDraw.filledCircle(territoires.get(listeVoisins[j]).getPos()[0],territoires.get(listeVoisins[j]).getPos()[1], 15);	
							StdDraw.setPenColor(Color.WHITE);
							StdDraw.setFont();
							StdDraw.text(territoires.get(listeVoisins[j]).getPos()[0]-0.1, territoires.get(listeVoisins[j]).getPos()[1]-0.8,  ""+ territoires.get(listeVoisins[j]).getArmy().size());
							StdDraw.show();
						}
					}
					
					while(StdDraw.isMousePressed()){};
					deatharray.add(pays.getId());
					for(int m = 0;listeVoisins.length>m;m++) {
						deatharray.add(listeVoisins[m]);
					}
					return deatharray;
				}
			}
		}
		deatharray.add(0);
		return deatharray;
	}
					
	public boolean checkWin(Joueur player,Mission mission) {
		ArrayList<Joueur> bonJoueur = new ArrayList<Joueur>();
		bonJoueur.add(player);
		Mission newMission = new Mission(bonJoueur);
		if(newMission.isDone(player) && newMission.mCCheck(player)){
			victory(player);	
			return true;
		}
		return false;
	}
	
	public void victory(Joueur vainqueur) {
		System.out.println("BRAVO ! VICTOIRE ECRASANTE DE " + vainqueur.getNom());
	}
	
	public boolean conditionsAttaque(Territoire territoireDepart,Territoire territoireArrivee) {
		if(territoireDepart.getArmy().size()==1) {
			return false;
		}
		return true;
	}
	
	public static int maxUnitsATT = 3;
	public static int maxUnitsDEF = 2;
	public static int mvtsParTourSoldat = 2;
	public static int mvtsParTourCanon = 1;
	public static int mvtsParTourCavalier = 3;
	
	public ArrayList<Unite> setArmyAndPower(ArrayList<Unite> army, boolean attack) {
		ArrayList<Unite> newArmy = new ArrayList<Unite>();
		int count=0;
		Random rand = new Random();
		int max = maxUnitsDEF;
		if (attack) {
			max = maxUnitsATT;
		}
		for (Unite unit : army) {
			if (unit.getType() == "Soldier") {
				if((attack && unit.getMvtRestants()>0) || !attack) {
					int soldierPower = rand.nextInt((6 - 1) + 1) + 1;
					unit.setPower(soldierPower); 
					count++;
					newArmy.add(unit);
					if (count==max) {
						break;
					}
				}else{
					continue;
				}
			}
		}
		for (Unite unit : army) {
			if (unit.getType() == "Canon") {	
				if((attack && unit.getMvtRestants()>0) || !attack) {
					int canonPower = rand.nextInt((7 - 2) + 1) + 2;
					unit.setPower(canonPower); 
					count++;
					newArmy.add(unit);
					if (count==max) {
						break;
					}
				}else{
					continue;
				}
			}
		}
		for (Unite unit : army) {
			if (unit.getType() == "Cavalier") {		
				if((attack && unit.getMvtRestants()>0) || !attack) {
					int knightPower = rand.nextInt((9 - 4) + 1) + 4;
					unit.setPower(knightPower); 
					count++;
					newArmy.add(unit);
					if (count==max) {
						break;
					}
				}else{
					continue;
				}
			}	
		}
		if(attack && count==0) {
			System.out.println("VOUS N'AVEZ PLUS D'UNITES DEPLACABLES !");
			System.out.println("VOUS NE POUVEZ PLUS ATTAQUER AVEC LES UNITES DE CE TERRITOIRE !");
		}
		return newArmy;
	}
	
	public void affrontement(ArrayList<Unite> armyAtt,ArrayList<Unite> armyDef,Joueur playerAtt,Joueur playerDef,Territoire territoireConquerant,Territoire territoireDefaitiste) {
		while(armyAtt.size()!=0 && armyDef.size()!=0){
			int attMaxPower = strongerUnitPower(armyAtt,"attack");
			Unite attStrongerUnit = strongerUnit(armyAtt,"attack");
			int defMaxPower = strongerUnitPower(armyDef,"defense");
			Unite defStrongerUnit = strongerUnit(armyDef,"defense");
			if (attMaxPower > defMaxPower) {
				armyDef = deleteUnit(defStrongerUnit,armyDef,playerDef);//diminue la taille de armyDef
			}else{
				armyAtt = deleteUnit(attStrongerUnit,armyAtt,playerAtt);//diminue la taille de armyAtt
			}
		}
		if (armyAtt.size()==0) {//DEFENSEUR WIN
			System.out.println("Defaite de l'attaquant ! Vous pouvez retenter d'attaquer avec d'autres unités !");
		}else if(armyDef.size()==0) {//ATTAQUANT WIN
			if(territoireDefaitiste.getArmy().size()==0) {//PLUS AUCUNE ARMEE SUR LE TERRITOIRE DEFENSEUR
				territoireDefaitiste.setJoueur(playerAtt); // + CHANGER L'APPARTENANCE DES UNITES ?
				deplacementTerritoireAllie(playerAtt,armyAtt,territoireConquerant,territoireDefaitiste);
				System.out.println("Victoire de l'attaquant! Vous avez conquérit un territoire!");
			}else {
				System.out.println("Victoire de l'attaquant! Il reste des unités à détruire pour pouvoir conquérir le territoire!");
			}
		}else {
			System.out.println("erreur again :)");
			
		}
	}
	
	public int strongerUnitPower(ArrayList<Unite> army, String type) {
		int max = army.get(0).getPower();
		Unite unityMax = army.get(0);
		for (Unite unit : army) {
			if (unit.getPower() > max){
				max = unit.getPower();
				unityMax = unit;
			}else if(unit.getPower() == max){
				unityMax = getPriority(unit,unityMax,type);
				max = unit.getPower();
			}
		}
		return max;
	}
	
	public Unite strongerUnit(ArrayList<Unite> army, String type) {
		int max = army.get(0).getPower();
		Unite unityMax = army.get(0);
		for (Unite unit : army) {
			if (unit.getPower()>max){
				unityMax = unit;
			}else if(unit.getPower() == max){
				unityMax = getPriority(unit,unityMax,type);
				max = unit.getPower();
			}
		}
		return unityMax;
	}
	
	public Unite getPriority(Unite lun, Unite lautre, String type) {
		if (type == "defense") {
			int a = lun.getPrioriteDEF();
			int b = lautre.getPrioriteDEF();
			if (a > b) {
				return lautre;
			}else {
				return lun;
			}
		}else{
			int a = lun.getPrioriteATT();
			int b = lautre.getPrioriteATT();
			if (a > b) {
				return lautre;
			}else {
				return lun;
			}
		}
	}
	
	public ArrayList<Unite> deleteUnit(Unite unite,ArrayList<Unite> armee,Joueur player) {
		unite.setOnField(false);
		armee.remove(unite.getID());
		player.getArmy().remove(unite.getID());
		return armee;
	}
}

