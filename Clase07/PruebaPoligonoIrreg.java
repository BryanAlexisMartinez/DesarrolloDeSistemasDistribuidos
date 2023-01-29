public class PruebaRectangulo {
public static void main (String[] args) {
PoligonoIrreg rect1 = new PoligonoIrreg(2,3,5,1);
double ancho, alto;
System.out.println("Calculando el área de un rectángulo dadas sus coordenadas en un plano cartesiano:");
System.out.println(rect1);
alto = rect1.superiorIzquierda().ordenada() - rect1.inferiorDerecha().ordenada();
ancho = rect1.inferiorDerecha().abcisa() - rect1.superiorIzquierda().abcisa();
System.out.println("El área del rectángulo es = " + ancho*alto);
}
}


