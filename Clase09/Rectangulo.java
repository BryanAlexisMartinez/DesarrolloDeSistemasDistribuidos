public class Rectangulo {
    private Coordenada superiorIzq, inferiorDer;
    public Rectangulo(){
        superiorIzq = new Coordenada(0,0);
        inferiorDer = new Coordenada(0,0);
    }
    
    public Rectangulo(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer){
        superiorIzq = new Coordenada(xSupIzq, ySupIzq);
        inferiorDer = new Coordenada(xInfDer, yInfDer);        
    }
    public Rectangulo(Coordenada si, Coordenada id){
        superiorIzq = si;
        inferiorDer = id;
    }

    //Metodo getter de la coordenada superior izquierda
    public Coordenada superiorIzquierda( ) { return superiorIzq; }

    //Metodo getter de la coordenada inferior derecha
    public Coordenada inferiorDerecha( ) { return inferiorDer; }

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
    @Override

    public String toString( ) {
        return "Esquina superior izquierda: " + superiorIzq + "\tEsquina superior derecha:" + inferiorDer + "\n";
    }
}

