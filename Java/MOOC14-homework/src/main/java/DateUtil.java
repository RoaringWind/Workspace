
public class DateUtil {
    public boolean isLeapYear(int year) {
        if (year>10000||year<=0) {
            return false;
        }
        if(year%400==0||(year%100!=0 && year%4==0)) {
            return true;
        }
        return false;
    }
}
