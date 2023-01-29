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
            String[] commands = {"curl", "-v", "--header", "X-Debug:true", "--data", "1757600,IPN", "34.125.171.254:80/searchipn"};  
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

// Read the output from the command
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

// Read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch(Exception e){
            System.out.println("Error en la creacion del hilo");
        }
    }
}

