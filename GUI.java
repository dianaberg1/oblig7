import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {
    
    private Kontroll kontroll;
    private JFrame vindu;
    private JPanel panel, spill, knappepanel, hovedpanel, spillpanel;
    private JButton opp, hoyre, ned, venstre, avslutt;
    private JLabel lengde;
    public int slangelengde = 1; //Slangen starter med lengde 1
    private JLabel[][] ruter = new JLabel[12][12];

    public GUI(Kontroll kontroll){

        this.kontroll = kontroll;

        try {
            UIManager.setLookAndFeel(
	        UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            System.exit(0);
        }

        //Setter opp vinduet som skal dukke opp
        vindu = new JFrame("Slangespillet");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setPreferredSize(new Dimension(500, 600));
        vindu.pack();
        vindu.setVisible(true);

        //Lager et hovedpanel
        hovedpanel = new JPanel();
        vindu.add(hovedpanel);
        //Lager et spillpanel som skal plassere rutenettet i soer
        spillpanel = new JPanel();
        spillpanel.setLayout(new BorderLayout());
        hovedpanel.add(spillpanel, BorderLayout.SOUTH);

        //Lager et panel til rutenettet
        spill = new JPanel();
        spill.setLayout(new GridLayout(12,12));
        spill.setPreferredSize(new Dimension(400, 400));
        spillpanel.add(spill);

        for (int rad = 0; rad < 12; rad++){
            for (int kol = 0; kol < 12; kol++){
                JLabel b = new JLabel(" ");
                b.setBackground(Color.WHITE);
                b.setOpaque(true);
                Border grenser = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
                b.setBorder(grenser);
                ruter[rad][kol] = b;
                spill.add(b);
            }
        }

        //Lager et panel til den oeverste delen med knappene
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 100));
        hovedpanel.add(panel, BorderLayout.NORTH);

        //Lager et panel til knappene Opp, Ned, Hoyre og Venstre
        knappepanel = new JPanel();
        knappepanel.setLayout(new BorderLayout());
        panel.add(knappepanel, BorderLayout.CENTER);
        //Legger inn knappene
        opp = new JButton("Opp");
        hoyre = new JButton("Hoyre");
        ned = new JButton("Ned");
        venstre = new JButton("Venstre");

        //Legger til ActionListener i knappene slik at de faar en virkning

        class Opp implements ActionListener{
            @Override 
            public void actionPerformed(ActionEvent e){
                kontroll.settRetning("nord");
            }
        } 

        class Hoyre implements ActionListener{
            @Override 
            public void actionPerformed(ActionEvent e){
                kontroll.settRetning("oest");
            }
        } 

        class Soer implements ActionListener{
            @Override 
            public void actionPerformed(ActionEvent e){
                kontroll.settRetning("soer");
            }
        } 

        class Venstre implements ActionListener{
            @Override 
            public void actionPerformed(ActionEvent e){
                kontroll.settRetning("vest");
            }
        } 

        opp.addActionListener(new Opp());
        hoyre.addActionListener(new Hoyre());
        ned.addActionListener(new Soer());
        venstre.addActionListener(new Venstre());

        knappepanel.add(opp, BorderLayout.NORTH);
        knappepanel.add(hoyre, BorderLayout.EAST);
        knappepanel.add(ned, BorderLayout.SOUTH);
        knappepanel.add(venstre, BorderLayout.WEST);

        //Legger inn en Avslutt-knapp og en JLabel for aa vise lengden til slangen
        avslutt = new JButton("Avslutt");
        //Legger til ActionListener i Avslutt slik at knappen faar en virkning
        class Stoppspillet implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                kontroll.avsluttSpillet(); 
            }
        }
        avslutt.addActionListener(new Stoppspillet());
        panel.add(avslutt, BorderLayout.EAST);
        lengde = new JLabel("Lengde: 1");
        panel.add(lengde, BorderLayout.LINE_START);
    }

    //Metoder for aa farge en rute
    public void fargRute(int rad, int kol, Color farge){
        JLabel l = ruter[rad][kol];
        l.setOpaque(true);
        l.setBackground(farge);
    }

    //Metode for aa skrive inn et tegn i en rute
    public void skrivTegn(int rad, int kol, String tegn){
        JLabel l = ruter[rad][kol];
        l.setText(tegn);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        l.setForeground(Color.BLUE);
        l.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    }

    /*Metode som returnerer tegnet som er i en rute, brukes til aa faa slangen 
    til aa identifisere hvor det er skatter */
    public String sjekkRute(int rad, int kol){
        JLabel l = ruter[rad][kol];
        return l.getText();
    }

    //Metode for aa oppdatere poengsummen slik at brukeren ser det
    public void oppdaterPoeng(){
        lengde.setText("Lengde: " + slangelengde);
    }
}
