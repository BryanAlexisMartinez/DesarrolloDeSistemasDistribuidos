import java.util.*;
public class Ejercicio15{
    public static int variable_compartida=0;
    public static int n;
    
    public static void modifica(Long Id){
        if(Id==11){
            variable_compartida++;
        }
        if(Id==12){
            variable_compartida--;
        }
    }
    public static void main(String[] args) throws InterruptedException{
        n = Integer.parseInt(args[0]);
        RunableImpl t = new RunableImpl();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(variable_compartida);
        
    }
    public static class RunableImpl implements Runnable{
        public void run(){
            for(int i=0; i<n; i++){
                modifica(Thread.currentThread().getId());
            }
        }
    }
}
