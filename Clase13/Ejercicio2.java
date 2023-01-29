import java.util.*;
import java.io.*;
public class Ejercicio2{
    public static void main(String args[]){
        try{
            Map<String, Integer> hm = new HashMap<>(); 
            ArrayList<Integer> aux = new ArrayList<Integer>(); //****
            File archivo=new File("El_viejo_y_el_mar.txt");
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
            System.out.print("La cantidad de diferentes caracteres es de: ");
            System.out.println(hm.size());
            System.out.println("Los caracteres ordenados quedan de la siguiente manera (de mayor número de apariciones a menor número): ");
            for (Map.Entry<String, Integer> me : hm.entrySet()) { 
                aux.add(me.getValue());
            }
            aux.sort(Comparator.reverseOrder());
         
            for(Integer i : aux){
                for (Map.Entry<String, Integer> me : hm.entrySet()) { 
                    if(me.getValue()==i){
                        System.out.print(me.getKey()+": ");
                        System.out.print(me.getValue()+"\t");
                    }
                }
            }
            System.out.println("\n");    
        }
        catch(Exception e){
            System.out.println(e);
        }
//*****************************************************
    }
}
