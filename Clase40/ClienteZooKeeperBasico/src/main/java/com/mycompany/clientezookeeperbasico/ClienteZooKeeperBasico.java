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
package com.mycompany.clientezookeeperbasico;

/**
 *
 * @author alexis
 */
import org.apache.zookeeper.*;
import java.io.IOException;
//Se declara la clase publica que implementa Watcher que contiene process
public class ClienteZooKeeperBasico implements Watcher {
    //Especifica el puerto donde está el zookeeper corriendo
    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    //En caso de que el tiempo de espera para la conexión sea más de 3s entonces se detiene el programa
    private static final int SESSION_TIMEOUT = 3000;
    private ZooKeeper zooKeeper;
    //Se delegan el manejo de excepciones
    public static void main(String[] arg) throws IOException, InterruptedException, KeeperException {
        //Se crea una instancia  de la clase que tiene implementada la interfaz watch
        ClienteZooKeeperBasico clienteBasico = new ClienteZooKeeperBasico();
        //Ejecuta el método connectZookeeper
        clienteBasico.connectToZookeeper();
        //Método run
        clienteBasico.run();
        clienteBasico.close();
        System.out.println("Desconectado del servidor Zookeeper. Terminando la aplicación cliente.");
    }
    //Método
    public void connectToZookeeper() throws IOException {
        //Se le manda la ubicación y el timeout para salir en caso de ser necesario
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    private void run() throws InterruptedException {
        //synchronized permite que solamente un hilo del zookeeper ejecute la instrucción
        synchronized (zooKeeper) {
            //Dejará al hilo en espera hasta que le llegue una notificación
            zooKeeper.wait();
        }
    }

    private void close() throws InterruptedException {
        this.zooKeeper.close();
    }

    @Override
    //Recibe mediante el hilo de eventos los eventos producidos por el servidor ZooKeeper mediante objetivo de tipo WatchedEvent
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                //Se obtiene el estado del evento con getState
                //Si su estado es este, entonces significa que está conectado
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Conectado exitosamente a Zookeeper");
                } else {
                    //En caso contrario
                    synchronized (zooKeeper) {
                        //Significa que está desconectado
                        System.out.println("Desconectando de Zookeeper...");
                        //notifyAll despierta a todos los hilos en estado de espera, que en este caso
                        //es el hilo principal, que al activarse terminará la conexión
                        zooKeeper.notifyAll();
                    }
                }
        }
    }
}
