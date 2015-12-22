/*
 * 
 */
package grafika;

import java.awt.Rectangle;

/**
 * Třída Kolize obsahuje statické metody pro zjištění různých typů kolizí mezi
 * grafickými objekty
 *
 * @author ml
 */
class Kolize {

     /**
     * Metoda zjišťuje kolize mezi dvěma objekty typu Kruh
     * @param k1 - první objekt kruhu v potenciální kolizi
     * @param k2 - druhý objekt kruhu
     */
    public static boolean kruhToKruh(Kruh k1, Kruh k2) {
        double vzdalenost = Math.sqrt(Math.pow(Math.abs(k1.pozice.x + k1.getPrumer() / 2 - k2.pozice.x - k2.getPrumer() / 2), 2)
                + Math.pow(Math.abs(k1.pozice.y + k1.getPrumer() / 2 - k2.pozice.y - k2.getPrumer() / 2), 2));
        return vzdalenost < k1.getPrumer() / 2 + k2.getPrumer() / 2;
    }

     /**
     * Metoda zjišťuje kolize mezi objektem typu Kruh a typu Ctverec
     * @param k - první objekt - Kruh
     * @param c - druhý objekt - Ctverec
     */
    public static boolean kruhToCtverec(Kruh k, Ctverec c) {
        int vzdalenostx = Math.abs(k.pozice.x + Math.round(k.getPrumer() / 2) - c.pozice.x - Math.round(c.getStrana() / 2));
        int vzdalenosty = Math.abs(k.pozice.y + Math.round(k.getPrumer() / 2) - c.pozice.y - Math.round(c.getStrana() / 2));
        double vzdalenostRohu = Math.pow(vzdalenostx - c.getStrana() / 2, 2) + Math.pow(vzdalenosty - c.getStrana() / 2, 2);
        if (vzdalenostx > (Math.round(c.getStrana() / 2) + Math.round(k.getPrumer() / 2))) {
            return false;
        }
        if (vzdalenosty > (Math.round(c.getStrana() / 2) + Math.round(k.getPrumer() / 2))) {
            return false;
        }
        if (vzdalenostx <= Math.round(c.getStrana() / 2)) {
            return true;
        }
        if (vzdalenosty <= Math.round(c.getStrana() / 2)) {
            return true;
        }
        return vzdalenostRohu <= Math.pow((k.getPrumer() / 2), 2);
    }

     
     /**
     * Metoda zjišťuje kolize mezi dvěma objekty typu Ctverec
     * @param c1 - první objekt - Ctverec
     * @param c2 - druhý objekt - Ctverec
     */
    public static boolean ctverecToCtverec(Ctverec c1, Ctverec c2) {
        Rectangle r1 = new Rectangle(c1.pozice.x, c1.pozice.y, c1.getStrana(), c1.getStrana());
        Rectangle r2 = new Rectangle(c2.pozice.x, c2.pozice.y, c2.getStrana(), c2.getStrana());
        return r1.intersects(r2);
    }
}
