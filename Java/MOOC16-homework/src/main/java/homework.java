
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
        String line; // ��������ÿ�ж�ȡ������
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // ��ȡ��һ��
        while (line != null) { // ��� line Ϊ��˵��������
            buffer.append(line); // ��������������ӵ� buffer ��
            buffer.append("\n"); // ��ӻ��з�
            line = reader.readLine(); // ��ȡ��һ��
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
