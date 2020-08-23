package chen.wind;

import java.nio.ByteBuffer;

public class Board {
    private final int boardSize = 8;
    private int[][] board = new int[boardSize][boardSize];
    private int playerNow;
    private int checkTime=0;
    /**
     * ��ʼ�����̣��ڰ����ӽ���
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
        System.out.println("���̳�ʼ�����");
    }
    /**
     * �������
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
                    System.out.printf("��ǰ����:%2d,����(%d,%d)���Է�������\n",getPlayerNow(),i,j);
                    return true;
                }
            }
        }
        checkTime++;
        if(checkTime==2) {
            System.out.println("û�кϷ�λ�ã�������Ϸ");
        }else {
            System.out.printf("��ǰ����:%d�޴����£�����\n",getPlayerNow());
            reversePlayer();
        }
        return false;
    }
    public boolean checkPutChess(int player, int x, int y , boolean putChess) {
        // ����putChessָ�Ƿ��������
        if (Math.abs(player) != 1) {
            System.out.println("���������ɫ");
            return false; // �������Ӽ��,�Ƿ��������ڼ��
        }
        if(player!=playerNow) {
            System.out.println("û���ֵ���������");
            return false;
        }
        if (!inBoard(x, y)) {
            System.out.println("���Ӳ���������");
            return false;
        }
        if (board[x][y] != 0) {
            System.out.println("����������");
            return false;
        }
        int[][] directions = { { 1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, 1 }, { 0, -1 } };// 8������
        boolean boardChanged = false;
        for (int i = 0; i < directions.length; i++) {
            int reverseTime = 0;
            int nextCheckPosX = x + directions[i][0], nextCheckPosY = y + directions[i][1];

            while (inBoard(nextCheckPosX, nextCheckPosY) && reverseTime < 2) {
                int chess = getBoard(nextCheckPosX, nextCheckPosY);
                if (chess == 0) {
                    // ������
                    break;
                } else if (chess == (int) Math.pow(-1, reverseTime + 1) * player) {
                    reverseTime++;
                } else if (reverseTime == 0) {
                    // ������λ�üȲ��Ƿ���ȥ�ĵط��Ľ��ڣ�֮ǰҲû�з�ת
                    break;
                } else {// �����reverseTime��Ϊ1
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
                        // �ݣ�������ͬһ�л���ͬһ�У�������||������&&
                        putChess(player, modifyPosX, modifyPosY);
                        modifyPosX += directions[i][0];
                        modifyPosY += directions[i][1];
                    }
                }
                
            }
        }
        
        if (boardChanged && putChess) {
            System.out.printf("����:%d �ɹ���������\n",getPlayerNow());
            reversePlayer();
            printBoard();
            checkPlayerNowHaveLegalPlace();
            return true;
        } else if(boardChanged){
            //System.out.println("�˴����Է�����");
            return true;
        }else if(putChess) {
            System.out.println("���Է������ӣ���û�г��ӣ�����δ�ı����̣��޲���");
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
            System.out.println("�������겻��������");
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
