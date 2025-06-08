package interactionUtilisateur;

/**
 * La classe Scanner fournit des méthodes pour lire les entrées de l'utilisateur.
 */
public class Scanner {

    /**
     * Demande à l'utilisateur de saisir une chaîne de caractères.
     *
     * @return la chaîne de caractères saisie par l'utilisateur
     */
    public static String demandeString() {
        java.util.Scanner s = new java.util.Scanner(System.in);
        return s.nextLine();
    }

    /**
     * Demande à l'utilisateur de saisir un entier.
     *
     * @return l'entier saisi par l'utilisateur
     */
    public static int demandeInt() {
        java.util.Scanner s = new java.util.Scanner(System.in);
        return s.nextInt();
    }
}
