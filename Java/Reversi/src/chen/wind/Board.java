package chen.wind;

import java.nio.ByteBuffer;

public class Board {
    private final int boardSize = 8;
    private int[][] board = new int[boardSize][boardSize];
    private int playerNow;
    private int checkTime=0;
    /**
     * 初始化棋盘，黑白棋子交叉
     */
    public Board() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
        board[3][3] = board[4][4] = 1;
        board[3][4] = board[4][3] = -1;
        playerNow=1;
        printBoard();
        System.out.println("棋盘初始化完毕");
    }
    /**
     * 输出棋盘
     */
    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.printf("%2d ", board[i][j]);
            }
            System.out.println();
        }
    }
    public boolean checkPlayerNowHaveLegalPlace() {
        for (int i=0;i<boardSize;i++) {
            for(int j=0;j<boardSize;j++) {
                if(checkPutChess(getPlayerNow(), i, j, false)){
                    checkTime=0;
                    System.out.printf("当前棋手:%2d,坐标(%d,%d)可以放置棋子\n",getPlayerNow(),i,j);
                    return true;
                }
            }
        }
        checkTime++;
        if(checkTime==2) {
            System.out.println("没有合法位置，结束游戏");
        }else {
            System.out.printf("当前棋手:%d无处可下，换边\n",getPlayerNow());
            reversePlayer();
        }
        return false;
    }
    public boolean checkPutChess(int player, int x, int y , boolean putChess) {
        // 最后的putChess指是否放下棋子
        if (Math.abs(player) != 1) {
            System.out.println("检查棋子颜色");
            return false; // 正常棋子检测,是否在棋盘内检测
        }
        if(player!=playerNow) {
            System.out.println("没有轮到此棋子走");
            return false;
        }
        if (!inBoard(x, y)) {
            System.out.println("棋子不在棋盘内");
            return false;
        }
        if (board[x][y] != 0) {
            System.out.println("已有棋子啦");
            return false;
        }
        int[][] directions = { { 1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, 1 }, { 0, -1 } };// 8个方向
        boolean boardChanged = false;
        for (int i = 0; i < directions.length; i++) {
            int reverseTime = 0;
            int nextCheckPosX = x + directions[i][0], nextCheckPosY = y + directions[i][1];

            while (inBoard(nextCheckPosX, nextCheckPosY) && reverseTime < 2) {
                int chess = getBoard(nextCheckPosX, nextCheckPosY);
                if (chess == 0) {
                    // 无棋子
                    break;
                } else if (chess == (int) Math.pow(-1, reverseTime + 1) * player) {
                    reverseTime++;
                } else if (reverseTime == 0) {
                    // 搜索的位置既不是放下去的地方的近邻，之前也没有反转
                    break;
                } else {// 到这里，reverseTime必为1
                    ;
                }
                nextCheckPosX += directions[i][0];
                nextCheckPosY += directions[i][1];
            }
            if (reverseTime == 2) {
                boardChanged = true;
                if (putChess) {
                    int modifyPosX = x, modifyPosY = y;
                    while (modifyPosX != nextCheckPosX || modifyPosY != nextCheckPosY) {
                        // 草，可能在同一行或者同一列，所以是||而不是&&
                        putChess(player, modifyPosX, modifyPosY);
                        modifyPosX += directions[i][0];
                        modifyPosY += directions[i][1];
                    }
                }
                
            }
        }
        
        if (boardChanged && putChess) {
            System.out.printf("棋手:%d 成功放置棋子\n",getPlayerNow());
            reversePlayer();
            printBoard();
            checkPlayerNowHaveLegalPlace();
            return true;
        } else if(boardChanged){
            //System.out.println("此处可以放棋子");
            return true;
        }else if(putChess) {
            System.out.println("尝试放下棋子，但没有吃子，所以未改变棋盘，无操作");
            return false;
        }else {
            return false;
        }
    }
    public void setBoard(int player, int x, int y) {
        try {
            board[x][y] = player;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void putChess(int player,int x,int y) {
        board[x][y]=player;
    }
    public void reversePlayer() {
        playerNow*=-1;
    }
    public int getPlayerNow() {
        return playerNow;
    }
    public int getBoard(int x, int y) {
        int res = 0;
        try {
            res = board[x][y];
        } catch (Exception e) {
            System.out.println("请求坐标不在棋盘内");
            e.printStackTrace();
        }
        return res;
    }
    private boolean inBoard(int x, int y) {
        return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
    }
    public String getBoardinBuffer() {
        StringBuffer boardBuffer=new StringBuffer();
        for(int i=0;i<boardSize;i++) {
            for (int j=0;j<boardSize;j++) {
                boardBuffer.append(String.format("%2d ", getBoard(i, j)));
                
            }
            boardBuffer.append("\n");
        }
        return boardBuffer.toString();
        
    }
}
