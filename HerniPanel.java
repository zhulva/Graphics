package grafika;

import grafika.Tvar.TypTvaru;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Třída představující herní panel. Dědí z JPanelu a implementuje rozhraní pro
 * naslouchání událostem ActionEvent, KeyListener, MouseListener a MouseMotion
 *
 * @author Marek Lučný
 */
public class HerniPanel extends JPanel implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

    /**************************************************************************/
    /* Privátní atributy třídy HerniPanel */
    private final int sirkaPanelu = 600;  // preferovaná šířka panelu
    private final int vyskaPanelu = 400;  // preferovaná výška panelu
    private Timer casovac; // objekt časovače
    private final List<Tvar> tvary = new ArrayList<>();  // seznam objektů
    private Tvar aktivni; // aktivní objekt
    private JLabel info; // zpřístupňuje informační popisek statového řádku okna
    /* Pomocné proměnné */
    private int velikost; // uložení velikosti objektu
    private Point vychozi, puvodniPoziceObjektu; // uložení pozice objektu

    /**************************************************************************/
    /* Privátní metody třídy HerniPanel */

    /**
     * Metoda zjišťuje kolize mezi objekty
     */
    private void detekceKolize() {
        /* Proměnná kolize slouží k uložení výsledku detekce */
        boolean kolize;
        /* Vynulování příznaku kolize u všech objektů */
        for (Tvar t : tvary) {
            t.setKolize(false);
        }
        /* Cyklus prochází všechny objekty a zjišťuje možné kolize s ostatními objekty*/
        for (int i = 0; i < tvary.size() - 1; i++) {
            for (int p = i + 1; p < tvary.size(); p++) {
                kolize = false;
                /* pokud objekt s indexem i se překrývá s objektem s indexem p, nastává kolize */
                if (tvary.get(i) instanceof Kruh && tvary.get(p) instanceof Kruh) {
                    if (Kolize.kruhToKruh((Kruh) tvary.get(i), (Kruh) tvary.get(p))) {
                        System.out.println("Kolize kruh - kruh");
                        kolize = true;
                    }
                } else if (tvary.get(i) instanceof Kruh && tvary.get(p) instanceof Ctverec) {
                    if (Kolize.kruhToCtverec((Kruh) tvary.get(i), (Ctverec) tvary.get(p))) {
                        System.out.println("Kolize kruh - ctverec");
                        kolize = true;
                    }
                } else if (tvary.get(p) instanceof Kruh && tvary.get(i) instanceof Ctverec) {
                    if (Kolize.kruhToCtverec((Kruh) tvary.get(p), (Ctverec) tvary.get(i))) {
                        System.out.println("Kolize ctverec - kruh");
                        kolize = true;
                    }
                } else {
                    if (Kolize.ctverecToCtverec((Ctverec) tvary.get(i), (Ctverec) tvary.get(p))) {
                        System.out.println("Kolize ctverec - ctverec");
                        kolize = true;
                    }
                }
                /* V případě, že nastala kolize, nastav příznak kolize pro daný objekt 
                   (pokud již nebyl při některém průchodu cyklem nastaven ) */
                if (!tvary.get(i).isKolize()) {
                    tvary.get(i).setKolize(kolize);
                }
                if (!tvary.get(p).isKolize()) {
                    tvary.get(p).setKolize(kolize);
                }
            }
        }
    }

    /**
     * Metoda sloužící k inicializaci HernihoPanelu
     */
    private void init() {
        // nastavení velikosti panelu
        this.setPreferredSize(new Dimension(sirkaPanelu, vyskaPanelu));
        this.setSize(new Dimension(sirkaPanelu, vyskaPanelu));
        // nastavení barvy panelu
        this.setBackground(Color.black);
        // panel může mít zaměření - focus
        this.setFocusable(true);
        // vytvoření objektu časovače
        casovac = new Timer(100, this);
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Metoda sloužící k inicializaci proměnných a spuštění časovače.
     */
    public void start() {
        casovac.start();
    }
    
    /**************************************************************************/
    /* Veřejné metody třídy HerniPanel */
    /**
     * Konstruktor HernihoPanelu.
     */
    public HerniPanel() {
        init();
    }

    /**
     * Metoda slouží k překreslení herního panelu
     * @param g grafický kontext
     */
    @Override
    public void paintComponent(Graphics g) {
        /* Provedení metody předka */
        super.paintComponent(g);
        /* Zjištění možných kolizí objektů */
        detekceKolize();
        /* Vykreslení všech objektů */
        for (Tvar t : tvary) {
            t.vykresli(g);
        }
        /* Je-li některý objekt aktivní - zobrazí se informace o něm na stavovém řádku */
        if (aktivni != null) {
            this.info.setText(aktivni.toString());
        }
    }

    /**
     * Metoda vytváří nový objekt na herním panelu
     * @param typ - určuje typ objektu (TypTvaru.CTVEREC, TypTvaru.KRUH)
     * @param barva - specifikuje barvu výplně objektu
     * @param rychlost - nastavuje rychlost objektu
     */
    public void novyObjekt(TypTvaru typ, Color barva, int rychlost) {
        if (typ.equals(Tvar.TypTvaru.KRUH)) {
            tvary.add(new Kruh(this));
        }
        if (typ.equals(Tvar.TypTvaru.CTVEREC)) {
            tvary.add(new Ctverec(this));
        }
        tvary.get(tvary.size() - 1).setBarva(barva);
        tvary.get(tvary.size() - 1).setRychlost(rychlost);
        this.repaint();
    }

    /**
     * Metoda vytváří propojení na informační popisek (JLabel) stavového řádku
     * @param info
     */
    public void setInfo(JLabel info) {
        this.info = info;
    }

    /**
     * Metoda umožní odstranění aktivního objektu ze seznamu tvary
     */
    public void smazatObjekt() {
        for (int i = 0; i < tvary.size(); i++) {
            if (tvary.get(i).isAktivni()) {
                tvary.remove(i);
                break;
            }
        }
        this.repaint();
    }


/**************************************************************************************/    
/* Ohlasové metody na události časovače, myši a klávesnice */    
    /**
     * Definuje, co se má stát při vzniku události ActionEvent, kterou generuje
     * Timer.
     *
     * @param ae událost ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        for(Tvar t: tvary){
        if(t instanceof Ctverec){
        t.presun(t.zjistiPozici().x + t.getRychlost(),t.zjistiPozici().y );
        }
        }
        for(Tvar t: tvary){
        if(t instanceof Kruh){
        t.presun(t.zjistiPozici().x,t.zjistiPozici().y + t.getRychlost());
        }
        }
        this.repaint(); // metoda, která překreslí panel
    }

    /**
     * Metoda ošetřuje kliknutí myši v herním panelu
     * @param me - objekt obsahuje atributy a metody spojené s událostí vyvolanou myší
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        /* Na začátku se předpokládá, že žádný objekt nebyl označen jako aktivní */
        aktivni = null;
        /* Zjišťuje se přítomnost kurzoru myši v některém z objektů */
        for (Tvar t : tvary) {
            System.out.println(me.getX() + " - " + me.getY());
            /* V případě, že se kurzor nachází v objektu  */
            if (t.detekujKurzor(me.getX(), me.getY())) {
                /* stane se tanto objekt aktivním */
                aktivni = t;
                t.setAktivni(true);
                /* uložení pozice myši v okamžiku kliknutí do objektu */
                vychozi = new Point(me.getX(), me.getY());
                /* uložení aktuální pozice objektu */
                puvodniPoziceObjektu = aktivni.zjistiPozici();
            } else {
                /* kurzor myši není uvnitř objektu */
                t.setAktivni(false);
            }
        }
        /* v případě stisku pravého tlačítka myši v souvislosti s aktivním objektem */
        if ((aktivni != null) && (me.getButton() == MouseEvent.BUTTON3)) {
            /* dojde k nastavení barvy objektu s využitím dialogu pro výběr barev */
            aktivni.setBarva(JColorChooser.showDialog(this, "Vyber barvu textu", aktivni.getBarva()));
        }
        this.requestFocus(true);
        this.repaint();
    }

    /**
     * Ohlasová metoda ošetřující stisknutí (podržení) klávesy myši 
     * @param me
     */
    @Override
    public void mousePressed(MouseEvent me) {
    }

    /**
     * Ohlasová metoda ošetřující uvolnění klávesy myši 
     * @param me
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        /* Je-li aktivní některý z objektů */ 
        if (aktivni != null) {
            /* a byla současně stisknuta klávesa SHIFT */
            if ((me.getModifiers() & InputEvent.SHIFT_MASK) == InputEvent.SHIFT_MASK) {
                /* dojde ke změně velikosti objektu podle rozdílu mezi výchozí a aktuální pozicí kurzoru myši */
                velikost = (int) Math.sqrt(Math.pow(Math.abs(me.getX() - vychozi.x), 2) + Math.pow(Math.abs(me.getY() - vychozi.y), 2));
                if (velikost < 10) {
                    /* Velikost objektu nesmí být nižší než 10 */
                    velikost = 10;
                }
                /* Využití tzv. polymorfismu v OOP - zjišťuje se, zdali aktivní objekt 
                   je objektem typu Kruh nebo Čtverec  */
                if (aktivni instanceof Kruh) {
                    /* Aktivní objekt je přetypován na Kruh, což zpřístupní metodu 
                       pro nastavení průměru kruhu */
                    ((Kruh) aktivni).setPrumer(velikost);
                }
                if (aktivni instanceof Ctverec) {
                    /* Aktivní objekt je přetypován na Ctverec, což zpřístupní metodu 
                       pro nastavení strany čtverce */
                    ((Ctverec) aktivni).setStrana(velikost);
                }
            }
            /* Pokud byla stisknuta klávesa CTRL, dojde ke změně pozice objektu */
            if ((me.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
                aktivni.presun(puvodniPoziceObjektu.x + (me.getX() - vychozi.x), puvodniPoziceObjektu.y + (me.getY() - vychozi.y));
                vychozi = new Point(me.getX(), me.getY());
                puvodniPoziceObjektu = aktivni.zjistiPozici();
            }
            this.repaint();
        }
    }

    /**
     * Metoda je připravena na ošetření události vstupu myši na herní plochu
     * @param me
     */
    @Override
    public void mouseEntered(MouseEvent me) {
    }

    /**
     * Metoda je připravena na ošetření události opuštění myši z herní plochy
     * @param me
     */
    @Override
    public void mouseExited(MouseEvent me) {
    }

    /**
     * Metoda je připravena na ošetření události psaní z klávesnice
     * @param ke
     */

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * Ohlasová metoda řeší stisk některých kláves na klávesnici
     * Kurzorové klávesy ovládají pohyb aktivního objektu
     * Klávesy PageUp a PageDown umožňují zvyšovat/snižovat rychlost objektu
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
        if (aktivni != null) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    aktivni.presun(aktivni.zjistiPozici().x - aktivni.getRychlost(), aktivni.zjistiPozici().y);
                    break;
                case KeyEvent.VK_UP:
                    aktivni.presun(aktivni.zjistiPozici().x, aktivni.zjistiPozici().y - aktivni.getRychlost());
                    break;
                case KeyEvent.VK_RIGHT:
                    aktivni.presun(aktivni.zjistiPozici().x + aktivni.getRychlost(), aktivni.zjistiPozici().y);
                    break;
                case KeyEvent.VK_DOWN:
                    aktivni.presun(aktivni.zjistiPozici().x, aktivni.zjistiPozici().y + aktivni.getRychlost());
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    aktivni.setRychlost(aktivni.getRychlost() - 1);
                    break;
                case KeyEvent.VK_PAGE_UP:
                    aktivni.setRychlost(aktivni.getRychlost() + 1);
                    break;
            }
        }
        this.repaint();
    }

    /**
     * Ohlasová metoda je připravena na ošetření události uvolnění klávesy
     * @param 
     */
    @Override
    public void keyReleased(KeyEvent ke) {
    }

    /**
     * Ohlasová metoda řeší událost tažení myši v herním panelu
     * Při stisku SHIFT dochází ke změně velikosti objektu
     * Při stisku CTRL dochází ke změně pozice objektu
     * @param me
     */
    @Override
    public void mouseDragged(MouseEvent me) {
        if (aktivni != null) {
            if ((me.getModifiers() & InputEvent.SHIFT_MASK) == InputEvent.SHIFT_MASK) {
                velikost = (int) Math.sqrt(Math.pow(Math.abs(me.getX() - vychozi.x), 2) + Math.pow(Math.abs(me.getY() - vychozi.y), 2));
                if (velikost < 10) {
                    velikost = 10;
                }
                if (aktivni instanceof Kruh) {
                    ((Kruh) aktivni).setPrumer(velikost);
                }
                if (aktivni instanceof Ctverec) {
                    ((Ctverec) aktivni).setStrana(velikost);
                }
            }
            if ((me.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
                aktivni.presun(puvodniPoziceObjektu.x + (me.getX() - vychozi.x), puvodniPoziceObjektu.y + (me.getY() - vychozi.y));
                vychozi = new Point(me.getX(), me.getY());
                puvodniPoziceObjektu = aktivni.zjistiPozici();
                System.out.println(aktivni.zjistiPozici().toString());
            }
            this.repaint();
        }
    }

    /**
     * Ohlasová metoda je připravena reagovat na událost pohybu myši nad herním panelem
     * @param me
     */
    @Override
    public void mouseMoved(MouseEvent me) {
    }

    public void stop() {
       casovac.stop();
    }

}
