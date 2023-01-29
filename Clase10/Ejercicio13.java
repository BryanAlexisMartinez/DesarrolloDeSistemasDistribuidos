import java.util.List;
import java.util.Random;
import java.util.*;
class Main
{
    public static void main(String[] args)
    {      
        int cantidad = Integer.parseInt(args[0]);
        String sexo = args[1];         
        List<String> curps = new ArrayList<>();
        
        
        for(int i=0;i<cantidad;i++){
            curps.add(getCURP());
        }
        //System.out.println("Todos los CURPS generados son: ");
        System.out.println(curps);
    
        Iterator<String> filtro = curps.iterator();
        while (filtro.hasNext()) {
            String curpPrueba = filtro.next();
            char genero = curpPrueba.charAt(10);
            if(sexo.charAt(0) != genero)
                filtro.remove();
            
        }
        System.out.println();
       // System.out.println("Los curps con solo el genero "+ sexo.charAt(0) +" son: ");
        System.out.println(curps);
    }
    // Función para generar una CURP aleatoria
    static String getCURP()
    {
        String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Numero = "0123456789";
        String Sexo = "HM";
        String Entidad[] = {"AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS"};
        int indice;
        
        StringBuilder sb = new StringBuilder(18);
        
        for (int i = 1; i < 5; i++) {
            indice = (int) (Letra.length()* Math.random());
            sb.append(Letra.charAt(indice));        
        }
        
        for (int i = 5; i < 11; i++) {
            indice = (int) (Numero.length()* Math.random());
            sb.append(Numero.charAt(indice));        
        }
 
        indice = (int) (Sexo.length()* Math.random());
        sb.append(Sexo.charAt(indice));        
        
        sb.append(Entidad[(int)(Math.random()*32)]);
 
        for (int i = 14; i < 17; i++) {
            indice = (int) (Letra.length()* Math.random());
            sb.append(Letra.charAt(indice));        
        }
 
        for (int i = 17; i < 19; i++) {
            indice = (int) (Numero.length()* Math.random());
            sb.append(Numero.charAt(indice));        
        }
        
        return sb.toString();
    }           
}
