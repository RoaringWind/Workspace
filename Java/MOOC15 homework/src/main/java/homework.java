import java.util.Locale;
import java.util.ResourceBundle;

public class homework {

    public static void main(String[] args) {
        Locale zhLocale=new Locale("zh", "CN");
        Locale enLocale=new Locale("en", "US");
        
        try {
            ResourceBundle bundle=ResourceBundle.getBundle("msg",zhLocale);
            ResourceBundle bundle2=ResourceBundle.getBundle("msg",zhLocale);
            System.out.println(bundle.getString("university"));
            System.out.println(bundle2.getString("name"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            ResourceBundle bundle=ResourceBundle.getBundle("msg",enLocale);
            ResourceBundle bundle2=ResourceBundle.getBundle("msg",enLocale);
            System.out.println(bundle.getString("name"));
            System.out.println(bundle.getString("university"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }

}
