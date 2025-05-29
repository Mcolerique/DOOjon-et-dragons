package entitee.personnage.sort;

import donjon.Donjon;
import entitee.Entitee;

import java.util.ArrayList;

public abstract class Sort {
    private String m_nom;
    private String m_description;
    public Sort(String n, String desc)
    {
        m_nom = n;
        m_description = desc;
    }
    public abstract void utiliserSort(ArrayList<Entitee> listEntite);
    public String getNom()
    {
        return m_nom;
    }
}
