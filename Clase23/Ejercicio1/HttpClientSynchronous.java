import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

class Task implements Runnable{
            private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public Task(){
    }

    public void run(){

        try{
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://192.168.56.1:8088/search"))
                .setHeader("X-Debug","true") // add request header
                .POST(BodyPublishers.ofString("1757600,IPN"))
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                
        // print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        }catch(Exception e){
            System.out.println(e);
        }
    }
}

public class HttpClientSynchronous {

    public static void main(String[] args) {

        int m = Integer.parseInt(args[0]);
        int MAX_T = 100;
        Runnable[] r = new Runnable[m];

        for(int i=0; i<m; i++){
            r[i] = new Task();
        }

        ExecutorService pool = Executors.newFixedThreadPool(MAX_T); 

        for(int i=0; i<m; i++){
            pool.execute(r[i]);
        }

        pool.shutdown();
    }
}
