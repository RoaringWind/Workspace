package idcard;

import java.time.*;
import java.util.HashMap;
import java.util.Scanner;

public class IDcard01 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in = sc.next();
        sc.close();
        if (check(in)) {
            System.out.printf("%s-%s-%s\n", in.substring(6, 10), in.substring(10, 12), in.substring(12, 14));
        } else {
            System.out.println("0000-00-00");
        }

    }

    private static HashMap<String, Integer> month_day = new HashMap<String, Integer>();
    static {
        // private static HashMap <String,Integer> month_day=new HashMap(); can't work
        month_day.put("01", 31);
        month_day.put("02", 28);
        month_day.put("03", 31);
        month_day.put("04", 30);
        month_day.put("05", 31);
        month_day.put("06", 30);
        month_day.put("07", 31);
        month_day.put("08", 31);
        month_day.put("09", 30);
        month_day.put("10", 31);
        month_day.put("11", 30);
        month_day.put("12", 31);
    }

    // 53010219200508011x
    // 53010219000228011x
    public static boolean check(String x) {
        if (x.length() != 18) {
            // System.out.println("length not 18");
            return false;
        } else {
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            int day = LocalDate.now().getDayOfMonth();
            int a = month_day.containsKey(x.substring(10, 12)) ? month_day.get(x.substring(10, 12)) : -1;
            if (a == -1) {
                // System.out.printf("not found month %s\n",x.substring(12, 14));
                return false;
            }
            int max = year * 10000 + month * 100 + day;
            int min = 19000101;
            int xDate = Integer.valueOf(x.substring(6, 14));
            if (xDate < min || xDate > max) {
                // System.out.printf("Bigger than biggest,less than smallest %d %d
                // %d\n",max,min,xDate);
                return false;
            }
            int xYear = Integer.valueOf(x.substring(6, 10));
            int xMonth = Integer.valueOf(x.substring(10, 12));
            int xDay = Integer.valueOf(x.substring(12, 14));
            if (xMonth == 2) {
                if (xYear % 400 == 0 || (xYear % 100 != 0 && xYear % 4 == 0)) {
                    a += 1;
                    // System.out.println("isRun");
                }
            }
            if (xDay <= a) {
                return true;
            } else {
                // System.out.println("tooBigDay");
                return false;
            }
        }
    }

}
