import prezentacja.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

public class Main 
{
	public static void main(String[] args) {
		try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/logging.properties")) {
			LogManager.getLogManager().readConfiguration(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Nie można wczytać pliku konfiguracyjnego: " + e.getMessage());
        }
		
		Logger logger = Logger.getLogger(Main.class.getName());
        
        logger.log(Level.INFO, "uruchomiono program");
        logger.entering("element graficzny", "Okno");
        new Okno(logger);
        logger.exiting("element graficzny", "Okno");
    }
}
