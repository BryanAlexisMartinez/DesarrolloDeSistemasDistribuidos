import java.util.List;
import java.util.Random;
import java.util.*;
class Main
{
    public static void main(String[] args)
    {      
        int n = Integer.parseInt(args[0]);
        
        ArrayList<String> Curps = new ArrayList<String>();
        Curps.add(curps());
        Iterator<String> itr = Curps.iterator();
        String Aux;
        for(int i=0; i<n-1; i++){
            int j=0;
            int bandera=0;
            Aux=curps();
            System.out.println(Aux);
            while(itr.hasNext()&&bandera==0){
                if(Aux.compareTo(itr.next())<0){
                    Curps.add(j,Aux);
                    bandera=1;
                }
                j++;
            }
            if(!itr.hasNext()&&Curps.size()==i+1){
                Curps.add(Aux);
            }
            itr=Curps.iterator();
            System.out.println(Curps);
        }
    }
    // Función para generar una CURP aleatoria
    static String curps()
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
