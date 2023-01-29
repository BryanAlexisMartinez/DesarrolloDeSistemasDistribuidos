import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat; 
import java.util.Date;
public class Ejercicio1{
    public static void main(String args[]){
        
//******************************************
        try{
            Map<String, Integer> hm = new HashMap<>(); 
            File archivo=new File("BIBLIA_COMPLETA.txt");
            Scanner entrada=new Scanner(archivo);
            String in;
            while(entrada.hasNextLine()){
                in=entrada.nextLine();
                for(int i=0; i<in.length(); i++){
                    if(!hm.containsKey(String.valueOf(in.charAt(i)))){
                        hm.put(String.valueOf(in.charAt(i)),new Integer(1));
                    }
                    else{
                        hm.put(String.valueOf(in.charAt(i)), hm.get(String.valueOf(in.charAt(i)))+1);
                    }
                }
            }
            //Date d = new Date();
            //SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
            System.out.print("La cantidad de diferentes caracteres es de: ");
            System.out.println(hm.size());
            for (Map.Entry<String, Integer> me : hm.entrySet()) {   
                System.out.print(me.getKey() + ":"); 
                System.out.print(me.getValue()+"\t"); 
            } 
            System.out.println("\n");
           // System.out.print(ft.format(d));
        }
        catch(Exception e){
            System.out.println(e);
        }

//****************************************************
    }
}
