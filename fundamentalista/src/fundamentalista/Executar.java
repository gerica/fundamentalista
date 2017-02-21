package fundamentalista;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import fundamentalista.view.PapelView;

@Configuration
@EntityScan("fundamentalista.*")
@EnableAutoConfiguration()
@SpringBootApplication
public class Executar {

	private static final Logger logger = LoggerFactory.getLogger(Executar.class);

	public static void main(String[] args) {
		logger.info("Fundamentalista inicializar");

		// ApplicationContext ctx = SpringApplication.run(Executar.class, args);
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Executar.class).headless(false).run(args);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PapelView main = (PapelView) ctx.getBean(PapelView.class);
				main.refresh();
			}
		});

	}

}
