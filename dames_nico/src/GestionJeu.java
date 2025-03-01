/**
 * @author Nicolas Arsenault
 * @programme dames_nico
 * @dateCreation 2025-02-14
 * @dateModification 2025-02-11
 * @descrpition Ce programme simule un jeu de dames, où l'utilisateur joue
 * contre un robot. Ce dernier fait des coups aléatoires. Les pions
 * peuvent bouger en diagonales et seulement en avançant. Les dames
 * peuvent reculer et avancer sur les diagonales. Pour manger un autre
 * pion, il faut que la case de la diagonale en sa direction soit libre.
 * En arrivant à l'autre bout du plateau, un pion est promu en dame.
 * La partie finie lorsqu'un joueur ne peut plus rien joueur ou lorsqu'il
 * n'y a plus aucune pièces.
 */

/**
 * @classe GestionJeu.
 * @visibilite publique (public).
 * @description Exécute l'appelle du menu du programme.
 *
 */

public class GestionJeu
{
    /**
     * @methode main.
     * @visibilite publique (public).
     * @description Méthode principale pour démarrer le jeu et gérer les tours.
     */

    public static void main(String[] args)
    {
        // Création du jeu
        Jeu nouveauJeu = new Jeu();
        boolean debutTourBlanc = true; //Indicateur pour l'affichage de "Tour du joueur BLANC".
        boolean debutTourNoir = true; //Indicateur pour l'affichage de "Tour du joueur NOIR".

        // Affichage initial du plateau.
        nouveauJeu.dessinerJeu();

        // Boucle de jeu.
        while (!nouveauJeu.estFinDePartie()) //Tant que la partie n'est pas finie, recommencer les tours.
        {
            //Si c'est le tour du joueur, joueur le tour, sinon, lancer le tour automatique...
            if (nouveauJeu.tourJoueur)
            {
                if(debutTourBlanc) //Si c'est le début du tour, afficher "Tour du joueur BLANC".
                {
                    System.out.println("Tour du joueur BLANC (O)");
                }
                debutTourBlanc = false; //Après l'affichage, ce n'est plus le début du tour.

                // Verifier le retour du tour du joueur, si le tour est fini ou non...
                boolean tourTermine = nouveauJeu.tourDuJoueur();

                if (tourTermine) //S'il est fini, c'est le début du tour BLANC (prochain) et on change de tour.
                {
                    debutTourBlanc = true;
                    nouveauJeu.changerTour(); // Changer de tour après le tour réussi.
                }

            }
            else //Si c'est le tour du robot.
            {
                //Si c'est le début du tour, afficher "Tour du joueur NOIR".
                if(debutTourNoir)
                {
                    System.out.println("Tour du joueur NOIR (X)");
                }
                debutTourNoir = false;
                // Si le tour de l'ennemi est réussi, on change de tour.
                boolean tourTermine = nouveauJeu.tourDeLEnemi();
                if (tourTermine)
                {
                    debutTourNoir = true; //Ça va donc devenir le debut du tour du noir la prochaine fois.
                    nouveauJeu.changerTour(); // Changer de tour après le tour réussi.
                }
            }
        }
    }
}
