/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;

/**
 * Třída Tvar
 * @author ml
 */
public abstract class Tvar {
    //== Datové atributy (statické i instanční)======================================
    
    /**
     * Statický výčtový atribut pro určení typu grafického objektu
     * Je přístupný přímo prostřednictvím třídy, není třeba vytvářet instance
     */        
    public static enum TypTvaru { 
        CTVEREC, 
        KRUH
    } 

    /**
     * Definuje objekt df, který slouží k nastavení zaokrouhleného číselného formátu
     */
    public static final DecimalFormat df = new DecimalFormat("#.00");

    /* Privátní (členské) atributy třídy - přístupné pouze instancím dané třídy */
    private HerniPanel panel;   // reference na panel
    private Color barva = Color.WHITE; // barva výplně
    private int rychlost; // rychlost pohybu tvaru
    private double vzdalenost; // celková vzdálenost přesunu objektu
    /* Chráněné atributy třídy - přístupné potomkům */
    protected Point pozice; // pozice objektu
    protected boolean kolize = false; // je objekt v kolizi s jiným ojektem?
    protected boolean aktivni = false;  // je objekt aktivní?
    protected boolean viditelny;  // je objekt viditelný    
 
    /**
     *************************************************************************/
    /* Veřejné metody třídy HerniPanel */
    
    /* Konstruktor třídy Tvar
     * @param panel - odkazuje na herní panel, na němž jsou tvary vytvářeny
     * @param x - počáteční pozice x
     * @param y - počáteční pozice y
     */
    public Tvar(HerniPanel panel, int x, int y)
    {
        this.panel = panel; 
        this.pozice = new Point(x, y);
        this.viditelny = true;
        this.rychlost = 1;
    }
    
    /* Přetížený konstruktor třídy Tvar
     * @param panel - odkazuje na herní panel, na němž jsou tvary vytvářeny
     */
    public Tvar(HerniPanel panel)
    {
        /* */
        this(panel, (int) Math.floor(Math.random() * panel.getWidth()), (int) Math.floor(Math.random() * panel.getHeight()));
    }
   
    /**
     * Vrací zda je objekt visible či nikoli.
     * @return visible
     */
    public boolean getViditelny() {
        return viditelny;
    }

    /**
     * Nastavuje viditelnost objektu
     * @param viditelny
     */
    public void setViditelny(boolean viditelny) {
        this.viditelny = viditelny;
    }

    /**
     * Vrací barvu výplně
     * @return
     */
    public Color getBarva(){
        return this.barva;
    }    
    
    /**
     * Nastavuje barvu výplně
     * @param barva
     */
    public void setBarva(Color barva){
        this.barva = barva;
    }    

    /**
     * Vrací rychlost objektu
     * @return
     */
    public int getRychlost() {
        return rychlost;
    }

    /**
     * Nastavuje rychlost objektu
     * @param rychlost
     */
    public void setRychlost(int rychlost) {
        this.rychlost = rychlost;
        if (this.rychlost > 20) this.rychlost = 1;
    }

    /**
     * Indikuje, že je objekt v kolizi s jiným objektem
     * @return
     */
    public boolean isKolize() {
        return kolize;
    }

    /**
     * Nastavuje indikátor kolize
     * @param kolize
     */
    public void setKolize(boolean kolize) {
        this.kolize = kolize;
    }

    /**
     * Zjišťuje, zda je objekt aktivní
     * @return
     */
    public boolean isAktivni() {
        return aktivni;
    }

    /**
     * Nastavuje indikátor aktivity objektu
     * @param aktivni
     */
    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }
        
    /**
     * Preovádí přesun objektu na určené souřadnice
     * @param dx
     * @param dy
     */
    public void presun(int dx, int dy){
        this.vzdalenost += Math.sqrt(Math.pow(dx-this.pozice.x,2) + Math.pow(dy-this.pozice.y,2));
        this.pozice.x = dx;
        this.pozice.y = dy;
        if (this.pozice.x > panel.getWidth()) this.pozice.x = 0;
        if (this.pozice.y > panel.getHeight()) this.pozice.y = 0;
    }
    
    /**
     * Zjišťuje aktuální pozici objektu
     * @return
     */
    public Point zjistiPozici(){
        return this.pozice;
    }
    
    /**
     * Vytváří informativní řetězec o objektu 
     * @return
     */
    @Override
    public String toString(){
        return "pozice: " + this.pozice.x + "," + this.pozice.y + " | rychlost: " + this.rychlost + " | vzdálenost: " + df.format(this.vzdalenost);
    }

    //== Chráněné abstraktní metody (instancí i třídy) ======================================
    /**
     * Vykreslí objekt na aktuální souřadnice
     * @param g
     */
    abstract protected void vykresli(Graphics g);
    
    /**
     * Zjišťuje, zda se kurzor myši nachází v objektu
     * @param x
     * @param y
     * @return
     */
    abstract protected boolean detekujKurzor(int x, int y);

    /**
     * Slouží k výpočtu obvodu tvaru
     * @return
     */
    abstract protected float obvod();

    /**
     * Slouží k výpočtu plochy tvaru
     * @return
     */
    abstract protected float obsah();
}
