import java.util.Scanner;

public class Board
{

    private Tile[][] tileBoard; // .length() = (boardSize)²
    int boardSize;
    int bombAmount;

    int coords; // temporary number of coordinates from user.
    int x, y; // coordinates of the tile that's been picked.

    int[] bombLocations; // hmm..
    int xB, yB; // coords of bomb.

    private int num_of_turns;
    private int num_of_turns_to_win;

    private boolean is_playing; // false if picked bomb.

    public Scanner scanner = new Scanner(System.in);


    public Board ()
    {


        System.out.println(Game.cyanTXT("\n\nWELCOME TO MINESWEEPER!\n"));


        System.out.println("What size should the board be? (max: 50)");
        boardSize = scanner.nextInt();


        System.out.println("And how many bombs? ");
        bombAmount = scanner.nextInt();


        tileBoard = new Tile [boardSize + 1] [boardSize + 1];


        for (int i = 1; i < tileBoard.length; i++)
            for (int j = 1; j < tileBoard[i].length; j++)
                tileBoard[i][j] = new Tile();
        /** construct the tiles */








        randomizeBombLocations();
        num_of_turns = 0;
        num_of_turns_to_win = (boardSize * boardSize) - bombAmount;


        for (int i = 1; i < tileBoard.length; i++) {
            for (int j = 1; j < tileBoard[i].length; j++) {
                if (tileBoard[i][j].getIsBomb())
                    tileBoard[i][j].setMode(-2);
            }
        }


        if ( boardSize <= 50 ) // because how are you even gonna play with more than 2500 tiles?
        {
            is_playing = true;
            play();
        }
    }


    public void play()
    {
        do {
            System.out.println("\n\n");


            for (int i = 1; i < tileBoard.length; i++) {
                for (int j = 1; j < tileBoard[i].length; j++)
                    System.out.print(tileBoard[i][j].getStr());

                System.out.println();
            }
            /** print the tiles */


            System.out.print("\nEnter X coordinate: ");
            x = scanner.nextInt();

            System.out.print("Enter Y coordinate: ");
            y = scanner.nextInt();

            if (x > boardSize || x <= 0 || y > boardSize || y <= 0 )
            {
                System.out.println(Game.bold_redTXT("Invalid input.\nThere's no tile with such coordinates."));
                is_playing = false;
            }

            num_of_turns++;


            revealTile (x, y);


        }while (is_playing) ;

    }



    public void randomizeBombLocations()
    {
        for (int i = 1; i <= bombAmount; i++)
        {
            xB = (int) (Math.random() * boardSize + 1); // 1-boardSize
            yB = (int) (Math.random() * boardSize + 1); // 1-boardSize

            System.out.println("x: "+xB +"  y:"+ yB);

            tileBoard[yB][xB].setIs_bomb(true);
        }
    }



public void revealTile (int x, int y)
{
    tileBoard[y][x].setIs_picked(true);
    print(x,y);
}


public void print(int x, int y)
{
    if (tileBoard[y][x].getIsBomb())
        lost(x,y);

    else
    {
        tileBoard[y][x].setNum_of_bombs_around(checkNearBombs(x, y));
        tileBoard[y][x].setMode(tileBoard[y][x].getNum_of_bombs_around());

        switch (tileBoard[y][x].getMode())
        {
            case -2:
                tileBoard[y][x].setStr(Game.bold_redTXT("X  "));
                break;

            case 0:
                tileBoard[y][x].setStr(Game.greenTXT("0  "));
                break;
            case 1:
                tileBoard[y][x].setStr(Game.greenTXT("1  "));
                break;
            case 2:
                tileBoard[y][x].setStr(Game.yellowTXT("2  "));
                break;
            case 3:
                tileBoard[y][x].setStr(Game.redTXT("3  "));
                break;
            case 4:
                tileBoard[y][x].setStr(Game.redTXT("4  "));
                break;
            case 5:
                tileBoard[y][x].setStr(Game.redTXT("5  "));
                break;
            case 6:
                tileBoard[y][x].setStr(Game.redTXT("6  "));
                break;
            case 7:
                tileBoard[y][x].setStr(Game.redTXT("7  "));
                break;
            case 8:
                tileBoard[y][x].setStr(Game.redTXT("8  "));
                break;

        }
    }
}



public void lost (int x, int y)
{
    is_playing = false;

    for (int i = 1; i < tileBoard.length; i++) {
        for (int j = 1; j < tileBoard[i].length; j++) {
            if (tileBoard[i][j].getIsBomb())
                System.out.print(Game.bold_redTXT("X  "));
            else
                System.out.print(tileBoard[i][j].getStr());
        }
        System.out.println();
    }

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

