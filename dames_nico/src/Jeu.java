import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @classe Pion.
 * @visibilite défaut (default).
 * @description Représente un pion dans le jeu de dame.
 *
 */
class Pion
{
    /** Identifie si le pion est une dame. */
    public boolean estDame = false;
    /** Identifie si le pion appartient au robot. */
    public boolean estEnnemi;


    /**
     * @constructeur Pion.
     * @visibilite publique (public).
     * @param _estEnnemi Indique si le pion appartient au robot, en format boolean.
     */
    public Pion(boolean _estEnnemi)
    {
        estEnnemi = _estEnnemi;
    }
}

/**
 * @classe Jeu.
 * @visibilite publique (public).
 * @description Représente le jeu de dames et gère le déroulement, ainsi que les validations.
 */

public class Jeu {

    // Constantes pour les couleurs dans la console
    private static final String REINIT = "\u001B[0m";
    private static final String ROUGE = "\u001B[31m";
    private static final String VERT = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    /** Map pour stocker la position des pions. */
    private final Map<String, Pion> positionPions = new HashMap<>();
    /** true = tour du joueur, false = tour de l'ennemi. */
    public boolean tourJoueur = true;

    /**
     * @constructeur Jeu.
     * @visibilite publique (public).
     * @description Constructeur pour initialiser le jeu.
     */
    public Jeu()
    {
        initialisationJeu();
    }

    /**
     * @methode initialisationJeu.
     * @visibilite privée (private).
     * @description Méthode pour initialiser les pions sur le plateau.
     */
    private void initialisationJeu()
    {
        // Initialisation des pions du robot ennemi (3 premières lignes)
        initialiserPions(0, 3, true);

        // Initialisation des pions du joueur (3 dernières lignes)
        initialiserPions(5, 8, false);
    }

    /**
     * @methode initialiserPions.
     * @visibilite privée (private).
     * @description Affiche les informations d'un véhicule à la console.
     * @param debut    La ligne de début, en format int.
     * @param fin      La ligne de fin, en format int.
     * @param estEnnemi Indique si les pions appartiennent au robot, en format boolean.
     * @description Méthode pour initialiser les pions sur une plage de lignes.
     */
    private void initialiserPions(int debut, int fin, boolean estEnnemi)
    {
        for (int i = debut; i < fin; i++) //Boucler dans les lignes (1-8)
        {
            for (int j = 0; j < 8; j++) //Boucler dans les colonnes (A-H).
            {
                if ((i + j) % 2 == 1) //Calcul pour selectionner les cases à mettre les pions dessus.
                {
                    Pion pion = new Pion(estEnnemi); //Créer un nouveau pion et indiquer s'il est un ennemi ou non.
                    positionPions.put(extraireCoordDeXY(i, j), pion); // Ajouter à la "map" le pion créé, ainsi que sa position sur le plateau (i, j).
                }
            }
        }
    }


    /**
     * @methode extraireCoordDeXY.
     * @visibilite privée (private).
     * @description Méthode pour obtenir la coordonnée en chaîne à partir des indices XY.
     * @param x L'indice X (lignes 1-8), en format int.
     * @param y L'indice Y (colonnes A-H), en format int.
     * @return La coordonnée en chaîne, en format String.
     */
    private String extraireCoordDeXY(int x, int y)
    {
        return String.valueOf((char) (y + 'A')) + (x + 1); //y + ASCII de A = lettre du plateau (colonne). X + ASCII de 1 = Chiffre du plateau (ligne).
    }

