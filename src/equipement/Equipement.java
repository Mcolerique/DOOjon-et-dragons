package equipement;

import entitee.Monstre;
import equipement.arme.Arme;
import equipement.armure.Armure;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public abstract class Equipement {
    private String m_nom;
    protected int[] m_modifStat = new int[5];
    protected TypeEquipement m_typeEquipement;

    public Equipement(String nom) {
        m_nom = nom;
        m_modifStat[0] = 0;
        m_modifStat[1] = 0;
        m_modifStat[2] = 0;
        m_modifStat[3] = 0;
        m_modifStat[4] = 0;
    }

    public int[] getModifStat() {
        return m_modifStat;
    }

    public String getNom() {
        return m_nom;
    }
    public TypeEquipement getType()
    {
        return m_typeEquipement;
    }
    @Override
    public String toString() {
        return m_nom;
    }


}
