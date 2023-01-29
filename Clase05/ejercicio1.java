public class ejercicio1{
    public static void main(String[] args){
        long inicio = System.nanoTime();
        int n = Integer.parseInt(args[0]);
        int countIpn = 0;
        byte[] cadena = new byte[n*4];
        short[] posiciones = new short[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < 3; j++){
                int letra = (int) (Math.random() * (90 - 65)) + 65;
                cadena[i*4+j] = (byte)(char)letra;
            }
            cadena[i*4+3] = (byte)(char)32;
        }
        for(int i = 0; i < cadena.length; i++){
            if(i<cadena.length-4){
                if(cadena[i] == (byte)'I' && cadena[i+1] == (byte) 'P' && cadena[i+2] == (byte) 'N'){
                    posiciones[countIpn] = (short)i;
                    countIpn++;
                }   
            }
            
        }
        System.out.println("Concurrencias IPN: "+countIpn);
        System.out.println("Tiempo: " + (System.nanoTime()-inicio) + " ns");
    }
}

