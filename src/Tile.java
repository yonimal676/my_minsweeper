public class Tile
{
    private boolean is_picked;
    private boolean is_bomb;
    private int num_of_bombs_around;
    private String str;

    private int mode = -1;

    public Tile ()
    {
        is_picked = false;
        is_bomb = false;
        num_of_bombs_around = 0;

        if (mode == -1)
            str = "?  " ;
        else if (mode == -2)
            str = Game.COLOR_RED_BOLD + "X  " + Game.COLOR_RESET;
        else if (mode == 0)
            str = Game.COLOR_GREEN+ "0  " + Game.COLOR_RESET;
    }





    public int getMode() {return mode;}
    public void setMode(int mode) {this.mode = mode;}
    public boolean isIs_picked() {
        return is_picked;
    }
    public void setIs_picked(boolean is_picked) {
        this.is_picked = is_picked;
    }
    public boolean isIs_bomb() {
        return is_bomb;
    }
    public void setIs_bomb(boolean is_bomb) {
        this.is_bomb = is_bomb;
    }
    public int getNum_of_bombs_around() {
        return num_of_bombs_around;
    }
    public void setNum_of_bombs_around(int num_of_bombs_around) {
        this.num_of_bombs_around = num_of_bombs_around;
    }
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
}
