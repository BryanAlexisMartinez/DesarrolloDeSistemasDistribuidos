import java.util.ArrayList;
import java.util.List;

public class PoligonoIrreg {
  private List<Coordenada> vertices;

  public PoligonoIrreg() {
    vertices = new ArrayList<Coordenada>();
  }

  //Añadir vértice.
  public void anadeVertice(Coordenada coord) {
    vertices.add(coord);
  }

  //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
  @Override
  public String toString() {
    StringBuilder conjuntoVertices = new StringBuilder();
    conjuntoVertices.append("\n\nConjunto de vértices:\n");
    for (Coordenada vertice : vertices) {
      conjuntoVertices.append(vertice).append("\n");
    }
    return conjuntoVertices.toString();
  }
}

