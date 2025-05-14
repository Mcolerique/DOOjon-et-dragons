package des;
import java.util.Random;

public class Des {
    public static int lancerDes(int nbFaces)
    {
        Random random = new Random();
        return random.nextInt(1, (nbFaces+1));
    }
}
