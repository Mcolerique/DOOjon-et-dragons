package interactionUtilisateur;

public class Scanner {
    public static String demandeString()
    {
        java.util.Scanner s = new java.util.Scanner(System.in);
        return s.nextLine();
    }
    public static int demandeInt()
    {
        java.util.Scanner s = new java.util.Scanner(System.in);
        return s.nextInt();
    }
}
