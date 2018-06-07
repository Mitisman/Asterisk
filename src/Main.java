import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

public class Main {
	
	private static File fichier = new File("coordonnées_region.txt");
	private static File voisins = new File("voisins.txt");
	private static String menuP = new String("menuP.jpg");
	private static String menuPn = new String("menuP-n.jpg");
	private static String menuPc = new String("menuP-c.jpg");
	private static String menuPq = new String("menuP-q.jpg");
	private static String menuNv = new String("menuNv.jpg");
	private static String menuNv1 = new String("menuNv1.jpg");
	private static String menuNv2 = new String("menuNv2.jpg");
	private static String menuNv3 = new String("menuNv3.jpg");
	private static String fin =  new String("Fin.jpg");
	private static String finM = new String("FinM.jpg");
	private static String menuNv4 = new String("menuNv4.jpg");
	private static String menuNv5 = new String("menuNv5.jpg");
	private static String menuNv6 = new String("menuNv6.jpg");
	private static String information = new String();
	private static Font font = new Font("Comic Strip MN", Font.TRUETYPE_FONT, 60);
	private static String[] nomJ = {"Goudurisk","Obélisk","Astérisk","Panoramisk","Abraracourcisk","Idéfisk"};
	private static Color[] Couleur = {Color.RED,Color.BLUE,Color.gray,Color.GREEN,Color.ORANGE,Color.PINK};
	private static String menuNvIA = new String("menuNvIA.jpg");
	private static String menuR = new String("menuResume.jpg");
	private static String menuRs = new String("menuResumeS.jpg");
	private static int choixpeau = 0;
	private static int nbj;
	private static int nbia;
	private static boolean choix = false;
	private static boolean start = false;
	private static boolean choixIA = false;
	private static ArrayList<Joueur> joueur = new ArrayList<>();
	private static ArrayList<String> nregion = new ArrayList<>();
	private static ArrayList<String> nterritoires = new ArrayList<>();
	private static final int largeur = 1193;
	private static final int hauteur = 675;
	private static boolean gameOver = false;
	private static ArrayList<int[]> v = new ArrayList<>();
	private static Joueur winner;
	
