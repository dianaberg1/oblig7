import java.util.ArrayList;
import java.awt.Color;

public class Modell{

    private GUI gui;
    private Rute[][] rutenett;
    private ArrayList<Rute> slange = new ArrayList<>();
    public int timer = 2000; //Antall millisekunder slangen skal sove mellom hver bevegelse
    
    public Modell(GUI gui){
        this.gui = gui;
        this.rutenett = new Rute[12][12];
        
        //Setter brettet
        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++){
                Rute rute = new Rute();
                rute.rad = i;
                rute.kol = j;
                rutenett[i][j] = rute;
            }
        }

        //Plasserer 10 skatter i tilfeldige posisjoner
        for (int i = 0; i < 10; i++){
            int rad = trekk(0, 11);
            int kol = trekk(0, 11);
            gui.skrivTegn(rad, kol, "●");
        }

        //Oppretter slangen i midten av brettet
        slange = new ArrayList<Rute>();
        int rad = 5;
        int kol = 5;
        Rute slangeHode = rutenett[rad][kol];
        slange.add(slangeHode);
        gui.fargRute(rad, kol, Color.PINK);
        
    }

    //Trekker et tilfeldig heltall i intervallet [a, b]
    public static int trekk(int a, int b){
        return (int)(Math.random()*(b-a+1))+a;
    }

    //-----------------------------------------------------------------------------------------
    //NB! Metodene flyttSlange og gyldig er inspirert av koden fra plenumstimen i uke 15 (9.mai)
    //------------------------------------------------------------------------------------------

    public void flyttSlange(String retning){

        Rute nyttHode = new Rute();
        nyttHode.rad = slange.get(0).rad;
        nyttHode.kol = slange.get(0).kol;

        //Hvis retningen er nord maa vi bevege oss oppover, altsaa minke raden vi er paa
        if (retning == "nord"){
            nyttHode.rad--;
        }
        //Hvis retningen er oest maa vi oeke kolonnen vi er paa
        if (retning == "oest"){
            nyttHode.kol++;
        }
        //Hvis retningen er soer maa vi bevege oss nedover, altsaa oeke raden vi er paa
        if (retning == "soer"){
            nyttHode.rad++;
        }
        //Hvis retningen er vest maa vi minke kolonnen vi er paa
        if (retning == "vest"){
            nyttHode.kol--;
        }

        if (gyldig(nyttHode.rad, nyttHode.kol)){
            slange.add(0, nyttHode); //Legger det nye hodet i starten av slangen
            //Hvis slangen faar lengde 5 oekes hastigheten til 1000 millisekunder
            if (gui.slangelengde == 5){
                timer = 1000;
            } //Hvis slangen faar lengde 10 oekes hastigheten til 500 millisekunder
            if (gui.slangelengde == 10){
                timer = 500;
            }
            /*Hvis slangehodet befinner seg i en rute hvor det er en skatt, blir ruten til en del av
            slangen og det legges til en ny skatt. Oppdaterer ogsaa poengsummen (lengden til slangen)*/
            if (gui.sjekkRute(nyttHode.rad, nyttHode.kol).equals("●")){
                gui.skrivTegn(nyttHode.rad, nyttHode.kol, " ");
                gui.fargRute(nyttHode.rad, nyttHode.kol, Color.PINK);
                gui.skrivTegn(trekk(0,11), trekk(0,11), "●"); //Legger til nye skatter
                gui.slangelengde++;
                gui.oppdaterPoeng();
            } else {
                /*Hvis det ikke er en skatt i ruten blir ruten farget og vi henter det siste elementet 
                i slangen, farger den hvit og fjerner det fra slangearrayen slik at halen har blitt flyttet*/
                gui.fargRute(nyttHode.rad, nyttHode.kol, Color.PINK);
                Rute hale = slange.get(slange.size()-1);
                gui.fargRute(hale.rad, hale.kol, Color.WHITE);
                slange.remove(hale);
            }
        } else {
            System.exit(0);
        }
    }

    /*Sjekker om slangen befinner seg i gyldige koordinater, hvis den gaar utenfor rutenettet eller
    kolliderer med seg selv saa avsluttes spillet*/
    public boolean gyldig(int rad, int kol){

        if (rad < 0 || rad >= 12){
            System.out.println("\nSlangen kolliderte med veggen! Du fikk " + gui.slangelengde + " poeng.\n");
            return false;
        }
        if (kol < 0 || kol >= 12){
            System.out.println("\nSlangen kolliderte med veggen! Du fikk " + gui.slangelengde + " poeng.\n");
            return false;
        }
        for (Rute del : slange){
            if (del.rad == rad && del.kol == kol){
                System.out.println("\nSlangen kolliderte med seg selv! Du fikk " + gui.slangelengde + " poeng.\n");
                return false;
            }
        }
        return true;
    }
}