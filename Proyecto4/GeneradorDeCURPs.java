//Proyecto 4
//Martínez Alvarado Bryan Alexis
//4CM12
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeneradorDeCURPs {
    public static void main(String[] args) {
        int puerto = 80;
        Scanner scanner = new Scanner(System.in);
        //Se define cuandos curps por segundo se van a estar generando
        System.out.print("Ingresa el número de curps aleatorias a generar por segundo: ");
        int curpsporsegundo = scanner.nextInt();
        GenerarServidor nuevoserver = new GenerarServidor();
        PeticionesAlServer p = new PeticionesAlServer(puerto);
        p.puerto = puerto;
        p.curpsporsegundo = curpsporsegundo;
        p.nuevoserver = nuevoserver;
        ExecutorService pool = Executors.newFixedThreadPool(curpsporsegundo);
        pool.submit(p);
        for(int i = 1; i < curpsporsegundo; i++) {
            EnviarLosCURPs enviarloscurps = new EnviarLosCURPs(nuevoserver);
            pool.submit(enviarloscurps);
        }
    }
}

class GenerarServidor {
    ArrayList<String> servidores = new ArrayList<>();
    int servidor = 0;

    public GenerarServidor() {
        //servidores.add("http://localhost:4001");
        servidores.add("http://34.125.46.215:80");
        servidores.add("http://34.125.36.6:80");
        servidores.add("http://34.125.135.10:80");
    }

    public synchronized String generarServidor() {
        if(servidor >= servidores.size()) {
            servidor = 0;
        }

        String generarServidor = servidores.get(servidor++);
        return generarServidor;
    }
}

class EnviarLosCURPs extends Thread {
    GenerarServidor nuevoserver = null;
    GeneradorCups generadordecurps = new GeneradorCups();

    public EnviarLosCURPs(GenerarServidor nuevoserver) {
        this.nuevoserver = nuevoserver;
    }

    public void run() {
        while(true) {
            HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(8)).build();
            String servidor = nuevoserver.generarServidor();
            System.out.println("El CURP generado ha sido enviado al servidor: " + servidor);
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(servidor + "/curps")).setHeader("curp", generadordecurps.nuevoCurp()).build();

            try {
                long inicio = System.currentTimeMillis();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                long fin = System.currentTimeMillis();
                Thread.sleep((1000 - (fin - inicio)));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}

class PeticionesAlServer extends Thread {
    public int puerto;
    public int curpsporsegundo;
    public GenerarServidor nuevoserver;

    private HttpServer server;
    private String seleccion1 = "", seleccion2 = "", seleccion3 = "";
    private int totalDeCurps = 0;

    public PeticionesAlServer(int p) {
        try {
            this.server = HttpServer.create(new InetSocketAddress(p), 0);
        } catch (Exception e) {
        }
    }

    public void run() {
        HttpContext peticionesCon = server.createContext("/seleccion");
        peticionesCon.setHandler(this::peticionesHandler);
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    private void peticionesHandler(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getRequestHeaders();
        String str = headers.get("seleccion").get(0);
        int seleccion = Integer.parseInt(str);
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(8)).build();
        System.out.println("seleccion => " + seleccion);

        if(seleccion == 1) {
            exchange.getResponseHeaders().put("seleccion", Arrays.asList(Integer.toString(curpsporsegundo)));
        }

        if(seleccion == 2) {
            HttpRequest request1 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(0) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request2 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(1) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request3 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(2) + "/seleccion")).setHeader("seleccion", str).build();

            try {
                HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());

