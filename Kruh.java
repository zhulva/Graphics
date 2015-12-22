package grafika;

/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
import java.awt.Color;
import java.awt.Graphics;

/*******************************************************************************
 * Instance třídy Kruh představují ...
 *
 * @author    jméno autora
 * @version   0.00.000
 */
public class Kruh extends Tvar
{
    
    private int prumer;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     * @param panel
     */

    public Kruh(HerniPanel panel)
    {
        super(panel);
        this.prumer = 30;
    }

    public Kruh(HerniPanel panel, int x, int y)
    {
        super(panel, x, y);
    }

    public Kruh(HerniPanel panel, int x, int y, int d)
    {
        this(panel, x, y);
        this.prumer = d;
    }
    
    //== Nesoukromé metody (instancí i třídy) ======================================

    public int getPrumer() {
        return prumer;
    }

    public void setPrumer(int prumer) {
        this.prumer = prumer;
    }
    
    /**
     * Vykreslí objekt na aktuální souřadnice
     * @param g grafický kontext
     */
    @Override
    public void vykresli(Graphics g) {
        if (this.kolize) 
            g.setColor(Color.WHITE);
        else
            g.setColor(this.getBarva());          
        g.fillOval(this.pozice.x, this.pozice.y, this.prumer, this.prumer);
        g.setColor(new Color(255-this.getBarva().getRed(),255-this.getBarva().getGreen(),255-this.getBarva().getBlue()));
        g.drawString(String.valueOf(this.getRychlost()), this.pozice.x + this.getPrumer()/2, this.pozice.y + this.getPrumer()/2);
        if (this.aktivni) {
            g.setColor(Color.YELLOW);
            g.drawOval(this.pozice.x, this.pozice.y, this.prumer, this.prumer);
        }
    }

    public String toString(){
        String vystup = super.toString();
        return "Kruh | " + vystup + " | průměr: " + this.prumer + " | obvod " + df.format(this.obvod()) + " | obsah: " + df.format(this.obsah());
    }

    @Override
    public float obvod() {
        return (float) Math.PI * this.prumer; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float obsah() {
        return  (float) (Math.PI * (float) Math.pow(this.prumer/2,2)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean detekujKurzor(int x, int y) {
        return Math.sqrt(Math.pow(Math.abs(x-pozice.x-this.prumer/2),2) + Math.pow(Math.abs(y-pozice.y-this.prumer/2),2)) <= this.prumer/2;
    }

    
    /**
     * Vrací zda je objekt visible či nikoli.
     * @return visible
     */
    //== Soukromé metody (instancí i třídy) ========================================

}
