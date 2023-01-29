public class PoligonoIrreg {
  private Coordenada[] vertices;
  private int numVertices;

  public PoligonoIrreg() {
    vertices = new Coordenada[100];
    numVertices = 0;
  }

  //Añadir vértice.
  public void anadeVertice(Coordenada coord) {
    vertices[numVertices] = coord;
    numVertices++;
  }

  //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
  @Override
  public String toString() {
    StringBuilder conjuntoVertices = new StringBuilder();
    conjuntoVertices.append("Conjunto de vértices: \n");
    for (short i = 0; i < numVertices; i++) {
      conjuntoVertices.append(vertices[i].toString() + "\n");
    }

    return conjuntoVertices.toString();
  }
}