    /**
     * @methode dessinerJeu.
     * @visibilite publique (public).
     * @description  Méthiode pour dessiner le plateau de jeu.
     */
    public void dessinerJeu()
    {
        System.out.println("    A   B   C   D   E   F   G   H"); //Afficher les colonnes.
        System.out.println("  +---+---+---+---+---+---+---+---+"); //Séparateur.

        for (int i = 0; i < 8; i++) //Boucler dans toutes les lignes.
        {
            System.out.print((i + 1) + " |"); //Afficher l'indice de la ligne et commencer le dessin des cases "|".

            for (int j = 0; j < 8; j++)  //Boucler dans toutes les colonnes.
            {
                String coord = extraireCoordDeXY(i, j); //Extraire les coordonnées de la case actuelle.
                Pion pion = positionPions.get(coord); //Extraire le pion à la case actuelle.

                if (pion != null)  //Si le pion existe.
                {
                    String symbole = pion.estDame ? " D " : " O "; // Vérifier si le pion est une dame ou non et enregistrer le symbole à écrire.
                    String couleur = pion.estEnnemi ? ROUGE : VERT; // Vérifier si le pion est un ennemi ou non et enregistrer la couleur en conséquent.
                    System.out.print(couleur + symbole + REINIT + "|"); //Écrire le symbole (X, O, D) sur la case et sa couleur.
                }
                else
                {
                    System.out.print("   |"); //Fermer la case.
                }
            }

            System.out.print("\n");
            System.out.println("  +---+---+---+---+---+---+---+---+"); // Séparer la ligne.
        }
    }

    /**
     * @methode saisieValide.
     * @visibilite privée (private).
     * @description Vérifier si la saisie de la case est valide.
     * @param _saisie La saisie de l'utilisateur, en format String.
     * @return true si la saisie est valide, false sinon, en format boolean.
     */
    private boolean saisieValide(String _saisie)
    {
        if (_saisie.length() != 2) return false; //Vérifie que l'utilisateur a saisi deux caractères.
        return _saisie.matches("^[A-H][1-8]$"); //Vérifie si l'utilisateur a effectué une saisie dans la plage (A1-H8).
    }

    /**
     * @methode extraireValeurX.
     * @visibilite privée (private).
     * @description Extraire la position X de la saisie.
     * @param _saisie La saisie de l'utilisateur, en format String.
     * @return La position X, en format int.
     */
    private int extraireValeurX(String _saisie)
    {
        return (_saisie.charAt(1) - 49); //Retourne l'indice X (ligne)... 49 = code ASCII pour '1'.
    }

    /**
     *
     * @methode extraireValeurY.
     * @visibilite privée (private).
     * @description Extraire la position Y de la saisie.
     * @param _saisie La saisie de l'utilisateur, en format String.
     * @return La position Y. en format int.
     */
    private int extraireValeurY(String _saisie)
    {
        return (_saisie.charAt(0) - 65); //Retourne l'indice Y (colonne)... 65 = ASCII de 'A'.
    }

    /**
     * @methode extractionPion.
     * @visibilite privée (private).
     * @description Extraire un pion en fonction de sa position.
     * @param _saisie    La saisie de l'utilisateur, en format String.
     * @param estEnnemi Indique si le pion appartient au robot, en format boolean.
     * @return Le pion extrait, ou null si non trouvé, en format Pion.
     */

    private Pion extractionPion(String _saisie, boolean estEnnemi)
    {
        String coord = _saisie.toUpperCase(); //Convertir la saisie en majuscule (si ce n'est pas déja le cas).
        Pion tempo = positionPions.get(coord); //Extraire le pion à la coordonnée saisie.

        if (tempo != null && tempo.estEnnemi != estEnnemi) //Si le pion existe et qu'il ne correspond pas à sa valeur estEnnemi, retourner null.
        {
            return null;
        }
        return tempo;
    }

