package office.html2xls;

import java.io.IOException;

public class RuntimeTest {
    static String htmlFile = "X:\\tcode\\excel\\11.html";
    static String desXls = "X:\\tcode\\excel\\11-dest.xlsx";
    public static void main(String[] args) {
        String script ="wscript X:\\tcode\\excel\\conver.vbs "+htmlFile +" "+desXls+"";

        try {
            Runtime.getRuntime().exec(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
