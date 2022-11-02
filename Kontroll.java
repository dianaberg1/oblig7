public class Kontroll {
    
    private GUI gui;
    private Modell modell;
    private Thread klokketraad;
    private String retning = "nord";

    public Kontroll(){
        gui = new GUI(this);
        modell = new Modell(gui);
        klokketraad = new Thread(new Teller());
        klokketraad.start();
    }


    //Avslutter spillet 
    public void avsluttSpillet(){
        System.exit(0);
    }

    //Flytter slangen 
    class Teller implements Runnable{
        @Override 
        public void run(){
            while (true){
                try {
                    Thread.sleep(modell.timer);
                } catch (Exception e) {
                    return;
                }
                flyttSlange();
            }
        }
    }

    //Setter retningen
    public void settRetning(String retning){
        this.retning = retning;
    }

    private void flyttSlange(){
        modell.flyttSlange(retning);
    }
    
}
