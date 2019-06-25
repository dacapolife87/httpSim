import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HttpSim {

    public static void main(String[] args) {

        if( args == null || args.length != 3)
        {
            System.out.println("Http Request Simulator");
            System.out.println("args[0] : method Type");
            System.out.println("args[1] : Request Url");
            System.out.println("args[2] : Json filePath");
            System.exit(1);
        }
        String methodType = args[0];
        String reqUrl = args[1];
        String filePath = args[2];
        String jsonBody = "";
        StringBuilder sb = new StringBuilder();
        File jsonFile = new File(filePath);

        FileReader filereader = null;
        try {
            filereader = new FileReader(jsonFile);
            int singleCh = 0;
            while((singleCh = filereader.read()) != -1){
                sb.append((char)singleCh);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                filereader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        jsonBody = sb.toString();

        if(methodType.equalsIgnoreCase("POST")){
            postHttpRequest(reqUrl,jsonBody);
        }else{

        }
    }

    public static void postHttpRequest(String reqUrl, String jsonBody){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(reqUrl);

        try {
            httpPost.setEntity(new StringEntity(jsonBody,"application/json","UTF-8"));

            System.out.println("=================[Request]=======================");
            System.out.println("Req Url : "+reqUrl);
            System.out.println("HTTP REQ BODY");
            System.out.println(jsonBody);
            System.out.println("=================================================");
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            if(httpResponse.getStatusLine().getStatusCode()!=200){
                System.out.println(httpResponse.getEntity().getContent());
            }else{
                System.out.println("=================[Response]=======================");
                System.out.println("HTTP Response : "+httpResponse.getStatusLine().getStatusCode());
                System.out.println("==================================================");
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}
