import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

public class Partie {
	
	public final int maxUnitsATT = 3;
	public final int maxUnitsDEF = 2;
	public static int mvtsParTourSoldat = 2;
	public static int mvtsParTourCanon = 1;
	public static int mvtsParTourCavalier = 3;
	
	public int attaqueEnnemi(Joueur player, ArrayList<Unite> bataillonAttaquant, Territoire territoireDepart, Territoire territoireDestination) {
		
		ArrayList<Unite> bataillonDefendant = new ArrayList<Unite>();
		ArrayList<Unite> armeesDefenseur = territoireDestination.getArmy();
		
		bataillonDefendant = setArmyAndPower(armeesDefenseur,false);
		bataillonAttaquant = setArmyAndPower(bataillonAttaquant,true);
		
		System.out.println();
		System.out.println("Bataillon ATTAQUANT :");
		for(Unite unit : bataillonAttaquant) {
			System.out.println(unit.getType() + " " + unit.getPower());
		}
		System.out.println();
		System.out.println("Bataillon DEFENDANT :");
		for(Unite unit2 : bataillonDefendant) {
			System.out.println(unit2.getType() + " " + unit2.getPower());
		}
		Joueur playerDef = territoireDestination.getJoueur();
		Joueur playerAtt = player;
		int m = affrontement(bataillonAttaquant,bataillonDefendant,playerAtt,playerDef,territoireDepart, territoireDestination);
		return m;
	}
	
