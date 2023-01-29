/*
PROYECTO 2 Segunda parte.
Martinez Alvarado Bryan Alexis.
Grupo 4CM12.
*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class PalindromosDeUnTexto {
    public static void main(String[] args) throws IOException {
        FileInputStream documentotexto = new FileInputStream("TextoConPalindromos.txt");
        int n = documentotexto.read();
        String g = "";
        while (n != -1) {
            char car = (char) n;
            g = g + car;
            if (car == ' ') {
                boolean flag = palindromo(g.trim());
                if (flag) {
                    System.out.println(g);
                    g = "";
                }
            }
            n = documentotexto.read();
        }
    }
    private static boolean palindromo(String g) {
        boolean flag = false;
        for (int i = 0, j = g.length() - 1; i <= j; i++, j--) {
            if (g.charAt(i) != g.charAt(j)) {
                flag = true;
                break;
            }
        }
        return !flag;
    }
}
