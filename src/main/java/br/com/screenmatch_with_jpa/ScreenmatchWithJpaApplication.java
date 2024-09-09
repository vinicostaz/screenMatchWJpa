package br.com.screenmatch_with_jpa;

import br.com.screenmatch_with_jpa.main.Main;
import br.com.screenmatch_with_jpa.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchWithJpaApplication implements CommandLineRunner {
	@Autowired
	private SeriesRepository repository;

	public static void main(String[] args) {SpringApplication.run(ScreenmatchWithJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.showMenu();

	}

}
