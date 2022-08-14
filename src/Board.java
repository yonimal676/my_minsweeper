import java.util.Scanner;

public class Board
{

        private Tile[][] tileBoard; // .length() = (boardSize)²
        int boardSize;
        int bombAmount;

        int coords; // temporary number of coordinates from user.
        int x, y; // coordinates of the tile that's been picked.

        int[] bombLocations;
        int xB, yB; // coords of bomb.

        private int num_of_turns;
        private int num_of_turns_to_win;

        private boolean is_playing = true; // false if picked bomb.


        public Board ()
        {
            Scanner scanner = new Scanner(System.in);


            System.out.println(Game.COLOR_CYAN +"\n\nWELCOME TO MINESWEEPER!\n" + Game.COLOR_RESET);


            System.out.println("What size should the board be? ");
            boardSize = scanner.nextInt();
            
            System.out.println("And how many bombs? ");
            bombAmount = scanner.nextInt();


            tileBoard = new Tile [boardSize] [boardSize];

            for (int i = 0; i < tileBoard.length; i++)
                for (int j = 0; j < tileBoard[i].length; j++)
                    tileBoard[i][j] = new Tile();
            /** construct the tiles */



            bombLocations = new int [bombAmount];

            for (int i = 0; i < bombAmount; i++)
                bombLocations[i] = (int) (Math.random() * (boardSize * boardSize) );


            for (int i = 0 ; i < bombLocations.length - 1; i++)
                for (int j = 1; j < i; j++)
                    if (bombLocations[j] == bombLocations[i])
                        bombLocations[j]++;


            for (int i = 0; i < bombLocations.length; i++)
            {
                // show

                xB = bombLocations[i] / 10;
                yB = bombLocations[i] % 10;

                tileBoard[yB][xB].setMode(-2);

            }





            num_of_turns = 0;
            num_of_turns_to_win = (boardSize * boardSize) - bombAmount;








            do {
                System.out.println("\n\n");


                for (int i = 0; i < tileBoard.length; i++)
                    for (int j = 0; j < tileBoard[i].length; j++)
                        tileBoard[i][j] = new Tile();
                /** construct the tiles */


                for (int i = 0; i < tileBoard.length; i++) {
                    for (int j = 0; j < tileBoard[i].length; j++)
                        System.out.print(tileBoard[i][j].getStr());

                    System.out.println();
                }
                /** print the tiles */













                System.out.print("\nEnter coordinates (x/y): ");
                coords = scanner.nextInt();

                x = coords / 10;
                y = coords % 10;




            } while (is_playing);




        }











        // This is how much I hate object-oriented programming.

        public int getNum_of_turns() {return num_of_turns;}
        public void setNum_of_turns(int num_of_turns) {this.num_of_turns = num_of_turns;}
        public Tile[][] getBoard_tiles() {return tileBoard;}
        public void setBoard_tiles(Tile[][] board_tiles) {this.tileBoard = board_tiles;}
        public int getNum_of_turns_to_win() {return num_of_turns_to_win;}
        public void setNum_of_turns_to_win(int num_of_turns_to_win) {this.num_of_turns_to_win = num_of_turns_to_win;}
        public boolean isIs_playing() {return is_playing;}
        public void setIs_playing(boolean is_playing) {this.is_playing = is_playing;}
}//class