                HttpHeaders headers1 = response1.headers();
                headers1.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        int curps = Integer.parseInt(slc);
                        totalDeCurps += curps;
                    }
                });

                HttpHeaders headers2 = response2.headers();
                headers2.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        int curps = Integer.parseInt(slc);
                        totalDeCurps += curps;
                    }
                });

                HttpHeaders headers3 = response3.headers();
                headers3.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        int curps = Integer.parseInt(slc);
                        totalDeCurps += curps;
                    }
                });
                exchange.getResponseHeaders().put("seleccion", Arrays.asList(Integer.toString(totalDeCurps)));
                totalDeCurps = 0;
            } catch (Exception e) {
            }
        }

        if(seleccion == 3) {
            HttpRequest request1 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(0) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request2 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(1) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request3 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(2) + "/seleccion")).setHeader("seleccion", str).build();

            try {
                HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());
                HttpHeaders headers1 = response1.headers();
                headers1.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion1 = slc;
                    }
                });

                HttpHeaders headers2 = response2.headers();
                headers2.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion2 = slc;
                    }
                });

                HttpHeaders headers3 = response3.headers();
                headers3.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion3 = slc;
                    }
                });
                exchange.getResponseHeaders().put("seleccion", Arrays.asList(seleccion1, seleccion2, seleccion3));
            } catch (Exception e) {
            }
        }

        if(seleccion == 4) {
            HttpRequest request1 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(0) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request2 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(1) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request3 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(2) + "/seleccion")).setHeader("seleccion", str).build();
            try {
                HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());

                HttpHeaders headers1 = response1.headers();
                headers1.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion1 = slc;
                    }
                });

                HttpHeaders headers2 = response2.headers();
                headers2.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion2 = slc;
                    }
                });

                HttpHeaders headers3 = response3.headers();
                headers3.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion3 = slc;
                    }
                });
                exchange
                    .getResponseHeaders()
                    .put("seleccion", Arrays.asList(seleccion1, seleccion2, seleccion3));
            } catch (Exception e) {
            }
        }

        if(seleccion == 5) {
            HttpRequest request1 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(0) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request2 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(1) + "/seleccion")).setHeader("seleccion", str).build();
            HttpRequest request3 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(2) + "/seleccion")).setHeader("seleccion", str).build();
            try {
                HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());

                HttpHeaders headers1 = response1.headers();
                headers1.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc1 = text.get(0);
                        String slc2 = text.get(1);
                        seleccion1 = slc1 + " " + slc2;
                    }
                });

                HttpHeaders headers2 = response2.headers();
                headers2.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc1 = text.get(0);
                        String slc2 = text.get(1);
                        seleccion2 = slc1 + " " + slc2;
                    }
                });

                HttpHeaders headers3 = response3.headers();
                headers3.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc1 = text.get(0);
                        String slc2 = text.get(1);
                        seleccion3 = slc1 + " " + slc2;
                    }
                });
                exchange
                    .getResponseHeaders()
                    .put("seleccion", Arrays.asList(seleccion1, seleccion2, seleccion3));
            } catch (Exception e) {
            }
        }

        if(seleccion == 6) {
            HttpRequest request1 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(0) + "/seleccion")).setHeader("entidad", headers.getFirst("entidad")).setHeader("seleccion", str).build();
            HttpRequest request2 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(1) + "/seleccion")).setHeader("entidad", headers.getFirst("entidad")).setHeader("seleccion", str).build();
            HttpRequest request3 = HttpRequest.newBuilder().GET().uri(URI.create(this.nuevoserver.servidores.get(2) + "/seleccion")).setHeader("entidad", headers.getFirst("entidad")).setHeader("seleccion", str).build();
            try {
                HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());

                HttpHeaders headers1 = response1.headers();
                headers1.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion1 = slc;
                    }
                });

                HttpHeaders headers2 = response2.headers();
                headers2.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion2 = slc;
                    }
                });

                HttpHeaders headers3 = response3.headers();
                headers3.map().forEach((key, text) -> {
                    if(key.equalsIgnoreCase("seleccion")) {
                        String slc = text.get(0);
                        seleccion3 = slc;
                    }
                });
                exchange
                    .getResponseHeaders()
                    .put("seleccion", Arrays.asList(seleccion1, seleccion2, seleccion3));
            } catch (Exception e) {
            }
        }
        exchange.sendResponseHeaders(200, "response".getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write("response".getBytes());
        os.flush();
        os.close();
        exchange.close();
    }
}

/*class generadorCurps(int curpsPorSegundo) throws InterruptedException {
        Random random = new Random();
        
        while (true) {
            for (int i = 0; i < curpsPorSegundo; i++) {
                // Generamos una curp aleatoria
                String curp = generadorCurp();
                // Hacemos algo con la curp (por ejemplo, imprimirla por consola)
                //System.out.println(curp);
            }
            // Esperamos un segundo antes de generar más curps aleatorias
            TimeUnit.SECONDS.sleep(1);
        }
    }*/

