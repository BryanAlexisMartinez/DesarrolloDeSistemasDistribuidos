import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientSynchronous {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request1 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://10.0.2.15:8080/search"))
                .setHeader("X-Debug","true") // add request header
                .POST(BodyPublishers.ofString("17576000,IPN"))
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://192.168.1.134:8080/search"))
                .setHeader("X-Debug","true") // add request header
                .POST(BodyPublishers.ofString("17576000,IPN"))
                .build();

        HttpRequest request3 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://192.168.1.86:8081/search"))
                .setHeader("X-Debug","true") // add request header
                .POST(BodyPublishers.ofString("17576000,IPN"))
                .build();

        HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());

        // print response headers
        HttpHeaders headers1 = response1.headers();
        headers1.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response1.statusCode());

        // print response body
        System.out.println(response1.body());

        // print response headers
        HttpHeaders headers2 = response2.headers();
        headers2.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response2.statusCode());

        // print response body
        System.out.println(response2.body());

        // print response headers
        HttpHeaders headers3 = response3.headers();
        headers3.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response3.statusCode());

        // print response body
        System.out.println(response3.body());
    }
}
