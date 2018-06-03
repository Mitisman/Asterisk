import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mission {
	
	public Mission (ArrayList<Joueur> j) {
		this.joueurs = j;
		
		this.Mission.add(mA);
		this.Mission.add(mB);
		
		switch (joueurs.size()) {
		case 2:
			Mission.add(mC);
			Mission.add(mD);
			break;
		case 3:
			Mission.add(mC);
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
					Mission.add(alea, "D�truire " + joueurs.get(i+1).getNom());
				} else {
					Mission.remove(alea);
					Mission.add(alea, "D�truire " + joueurs.get(i-1).getNom());
				}
			}
		j.setMission(Mission.get(alea));
		}
	}
	
	ArrayList<String> Mission = new ArrayList<>();
	ArrayList<Joueur> joueurs = new ArrayList<>();
	String mA = "Contr�ler la plus grosse r�gion + 1 autre r�gion";
	String mB = "Cont�ler 3 r�gions et au moins 18 territoires";
	String mC = "Conqu�rir tous les territoires";
	String mD = "Contr�ler 30 territoires";
	String mE = "D�truire X";
	String mF = "Contr�ler 18 territoires avec au moins 2 arm�es";
	String mG = "Contr�ler 24 territoires";
	String mH = "contr�ler 21 territoires";
}