class GeneradorCups {
    public String nuevoCurp() {
        String curp = "";
        //Random random = new Random();
        char[] vocales = {'A', 'E', 'I', 'O', 'U'};
        char[] consonantes = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
        char[] abecedario = {'A', 'E', 'I', 'O', 'U', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
        char[] sex = {'H', 'M'};
        char[] primerEntidad = {'A', 'B', 'C', 'D', 'G', 'H', 'J', 'M', 'N', 'O', 'P', 'Q', 'S', 'T', 'V', 'Y', 'Z'};
        //String aguascalientes = "AS";
        char[] entidadA = {'S'};
        char[] entidadB = {'C', 'S'};
        char[] entidadC = {'C', 'L', 'M', 'S', 'H'};
        char[] entidadD = {'F', 'G'};
        char[] entidadG = {'T', 'R'};
        char[] entidadH = {'G'};
        char[] entidadJ = {'C'};
        char[] entidadM = {'C', 'N', 'S'};
        char[] entidadN = {'T', 'L'};
        char[] entidadO = {'C'};
        char[] entidadP = {'L'};
        char[] entidadQ = {'Q', 'R'};
        char[] entidadS = {'P', 'L', 'R'};
        char[] entidadT = {'C', 'S', 'L'};
        char[] entidadV = {'Z'};
        char[] entidadY = {'N'};
        char[] entidadZ = {'S'};
        //char[] estado = {'AS', 'BS', 'CL', 'CS', 'DF','GT', 'HG', 'MC', 'MS', 'NL', 'PL', 'QR', 'SL', 'TC', 'TL', 'YN', 'NE', 'BC', 'CC', 'CM', 'CH', 'DG', 'GR', 'JC', 'MN', 'NT', 'OC', 'QT', 'SP', 'SR', 'TS', 'VZ', 'ZS'};
        //Generador random
        Random random = new Random();
        //Primera letra del primer apellido
        char letra1 = abecedario[random.nextInt(abecedario.length)];
        //Primera vocal interna del primer apellido
        char letra2 = vocales[random.nextInt(vocales.length)];
        //Primera letra del segundo apellido
        char letra3 = abecedario[random.nextInt(abecedario.length)];
        //Primera letra del nombre de pila
        char letra4 = abecedario[random.nextInt(abecedario.length)];
        //Año
        int anio = random.nextInt(99);
        //System.out.println(anio);
        //Mes
        String mes = String.format("%02d", random.nextInt(12) + 1);
        //Dia
        String dia = String.format("%02d", random.nextInt(28) + 1);
        //Sexo
        char sexo = sex[random.nextInt(sex.length)];
        //Entidad de nacimiento
        char entidad1 = primerEntidad[random.nextInt(primerEntidad.length)];
        char entidad2 = '\0';
        // System.out.println(entidad1);
        if (entidad1 == 'A') {
            entidad2 = entidadA[random.nextInt(entidadA.length)];
        } else if (entidad1 == 'B') {
            entidad2 = entidadB[random.nextInt(entidadB.length)];
        } else if (entidad1 == 'C') {
            entidad2 = entidadC[random.nextInt(entidadC.length)];
        } else if (entidad1 == 'D') {
            entidad2 = entidadD[random.nextInt(entidadD.length)];
        } else if (entidad1 == 'G') {
            entidad2 = entidadG[random.nextInt(entidadG.length)];
        } else if (entidad1 == 'H') {
            entidad2 = entidadH[random.nextInt(entidadH.length)];
        } else if (entidad1 == 'J') {
            entidad2 = entidadJ[random.nextInt(entidadJ.length)];
        } else if (entidad1 == 'M') {
            entidad2 = entidadM[random.nextInt(entidadM.length)];
        } else if (entidad1 == 'N') {
            entidad2 = entidadN[random.nextInt(entidadN.length)];
        } else if (entidad1 == 'O') {
            entidad2 = entidadO[random.nextInt(entidadO.length)];
        } else if (entidad1 == 'P') {
            entidad2 = entidadP[random.nextInt(entidadP.length)];
        } else if (entidad1 == 'Q') {
            entidad2 = entidadQ[random.nextInt(entidadQ.length)];
        } else if (entidad1 == 'S') {
            entidad2 = entidadS[random.nextInt(entidadS.length)];
        } else if (entidad1 == 'T') {
            entidad2 = entidadT[random.nextInt(entidadT.length)];
        } else if (entidad1 == 'V') {
            entidad2 = entidadV[random.nextInt(entidadV.length)];
        } else if (entidad1 == 'Y') {
            entidad2 = entidadY[random.nextInt(entidadY.length)];
        } else if (entidad1 == 'Z') {
            entidad2 = entidadZ[random.nextInt(entidadZ.length)];
        }
        // 'C', 'D', 'G', 'H', 'J', 'M', 'N', 'O', 'P', 'Q', 'S', 'T', 'V', 'Y', 'Z'
        // System.out.println(entidad2);
        /*switch (entidad1) {
            case '0':
                entidad2 = "AS";
                System.out.println(entidad2);
                break;  
                
        }
        System.out.println(entidad2);*/
        // System.out.println(aguascalientes);
        /* 
        String entidad4 = "";
        char entidad2 = '\0';
        switch (entidad1) {
            case 'A':
                entidad2 = entidadA[random.nextInt(entidadA.length)];
                break;
            case 'B':
                entidad2 = entidadB[random.nextInt(entidadB.length)];
                break;
            case 'C':
                entidad2 = entidadC[random.nextInt(entidadC.length)];
                break;
            case 'D':
                entidad2 = entidadD[random.nextInt(entidadD.length)];
                break;
            default:
                break;
        }*/
        //char entidad3 = abecedario[random.nextInt(abecedario.length)];
        //Siguiente consonante del primer apellido
        char SiguienteConsonantePrimerApellido = consonantes[random.nextInt(consonantes.length)];
        //Siguiente consonante del segundo apellido
        char SiguienteConsonanteSegundoApellido = consonantes[random.nextInt(consonantes.length)];
        //Siguiente consonante del primer nombre
        char SiguienteConsonantePrimerNombre = consonantes[random.nextInt(consonantes.length)];
        //Homoclave
        int homoclave1 = random.nextInt(10);
        int homoclave2 = random.nextInt(10);
        //Concatenar el CURP para generarlo
        curp = "" + letra1 + letra2 + letra3 + letra4 + anio + mes + dia + sexo + entidad1 + entidad2
                + SiguienteConsonantePrimerApellido + SiguienteConsonanteSegundoApellido + SiguienteConsonantePrimerNombre
                + homoclave1 + homoclave2;

        System.out.println(curp);
        return curp;
    }

     /*class curpsPorSegundoEleccion {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el número de curps aleatorias a generar por segundo: ");
        int curpsPorSegundo = scanner.nextInt();
        generadorCurps(curpsPorSegundo);
    }*/
}