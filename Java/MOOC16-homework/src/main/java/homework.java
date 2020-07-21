
import java.io.*;


import org.json.JSONML;
import org.json.XMLTokener;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONML;
import org.json.XML;
import org.json.JSONObject;


public class homework {

    public static void main(String[] args) throws Exception{
        StringBuffer buffer=new StringBuffer();
        String filenameString="D:\\PracticeCode\\Java\\MOOC16-homework\\src\\main\\score.xml";
        InputStream is = new FileInputStream(filenameString);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
        System.out.print(buffer.toString());
        
        String xml=buffer.toString();
        JSONObject jsonObject=JSONML.toJSONObject(xml);
        System.out.println(jsonObject.toString());
        JSONArray jsonArray=JSONML.toJSONArray(xml);
        var k=XML.toJSONObject(xml);
        System.out.println("json: "+k);
        String xml1 = XML.toString(k);
        System.out.println("xml1: "+xml1);
        
    }

}