    /**
     * @methode estDeplacementValide.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si le déplacement est valide.
     * @param coordActuelle   La coordonnée actuelle du pion, en format String.
     * @param coordDeplacement La coordonnée de déplacement, en format String.
     * @return true si le déplacement est valide, false sinon, en format boolean.
     */
    private boolean estDeplacementValide(String coordActuelle, String coordDeplacement)
    {
        // Calculer les coordonnées actuelles et de destination.
        int xActuel = extraireValeurX(coordActuelle);
        int yActuel = extraireValeurY(coordActuelle);

        int destinationX = extraireValeurX(coordDeplacement);
        int destinationY = extraireValeurY(coordDeplacement);

        // Vérifier que le déplacement est diagonal (différence en X et Y doit être 1).
        if (Math.abs(destinationX - xActuel) != 1 || Math.abs(destinationY - yActuel) != 1)
        {
            return false;
        }

        // Vérifier si on essaie de reculer dépendamment du pion.
        Pion pionActuel = positionPions.get(coordActuelle);
        boolean estEnnemi = pionActuel.estEnnemi; // True si c'est un pion ennemi, false sinon.

        // Si c'est un pion joueur, il ne doit pas reculer (pour les pions non-dames).
        if (!estEnnemi && !pionActuel.estDame)
        {
            if (destinationX > xActuel) { // Si le pion va vers le bas (vers les lignes plus grandes), c'est un recul.
                return false;
            }
        }

        // Si c'est un pion ennemi, il ne doit pas reculer non plus (pour les pions non-dames).
        if (estEnnemi && !pionActuel.estDame)
        {
            if (destinationX < xActuel) { // Si le pion va vers le haut (vers les lignes plus petites), c'est un recul.
                return false;
            }
        }

        // Vérifier si la case de destination est vide.
        Pion destinationPion = positionPions.get(coordDeplacement);

        if (destinationPion != null && destinationPion.estEnnemi == pionActuel.estEnnemi)
        {
            return false; // Il y a déjà un pion à la destination.
        }
        else if(destinationPion != null)
        {
            // Vérifier si la case suivante est valide.
            // Nous devons vérifier si la case après est valide et dans la plage A-H, 1-7.

            int prochainX = destinationX + (destinationX - xActuel);
            int prochainY = destinationY + (destinationY - yActuel);

            if(prochainX >= 0 && prochainX < 8 && prochainY >= 0 && prochainY < 8)
            {
                String coordProchain = extraireCoordDeXY(prochainX, prochainY); //Coordonnée de la case après le pion qui peut être mangé.
                Pion pionProchain = positionPions.get(coordProchain); //Extraire cette case "prochaine" afin de vérifier si un pion s'y trouve.

                if(pionProchain == null)
                {
                    // Si la case après est vide, le pion peut être mangé et déplacé.

                    return true;
                }
                return false; // Si la case suivante est occupée, le pion ne peut pas être mangé.
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @methode verifPromotionDame.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si un pion doit être promu en dame.
     * @param coordonnee La coordonnée du pion, en format String.
     */
    private void verifPromotionDame(String coordonnee)
    {
        Pion tempo = positionPions.get(coordonnee); //Extraire le pion à la coordonnée spécifiée.
        
        if(tempo.estEnnemi && extraireValeurX(coordonnee) == 7) //Si c'est un ennemi et qu'il se trouve à l'autre bout du plateau, faire la promotion.
        {
            tempo.estDame = true;
        } else if(!tempo.estEnnemi && extraireValeurX(coordonnee) == 0) //Si c'est l'un de nos pions et qu'il se trouve à l'autre bout...
        {
            tempo.estDame = true;
        }
    }

    /**
     * @methode mettreAJourPositionPions.
     * @visibilite privée (private).
     * @description Méthode pour mettre à jour la position des pions.
     * @param coordActuelle   La coordonnée actuelle du pion, en format String.
     * @param coordDeplacement La coordonnée de déplacement, en format String.
     * @param pion            Le pion à déplacer, en Pion.
     */
    private void mettreAJourPositionPions(String coordActuelle, String coordDeplacement, Pion pion)
    {
        // Calculer les coordonnées actuelles et de destination
        int xActuel = extraireValeurX(coordActuelle);
        int yActuel = extraireValeurY(coordActuelle);

        int destinationX = extraireValeurX(coordDeplacement);
        int destinationY = extraireValeurY(coordDeplacement);

        // Vérifier si le pion va manger un autre pion (déplacement sur un pion ennemi)
        Pion destinationPion = positionPions.get(coordDeplacement);

        // Si la destination est occupée par un pion ennemi, on doit manger ce pion
        if (destinationPion != null && destinationPion.estEnnemi != pion.estEnnemi)
        {
            // Calculer la case après le pion mangé
            int prochainX = destinationX + (destinationX - xActuel);  // Se déplace de la même distance, mais après le pion mangé
            int prochainY = destinationY + (destinationY - yActuel);

            if (prochainX >= 0 && prochainX < 8 && prochainY >= 0 && prochainY < 8)
            {
                String coordProchain = extraireCoordDeXY(prochainX, prochainY);

                // Vérifier que la case après est vide
                Pion pionProchain = positionPions.get(coordProchain);
                if (pionProchain == null) {
                    // Retirer le pion ennemi mangé
                    positionPions.remove(coordDeplacement);
                    // Déplacer notre pion sur la case suivante après le pion mangé
                    positionPions.put(coordProchain, pion);
                    verifPromotionDame(coordProchain);
                    afficherDeplacement(coordActuelle,coordProchain, pion.estEnnemi); //Afficher le déplacement qui vient d'être effectué.

                }
            }
        }
        else
        {
            // Si la destination est libre, simplement déplacer le pion
            positionPions.put(coordDeplacement, pion);
            verifPromotionDame(coordDeplacement);
            afficherDeplacement(coordActuelle, coordDeplacement, pion.estEnnemi); //Afficher le déplacement qui vient d'être effectué.
            
        }

        // Retirer le pion de sa position actuelle
        positionPions.remove(coordActuelle);
    }

    /**
     * @methode afficherDeplacement.
     * @visibilite privée (private)
     * @description Méthode pour afficher le déplacement d'un pion.
     * @param coordActuelle   La coordonnée actuelle du pion, en format String.
     * @param coordDeplacement La coordonnée de déplacement, en format String.
     * @param estEnnemi        Indique si le pion appartient au robot, en format boolean.
     */
    private void afficherDeplacement(String coordActuelle, String coordDeplacement, boolean estEnnemi)
    {
        String couleur = estEnnemi ? ROUGE : CYAN; //Si le pion est ennemi, la couleur du message est rouge, sinon c'est cyan.
        System.out.println(couleur + "Déplacement du pion " + coordActuelle + " à la case " + coordDeplacement + "." + REINIT); //Écrire le déplacement.
    }

    /**
     * @methode tourDuJoueur.
     * @visibilite publique (public).
     * @description Méthode pour gérer le tour du joueur.
     * @return true si le tour est terminé, false sinon, en format boolean.
     */
    public boolean tourDuJoueur() {
        Pion pionSelection = null; //Le pion qui sera sélectionné pour le déplacement.

        System.out.print("Sélectionnez un pion (ex: B6) : ");
        Scanner liseurSaisie = new Scanner(System.in); //Instancier un Scanner pour lire la saisie.
        String saisie = liseurSaisie.nextLine().toUpperCase(); //Lire l'entrée utilisateur.

        while (!saisieValide(saisie) || extractionPion(saisie, false) == null) //Tant que la saisie n'est pas valide ou que le pion n'existe pas...
        {
            System.out.println("Sélection invalide. Réessayez.\n");
            System.out.print("Sélectionnez un pion (ex: B6): ");
            saisie = liseurSaisie.nextLine().toUpperCase(); //Lire la saisie à nouveau.
        }

        pionSelection = extractionPion(saisie, false); //Lorsque c'est valide, on extrait le pion choisi.

        System.out.print("Sélectionnez une destination (ex: C5) ou 'XX' pour annuler : ");
        String saisieDestination = liseurSaisie.nextLine().toUpperCase(); //Lire la saisie pour la case de destination (mouvement).

        if (saisieDestination.matches("XX")) //Vérifier si l'utilisateur souhaite annuler sa sélection.
        {
            System.out.println("Annulation de la sélection. Veuillez recommencer.\n");
            return false; // Retour au début de la fonction, c'est encore le tour du joueur.
        }

        //Vérifier la saisie de la destination et si le déplacement est valide.
        if (!saisieValide(saisieDestination) || !estDeplacementValide(saisie, saisieDestination))
        {
            System.out.println("Déplacement invalide. Réessayez.\n");
            return false; // Retour au début de la fonction, c'est encore le tour du joueur.
        }

        // Appeler la méthode qui met à jour les positions, gère le mangeage de pion si nécessaire.
        mettreAJourPositionPions(saisie, saisieDestination, pionSelection);
        dessinerJeu();
        return true; // Retourne 'true' pour signaler que le tour a été terminé correctement (le tour va changer).
    }

    /**
     * @methode aucunMouvementPossible.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si un joueur (joueur ou ennemi) ne peut plus effectuer de mouvements valides.
     * @param estEnnemi Indique si on vérifie les mouvements pour l'ennemi (true) ou pour le joueur (false), en format boolean.
     * @return true si aucun mouvement n'est possible, false sinon, en format boolean.
     */
    private boolean aucunMouvementPossible(boolean estEnnemi)
    {
        // Parcourir tous les pions du joueur ou de l'ennemi.
        for (Map.Entry<String, Pion> entree : positionPions.entrySet())
        {
            String coordPion = entree.getKey(); // Coordonnée du pion.
            Pion pion = entree.getValue(); // Pion à cette coordonnée.

            // Vérifier si le pion appartient au joueur ou à l'ennemi en fonction du paramètre estEnnemi.
            if (pion.estEnnemi == estEnnemi)
            {
                // Vérifier les déplacements possibles pour ce pion.
                for (int x = -1; x <= 1; x++) // Déplacement vertical.
                {
                    for (int y = -1; y <= 1; y++) // Déplacement horizontal.
                    {

                        if (Math.abs(x) == Math.abs(y) && x != 0 && y != 0) // Vérifier si le déplacement est diagonal (différence absolue égale) et non nul.
                        {
                            // Calculer la destination potentielle.
                            String destination = extraireCoordDeXY(extraireValeurX(coordPion) + x, extraireValeurY(coordPion) + y);

                            // Vérifier si la destination est valide et si le déplacement est possible.
                            if (saisieValide(destination) && estDeplacementValide(coordPion, destination))
                            {
                                return false; // Un mouvement valide a été trouvé.
                            }
                        }
                    }
                }
            }
        }

        // Aucun mouvement valide n'a été trouvé pour ce joueur.
        return true;
    }

    /**
     * @methode tourDeLEnemi.
     * @visibilite publique (public).
     * @description Méthode pour gérer le tour de l'ennemi.
     * @return true si le tour est terminé, false sinon, en format boolean.
     */
    public boolean tourDeLEnemi()
    {
        // Liste des pions ennemis présents sur le plateau.
        List<String> pionsEnnemi = new ArrayList<>();

        // Remplir la liste avec les coordonnées des pions ennemis.
        for (Map.Entry<String, Pion> entree : positionPions.entrySet()) //Boucler dans chaque entree dans positionPions.
        {
            String coord = entree.getKey(); //Extraire la coordonnée.
            Pion pion = entree.getValue(); //Extraire le pion.
            if (pion.estEnnemi)  //Si le pion est ennemi, l'ajouter dans la liste des pions.
            {
                pionsEnnemi.add(coord);
            }
        }

        // Si aucun pion ennemi n'est disponible, terminer le tour.
        if (pionsEnnemi.isEmpty())
        {
            System.out.println("Aucun pion ennemi disponible. Tour terminé.");
            return false;  // Aucun pion ennemi à déplacer.
        }

        // Compteur pour éviter une boucle infinie.
        int compteurTentatives = 0;
        int maxTentatives = pionsEnnemi.size() * 4;  // Ne pas essayer plus de fois que le nombre de pions ennemis x leur mouvement possible.

        Random aleatoire = new Random(); //Instancier Random pour avoir accès aux méthodes aléatoires.
        String pionChoisi = null; //Les coordonnees du pion choisi.
        Pion pionSelectionne = null; //Le pion qui sera choisi au hasard.
        List<String> destinationsPossibles = null; //Liste contenant les mouvements possibles du pion.

        while (compteurTentatives < maxTentatives) //Boucler pour le nombre de tentatives max.
        {
            // Choisir un pion ennemi aléatoire de la liste de pions.
            pionChoisi = pionsEnnemi.get(aleatoire.nextInt(pionsEnnemi.size()));
            pionSelectionne = positionPions.get(pionChoisi); //Extraire le pion choisi à l'aide de ses coordonnées.

            // Liste des cases valides où le pion peut se déplacer.
            destinationsPossibles = new ArrayList<>();

            // Vérifier les déplacements possibles pour ce pion.
            for (int x = -1; x <= 1; x++) //Boucler de -1 à 1 pour un déplacement vertical.
            {
                for (int y = -1; y <= 1; y++) //Boucler de -1 à 1 pour un déplacement horizontal.
                {
                    // Vérifier si les déplacements sont diagonaux (différence absolue égale) et que le mouvement ne soit pas nul.
                    if (Math.abs(x) == Math.abs(y) && x != 0 && y != 0)
                    {
                        //Ajouter le x (-1 ou 1) et le y (-1 ou 1) à la case du pion choisi pour générer un déplacement.
                        String destination = extraireCoordDeXY(extraireValeurX(pionChoisi) + x, extraireValeurY(pionChoisi) + y);
                        // Vérifier si la destination est valide et si le déplacement est possible
                        if (saisieValide(destination) && estDeplacementValide(pionChoisi, destination))
                        {
                            destinationsPossibles.add(destination); //Ajouter la destination dans la liste des destinations possibles. (Plus aléatoire).
                        }
                    }
                }
            }

            // Si des destinations valides sont trouvées, effectuer le déplacement.
            if (!destinationsPossibles.isEmpty())
            {
                // Choisir une destination valide au hasard
                String destinationChoisie = destinationsPossibles.get(aleatoire.nextInt(destinationsPossibles.size()));
                mettreAJourPositionPions(pionChoisi, destinationChoisie, pionSelectionne);
                dessinerJeu();
                return true;  // Tour de l'ennemi terminé avec succès.
            }

            // Si l'on n'a pas retourné true (tour joué), incrémenter la tentative et réessayer.
            compteurTentatives++;
        }

        // Si le compteur atteint le nombre maximum de tentatives sans trouver un déplacement valide.
        System.out.println("Tous les pions ennemis sont bloqués. La partie est terminée.");
        finirPartie();
        return false;  // Aucun déplacement valide trouvé après plusieurs tentatives.
    }

    /**
     * @methode changerTour.
     * @visibilite publique (public).
     * @description Méthode pour changer le tour (joueur ou ennemi).
     */
    public void changerTour()
    {
        tourJoueur = !tourJoueur;
    }

    /**
     * @methode finirPartie.
     * @visibilite privée (private).
     * @description Méthode pour terminer la partie.
     */
    private void finirPartie()
    {
        System.exit(0); //Forcer l'arrêt du programme.
    }

    /**
     * @methode estFinDePartie.
     * @visibilite publique (public).
     * @description Méthode pour vérifier si la partie est terminée.
     * @return true si la partie est terminée, false sinon, en format boolean.
     */
    public boolean estFinDePartie()
    {
        // Vérifier si un joueur n'a plus de pions.
        List<Pion> pEnnemi = new ArrayList<>(); // Contient une liste de pions ennemis.
        List<Pion> pJoueur = new ArrayList<>(); // Contient une liste de pions du joueur.

        //Boucler dans tous les pions de la liste.
        for (Map.Entry<String, Pion> entree : positionPions.entrySet())
        {
            Pion tempo = entree.getValue();

            //Ajouter à la liste respective les pions extraits.
            if (tempo.estEnnemi)
            {
                pEnnemi.add(tempo);
            }
            else
            {
                pJoueur.add(tempo);
            }
        }

        //S'il n'y a plus aucun pions, la partie est fini.
        if (pEnnemi.isEmpty())
        {
            System.out.println("\nLa partie est terminée!\n"+ CYAN + "Le joueur BLANC a gagné!!" + REINIT);
            return true;
        }
        else if (pJoueur.isEmpty())
        {
            System.out.println("\nLa partie est terminée!\n"+ ROUGE + "Le joueur NOIR a gagné!!" + REINIT);
            return true;
        }

        // Vérifier si un joueur ne peut plus jouer.
        if (aucunMouvementPossible(false)) // Vérifier pour le joueur.
        {
            System.out.println("\nLa partie est terminée!\n" + ROUGE + "Le joueur BLANC ne peut plus jouer. Le joueur NOIR a gagné!!" + REINIT);
            return true;
        }
        else if (aucunMouvementPossible(true)) // Vérifier pour l'ennemi.
        {
            System.out.println("\nLa partie est terminée!\n" + CYAN + "Le joueur NOIR ne peut plus jouer. Le joueur BLANC a gagné!!" + REINIT);
            return true;
        }

        return false;
    }
}
