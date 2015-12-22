package grafika;
/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/*******************************************************************************
 * Třída Okno slouží ke spouštění aplikace ...
 *
 * @author    jméno autora
 * @version   0.00.000
 */
public final class Okno extends JFrame
{
   /* Deklarace komponent okenní aplikace */
    private HerniPanel panel;
    private JToolBar toolBar;
    private JPanel statusBar;
    private JButton novyKruh, novyCtverec, smazat, start, stop;
    public JLabel info;
    
    /**
     * Konstruktor třídy Okno
     * Slouží k inicializaci grafických komponent
     */
    public Okno() {
        this.setTitle("Grafika");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        addComponentsToPane(this.getContentPane());
        this.pack();
        panel.requestFocus();
    } 
    
    /**
     * Metoda přidává komponenty na plochu formuláře-okna
     * @param pane - kontejner, který slouží k uspořádání grafických prvků v okně
     */
    public void addComponentsToPane(Container pane) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
        /* Výchozí orientace pro umisťování komponent v okně */ 
        pane.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
         
        /* Vytvoření nástrojobého panelu v horní části aplikace */
        toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(this.getWidth(), 30));
        pane.add(toolBar, BorderLayout.PAGE_START);
        
        /* Vytvoření herního panelu uprostřed okna */
        panel = new HerniPanel();
        pane.add(panel, BorderLayout.CENTER);
         
        /* Vytvoření stavového řádku ve spodní části okna */
        statusBar = new JPanel();
        statusBar.setPreferredSize(new Dimension(this.getWidth()/2, 30));
        statusBar.setBackground(Color.yellow);
        statusBar.setLayout(new java.awt.GridLayout(1, 2));
        pane.add(statusBar, BorderLayout.PAGE_END);
        
        /* tlačítko pro vytváření instancí třídy Kruh */
        novyKruh = new JButton("Nový kruh");
        /* přidání události k tlačítku */
        novyKruh.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               novyKruhActionPerformed(evt);
            }
        });               
        /* Přidání tlačítka do toolBaru */
        toolBar.add(novyKruh);
        
        /* tlačítko pro vytváření instancí třídy Ctverec */
        novyCtverec = new JButton("Nový čtverec");
        novyCtverec.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               novyCtverecActionPerformed(evt);
            }
        });               
        toolBar.add(novyCtverec);
        
        /* tlačítko pro mazání aktivního objektu */
        smazat = new JButton("Smazat");
        smazat.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               smazatActionPerformed(evt);
            }
        });
        toolBar.add(smazat);
        
        /* tlačítko pro zapnutí časovače */
        start = new JButton("Start");
        start.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               startActionPerformed(evt);
            }
        });
        toolBar.add(start);
        
        /* tlačítko pro vypnutí časovače */
        stop = new JButton("Stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               stopActionPerformed(evt);
            }
        });
        toolBar.add(stop);
        
        /* vytvoření informačního popisku na stavovém řádku */
        info = new JLabel("Informace o objektu");
        statusBar.add(info);
        panel.setInfo(info);
    }
    
    /***************************************************************************
     * Ohlasové metody na události tlačítek */

    private void novyKruhActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.novyObjekt(Tvar.TypTvaru.KRUH, Color.green, 5);
    }   
     
    private void novyCtverecActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.novyObjekt(Tvar.TypTvaru.CTVEREC, Color.red, 2);
    }   

    private void smazatActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.smazatObjekt();
    }   
    
    private void startActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.start();
    }   
    
    private void stopActionPerformed(java.awt.event.ActionEvent evt) {                                         
        panel.stop();
    }   
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        Okno hlavniOkno = new Okno();
        hlavniOkno.setVisible(true);
    }
}
