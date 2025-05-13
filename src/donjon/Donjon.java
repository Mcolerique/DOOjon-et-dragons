package donjon;

import entitee.Entitee;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Donjon {
    private Hashtable<Entitee, int[]> m_positionEntitee;
    public Donjon()
    {

    }
    public ArrayList<Entitee> listeCible(Entitee attaquant)
    {
        int distanceX, distanceY;
        int[] posEntitee;
        int[] maPosition = m_positionEntitee.get(attaquant);
        ArrayList<Entitee> listCible = new ArrayList<>();
        Set<Entitee> setKey = m_positionEntitee.keySet();

        for(Entitee i : setKey) {
            posEntitee = m_positionEntitee.get(i);
            distanceX = Math.abs(maPosition[0] - posEntitee[0]);
            distanceY = Math.abs(maPosition[1] - posEntitee[1]);
            if (distanceX <= attaquant.getPorteeArme() && distanceY <= attaquant.getPorteeArme()) {
                listCible.add(i);
            }
        }
        return listCible;
    }
}
