package netspider;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) {
        getTopicId();
    }
    public static void loadOneTopicHotQA(int child_topic_id) throws Exception{
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRetryHandler(new DefaultHttpRequestRetryHandler())
                .setConnectionManager(cm)
                .build();
        HttpGet req = new HttpGet("https://www.zhihu.com/topic/" + child_topic_id + "/hot");
        HttpResponse resp = httpClient.execute(req);
        String result = EntityUtils.toString(resp.getEntity());
        //正则匹配打不出来，后面以截图形式给出
        Pattern p_qa_id = Pattern.compile(regex_qa_id);
        Pattern p_qa_name = Pattern.compile(regex_qa_name);
        Pattern p_qa_username = Pattern.compile(regex_qa_username);
        Matcher m_qa_id = p_qa_id.matcher(result);
        Matcher m_qa_name = p_qa_name.matcher(result);
        Matcher m_qa_username = p_qa_username.matcher(result);
        while (m_qa_id.find() && m_qa_name.find() && m_qa_username.find()){
            String[] qanda_id = m_qa_id.group().split("/");
            String q_id = qanda_id[2];
            String a_id = qanda_id[4].substring(0, qanda_id[4].length() - 2);
            String q_name = m_qa_name.group().split("\n")[1];
            String temp = m_qa_username.group();
            String q_username = null;
            if (temp.contains("匿名用户")) q_username = "匿名用户";
            else if (temp.contains("知乎用户")) q_username = "知乎用户";
            else q_username = temp.substring(30, temp.length() - 1);
            HotQA qa = new HotQA(child_topic_id,Integer.valueOf(q_id), q_name, Integer.valueOf(a_id), q_username);
            DaoImpl daoimpl = new DaoImpl();
            daoimpl.save(qa, child_topic_id);
        }
    }


    public static void getTopicId(){
        new Thread(new Runnable() {

            @Override
            public void run() {
              //  Connection connection;
                int id = 1;
                try {
                    URL url = new URL("https://www.zhihu.com/topics");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    BufferedReader bfr = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while ((line = (bfr.readLine())) != null){
                        sb.append(line);
                    }
                    String result = sb.toString();
                    String regex = "data-id=\"[0-9]{0,6}\"";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher m = pattern.matcher(result);
                    String regx = "href=\"#.*?\"";
                    Pattern p = Pattern.compile(regx);
                    Matcher mn = p.matcher(result);
                    while (m.find() && mn.find()){
                        String s = m.group();
                        s = s.substring(9,s.length() - 1);
                        String sn = mn.group();
                        sn = sn.substring(7,sn.length() - 1);
                        System.out.println(s + " " + sn);
                        System.out.println(sn);
       /*                 connection = JDBCUtil.getConn();
                        PreparedStatement state = (PreparedStatement) connection.prepareStatement("insert into main_topic values(?,?,?)");
                        state.setInt(1, id++);
                        state.setInt(2, Integer.valueOf(s));
                        state.setString(3, sn);
                        state.execute();*/
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
