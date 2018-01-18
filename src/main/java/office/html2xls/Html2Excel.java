package office.html2xls;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Html2Excel {
    public static String VBS_PATH;
    public Html2Excel(){
        URL resource = Html2Excel.class.getClassLoader().getResource("convers.vbs");
        try {
            VBS_PATH = Paths.get(resource.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] fies  = new String[]{"X:\\tcode\\excel\\vbatest\\tta.xls"};
        Html2Excel h2e = new Html2Excel();
        for(int i=0;i<fies.length;i++){
            h2e.processFile(fies[i]);
        }
      //  h2e.processFile(testFile);
    }
    public void processFile(String fileStr){
        File srcFile = new File(fileStr);
        String abPath = srcFile.getAbsolutePath();
        String srcPath = abPath.substring(0,abPath.lastIndexOf("\\"));

        String fileNameNSuf = srcPath+"\\"+srcFile.getName().split("\\.")[0];

        String htmlFileStr =  fileNameNSuf+".html";
        String desExlFileStr = fileNameNSuf+".xlsx";
        String bakExlFileStr = fileNameNSuf+"_bak.xls";
        //bakup file
        try {
            FileUtils.copyFile(srcFile,new File(bakExlFileStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        srcFile.renameTo(new File(htmlFileStr));
        String script ="wscript "+VBS_PATH+" \""+htmlFileStr +"\" \""+desExlFileStr+"\"";
        try {
            Runtime.getRuntime().exec(script);
            try {
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            (new File(htmlFileStr)).delete();
            (new File(bakExlFileStr)).delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
