package Poligono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Polígono irregular
public class Demo implements java.io.Serializable
{
  private List<Coordenada> vertices;

  public Demo() {
    vertices = new ArrayList<Coordenada>();
  }

  //Añadir vértice.
  public void anadeVertice(Coordenada coord) {
    vertices.add(coord);
  }
  public void ordenaVertices() {
    Collections.sort(vertices, new Comparator<Coordenada>() {
      @Override
      public int compare(Coordenada o1, Coordenada o2) {
        return o1.getMagnitud().compareTo(o2.getMagnitud());
      }
    });
  }
  //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
  @Override
  public String toString() {
    StringBuilder conjuntoVertices = new StringBuilder();
    for (Coordenada vertice : vertices) {
      conjuntoVertices.append(vertice).append(" Magnitud: ").append(vertice.getMagnitud()).append("\n");
    }
    return conjuntoVertices.toString();
  }

}

