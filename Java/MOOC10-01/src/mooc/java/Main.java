package mooc.java;

import java.util.Arrays;

import java.util.Scanner;

class Currency {

    private String name; // 货币名称

    private int originalValue; // 原始值

    private int value; // 转换为人民币后的值

    public static String[] CURRENCY_NAME = { "CNY", "HKD", "USD", "EUR" };

    public static int[] CURRENCY_RATIO = { 100, 118, 15, 13 };

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public int getOriginalValue() {

        return originalValue;

    }

    public void setOriginalValue(int originalValue) {

        this.originalValue = originalValue;

    }

    public int getValue() {

        return value;

    }

    public void setValue(int value) {

        this.value = value;

    }

    public int compareTo(Currency currency) {
        // TODO Auto-generated method stub
        return 0;
    }

}

class HKD extends Currency implements Comparable<Currency> {
    public HKD(int a) {
        this.setValue(a);
        this.setOriginalValue(a);
    }
    private static final int j;
    static {
        String name=Thread.currentThread().getStackTrace()[1].getClassName();
        int i;
        for (i=1;i<CURRENCY_NAME.length;i++) {
            if(CURRENCY_NAME[i].equals(name.substring(name.length()-3))) {
                break;
            }
        }
        j=i;
    }
    
    @Override
    public int compareTo(Currency o) {
        if (this.getValue()>o.getValue()) {
            return 1;
        }else if(this.getValue()==o.getValue()) {
            return 0;
        }else {
            return -1;
        }
    }
    
    @Override
    public void setValue(int value) {
        super.setValue(value*CURRENCY_RATIO[j]/CURRENCY_RATIO[0]);
    }
    // 实现你的构造函数与Comparable中的接口
    
    @Override
    public String getName() {
        return CURRENCY_NAME[j];
    }
}

class USD extends Currency implements Comparable<Currency> {
    public USD(int a) {
        this.setValue(a);
        this.setOriginalValue(a);
    }
    private static final int j;
    static {
        String name=Thread.currentThread().getStackTrace()[1].getClassName();
        int i;
        for (i=1;i<CURRENCY_NAME.length;i++) {
            if(CURRENCY_NAME[i].equals(name.substring(name.length()-3))) {
                break;
            }
        }
        j=i;
    }
    @Override
    public int compareTo(Currency o) {
        if (this.getValue()>o.getValue()) {
            return 1;
        }else if(this.getValue()==o.getValue()) {
            return 0;
        }else {
            return -1;
        }
    }
    @Override
    public String getName() {
        return CURRENCY_NAME[j];
    }
    @Override
    public void setValue(int value) {
        super.setValue(value*CURRENCY_RATIO[j]/CURRENCY_RATIO[0]);
    }
}

class EUR extends Currency implements Comparable<Currency> {
    public EUR(int a) {
        this.setValue(a);
        this.setOriginalValue(a);
    }
    private static final int j;
    static {
        String name=Thread.currentThread().getStackTrace()[1].getClassName();
        int i;
        for (i=1;i<CURRENCY_NAME.length;i++) {
            if(CURRENCY_NAME[i].equals(name.substring(name.length()-3))) {
                break;
            }
        }
        j=i;
    }
    @Override
    public String getName() {
        return CURRENCY_NAME[j];
    }
    @Override
    public int compareTo(Currency o) {
        if (this.getValue()>o.getValue()) {
            return 1;
        }else if(this.getValue()==o.getValue()) {
            return 0;
        }else {
            return -1;
        }
    }
    
    @Override
    public void setValue(int value) {
        super.setValue(value*CURRENCY_RATIO[j]/CURRENCY_RATIO[0]);
    }
}

public class Main {

    public static void main(String[] args) {

        Currency[] cs = new Currency[3];

        // 初始化

        Scanner sc = new Scanner(System.in);

        // 利用hasNextXXX()判断是否还有下一输入项

        int a = 0;

        int b = 0;

        int c = 0;

        if (sc.hasNext()) {

            a = sc.nextInt();

            cs[0] = new HKD(a);

        }

        if (sc.hasNext()) {

            b = sc.nextInt();

            cs[1] = new USD(b);

        }

        if (sc.hasNext()) {

            c = sc.nextInt();

            cs[2] = new EUR(c);

        }

        // 初始化结束

        // 请补充排序

        // 请补充输出结果
        Currency tmp=null;
//        for (int i=0;i<3;i++) {
//            for (int j=i+1;j<3;j++) {
//                if (cs[i].compareTo(cs[j])>0) {
//                    tmp=cs[i];
//                    cs[i]=cs[j];
//                    cs[j]=tmp;
//                }
//            }
//        }
        for (int i = 0; i < cs.length - 1; i++) {
            boolean Flag = false; // 是否发生交换。没有交换，提前跳出外层循环
            for (int j = 0; j < cs.length - 1 - i; j++) {
                if (cs[j].compareTo(cs[j+1])<0) {
                    tmp = cs[j];
                    cs[j] = cs[j + 1];
                    cs[j + 1] = tmp;
                    Flag = true;
                }
            }
            if (!Flag)
            {
                break;
            }
        }
        for (int i=0;i<3;i++) {
            System.out.printf("%s%d\n",cs[i].getName(),cs[i].getOriginalValue());
        }
    }

}