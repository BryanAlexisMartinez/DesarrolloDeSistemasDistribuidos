//Proyecto 4
//Martínez Alvarado Bryan Alexis
//4CM12
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class ComputadoraLocal {
    public static void main(String[] args) {
        String GeneradorDeCURPS = "http://34.125.78.229:80";
        Scanner scanner = new Scanner(System.in);
        int seleccion = 0;
        String entidad = "algoRandomDiferenteAUnaEntidadValida";
        while(seleccion != 7) {
            
            System.out.println("************************************************************************");
            System.out.println("Por favor ingrese la opcion que quiera consultar");
            System.out.println("1. ¿Cuántos CURPs están siendo generados cada segundo?");
            System.out.println("2. ¿Cuántos CURPs hay en total en la base de datos?");
            System.out.println("3. ¿Cuántos CURPs hay en cada servidor?");
            System.out.println("4. ¿Cuántos bytes está utilizando cada servidor?");
            System.out.println("5. ¿Cuántos hombres y cuántas mujeres están registrados?");
            System.out.println("6. ¿Cuántos CURPs hay de una entidad federativa en específico?");
            System.out.println("7. Salir del menú principal");
            System.out.println("************************************************************************");
            
            seleccion = scanner.nextInt();
            System.out.println("Ha elegido la opción: " + seleccion);
            HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(8)).build();
            HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create(GeneradorDeCURPS + "/seleccion")).setHeader("User-Agent", "Java 1.17").setHeader("seleccion", Integer.toString(seleccion)).setHeader("entidad", entidad).build();
    
            try {
                HttpHeaders headers = null;
                if(seleccion != 6) {
                    HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    headers = httpResponse.headers();
                }
                
                if(seleccion == 1) {
                    headers.map().forEach((key, text) -> {
                       if(key.equalsIgnoreCase("seleccion")) {
                            System.out.println("Se están generando " + text.get(0) + " curps por segundo.");
                       }
                    });
                }

                if(seleccion == 2) {
                    headers.map().forEach((key, text) -> {
                        if(key.equalsIgnoreCase("seleccion")) {
                            System.out.println("Hay en total " + text.get(0) + " curps en la base de datos.");
                        }
                    });
                }

                if(seleccion == 3) {
                    headers.map().forEach((key, text) -> {
                        if(key.equalsIgnoreCase("seleccion")) {
                            System.out.println("Hay en total " + text.get(0) + " curps en el servidor #1.");
                            System.out.println("Hay en total " + text.get(1) + " curps en el servidor #2.");
                            System.out.println("Hay en total " + text.get(2) + " curps en el servidor #3.");
                        }
                    });
                }

                if(seleccion == 4) {
                    headers.map().forEach((key, text) -> {
                        if(key.equalsIgnoreCase("seleccion")) {
                            System.out.println("El primer servidor está utilizando " + text.get(0) + " bytes.");
                            System.out.println("El segundo servidor está utilizando " + text.get(1) + " bytes.");
                            System.out.println("El tercer servidor está utilizando " + text.get(2) + " bytes.");
                        }
                    });
                }

                if(seleccion == 5) {
                    headers.map().forEach((key, text) -> {
                        if(key.equalsIgnoreCase("seleccion")) {
                            int mujeres = Integer.parseInt(text.get(0).split("\\s+")[0]) + Integer.parseInt(text.get(1).split("\\s+")[0]) + Integer.parseInt(text.get(2).split("\\s+")[0]);
                            int hombres = Integer.parseInt(text.get(0).split("\\s+")[1]) + Integer.parseInt(text.get(1).split("\\s+")[1]) + Integer.parseInt(text.get(2).split("\\s+")[1]);
                            System.out.println("Hay " + mujeres + " mujeres y " + hombres + " hombres registrados en la base de datos.");
                        }
                    });
                }

                ArrayList<String> entidadesFederativas = new ArrayList<>();
                entidadesFederativas.add("AS");
                entidadesFederativas.add("BC");
                entidadesFederativas.add("BS");
                entidadesFederativas.add("CC");
                entidadesFederativas.add("CS");
                entidadesFederativas.add("CH");
                entidadesFederativas.add("DF");
                entidadesFederativas.add("CL");
                entidadesFederativas.add("CM");
                entidadesFederativas.add("DG");
                entidadesFederativas.add("GT");
                entidadesFederativas.add("GR");
                entidadesFederativas.add("HG");
                entidadesFederativas.add("JC");
                entidadesFederativas.add("MC");
                entidadesFederativas.add("MN");
                entidadesFederativas.add("MS");
                entidadesFederativas.add("NT");
                entidadesFederativas.add("NL");
                entidadesFederativas.add("OC");
                entidadesFederativas.add("PL");
                entidadesFederativas.add("QO");
                entidadesFederativas.add("QR");
                entidadesFederativas.add("SP");
                entidadesFederativas.add("SL");
                entidadesFederativas.add("SR");
                entidadesFederativas.add("TC");
                entidadesFederativas.add("TS");
                entidadesFederativas.add("TL");
                entidadesFederativas.add("VZ");
                entidadesFederativas.add("YN");
                entidadesFederativas.add("ZS");

                if(seleccion == 6) {
                    entidad = "algoRandomDiferenteAUnaEntidadValida";
                    while(!entidadesFederativas.contains(entidad)) {
                        System.out.println("Escriba la clave de la entidad federativa a consultar: ");
                        entidad = scanner.nextLine().trim();
                    }
                    HttpRequest httpRequest1 = HttpRequest.newBuilder().GET().uri(URI.create(GeneradorDeCURPS + "/seleccion")).setHeader("User-Agent", "Java 1.17").setHeader("seleccion", Integer.toString(seleccion)).setHeader("entidad", entidad).build();
                    HttpResponse<String> httpResponse1 = httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
                    headers = httpResponse1.headers();
                    headers.map().forEach((key, text) -> {
                        if(key.equalsIgnoreCase("seleccion")) {
                            System.out.println("En el primer servidor hay " + text.get(0) + " curps de esa entidad.");
                            System.out.println("En el segundo servidor hay " + text.get(1) + " curps de esa entidad.");
                            System.out.println("En el tercer servidor hay " + text.get(2) + " curps de esa entidad.");
                        }
                    });
                    //Devolvemos un valor random a la entidad 
                    entidad = "algoRandomDiferenteAUnaEntidadValida";
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}