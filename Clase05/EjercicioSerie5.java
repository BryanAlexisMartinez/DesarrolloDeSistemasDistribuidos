import java.util.Random;
import java.nio.charset.StandardCharsets;

public class EjercicioSerie5{

    public static void main( String[] args ) {
        int n = Integer.parseInt(args[0]);
        int frecuencia = 0;
        Random miAleatorio = new Random();
        byte[] cadenota = new byte[n*4];

        for(int i = 1; i <= n*4; i++){
            if(i%4 != 0)
                cadenota [i-1] = (byte) (miAleatorio.nextInt(26)+'A');
            else
                cadenota [i-1] = ' ';
        }

        String s = new String(cadenota, StandardCharsets.UTF_8);
        //System.out.println("Cadenota: " + s);
        String[] arrString = s.split(" ");

        for(int i = 0; i < arrString.length; i++){
            if(arrString[i].equals("IPN"))
                frecuencia++;
        }
        System.out.println("Numero de veces que se muestra IPN: " + frecuencia);
        long nano_startTime = System.nanoTime();
        long nano_endTime = System.nanoTime();
        System.out.println("Time taken in nano seconds: "
                           + (nano_endTime - nano_startTime));
    }
}
