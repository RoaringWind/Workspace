
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class countWords {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Path path=FileSystems.getDefault().getPath("a.txt");
        //System.out.println(path);
        File file=new File("a.txt");
        FileInputStream fileInputStream=null;
        FilterOutputStream filterOutputStream=null;
        BufferedReader bufferedReader=null;
        InputStreamReader inputStreamReader=null;
        HashMap<String, Integer> wordHashMap=new HashMap<>();
        try {
            fileInputStream=new FileInputStream("F:\\LocalCodeWorkspace\\Java\\MOOC11-homework\\src\\a.txt");
            inputStreamReader=new InputStreamReader(fileInputStream,"UTF-8");
            bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            StringBuffer stringBuffer=new StringBuffer();
            while((line=bufferedReader.readLine())!=null) {
                //System.out.println(line);
                
                for (int i=0;i<line.length();i++) {
                    
                    if (line.charAt(i)!=' ') {
                        stringBuffer.append(line.charAt(i));
                    }else if(stringBuffer.length()!=0){
                        String tmpString=stringBuffer.toString();
                        int tmp=wordHashMap.getOrDefault(tmpString, 0);
                        if (tmp==0) {
                            wordHashMap.put(tmpString, 1);
                        }else {
                            wordHashMap.put(tmpString, tmp+1);
                        }
                        stringBuffer.setLength(0);
                    }
                }
                if(stringBuffer.length()!=0) {
                    String tmpString=stringBuffer.toString();
                    int tmp=wordHashMap.getOrDefault(tmpString, 0);
                    if (tmp==0) {
                        wordHashMap.put(tmpString, 1);
                    }else {
                        wordHashMap.put(tmpString, tmp+1);
                    }
                    stringBuffer.setLength(0);
                }
            }
            
            //System.out.print(wordHashMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map.Entry<String,Integer>> list=new ArrayList<>();
        list.addAll(wordHashMap.entrySet());
        countWords.ValueComparator vc=new ValueComparator();
        Collections.sort(list,vc);
        File inFile=new File("F:\\LocalCodeWorkspace\\Java\\MOOC11-homework\\src\\b.txt");
        try {
            inFile.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(inFile));
            for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
            {
                String keyString=it.next().getKey();
                int value=wordHashMap.get(keyString);
                
                out.append(keyString+","+value+"\n");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
    private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
    {
        public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
        {
            return n.getValue()-m.getValue();
        }
    }
    
}
