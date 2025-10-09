import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;
import partie.Partie;

public class Main {
    public static void main(String args[]){
        Partie p;
        String choix;

        Affichage.affiche("\n" +
                "\n" +
                "Bienvenue dans                                                                                                                                                          \n" +
                "                                                                                                                                                          \n" +
                "                                                                                                                                                  \n" +
                "\u001B[35mMMMMMMMb.                    \u001B[36m         68b                                                `MMMMMMMb.                                                      \n" +
                "\u001B[35mMM    `Mb                    \u001B[36m         Y89                                                 MM    `Mb                                                      \n" +
                "\u001B[35mMM     MM                    \u001B[36m                                               /M            MM     MM                                                      \n" +
                "\u001B[35mMM     MM  6MMMMMb   6MMMMMb \u001B[36m`MM 6MMb `MM  6MMMMMb `MM 6MMb         6MMMMb /MMMMM         MM     MM `MM 6MM  6MMMMb   6MMbMMM 6MMMMMb `MM 6MMb   6MMMMb\\ \n" +
                "\u001B[35mMM     MM 6M'   `Mb 6M'   `Mb\u001B[36m MMM9 `Mb MM 6M'   `Mb MMM9 `Mb       6M'  `Mb MM            MM     MM  MM69 \" 8M'  `Mb 6M'`Mb  6M'   `Mb MMM9 `Mb MM'    ` \n" +
                "\u001B[35mMM     MM MM     MM MM     MM\u001B[36m MM'   MM MM MM     MM MM'   MM       MM    MM MM            MM     MM  MM'        ,oMM MM  MM  MM     MM MM'   MM YM.      \n" +
                "\u001B[35mMM     MM MM     MM MM     MM\u001B[36m MM    MM MM MM     MM MM    MM       MMMMMMMM MM            MM     MM  MM     ,6MM9'MM YM.,M9  MM     MM MM    MM  YMMMMb  \n" +
                "\u001B[35mMM     MM MM     MM MM     MM\u001B[36m MM    MM MM MM     MM MM    MM       MM       MM            MM     MM  MM     MM'   MM  YMM9   MM     MM MM    MM      `Mb \n" +
                "\u001B[35mMM    .M9 YM.   ,M9 YM.   ,M9\u001B[36m MM    MM MM YM.   ,M9 MM    MM       YM    d9 YM.  ,        MM    .M9  MM     MM.  ,MM (M      YM.   ,M9 MM    MM L    ,MM \n" +
                "\u001B[35mMMMMMMM9'  YMMMMM9   YMMMMM9 \u001B[36m_MM_  _MM_MM  YMMMMM9 _MM_  _MM_       YMMMM9   YMMM9       _MMMMMMM9' _MM_    `YMMM9'Yb.YMMMMb. YMMMMM9 _MM_  _MM_MYMMMM9  \n" +
                "                                        MM                                                                            6M    Yb                            \n" +
                "                                    (8) M9                                                                            YM.   d9                            \n" +
                "                                     YMM9                                                                              YMMMM9                             \n" +
                "\n" +
                "\n" +
                "                                                                                                                                   \u001B[37mUne production \u001B[32mR&B\n" +
                "                                                                                                                                                  TP8\n" +
                "                                                                                                                                                  IIN\u001B[0m\n");


        do {
            p = new Partie();
            p.lancerPartie();
            Affichage.affiche("Lancer une nouvelle partie ? (y/n)");
            choix = Scanner.demandeString();
        }while (!choix.equals("n"));
    }
}