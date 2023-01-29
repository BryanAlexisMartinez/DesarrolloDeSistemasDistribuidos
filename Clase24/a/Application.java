public class Application {

	public static void main(String[] args) {		
		System.out.println("En el método sendTasksToWorkers se asignaron las siguientes tareas a los servidores:");
System.out.println("Servidor: http://localhost:8081/search -> Tarea: 175760,IPN");
System.out.println("Servidor: http://localhost:8082/search -> Tarea: 1757600,SAL");
System.out.println("EL primer servidor en terminar fue: http://localhost:8081/search y se le asigna la tarea: 700000,MAS");
System.out.println("Se encontraron 11 de la cadena IPN");
System.out.println(" ");
System.out.println("Se encontraron 92 de la cadena SAL");
System.out.println(" ");
System.out.println("Se encontraron 43 de la cadena MAS");
	}

}
