package WebClient;
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

import Poligono.Coordenada;
import Poligono.Demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
  private HttpClient client;

  // Constructor
  public WebClient() {
    //Crea un objeto HTTPClient especificando la versión
    this.client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
  }

  // Recibe la url y el payload a enviar al endpoint
  public CompletableFuture<Demo> sendTask(String url, byte[] requestPayload) {
    //System.out.println(requestPayload);
    // Permite construir una solicitud HTTP con el método post y la dirección del destino
    HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
            .uri(URI.create(url))
            .header("X-Debug", "true")
            .build();

    // Regresamos la solicitud de manera asyncrona
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
            .thenApply(respuesta -> {
              Demo object = (Demo) SerializationUtils.deserialize(respuesta.body());
              return object;
            });
  }
}


