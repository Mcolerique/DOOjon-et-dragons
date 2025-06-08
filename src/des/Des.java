package des;

import java.util.Random;

/**
 * La classe Des fournit des méthodes pour simuler le lancer de dés.
 */
public class Des {

    /**
     * Lance un dé avec un nombre de faces spécifié.
     *
     * @param nbFaces le nombre de faces du dé
     * @return un entier aléatoire entre 1 et le nombre de faces inclusivement
     */
    public static int lancerDes(int nbFaces) {
        Random random = new Random();
        return random.nextInt(1, (nbFaces + 1));
    }
}
