package SnakeAI;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Racs {
    private int meret;
    public Csomopont[][] racs;

    public Racs(int meret){
        this.meret = meret;
        this.racs = new Csomopont[meret][meret];
        for(int i=0; i<meret; i++){
            for(int j=0; j<meret; j++){
                racs[i][j] = new Csomopont(i, j);
            }
        }
    }
}
