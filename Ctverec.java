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
public class Ctverec extends Tvar
{
    
    private int strana;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     * @param panel
     */

    public Ctverec(HerniPanel panel)
    {
        super(panel);
        this.strana = 20;
    }

    public Ctverec(HerniPanel panel, int x, int y)
    {
        super(panel, x, y);
    }

    public Ctverec(HerniPanel panel, int x, int y, int a)
    {
        this(panel, x, y);
        this.strana = a;
    }
    
    //== Nesoukromé metody (instancí i třídy) ======================================

    public int getStrana() {
        return strana;
    }

    public void setStrana(int strana) {
        this.strana = strana;
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
        g.fillRect(this.pozice.x, this.pozice.y, this.strana, this.strana);
        g.setColor(new Color(255-this.getBarva().getRed(),255-this.getBarva().getGreen(),255-this.getBarva().getBlue()));
        g.drawString(String.valueOf(this.getRychlost()), this.pozice.x + g.getFontMetrics().stringWidth(String.valueOf(this.getRychlost()))/2, this.pozice.y + g.getFontMetrics().getHeight());
        if (this.aktivni) {
            g.setColor(Color.YELLOW);
            g.drawRect(this.pozice.x, this.pozice.y, this.strana, this.strana);
        }        
    }

    public String toString(){
        String vystup = super.toString();
        return "Čtverec | " + vystup + " | strana: " + this.strana + " | obvod " + df.format(this.obvod()) + " | obsah: " + df.format(this.obsah());
    }

    @Override
    public float obvod() {
        return this.strana * 4;
    }

    @Override
    public float obsah() {
        return this.strana * this.strana;
    }

    @Override
    public boolean detekujKurzor(int x, int y) {
        return ((x >= pozice.x && x <= pozice.x + this.strana) && (y >= pozice.y && y <= pozice.y + this.strana));
    }

    
    /**
     * Vrací zda je objekt visible či nikoli.
     * @return visible
     */
    //== Soukromé metody (instancí i třídy) ========================================

}
