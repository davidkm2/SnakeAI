package SnakeAI;

import java.util.*;

class Snake {
    static int szelesseg = 400;
    static int magassag = 400;
    static int sorokszama = 25;
    static int oszlopokszama = 25;
    static int meret = 25;
    static double blokkszelesseg = szelesseg / meret;
    static double blokkmagassag = magassag / meret;
    static boolean gameover = false;
    static int fej_x = sorokszama /2 ;
    static int fej_y = oszlopokszama / 2;
    static Racs racs = new Racs(meret);
    static int alma_x;
    static int alma_y;
    static int szamlalo = 0;
    static LinkedList<Csomopont> Snake = new LinkedList<Csomopont>();

    public static boolean keretenbelul(Csomopont jelenlegi, int i, int j){
        if( ((jelenlegi.getX() + i) <0) || ( (jelenlegi.getX() + j) > meret-1)
                || ((jelenlegi.getY()+i) < 0) || ((jelenlegi.getY()+j) > meret-1)) {
            return false;
        }

        if(racs.racs[jelenlegi.getX()+i][jelenlegi.getY()+j].isResze())
            return false;

        if((jelenlegi.getX()+i == jelenlegi.getX() && jelenlegi.getY()+j == jelenlegi.getY())
        || ((i == -1) && (j == -1)) || ((i==-1) && (j == 1))
        || ((i == 1) && (j==-1)) || ((i==1) && (j == 1))){
            return false;
        }
        return true;
    }

    public static LinkedList<Csomopont> ut_ujjaepit(Csomopont jelen){
        Csomopont jelenlegi = jelen;
        LinkedList<Csomopont> utvonal = new LinkedList<Csomopont>();

        while (jelenlegi.getParent() != null){
            utvonal.add(jelenlegi.getParent());
            jelenlegi = jelenlegi.getParent();
        }

        return utvonal;
    }

    public static LinkedList<Csomopont> A_csillag(){
        LinkedList<Csomopont> nyiltak = new LinkedList<Csomopont>();

        nyiltak.add(racs.racs[fej_x][fej_y]);
        racs.racs[fej_x][fej_y].setUtkoltseg(0);
        racs.racs[fej_x][fej_y].setOsszkoltseg(racs.racs[fej_x][fej_y].heurisztika(alma_x, alma_y));

        LinkedList<Csomopont> zartak = new LinkedList<Csomopont>();
        LinkedList<Csomopont> utvonal = new LinkedList<Csomopont>();

        while(nyiltak.size() > 0){
            nyiltak.sort(Comparator.comparing(Csomopont::getOsszkoltseg));
            Csomopont jelenlegi = nyiltak.get(0);

            if( (jelenlegi.getX() == alma_x) && (jelenlegi.getY() == alma_y) )
                utvonal = ut_ujjaepit(jelenlegi);

            int index = nyiltak.indexOf(jelenlegi);
            nyiltak.remove(index);

            zartak.add(jelenlegi);

            for(int i=-1; i<2; i++){
                for(int j=-1; i<2; j++){

                    if(!keretenbelul(jelenlegi, i, j)){
                        continue;
                    }

                    Csomopont szomszed = racs.racs[jelenlegi.getX() + i][jelenlegi.getY() + j];

                    if(zartak.indexOf(szomszed) != -1){
                        continue;
                    }

                    int koltseg = szomszed.getUtkoltseg() + 1;

                    if(nyiltak.indexOf(szomszed) == -1){
                        nyiltak.add(szomszed);
                    }

                    szomszed.setParent(jelenlegi);
                    szomszed.setUtkoltseg(koltseg);
                    szomszed.setOsszkoltseg(szomszed.getUtkoltseg() + szomszed.heurisztika(alma_x, alma_y));
                }
            }

        }
        return utvonal;
    }

    public static void indit(){
        Csomopont farok;

        if(!gameover){
            LinkedList<Csomopont> utvonal = A_csillag();

            for(int i=0; i< utvonal.size(); i++){
                utvonal.get(i).setParent(null);
                utvonal.get(i).setUtkoltseg(-1);
                utvonal.get(i).setOsszkoltseg(-1);
            }

            for(int i=0; i<meret; i++){
                for(int j=0; j<meret; j++){
                    racs.racs[i][j].setParent(null);
                    racs.racs[i][j].setUtkoltseg(-1);
                    racs.racs[i][j].setOsszkoltseg(-1);
                }
            }
            Csomopont kovetkezo = new Csomopont();
            if(utvonal.size()>0){
                kovetkezo = utvonal.get(utvonal.size()-2);
            }
            else{
                gameover = true;
            }

            Snake.add(0, kovetkezo);
            kovetkezo.setResze(true);
            fej_x = kovetkezo.getX();
            fej_y = kovetkezo.getY();

            if(!((kovetkezo.getX() == alma_x) && (kovetkezo.getY() == alma_y))){
                farok = Snake.pop();
                farok.setResze(false);
                farok.setUtkoltseg(-1);
                farok.setOsszkoltseg(-1);
            } else{
                Random rand = new Random();
                do{
                    alma_x = rand.nextInt(meret);
                    alma_y = rand.nextInt(meret);
                    szamlalo++;
                } while (racs.racs[alma_x][alma_y].isResze());
            }

        }
    }

    public static void main(String[] args) {


        for(int i=0; i<meret; i++){
            for(int j=0; j<meret; j++){
                System.out.println(racs.racs[i][j].getX() + " " + racs.racs[i][j].getY());
            }
        }



        LinkedList<Csomopont> Snake = new LinkedList<Csomopont>();
        Snake.add(racs.racs[fej_x][fej_y]);
        racs.racs[fej_x][fej_y].setResze(true);

        int hossz = 1;



        Random rand = new Random();
        do {
            alma_x = rand.nextInt(meret);
            alma_y = rand.nextInt(meret);
        } while (racs.racs[alma_x][alma_y].isResze());

        indit();

    }

}