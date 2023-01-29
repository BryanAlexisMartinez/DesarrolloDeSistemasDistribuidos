/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
/* Librerias requeridas para el servidor en java */
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.Executors;
public class WebServer {
  /* Definicion de la cadena de los EndPoints */
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";
  private final int port; //Puerto de la aplicación
  private HttpServer server; //Servidor HTTP
  public static void main(String[] args) {
    int serverPort = 8080; //Se define el puerto default del servidor
    if (args.length == 1) { //Si es pasado por argumentos, se toma el del argumento
      serverPort = Integer.parseInt(args[0]);
    }
    /* Se instancia y se ejecuta el servidor e inicializa la configuracion del servidor */
    WebServer webServer = new WebServer(serverPort);
    webServer.startServer();
    // Imprime el puerto en donde se esta escuchando la aplciación
    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }
  /* Constructor que inicializa la variable port*/
  public WebServer(int port) {
    this.port = port;
  }
  /* Método para iniciar el servidor */
  public void startServer() {
    try {
      // Crea una instancia con un InetSocketAddress con su puerto y el tamaño de solicitudes pendientes que permitimos a nuestro servidor HTTP al tener una cola de espera
      this.server = HttpServer.create(new InetSocketAddress(port), 0); // 0 - se le deja la decisión al sistema
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    //Mapeo entre URI y un HttpHandler
    // Interfaz que se ejecuta cada vez que se procesa una transacción HTTP
    HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
    HttpContext taskContext = server.createContext(TASK_ENDPOINT);
    //Método que implementa el manejador para controlar la petición
    statusContext.setHandler(this::handleStatusCheckRequest);
    taskContext.setHandler(this::handleTaskRequest);
    //Es necesario antes de iniciar.
    // Se crea un pool de 8 hilos, se inicializan y se le asignan tareas
    server.setExecutor(Executors.newFixedThreadPool(8));
    server.start();
  }

  /* Manejador del endpoint Task */
  private void handleTaskRequest(HttpExchange exchange) throws IOException {
    /* Se verifica si la peticion corresponde al método POST */
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      exchange.close(); //Cierra el exchange
      return;
    }
    //Recupera todos los headers del exchange
    Headers headers = exchange.getRequestHeaders();
    //Si la cabecera X-TEST cumple el caso true
    if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange); //Enviamos el dummyResponse
      return;
    }
    boolean isDebugMode = false;
    //Si en los headers nos indican X-Debug
    if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
      isDebugMode = true; //Variable para poder enviar una serie de información mas adelante
    }
    //Tomar la cantidad del tiempo que tomo el procesamiento
    long startTime = System.nanoTime();
    byte[] requestBytes = exchange.getRequestBody().readAllBytes(); // Recupera toda la informacion del cuerpo del mensaje
    byte[] responseBytes = calculateResponse(requestBytes); //Realiza las operaciones correspondientes.
    long finishTime = System.nanoTime();
    /* Si esta en modo debug */
    if (isDebugMode) {
      /* Creamos un string con la información y la almacenamos en las cabeceras de la respuesta del servidor */
      String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime);
      exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
    }
    //Se envia como respuesta la respuesta y el exchange
    sendResponse(responseBytes, exchange);
  }
  /* Devuelve la multiplicacion de varios números */
  private byte[] calculateResponse(byte[] requestBytes) {
    Demo object = null;

    System.out.println("Recibo información serializada: " + requestBytes);
    object = (Demo)SerializationUtils.deserialize(requestBytes);
    System.out.println("Deserializada: ");
    for (int i = 0; i < object.numbers.length ; i++)
      System.out.println(object.numbers[i]);

    BigInteger result = BigInteger.ONE;
    for (BigInteger number : object.numbers) {
      result = result.multiply(number);
    }
    return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
  }

  /* Metodo GET para manejar la ruta Status*/
  private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }
    String responseMessage = "El servidor está vivo\n";
    sendResponse(responseMessage.getBytes(), exchange);
  }

  /* Agrega el status code exitoso 200 y la longitud de la respuesta */
  private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    exchange.sendResponseHeaders(200, responseBytes.length);
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes);
    outputStream.flush(); //Se cierra el stream
    outputStream.close();
    exchange.close();
  }
}

