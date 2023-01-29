package com.mycompany.clientezookeepereleccion;

import java.io.IOException;

/**
 *
 * @author alexis
 */
import org.apache.zookeeper.*;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
//Se declara la clase publica que implementa Watcher que contiene process

public class ClienteZooKeeperEleccion implements Watcher {

    //Especifica el puerto donde está el zookeeper corriendo
    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    //En caso de que el tiempo de espera para la conexión sea más de 3s entonces se detiene el programa
    private static final int SESSION_TIMEOUT = 3000;
    private ZooKeeper zooKeeper;

    private static final String ELECTION_NAMESPACE = "/eleccion";
    private String currentZnodeName;

    //Se delegan el manejo de excepciones
    public static void main(String[] arg) throws IOException, InterruptedException, KeeperException {
        //Se crea una instancia  de la clase que tiene implementada la interfaz watch
        ClienteZooKeeperEleccion clienteBasico = new ClienteZooKeeperEleccion();
        //Ejecuta el método connectZookeeper
        clienteBasico.connectToZookeeper();
        clienteBasico.voluntarioParaLiderazgo();
        clienteBasico.liderElecto();
        //Método run
        clienteBasico.run();
        clienteBasico.close();
        System.out.println("Desconectado del servidor Zookeeper. Terminando la aplicación cliente.");
    }
    
        public void voluntarioParaLiderazgo() throws KeeperException, InterruptedException {
        String znodePrefix = ELECTION_NAMESPACE + "/c_";
        String znodeFullPath = zooKeeper.create(znodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Nombre del znode: " + znodeFullPath);
        this.currentZnodeName = znodeFullPath.replace("/eleccion/", "");
    }

    public void liderElecto() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
        Collections.sort(children);
        String smallestChild = children.get(0);
        if (smallestChild.equals(currentZnodeName)) {
            System.out.println("Yo soy el lider");
            return;
        }
        System.out.println("Yo no soy el lider, " + smallestChild + " es el lider");
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
