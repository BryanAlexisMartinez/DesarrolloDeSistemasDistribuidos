public class ejercicio2{
    public static void main(String[] args){
        long inicio = System.nanoTime();
        int n = Integer.parseInt(args[0]);
        int countIpn = 0;
        StringBuilder s = new StringBuilder(n*4);
        short[] posiciones = new short[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < 3; j++){
                int letra = (int) (Math.random() * (90 - 65)) + 65;
                s.append(Character.toString((char)letra));
            }
            s.append(" ");
        }
        int ini = 0;
        if(s.indexOf("IPN", ini)!=-1){
            while(s.indexOf("IPN", ini)!=-1){
                posiciones[countIpn] = (short)s.indexOf("IPN", ini);
                countIpn++;
                ini = s.indexOf("IPN", ini) + 1;
            }
        }
        System.out.println("Concurrencias IPN: "+countIpn);
        System.out.println("Tiempo: " + (System.nanoTime()-inicio) + " ns");
    }
}