	public static void main(String[] args) throws FileNotFoundException {
		int[][] t = LireCoordonnées();
		ListeVoisins();
		
		LireRegion();
		LireTerritoire();
		
		//Menu/*
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setScale(0, 800);
		StdDraw.setCanvasSize(largeur, hauteur);
		StdDraw.setXscale(0, largeur);
		StdDraw.setYscale(0, hauteur);
		StdDraw.picture(largeur/2, hauteur/2, menuP);
		
		while(!choix) {
			if(StdDraw.isMousePressed()) {
				System.out.println(StdDraw.mouseX());
				System.out.println(StdDraw.mouseY());			
			}
			
			switch(choixpeau) {
			case 1:
				if(StdDraw.mouseX()>=226 && StdDraw.mouseX()<=245 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.picture(largeur/2, hauteur/2, menuNv1);
					if(StdDraw.isMousePressed()) {
						nbj = 1;
						choix = true;	
					}
				} else if(StdDraw.mouseX()>=312 && StdDraw.mouseX()<=357 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.picture(largeur/2, hauteur/2, menuNv2);
					if(StdDraw.isMousePressed()) {
						nbj = 2;
						choix = true;
					}
				} else if(StdDraw.mouseX()>=440 && StdDraw.mouseX()<=486 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.picture(largeur/2, hauteur/2, menuNv3);
					if(StdDraw.isMousePressed()) {
						nbj = 3;
						choix = true;
					}
				} else if(StdDraw.mouseX()>=572 && StdDraw.mouseX()<=615 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.picture(largeur/2, hauteur/2, menuNv4);
					if(StdDraw.isMousePressed()) {
						nbj = 4;
						choix = true;
					}
				} else if(StdDraw.mouseX()>=709 && StdDraw.mouseX()<=752 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.picture(largeur/2, hauteur/2, menuNv5);
					if(StdDraw.isMousePressed()) {
						nbj = 5;
						choix = true;
					}
				} else if(StdDraw.mouseX()>=835 && StdDraw.mouseX()<=880 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.picture(largeur/2, hauteur/2, menuNv6);
					if(StdDraw.isMousePressed()) {
						nbj = 6;
						choix = true;
					}
				} else {
					StdDraw.picture(largeur/2, hauteur/2, menuNv);
				}
				break;
			default:
				if(StdDraw.mouseX()>=305 && StdDraw.mouseX()<=881 && StdDraw.mouseY()>=285 && StdDraw.mouseY()<=324) {
					StdDraw.picture(largeur/2, hauteur/2, menuPn);
					if(StdDraw.isMousePressed()) {
						System.out.println("NV PARTIE");
						choixpeau = 1;
					}
				} else if(StdDraw.mouseX()>=238 && StdDraw.mouseX()<=953 && StdDraw.mouseY()>=186 && StdDraw.mouseY()<=227) {
					StdDraw.picture(largeur/2, hauteur/2, menuPc);
					if(StdDraw.isMousePressed()) {
						System.out.println("CHARGEMENT");
						choixpeau = 2;
					}
				} else if(StdDraw.mouseX()>=453 && StdDraw.mouseX()<=724 && StdDraw.mouseY()>=85 && StdDraw.mouseY()<=125) {
					StdDraw.picture(largeur/2, hauteur/2, menuPq);
					if(StdDraw.isMousePressed()) {
						System.out.println("QUITTER");
						System.exit(0);
					}
				} else {
					StdDraw.picture(largeur/2, hauteur/2, menuP);
				}
				break;
			}
		}
		
		StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
		StdDraw.setFont(font);
		StdDraw.enableDoubleBuffering();
		while(!choixIA) {
			switch(nbj) {
			case 1:
				if(StdDraw.mouseX()>=360 && StdDraw.mouseX()<=380 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(370,260,"1");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(450,260,"2");
					StdDraw.text(550,260,"3");
					StdDraw.text(650,260,"4");
					StdDraw.text(735,260,"5");
					if(StdDraw.isMousePressed()) {
						nbia = 1;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=430 && StdDraw.mouseX()<=470 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"1");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(450,260,"2");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(550,260,"3");
					StdDraw.text(650,260,"4");
					StdDraw.text(735,260,"5");
					if(StdDraw.isMousePressed()) {
						nbia = 2;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=530 && StdDraw.mouseX()<=570 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"1");
					StdDraw.text(450,260,"2");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(550,260,"3");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(650,260,"4");
					StdDraw.text(735,260,"5");
					if(StdDraw.isMousePressed()) {
						nbia = 3;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=630 && StdDraw.mouseX()<=670 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"1");
					StdDraw.text(450,260,"2");
					StdDraw.text(550,260,"3");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(650,260,"4");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(735,260,"5");
					if(StdDraw.isMousePressed()) {
						nbia = 4;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=715 && StdDraw.mouseX()<=755 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"1");
					StdDraw.text(450,260,"2");
					StdDraw.text(550,260,"3");
					StdDraw.text(650,260,"4");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(735,260,"5");
					StdDraw.setPenColor(Color.BLACK);
					if(StdDraw.isMousePressed()) {
						nbia = 5;
						choixIA = true;
					}
				} else {
					StdDraw.text(370,260,"1");
					StdDraw.text(450,260,"2");
					StdDraw.text(550,260,"3");
					StdDraw.text(650,260,"4");
					StdDraw.text(735,260,"5");
				}
				StdDraw.show();	
				break;
			case 2:
				if(StdDraw.mouseX()>=360 && StdDraw.mouseX()<=380 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(370,260,"0");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(450,260,"1");
					StdDraw.text(550,260,"2");
					StdDraw.text(650,260,"3");
					StdDraw.text(735,260,"4");
					if(StdDraw.isMousePressed()) {
						nbia = 0;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=430 && StdDraw.mouseX()<=470 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"0");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(450,260,"1");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(550,260,"2");
					StdDraw.text(650,260,"3");
					StdDraw.text(735,260,"4");
					if(StdDraw.isMousePressed()) {
						nbia = 1;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=530 && StdDraw.mouseX()<=570 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"0");
					StdDraw.text(450,260,"1");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(550,260,"2");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(650,260,"3");
					StdDraw.text(735,260,"4");
					if(StdDraw.isMousePressed()) {
						nbia = 2;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=630 && StdDraw.mouseX()<=670 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"0");
					StdDraw.text(450,260,"1");
					StdDraw.text(550,260,"2");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(650,260,"3");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(735,260,"4");
					if(StdDraw.isMousePressed()) {
						nbia = 3;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=715 && StdDraw.mouseX()<=755 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(370,260,"0");
					StdDraw.text(450,260,"1");
					StdDraw.text(550,260,"2");
					StdDraw.text(650,260,"3");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(735,260,"4");
					StdDraw.setPenColor(Color.BLACK);
					if(StdDraw.isMousePressed()) {
						nbia = 4;
						choixIA = true;
					}
				} else {
					StdDraw.text(370,260,"0");
					StdDraw.text(450,260,"1");
					StdDraw.text(550,260,"2");
					StdDraw.text(650,260,"3");
					StdDraw.text(735,260,"4");
				}
				StdDraw.show();
				break;
			case 3:
				if(StdDraw.mouseX()>=450 && StdDraw.mouseX()<=480 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(470,260,"0");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(550,260,"1");
					StdDraw.text(650,260,"2");
					StdDraw.text(735,260,"3");
					if(StdDraw.isMousePressed()) {
						nbia = 0;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=540 && StdDraw.mouseX()<=560 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(470,260,"0");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(550,260,"1");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(650,260,"2");
					StdDraw.text(735,260,"3");
					if(StdDraw.isMousePressed()) {
						nbia = 1;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=630 && StdDraw.mouseX()<=670 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281){
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(470,260,"0");
					StdDraw.text(550,260,"1");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(650,260,"2");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(735,260,"3");
					if(StdDraw.isMousePressed()) {
						nbia = 2;
						choixIA = true;						
					}
				} else if(StdDraw.mouseX()>=715 && StdDraw.mouseX()<=755 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281){
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(470,260,"0");
					StdDraw.text(550,260,"1");
					StdDraw.text(650,260,"2");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(735,260,"3");
					StdDraw.setPenColor(Color.BLACK);
					if(StdDraw.isMousePressed()) {
						nbia = 3;
						choixIA = true;						
					}
				} else {
					StdDraw.text(470,260,"0");
					StdDraw.text(550,260,"1");
					StdDraw.text(650,260,"2");
					StdDraw.text(735,260,"3");					
				}
				StdDraw.show();
				break;
			case 4:
				if(StdDraw.mouseX()>=530 && StdDraw.mouseX()<=570 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(550,260,"0");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(650,260,"1");
					StdDraw.text(735,260,"2");	
					if(StdDraw.isMousePressed()) {
						nbia = 0;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=640 && StdDraw.mouseX()<=660 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(550,260,"0");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(650,260,"1");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(735,260,"2");	
					if(StdDraw.isMousePressed()) {
						nbia = 1;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=715 && StdDraw.mouseX()<=755 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(550,260,"0");
					StdDraw.text(650,260,"1");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(735,260,"2");	
					StdDraw.setPenColor(Color.BLACK);
					if(StdDraw.isMousePressed()) {
						nbia = 2;
						choixIA = true;
					}
				} else {
					StdDraw.text(550,260,"0");
					StdDraw.text(650,260,"1");
					StdDraw.text(735,260,"2");	
				}
				StdDraw.show();
				break;
			case 5:
				if(StdDraw.mouseX()>=480 && StdDraw.mouseX()<=520 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(500,260,"0");
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(600,260,"1");
					if(StdDraw.isMousePressed()) {
						nbia = 0;
						choixIA = true;
					}
				} else if(StdDraw.mouseX()>=590 && StdDraw.mouseX()<=610 && StdDraw.mouseY()>=243 && StdDraw.mouseY()<=281) {
					StdDraw.clear();
					StdDraw.picture(largeur/2, hauteur/2, menuNvIA);
					StdDraw.text(500,260,"0");
					StdDraw.setPenColor(Color.RED);
					StdDraw.text(600,260,"1");
					StdDraw.setPenColor(Color.BLACK);
					if(StdDraw.isMousePressed()) {
						nbia = 1;
						choixIA = true;
					}
				} else {
					StdDraw.text(500,260,"0");
					StdDraw.text(600,260,"1");
				}
				StdDraw.show();
				break;
			case 6:
				nbia = 0;
				choixIA = true;
				break;
			}
		}
		
		MélangeS(nomJ);
		MélangeC(Couleur);
		
		int i;
		for(i = 0;i<nbj+nbia;i++) {
			if(i<nbj) {
				Joueur J = new Joueur(nomJ[i], Couleur[i], false);
				joueur.add(J);
			} else {
				Joueur J = new Joueur(nomJ[i], Couleur[i], true);
				joueur.add(J);
			}
			if(i<nbj+nbia - 1) {
				nomJ[i] = nomJ[i+1];
				Couleur[i] = Couleur[i+1];
			} else {
				nomJ[i] = nomJ[i-1];
				Couleur[i] = Couleur[i-1];
			} 
		}
		
		switch (nbj+nbia){
		case 2:
			for(Joueur player : joueur) {
				for(i=1;i<=40;i++) {
					Unite UNITE = new Unite(false,"Soldat",i,0,1,2,1);
					player.addUnite(UNITE);
				}
			}
			break;
		case 3:
			for(Joueur player : joueur) {
				for(i=1;i<=35;i++) {
					Unite UNITE = new Unite(false,"Soldat",i,0,1,2,1);
					player.addUnite(UNITE);
				}
			}
			break;
		case 4:
			for(Joueur player : joueur) {
				for(i=1;i<=30;i++) {
					Unite UNITE = new Unite(false,"Soldat",i,0,1,2,1);
					player.addUnite(UNITE);
				}
			}
			break;
		case 5:
			for(Joueur player : joueur) {
				for(i=1;i<=25;i++) {
					Unite UNITE = new Unite(false,"Soldat",i,0,1,2,1);
					player.addUnite(UNITE);
				}
			}
			break;
		case 6:
			for(Joueur player : joueur) {
				for(i=1;i<=20;i++) {
					Unite UNITE = new Unite(false,"Soldat",i,0,1,2,1);
					player.addUnite(UNITE);
				}
			}
			break;
		default:
			break;
	}
		
		Carte map = new Carte(t, v, nterritoires, nregion);
		map.initialisation();
		Random rand = new Random();
		ArrayList<Territoire> territoires = new ArrayList<>();
		territoires.addAll(map.getTerritoire());
		int l = 41;
		while(l>0) {
			for(Joueur player : joueur) {
				int randomIndex = rand.nextInt((l + 1));
				player.addTerritoire(territoires.get(randomIndex)); 
				territoires.get(randomIndex).setJoueur(player);
				territoires.remove(randomIndex);
				l--;
				if(l<0) {
					break;
				}
			}
		}
		Mission m = new Mission(joueur);
		

		StdDraw.clear();
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(1643, 675);
		StdDraw.setXscale(0, 1643);
		StdDraw.setYscale(0, 675);
		StdDraw.picture(1643/2, 675/2, menuR);
		StdDraw.setFont(font);
		while(!start) {
			StdDraw.setPenColor(Color.BLACK);
			if(StdDraw.mouseX()>=1415 && StdDraw.mouseX()<=1631 && StdDraw.mouseY()<=69 && StdDraw.mouseY()>=7) {
				StdDraw.clear();
				StdDraw.picture(1643/2, 675/2, menuRs);
				for(i = 0;i<joueur.size();i++) {
					if(joueur.get(i).getIA()) {
						information = joueur.get(i).getNom() + " IA";
					} else {
						information = joueur.get(i).getNom();
					}
					StdDraw.textLeft(100, 510 - i*95, information);
					StdDraw.setPenColor(joueur.get(i).getCouleur());
					StdDraw.filledCircle(50, 510 - i*95, 27);
					StdDraw.setPenColor(Color.BLACK);
				if(StdDraw.isMousePressed()) {
					start = true;
				}
				}
			} else {
				StdDraw.clear();
				StdDraw.picture(1643/2, 675/2, menuR);
				for(i = 0;i<joueur.size();i++) {
					if(joueur.get(i).getIA()) {
						information = joueur.get(i).getNom() + " IA";
					} else {
						information = joueur.get(i).getNom();
					}
					StdDraw.textLeft(100, 510 - i*95, information);
					StdDraw.setPenColor(joueur.get(i).getCouleur());
					StdDraw.filledCircle(50, 510 - i*95, 27);
					StdDraw.setPenColor(Color.BLACK);
				}
			} 
			StdDraw.show();
		}
		
		for(i = joueur.get(0).getArmy().size()-1;i>=0;i--) {
			System.out.println("SUR LE TERRAIN ???? " +joueur.get(0).getArmy().get(i).isOnField());
		}
		
		ArrayList<Unite> u = new ArrayList<>();
		for(i = joueur.size()-1;i>=0;i--) {
			Joueur j = joueur.get(i);
			territoires.clear();
			territoires.addAll(j.getTerritoire());
			u.clear();
			u = j.getArmyAvailable();
			for(int k = j.getTerritoire().size()-1;k>=0;k--) {
				if(u.get(k).type == "Soldat" && !u.get(k).isOnField()) {
					territoires.get(k).addArmy(u.get(k));
					u.get(k).setMvtRestants(3);
					u.get(k).setOnField(true); 
				}
			} 
			int alea = 0;
			for(int k = u.size()-1;k>=0;k--) {
				alea = rand.nextInt(territoires.size());
				territoires.get(alea).addArmy(u.get(k));
				u.get(k).setMvtRestants(3);
				u.get(k).setOnField(true);
			} 
		}
		
		//DEMARRAGE DE LA PARTIE
		Partie newGame = new Partie();
		//map.Start();
		
		while(!gameOver) {
			for(Joueur player : joueur) {
				int count=0;
				for(Unite unit : player.getArmy()) {
					if(unit.getType()=="Soldat") {
						unit.setMvtRestants(3);
					}else if(unit.getType()=="Cavalier") {
						unit.setMvtRestants(2);
					}else if(unit.getType()=="Canon") {
						unit.setMvtRestants(1);
					}
				}
				while(!(StdDraw.isMousePressed() && StdDraw.mouseX()>=1233 && StdDraw.mouseY()<=1620 && StdDraw.mouseY()>=84 && StdDraw.mouseY()<=125)) {
					map.Maj(newGame,player,count);
					count++;
				}
				while(StdDraw.isMousePressed()){}
				gameOver = newGame.checkWin(player,m);
				winner = player;
			}
		}
		
		//FIN DE LA PARTIE
		if(winner.getWin()==true) {
			StdDraw.disableDoubleBuffering();
			StdDraw.clear();
			StdDraw.setCanvasSize(1524, 1084);
			StdDraw.picture(0.5, 0.5, finM);   //VICTOIRE PAR MISSION SECRETE
			StdDraw.setXscale(0, 1524);
			StdDraw.setYscale(0, 1084);
			StdDraw.setFont(font);
			StdDraw.textLeft(460, 975, winner.getNom());
			StdDraw.show();
		}else {
			StdDraw.disableDoubleBuffering();
			StdDraw.clear();
			StdDraw.setCanvasSize(1524, 1084);
			StdDraw.picture(0.5, 0.5, fin);   //VICTOIRE PAR MISSION PRINCIPALE
			StdDraw.setXscale(0, 1524);
			StdDraw.setYscale(0, 1084);
			StdDraw.setFont(font);
			StdDraw.textLeft(460, 975, winner.getNom());
			StdDraw.show();
		}
		
	}
	
	public static void ListeVoisins() throws FileNotFoundException{
		int k = 0;
		int i = 0;
		int cache = 0;								//Pas du tout optimisé à cause de quick fixe :^)
		int[][] tab = new int[50][7];
		int[] toarray;
		Scanner sc = new Scanner(voisins);
		while(sc.hasNextLine()) {
			cache = sc.nextInt();
			if(cache == 99) {
				toarray = new int[k];
				for(int j = 0;j<k;j++) {
					toarray[j] = tab[i][j];
				}
				v.add(toarray);
				tab[i][k] = cache;
				i++;
				k = 0;
			} else {
				tab[i][k] = cache;
				k++;
			}
		}
	}
	
	  static void MélangeS(String[] tab) {
	    Random alea = new Random();
	    for (int i = tab.length - 1; i > 0; i--) {
	      int index = alea.nextInt(i + 1);
	      String a = tab[index];
	      tab[index] = tab[i];
	      tab[i] = a;
	    }
	  }
	
	  static void MélangeC(Color[] tab) {
		Random alea = new Random();
		for (int i = tab.length - 1; i > 0; i--) {
			int index = alea.nextInt(i + 1);
			Color a = tab[index];		      
			tab[index] = tab[i];
			tab[i] = a;
	    }
	  }
	
	public static int[][] LireCoordonnées(){
		int i = 0;
		int[][] tab = new int[42][2];
		Scanner sc;
		try {
			sc = new Scanner(fichier);
			while(sc.hasNextLine()) {
			    tab[i][0] = sc.nextInt();
			    tab[i][1] = sc.nextInt();
			    sc.nextLine();
			    i++;
			}
			sc.close();
			return tab;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return tab;
		}
	}
	
	public static ArrayList<String> LireRegion()  throws FileNotFoundException{
		Scanner sc = new Scanner(fichier).useDelimiter("99");
		while(sc.hasNextLine()) {
			sc.next();
			nregion.add(sc.next());
			sc.nextLine();
		}
		sc.close();
		return nregion;
	}
	
	public static ArrayList<String> LireTerritoire()  throws FileNotFoundException{
		Scanner sc = new Scanner(fichier).useDelimiter("99");
		while(sc.hasNextLine()) {
			sc.next();
			sc.next();
			nterritoires.add(sc.next());
			sc.nextLine();
		}
		sc.close();
		return nterritoires;
	}
	
}
