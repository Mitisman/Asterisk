import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mission {

	public Mission (ArrayList<Joueur> j) {
		System.out.println("--------------------------------------------------");
		this.joueurs = j;
		
		this.Mission.add(mA);
		this.Mission.add(mB);
		
		switch (joueurs.size()) {
		case 2:
			Mission.add(mD);
			break;
		case 3:
			Mission.add(mD);
			Mission.add(mE);
			Mission.add(mF);
			break;
		case 4:
			Mission.add(mE);
			Mission.add(mF);
			Mission.add(mG);
			break;
			
		case 5:
			Mission.add(mE);
			Mission.add(mF);
			Mission.add(mG);
			break;
		case 6 : 
			Mission.add(mE);
			Mission.add(mF);
			Mission.add(mH);
			break;
		}
		DistributionM();
	}

	public void DistributionM() {
		Collections.shuffle(Mission);
		Collections.shuffle(joueurs);
		Random rand = new Random();
		for(int i = joueurs.size()-1;i>=0;i--) {
			Joueur j = joueurs.get(i);
			int alea = rand.nextInt(Mission.size());
			if(Mission.get(alea) == mE) {
				if(i<joueurs.size() - 1) {
					Mission.remove(alea);
					Mission.add(alea, "Détruire " + joueurs.get(i+1).getNom());
				} else {
					Mission.remove(alea);
					Mission.add(alea, "Détruire " + joueurs.get(i-1).getNom());
				}
			}
		j.setMission(Mission.get(alea));
		}
	}
	
	public boolean isDone(Joueur player) {
		boolean principal = mCCheck(player);
		
		if(principal) {
			player.setWin();
		}
		
		boolean secondaire = false;
				if(player.getMission().trim().equals(mA.trim())) {
					secondaire = mACheck(player);
				}else if(player.getMission().trim().equals(mB.trim())){
					secondaire = mBCheck(player);
				}else if(player.getMission().trim().equals(mD.trim())){
					secondaire = mDCheck(player);
				}else if(player.getMission().trim().equals(mE.trim())){
					secondaire = mECheck(player);
				}else if(player.getMission().trim().equals(mF.trim())){
					secondaire = mFCheck(player);
				}else if(player.getMission().trim().equals(mG.trim())){
					secondaire = mGCheck(player);
				}else if(player.getMission().trim().equals(mH.trim())){
					secondaire = mHCheck(player);
				}else {
					System.out.println("COMMENT CA T'AS PAS DE MISSION?");
				}
		
		
		return principal || secondaire;
	}
	
	public boolean mACheck(Joueur player) {
		int check=0;
		int AdN=0;
		int AdS=0;
		int Europe=0;
		int Oceanie=0;
		int Afrique=0;
		ArrayList<Territoire> territoires = player.getTerritoire();
		for(Territoire pays : territoires) {
			if(pays.getRegion().trim().equals("Amérique du nord")) {
				AdN++;
			}else if(pays.getRegion().trim().equals("Amérique du sud")) {
				AdS++;
			}else if(pays.getRegion().trim().equals("Asie")) {
				check++;
			}else if(pays.getRegion().trim().equals("Europe")) {
				Europe++;
			}else if(pays.getRegion().trim().equals("Océanie")) {
				Oceanie++;
			}else if(pays.getRegion().trim().equals("Afrique")){
				Afrique++;
			}else {
				System.out.println("There might INDEED be a problem");
			}
		}
		if ((check==12) && ( AdN==9 || AdS==4 || Europe==7 || Oceanie==4 || Afrique==6 )) {
			return true;
		}
		return false;
	}
	
	public boolean mBCheck(Joueur player) {
		int AdN=0;
		int AdS=0;
		int Asie=0;
		int Europe=0;
		int Oceanie=0;
		int Afrique=0;
		int count=0;
		
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
			count++;
		}
		if(AdS==4) {
			count++;
		}
		if(Asie==12) {
			count++;
		}
		if(Europe==7) {
			count++;
		}
		if(Oceanie==4) {
			count++;
		}
		if(Afrique==6) {
			count++;
		}
		
		if(count>=3 && territoires.size()>=18) {
			return true;
		}
		return false;
	}
	
	public boolean mCCheck(Joueur player) {
		if(player.getTerritoire().size()==42) {
			return true;
		}
		return false;
	}
	
	public boolean mDCheck(Joueur player) {
		if(player.getTerritoire().size()>=30) {
			return true;
		}
		return false;
	}
	
	public boolean mECheck(Joueur player) {
		ArrayList<Joueur>players = new ArrayList<Joueur>();
		players.addAll(joueurs);
		Joueur joueurCible = player;
		String [] mots = player.getMission().split(" ");
		for(String mot : mots) {
			for(int i=0;i<players.size();i++) {
				if(mot.equals(players.get(i).getNom())) {
					players.remove(i);
				}
			}
		}
		for(String word : mots) {
			for(int j=0;j<players.size();j++) {
				if(word.equals(players.get(j).getNom())) {
					joueurCible = players.get(j);
 				}
			}
		}
		if(joueurCible.getTerritoire().size()==0) {
			return true;
		}
		return false;
	}
	
	public boolean mFCheck(Joueur player) {
		int check=0;
		ArrayList<Territoire> territoires = player.getTerritoire();
		for(Territoire pays : territoires) {
			if(pays.getArmy().size()>=2) {
				check++;
			}
		}
		if (check>=18) {
			return true;
		}
		return false;
	}
	
	public boolean mGCheck(Joueur player) {
		if(player.getTerritoire().size()>=24) {
			return true;
		}
		return false;
	}
	
	public boolean mHCheck(Joueur player) {
		if(player.getTerritoire().size()>=21) {
			return true;
		}
		return false;
	}
	
	ArrayList<String> Mission = new ArrayList<>();
	ArrayList<Joueur> joueurs = new ArrayList<>();
	String mA = "Contrôler la plus grosse région + 1 autre région";
	String mB = "Contôler 3 régions et au moins 18 territoires";
	String mD = "Contrôler 30 territoires";
	String mE = "Détruire X";
	String mF = "Contrôler 18 territoires avec au moins 2 armées";
	String mG = "Contrôler 24 territoires";
	String mH = "Contrôler 21 territoires";
	
	
	
}