	public void deplacementTerritoireAllie(Joueur player, ArrayList<Unite> unitesADeplacer, Territoire territoireDepart,Territoire territoireDestination){
		System.out.println();
		System.out.println("Unités déplacées :");
		for(Unite unit : unitesADeplacer) {
			int mvt = unit.getMvtRestants();
			territoireDepart.deleteArmy(unit);
			territoireDestination.addArmy(unit);
			unit.setMvtRestants(mvt-1);
			System.out.println(unit.getType() + " avec " + unit.getMvtRestants() + " déplacement(s) restant(s)");
		}
		System.out.println("#######################################################################");
	}
	
	
	public int repartitionArmees(Joueur player) {
		Random rand = new Random();
		int firstCondition = player.getTerritoire().size();
		firstCondition = (int) Math.floor(firstCondition/3);
		
		int secondCondition = regionComplete(player);
		
		int thirdCondition = 0;
		
		for(int i=0;i<player.getTerritoiresConquis();i++) {
			thirdCondition = thirdCondition + rand.nextInt(2);
		}
		
		int renfortsJoueur = Math.max(firstCondition + secondCondition + thirdCondition,2);
		
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
		ArrayList<Integer> deatharray = new ArrayList<>();
		if(StdDraw.isMousePressed()){
			ArrayList<Territoire> territoire = player.getTerritoire();
			for(int i = 0;i<territoire.size();i++) {
				Territoire pays = territoire.get(i);
				int[] territoirep = pays.getPos();
				if(territoirep[0]-20<=(int)StdDraw.mouseX() && (int)StdDraw.mouseX()<=territoirep[0]+20 && territoirep[1]-20<=(int)StdDraw.mouseY() && (int)StdDraw.mouseY()<=territoirep[1]+20) {
					int[] listeVoisins = pays.getVoisins();
					for(int j=0;j<listeVoisins.length;j++) {
						if(territoires.get(listeVoisins[j]).getJoueur() == player) {
							/* lignes qui colorient les cercles des voisins en )noir */
							StdDraw.setPenColor(Color.DARK_GRAY);
							StdDraw.filledCircle(territoires.get(listeVoisins[j]).getPos()[0],territoires.get(listeVoisins[j]).getPos()[1], 15);	
							StdDraw.setPenColor(Color.WHITE);
							StdDraw.setFont();
							StdDraw.text(territoires.get(listeVoisins[j]).getPos()[0]-0.1, territoires.get(listeVoisins[j]).getPos()[1]-0.8,  ""+ territoires.get(listeVoisins[j]).getArmy().size());
							StdDraw.show();							
						} else {
							/* lignes qui colorient les cercles des voisins en )noir */
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
		deatharray.add(99);
		return deatharray;
	}
					
	public boolean checkWin(Joueur player,Mission mission) {
		if(mission.isDone(player)) {
			return true;
		}
		return false;
	}

	public ArrayList<Unite> setArmyAndPower(ArrayList<Unite> army, boolean attack) {
		ArrayList<Unite> newArmy = new ArrayList<Unite>();
		int count=0;
		Random rand = new Random();
		int max = maxUnitsDEF;
		if (attack) {
			max = maxUnitsATT;
		}
		for (Unite unit : army) {
			if (unit.getType() == "Soldat") {					
				int soldierPower = rand.nextInt((6 - 1) + 1) + 1;
				unit.setPower(soldierPower); 
				count++;
				newArmy.add(unit);
				if (count==max) {
					break;
				}
			}
		}
		for (Unite unit : army) {
			if (unit.getType() == "Canon") {					
				int canonPower = rand.nextInt((7 - 2) + 1) + 2;
				unit.setPower(canonPower); 
				count++;
				newArmy.add(unit);
				if (count==max) {
					break;
				}
			}
		}
		for (Unite unit : army) {
			if (unit.getType() == "Cavalier") {					
				int knightPower = rand.nextInt((9 - 4) + 1) + 4;
				unit.setPower(knightPower); 
				count++;
				newArmy.add(unit);
				if (count==max) {
					break;
				}
			}	
		}
		return newArmy;
	}
	
	public int affrontement(ArrayList<Unite> armyAtt,ArrayList<Unite> armyDef,Joueur playerAtt,Joueur playerDef, Territoire territoireDepart,Territoire territoireDestination) {
		int uniteDefDestroy = 0;
		int uniteAttDestroy = 0;
		int message = 0;
		int debut = armyDef.size();
		ArrayList<Unite> unitesVictorieuses = new ArrayList<Unite>();
		
		while(armyAtt.size()!=0 && armyDef.size()!=0){
			int attMaxPower = strongerUnitPower(armyAtt,"attack");
			Unite attStrongerUnit = strongerUnit(armyAtt,"attack");
			int defMaxPower = strongerUnitPower(armyDef,"defense");
			Unite defStrongerUnit = strongerUnit(armyDef,"defense");
			System.out.println();
			System.out.println(attStrongerUnit.getType() + " " + attMaxPower + "  VS  " + defStrongerUnit.getType() + " " + defMaxPower);
			if (attMaxPower > defMaxPower) {
				unitesVictorieuses.add(attStrongerUnit);
				System.out.println("Le " + defStrongerUnit.getType() + " défensif est détruit !");
				armyAtt.remove(attStrongerUnit);
				armyDef = deleteUnit(defStrongerUnit,armyDef,playerDef,territoireDestination);//diminue la taille de armyDef
				uniteDefDestroy++;
			}else{
				System.out.println("Le " + attStrongerUnit.getType() + " attaquant est détruit !");
				armyDef.remove(defStrongerUnit);
				armyAtt = deleteUnit(attStrongerUnit,armyAtt,playerAtt, territoireDepart);//diminue la taille de armyAtt
				uniteAttDestroy++;
			}
		}
		System.out.println();
		System.out.println("Vous avez perdu " + uniteAttDestroy + " unite(s) !");
		System.out.println("Votre adversaire a perdu " + uniteDefDestroy + " unite(s) !");
		System.out.println();
		if (uniteDefDestroy == debut){
			if(territoireDestination.getArmy().size()==0) {
				System.out.println("Vous avez conquis le territoire adverse !");
				message = 1;
				playerAtt.setTerritoiresConquis(playerAtt.getTerritoiresConquis()+1);
				territoireDestination.setJoueur(playerAtt);
				playerAtt.addTerritoire(territoireDestination);
				deplacementTerritoireAllie(playerAtt,unitesVictorieuses,territoireDepart,territoireDestination);
			}else {
				message = 2;
				System.out.println("Vainquez les unités restantes pour conquérir le territoire !");
			}
		}else{
			System.out.println("Votre attaque a été repoussée !");
		}
		System.out.println();
		System.out.println("#######################################################################");
		return message;
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
	
	public ArrayList<Unite> deleteUnit(Unite unite,ArrayList<Unite> armee,Joueur player, Territoire territoire) {
		unite.setOnField(false);
		armee.remove(unite);
		territoire.getArmy().remove(unite);
		player.getArmy().remove(unite);
		return armee;
	}
}
