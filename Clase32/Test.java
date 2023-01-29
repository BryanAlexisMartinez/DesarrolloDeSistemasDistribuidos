// Código Java para probar la serialización y deserialización con ayuda de la clase SerializationUtils.java
import java.io.*;

class Demo implements java.io.Serializable
{
	public int a;
	public String b;

	// Default constructor
	public Demo(int a, String b)
	{
		this.a = a;
		this.b = b;
	}
}

class Test
{
	public static void main(String[] args)
	{
		Demo object = new Demo(2022, "Prueba serializacion y deserializacion");
		Demo objeto = null;
		
		byte[] serializado = SerializationUtils.serialize(object);
		System.out.println(serializado);
		objeto = (Demo)SerializationUtils.deserialize(serializado);
		System.out.println("a = " + objeto.a);
		System.out.println("b = " + objeto.b);
	}
}
