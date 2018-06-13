package dados;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Adriano
 * Quero Café
 */
public class AcessadorDeArquivo {
	
	public static Dados lerArquivo(String path) {
		try {
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream os = new ObjectInputStream(file);
			Dados d = (Dados) os.readObject();
			return d;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void gravarArquivo(Dados dados, String path) {
		try {
			FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream os = new ObjectOutputStream(file);
			os.writeObject(dados);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
