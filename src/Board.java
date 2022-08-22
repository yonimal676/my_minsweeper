import java.util.Scanner;

public class Board
{
    private Tile[][] tileBoard; // .length() = (boardSize)²
    int boardSize;
    int bombAmount;

    int x, y; // coordinates of the tile that's been picked.

    int xB, yB; // coords of bomb.

    private int num_of_turns;
    private int num_of_turns_to_win;
    private int how_many_previously_picked;
    private int how_many_picked;

    private boolean is_playing; // lost if false.

    public static Scanner scanner = new Scanner(System.in);


    public Board ()
    {
        System.out.println(Game.cyanTXT("\n\nWELCOME TO MINESWEEPER!\n"));

        System.out.println("What size should the board be? (min: 8, max: 50)");
        boardSize = scanner.nextInt();
        System.out.println("And how many bombs? ");
        bombAmount = scanner.nextInt();


        tileBoard = new Tile [boardSize + 1] [boardSize + 1];

        for (int i = 1; i < tileBoard.length; i++)
            for (int j = 1; j < tileBoard[i].length; j++)
                tileBoard[i][j] = new Tile();
        /** construct the tiles */


        num_of_turns = 0;
        how_many_picked = 0;
        how_many_previously_picked = 0;
        num_of_turns_to_win = (boardSize * boardSize) - bombAmount;


        randomizeBombLocations();

        for (int i = 1; i < tileBoard.length; i++)
            for (int j = 1; j < tileBoard[i].length; j++)
                if (tileBoard[i][j].getIsBomb())
                    tileBoard[i][j].setMode(-2);


        if ( boardSize <= 50 && boardSize >= 8 ) // because how are you even going to play with more than 2500 tiles?
        {
            is_playing = true;
            play();
        }
    }

    public void play()
    {
        do { // similar to 'run()'
            System.out.println("\n\n");

            for (int i = 0; i < tileBoard.length; i++)
            {
                for (int j = 0; j < tileBoard[i].length; j++)
                    if (i == 0 && j == 0)
                        System.out.print(Game.greenBG("     "));
                    else if (i == 0) {
                        if (j <= 9)
                            System.out.print(Game.greenBG(j + "  "));
                        else
                            System.out.print(Game.greenBG(j + " "));
                    }
                    else if (j == 0) {
                        if (i <= 9)
                            System.out.print(Game.greenBG(" " + i + " "));
                        else
                            System.out.print(Game.greenBG(" " + i ));
                    }
                    else
                        System.out.print(tileBoard[i][j].getStr());
                // end of inner loop
                System.out.println();
            }
            /** print the board */



            boolean temp = true;

            do {
                System.out.print("\nEnter X coordinate: ");
                x = scanner.nextInt();
                System.out.print("Enter Y coordinate: ");
                y = scanner.nextInt();

                if ( ! tileBoard[y][x].getIs_picked())
                    temp = false;
                else
                    System.out.println("You already picked this tile.\nTry again.\n");

            }while (temp);


            if (x > boardSize || x <= 0 || y > boardSize || y <= 0 )
            {
                System.out.println(Game.bold_redTXT("Invalid input.\nThere's no tile with such coordinates.\n\n"));
                is_playing = false;
            }


            num_of_turns++;

            for (int i = 1; i <= boardSize; i++)
                for (int j = 1; j <= boardSize; j++)
                    if (tileBoard[i][j].getIs_picked())
                        how_many_previously_picked++;    // 1. to know how many were previously picked,  ↓↓↓↓

            revealTile (x, y);


            for (int i = 1; i <= boardSize; i++)          // 2. to know how many were previously picked + newly picked,  ↓↓↓↓
                for (int j = 1; j <= boardSize; j++)
                    if (tileBoard[i][j].getIs_picked())
                        how_many_picked++;

            if (how_many_picked - how_many_previously_picked >= num_of_turns_to_win) // 3. to know how many were truly picked, because picking a tile could expose ↓↓↓
                endGame(true);                                             //    tiles that are already picked and count them. We don't want that.   ————————————

        } while (is_playing) ;
    }


