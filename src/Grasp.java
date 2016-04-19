import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Grasp {
  private int numeroVertices;
  private Double[][] distancias;
  private ArrayList<Nodo> solucion;
  private boolean[][] visitado;
  
  public Grasp() throws IOException {
    leerFichero("tests/test1.txt");
    mostrarDatos();
    ArrayList<Nodo> solucionFinal = resolver();
    mostrarSolucion(solucionFinal);
  }
  
  private ArrayList<Nodo> resolver() {
    visitado = new boolean[getNumeroVertices()][getNumeroVertices()];
    // S
    solucion = new ArrayList<Nodo>();
    // S*
    ArrayList<Nodo> solucionNueva = null;
    // Seleccionar la arista con mayor afinidad.
    Nodo mayorArista = buscarMayor();
    // S = {i,j}
    getSolucion().add(mayorArista);
    solucionNueva = getSolucion();

    while(sonIguales(solucionNueva) == true) {
      solucionNueva = getSolucion();
      mayorArista = md();
      if (mayorArista == null) {
        return solucionNueva;
      }
      getSolucion().add(mayorArista);
    }
    return solucionNueva;
  }

  private Nodo md() {
    Double sumatorioSolucion = costeSolucion();
    Nodo mayor = null;
    for (int i = 0; i < distancias.length; i++) {
      for (int j = i + 1; j < distancias.length; j++) {
        if (getDistancias()[i][j] + sumatorioSolucion > sumatorioSolucion  && visitado[i][j] == false) {
          // Mejora solucion.
          mayor = new Nodo(i, j, getDistancias()[i][j] + sumatorioSolucion);
        }
      }
    }
    if (mayor != null) {
      visitado[mayor.getI()][mayor.getJ()] = true;
    }
    return mayor;
  }

  private Double costeSolucion() {
    Double resultado = 0.0;
    for (int i = 0; i < getSolucion().size(); i++) {
      resultado += getSolucion().get(i).getValor();
    }
    return resultado;
  }

  private void mostrarSolucion(ArrayList<Nodo> solucion) {
    System.out.println("\n\nEl mejor subconjunto solución");
    System.out.print("{ ");
    for (int i = 0; i < solucion.size(); i++) {
      System.out.print("( " + solucion.get(i).getI() + ", " + solucion.get(i).getJ() + " )");
    }
    System.out.print(" }\n");
  }

  private boolean sonIguales(ArrayList<Nodo> solucionNueva) {
    for (int i = 0; i < getSolucion().size(); i++) {
      if (getSolucion().get(i).getValor() != solucionNueva.get(i).getValor()) {
        return false;
      }
    }
    return true;
  }

  private Nodo buscarMayor() {
    Double max = Double.MIN_VALUE;
    Nodo mayor = null;
    for (int i = 0; i < distancias.length; i++) {
      for (int j = i + 1; j < distancias.length; j++) {
        if (getDistancias()[i][j] > max && visitado[i][j] == false) {
          max = getDistancias()[i][j];
          mayor = new Nodo(i, j , max);
        }
      }
    }
    visitado[mayor.getI()][mayor.getJ()] = true;
    return mayor;
  }

  private void mostrarDatos() {
    System.out.println(" El número de vértices es: " + getNumeroVertices());
    for (int i = 0; i < distancias.length; i++) {
      System.out.println("\n");
      System.out.print("| ");
      for (int j = 0; j < distancias.length; j++) {
        System.out.print("  " + getDistancias()[i][j] + "  ");
      }
      System.out.print(" |");
    }
  }
  private void leerFichero(String archivo) throws IOException {
    String cadena;
    FileReader f = new FileReader(archivo);
    BufferedReader b = new BufferedReader(f);
    numeroVertices = Integer.parseInt(b.readLine());
    distancias = new Double[numeroVertices][numeroVertices];
    
    for (int i = 0; i < distancias.length; i++) {
      for (int j = 0; j < distancias.length; j++) {     
        if (i == j) {
          distancias[i][j] = 0.0;
        } else if (i != j && distancias[i][j] == null) {
          Double valor = Double.parseDouble(b.readLine());
          distancias[i][j] = valor;
          distancias[j][i] = valor;
        }
      }
    }
    b.close();
  }
  
  private int getNumeroVertices() {
    return numeroVertices;
  }
  
  private void setNumeroVertices(int numeroVertices) {
    this.numeroVertices = numeroVertices;
  }
  
  private Double[][] getDistancias() {
    return distancias;
  }
  
  private void setDistancias(Double[][] distancias) {
    this.distancias = distancias;
  }
  
  public static void main(String[] args) throws IOException {
    Grasp grasp = new Grasp();
  }

  private ArrayList<Nodo> getSolucion() {
    return solucion;
  }

  private void setSolucion(ArrayList<Nodo> solucion) {
    this.solucion = solucion;
  }
  
}
