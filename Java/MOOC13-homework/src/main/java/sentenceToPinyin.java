import net.sourceforge.pinyin4j.PinyinHelper;

public class sentenceToPinyin {

    public static void main(String[] args) {
        String str = "��ɳ�г�";
        for(int i=0;i<str.length();i++) {
            String[] pinyinArray =PinyinHelper.toHanyuPinyinStringArray(str.charAt(i));
            System.out.print(pinyinArray[0]);
        }
        
        
    }
}
