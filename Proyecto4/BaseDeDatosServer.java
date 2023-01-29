//Proyecto 4
//Martínez Alvarado Bryan Alexis
//4CM12
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.Executors;
public class BaseDeDatosServer {
    //seleccion
    private static final String peticionesEnd = "/seleccion";
    //curps
    private static final String curpsEnd = "/curps";
    //Total de curps
    private int totalDeCurps = 0;
    //Tamaño de los bytes
    private long tamanoBytes = 0;
    private HttpServer server;
    private ArrayList<String> curps;
    public static void main(String[] args) {
        //Puerto en el que se va a ejecutar
        int port = 80; //al ser el cloud deben de ser en el puerto 80
        new BaseDeDatosServer(port);
    }

    public BaseDeDatosServer(int puerto) {
        this.curps = new ArrayList<>();
        try {
            this.server = HttpServer.create(new InetSocketAddress(puerto), 0);
            HttpContext peticionesContext = server.createContext(peticionesEnd);
            HttpContext curpsContext = server.createContext(curpsEnd);
            peticionesContext.setHandler(this::handlePeticiones);
            curpsContext.setHandler(this::handleCurps);
            server.setExecutor(Executors.newFixedThreadPool(8));
            server.start();
        } catch (Exception e) {
        }
    }

    private void handlePeticiones(HttpExchange exchange) throws IOException {
        //Headers
        Headers headers = exchange.getRequestHeaders();
        String string = headers.getFirst("seleccion");
        //se detecta la opcion seleccionada
        int seleccion = Integer.parseInt(string);
        //Cuántos curps hay en total/cada servidor
        if(seleccion == 2 || seleccion == 3) {
            exchange.getResponseHeaders().put("seleccion", Arrays.asList(Integer.toString(totalDeCurps)));
        }
        //bytes utilizados por cada servidor
        if(seleccion == 4) {
            exchange.getResponseHeaders().put("seleccion", Arrays.asList(Long.toString(tamanoBytes)));
        }
        //Cuántos hombres y mujeres hay en la base de datos
        if(seleccion == 5) {
            int h = 0, m = 0;
            for(int i = 0; i < curps.size(); i++) {
                String curp = curps.get(i);
                System.out.println(curp);
                String letra = curp.substring(10, 11);
                System.out.println(letra);
                if(letra.equals("H")) {
                    h += 1;
                } else {
                    m += 1;
                }    
            }
            exchange.getResponseHeaders().put("seleccion", Arrays.asList(Integer.toString(m), Integer.toString(h)));
        }
        //Cuántos curps hay de una entidad en específico
        if(seleccion == 6) {
            int total = 0;
            String entidad1 = headers.getFirst("entidad");
            for(int i = 0; i < curps.size(); i++) {
                String curp = curps.get(i);
                System.out.println(curp);
                String entidad = curp.substring(11, 13);
                System.out.println(entidad);
                System.out.println(entidad1);
                if(entidad1.equals(entidad)) {
                    total += 1;
                }
            }
            exchange.getResponseHeaders().put("seleccion", Arrays.asList(Integer.toString(total)));
        }
        exchange.sendResponseHeaders(200, "response".getBytes().length);
        OutputStream os = exchange.getResponseBody();      
        os.write("response".getBytes());
        os.flush();
        os.close();
        exchange.close();
    }

    private void handleCurps(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getRequestHeaders();
        String nuevoCurp = headers.getFirst("curp");
        this.totalDeCurps += 1;
        this.tamanoBytes += nuevoCurp.getBytes().length;
        //añade nuevo curp
        curps.add(nuevoCurp);
        exchange.sendResponseHeaders(200, "response".getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write("response".getBytes());
        os.flush();
        os.close();
        exchange.close();
    }
}
