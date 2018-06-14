package dados;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * Esta classe gerencia todas as funcionalidades de acesso a arquivos no sistema.
 * 
 */
public class AcessadorDeArquivo {
	
	/**
	 * Deserializa um arquivo especificado e retorna objeto do tipo dados.
	 * 
	 * @param path Caminho relativo para o arquivo que deverá ser deserializado. 
	 * @return Objeto do tipo Dados deserializado. Retorna nulo caso o arquivo não exista ou haja algum erro na deserialização.
	 */
	public static Dados lerArquivo(String path) {
		try {
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream os = new ObjectInputStream(file);
			Dados d = (Dados) os.readObject();
			file.close();
			os.close();
			return d;
		} catch (IOException | ClassNotFoundException e) {}
		return null;
	}
	
	/**
	 * 
	 * Serializa o objeto em um arquivo especificado.
	 * 
	 * @param dados Objeto do tipo Dados que será escrito no arquivo.
	 * @param path Caminho para o arquivo serializado.
	 */
	public static void gravarArquivo(Dados dados, String path) {
		try {
			FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream os = new ObjectOutputStream(file);
			os.writeObject(dados);
			file.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
