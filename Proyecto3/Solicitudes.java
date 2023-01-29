import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Solicitudes{
    public static void main(String[] args){
        for(int i = 0; i < 4; i++){
            Hilo hiloSolicitud = new Hilo();
            hiloSolicitud.start();
        }
    }
}

class Hilo extends Thread{
    public void run(){
        try{
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"curl", "-v", "--header", "X-Debug:true", "--data", peticion, "34.125.171.254:80/searchipn"}; 
            String[] commands = {"curl", "-v", "--header", "X-Debug:true", "--data", peticion, "34.125.171.254:80/searchipn"}; 
            String[] commands = {"curl", "-v", "--header", "X-Debug:true", "--data", peticion, "34.125.171.254:80/searchipn"};  
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        }
        catch(Exception e){
            System.out.println("Error en la creacion del hilo");
        }
    }
}