    public void randomizeBombLocations()
    {
        for (int i = 1; i <= bombAmount; i++)
        {
            xB = (int) (Math.random() * boardSize + 1); // 1-boardSize
            yB = (int) (Math.random() * boardSize + 1); // 1-boardSize

            tileBoard[yB][xB].setIs_bomb(true);
        }
    }

public void revealTile (int x, int y)
{
    tileBoard[y][x].setIs_picked(true);

    if (tileBoard[y][x].getIsBomb())
        endGame(false);

    else // = not a bomb
        print(x,y, false);
}

public void print(int x, int y, boolean isMode0)
{
    if ( ! isMode0) { /** means we don't know if it's zero. */
        tileBoard[y][x].setNum_of_bombs_around(checkNearBombs(x, y));
        tileBoard[y][x].setMode(tileBoard[y][x].getNum_of_bombs_around());


        switch (tileBoard[y][x].getMode())
        {
            case -2:
                tileBoard[y][x].setStr(Game.bold_redTXT("  X"));
                break;

            case 0:
                tileBoard[y][x].setStr(Game.greenTXT("  0"));

                //  | position |       ---| x,y |

                //  | top left |       ---| -1, -1  |
                //  | top middle |     ---|  0, -1  |
                //  | top right |      ---| +1, -1  |
                //  | bottom left |    ---| -1, +1  |
                //  | bottom middle |  ---|  0, +1  |
                //  | bottom right |   ---| +1, +1  |
                //  | middle left |    ---| -1,  0  |
                //  | middle right |   ---| +1,  0  |


                if (y != boardSize)
                    revealNear(x, y + 1);

                if (y != 1)
                    revealNear(x, y - 1);

                if (x != boardSize)
                    revealNear(x + 1, y);

                if (x != 1)
                    revealNear(x - 1, y);

                if (x != boardSize && y != boardSize)
                    revealNear(x + 1, y + 1);

                if (x != 1 && y != 1)
                    revealNear(x - 1, y - 1);

                if (x != 1 && y != boardSize)
                    revealNear(x - 1, y + 1);

                if (x != boardSize && y != 1)
                    revealNear(x + 1, y - 1);
                break;
            case 1: tileBoard[y][x].setStr(Game.greenTXT("  1"));
                break;
            case 2: tileBoard[y][x].setStr(Game.yellowTXT("  2"));
                break;
            case 3: tileBoard[y][x].setStr(Game.redTXT("  3"));
                break;
            case 4: tileBoard[y][x].setStr(Game.redTXT("  4"));
                break;
            case 5: tileBoard[y][x].setStr(Game.redTXT("  5"));
                break;
            case 6: tileBoard[y][x].setStr(Game.redTXT("  6"));
                break;
            case 7: tileBoard[y][x].setStr(Game.redTXT("  7"));
                break;
            case 8: tileBoard[y][x].setStr(Game.redTXT("  8"));
                break;
        }
    }

    else {
        tileBoard[y][x].setStr(Game.greenTXT("  0"));

            if (y != boardSize)
                revealNear(x, y + 1);

            if (y != 1)
                revealNear(x, y - 1);

            if (x != boardSize)
                revealNear(x + 1, y);

            if (x != 1)
                revealNear(x - 1, y);

            if (x != boardSize && y != boardSize)
                revealNear(x + 1, y + 1);

            if (x != 1 && y != 1)
                revealNear(x - 1, y - 1);

            if (x != 1 && y != boardSize)
                revealNear(x - 1, y + 1);

            if (x != boardSize && y != 1)
                revealNear(x + 1, y - 1);
    }
}


public void revealNear (int x, int y)
{
    tileBoard[y][x].setIs_picked(true);
    tileBoard[y][x].setNum_of_bombs_around(checkNearBombs(x, y));
    tileBoard[y][x].setMode(tileBoard[y][x].getNum_of_bombs_around());

    tileBoard[y][x].setStr( Game.greenTXT ( "  " + tileBoard[y][x].getMode() ));
}

public void endGame (boolean wonOrLost) // true = won
{
    is_playing = false;

    for (int i = 0; i < tileBoard.length; i++)
    {
        for (int j = 0; j < tileBoard[i].length; j++)
            if (i == 0 && j == 0)
                System.out.print(Game.greenBG("     "));
            else if (i == 0) {
                if (j <= 9)
                    System.out.print(Game.greenBG(j + "  "));
                else
                    System.out.print(Game.greenBG(j + " "));
            }
            else if (j == 0) {
                if (i <= 9)
                    System.out.print(Game.greenBG(" " + i + " "));
                else
                    System.out.print(Game.greenBG(" " + i ));
            }
            else {
                if (tileBoard[i][j].getIsBomb())
                    System.out.print(Game.bold_redTXT("  X"));
                else
                    System.out.print(tileBoard[i][j].getStr());
            }
        // end of inner loop
        System.out.println();
    }

    if (wonOrLost)
        System.out.println(Game.cyanTXT("congratulations, you won!!"));
    else
        System.out.println(Game.cyanTXT("\nYou lost in "+ num_of_turns +" turns."));
}

public int checkNearBombs (int x, int y)
{
    int num = 0;

    if (x != boardSize)
        if (tileBoard[y][x + 1].getIsBomb())
            num++;
    if (x != 1)
        if (tileBoard[y][x - 1].getIsBomb())
            num++;
    if (y != 1)
        if (tileBoard[y - 1][x].getIsBomb())
        num++;
    if (y != boardSize)
        if (tileBoard[y + 1][x].getIsBomb())
            num++;
    if (y != boardSize && x != boardSize)
        if (tileBoard[y + 1][x + 1].getIsBomb())
            num++;
    if (y != 1 && x != boardSize)
        if (tileBoard[y - 1][x + 1].getIsBomb())
            num++;
    if (y != 1 && x != 1)
        if (tileBoard[y - 1][x - 1].getIsBomb())
            num++;
    if ( y != boardSize && x != 1)
        if (tileBoard[y + 1][x - 1].getIsBomb())
            num++;

    return num;
}
}//class

