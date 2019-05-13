package SnakeAI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

@Getter
@Setter
@NoArgsConstructor
public class Csomopont {
    private int x;
    private int y;
    private boolean resze = false;
    private Csomopont parent = null;
    private int utkoltseg = -1;
    private int osszkoltseg = -1;

    public Csomopont(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int heurisztika(int vegpont_x, int vegpont_y){
        return ( abs((vegpont_x - this.x)) + abs((vegpont_y - this.y)) );
    }

}
