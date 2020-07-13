import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class classwork {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Pattern pattern=Pattern.compile("aba");
        Matcher matcher=pattern.matcher("ababa");
        while(matcher.find()) {
            System.out.printf("%d %d\n",matcher.start(),matcher.end());
        }
    }

}
