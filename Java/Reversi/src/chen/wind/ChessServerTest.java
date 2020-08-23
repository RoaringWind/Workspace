package chen.wind;

public class ChessServerTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Board board=new Board();
        //board.printBoard();
        board.checkPutChess(1, 3, 5, true);
        board.checkPutChess(-1, 2, 3, true);
        
    }

}
