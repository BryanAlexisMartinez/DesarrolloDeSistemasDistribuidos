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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Aggregator {
    private WebClient webClient;

    //Constructor que instancia un WebClient.WebClient
    public Aggregator() {
        this.webClient = new WebClient();
    }

    // Recibe lista de endpoints y lista de tareas
    public List<Demo> sendTasksToWorkers(List<String> workersAddresses, List<Demo> tasks) {
        //Manejo de comunicación asincrona
        CompletableFuture<Demo>[] futures = new CompletableFuture[workersAddresses.size()];
        List<Demo> results = new ArrayList<Demo>();

        //Se itera cada endpoint y su tarea
        for (int i = 0; i < workersAddresses.size(); i++) {
            // Y se obtiene su dirección
            String workerAddress = workersAddresses.get(i);
            // Y su tarea
            Demo task = tasks.get(i);

            System.out.println("Contenido antes de enviar: ");
            System.out.println(task);

            //Se almacenan las tareas en formato de bytes
            byte[] requestPayload = SerializationUtils.serialize(task);
            // Y enviamos las tareas asyncronas.

            futures[0] = webClient.sendTask(workerAddress, requestPayload);
            Demo smite = futures[0].join();
            smite.anadeVertice(new Coordenada(23,61));
            System.out.println("Añado vertice");
            System.out.println(smite);
            Random rand = new Random();
            int high = 100;
            int low = -100;

            while (true) {
                try {
                    Thread.sleep(2000);
                    futures[0] = webClient.sendTask(workerAddress, SerializationUtils.serialize(smite));
                    smite = futures[0].join();
                    System.out.println("Recibo y añado vertice: ");
                    smite.anadeVertice(new Coordenada(rand.nextInt(high-low) + low, rand.nextInt(high-low) + low));
                    System.out.println(smite);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }
}


