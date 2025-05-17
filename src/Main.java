import donjon.*;
import entitee.*;
import equipement.*;
import interactionUtilisateur.*;


public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        ListeEquipements listeEquipements = new ListeEquipements();
        Donjon(listeEquipements);
    }
}