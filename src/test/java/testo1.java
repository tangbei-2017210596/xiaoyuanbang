import org.junit.jupiter.api.Test;

import java.io.File;

public class testo1 {
    @Test
    public void test1(){
        String path=new File("").getAbsoluteFile()+"\\img";
        new File(path).mkdirs();
        System.out.println(path);
    }
}
