
import static org.junit.Assert.*;
import org.junit.Test;

public class TriangleTest {
    
    @Test
    public void test() {
        assertEquals(false,new Triangle().judgeEdges(1, 2, 3));
    }
    
}
