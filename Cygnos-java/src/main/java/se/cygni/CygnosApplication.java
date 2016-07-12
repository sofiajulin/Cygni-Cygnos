package se.cygni;

import javafx.embed.swing.JFXPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CygnosApplication {

	static {
        // Needed for JavaFX to work
		System.setProperty("java.awt.headless", "false");

        // Force javafx toolkit init
        new JFXPanel();
	}

	public static void main(String[] args) {
		SpringApplication.run(CygnosApplication.class, args);
	}
}
