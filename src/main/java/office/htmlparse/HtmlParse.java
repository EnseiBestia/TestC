package office.htmlparse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParse {
    List<List<String>> commExcel  ;

    public static void main(String[] args) {
        HtmlParse hp = new HtmlParse();
        hp.parseHTMLFile("X:\\tcode\\excel\\11.html");
    }
    public HtmlParse(){
        commExcel = new ArrayList<List<String>>();

    }
    public void parseHTML(String html){
        Document doc = Jsoup.parse(html);
        Element body = doc.select("body").get(0);
        Elements eles = body.children();
        for(int t=0;t<eles.size();t++){
            Element t1 = eles.get(t);
            if(t1.tagName().equals("table")){
                parseTable(t1);
            }
        }
    }

    public void parseTable(Element tbc){
        Elements trs = tbc.children();
        for(int tri = 0; tri<trs.size();tri++){
            Element row = trs.get(tri);
            if(row.tagName()=="tr"){
                parseTR(row);
            }
        }
    }
    public void parseTR(Element trc){
        Elements tds = trc.children();
        for(int tdi = 0; tdi<tds.size();tdi++){
            Element row = tds.get(tdi);
            if(row.tagName()=="td"){
                parseTR(row);
            }
        }
    }
    public void parseTD(Element tdc){
        Elements es = tdc.children();
        if(es.size()>0) {
            for (int i = 0; i < es.size(); i++) {
                Element ev = es.get(i);
                //value
                //span
                if (ev.tagName().equals("span")) {
                    String cellValue = ev.text();
                }
                //table
                else if (ev.tagName().equals("table")) {
                    parseTable(ev);
                }
            }
        }else{//无内嵌元素，基本上为空值

        }
    }
    public void  parseHTMLFile(String htmlFile){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(htmlFile));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        parseHTML(contentBuilder.toString());
    }
      /*public void parseHTML(String html){
        Document doc = Jsoup.parse(html);

        Elements tables = doc.select("body > table");
        for( int i=0;i<tables.size();i++){
            Element tableC = tables.get(i);
            Elements rows = tableC.select("tr");
            for(int j=0;j<rows.size();j++){
                Element row = rows.get(j);
                Elements cols = row.select("td");
                List<String> curList = new ArrayList<String>();
                for(int h =0;h<cols.size();h++){
                    Element cel = cols.get(h);
                    try{
                        Element span = cel.select("span").get(0);
                        curList.add(span.text());
                    }catch(Exception e){
                        curList.add("");
                    }
                }
                commExcel.add(curList);
            }
        }
        for(int i=0;i<commExcel.size();i++){
            List<String> curList = commExcel.get(i);
            for(int j=0;j<curList.size();j++){
                System.out.print(curList.get(j)+"\t");
            }
            System.out.println();
        }
    }*/

}
