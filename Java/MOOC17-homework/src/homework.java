import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.model.RowBlocksReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class homework {
    public static void main(String[] args) throws Exception {
        String pathString="D:\\PracticeCode\\Java\\MOOC17-homework\\src\\student.xlsx";
        List<String> result=getStuInfoFromXlsx(pathString);
        System.out.print(result);
        
        
    }
    public static List<String> getStuInfoFromXlsx(String inPath) throws Exception{
        List<String> res=new ArrayList<>();
        File file = new File(inPath);
        FileInputStream fis=new FileInputStream(file);
        XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet workSheet=workbook.getSheetAt(0);
        Iterator<Row> rowIterator=workSheet.iterator();
        while (rowIterator.hasNext()) {
            Row row= rowIterator.next();
            Iterator<Cell> cellIterator=row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell=cellIterator.next();
                res.add(cell.getStringCellValue());
           }
        }
        return res;
    }
}
