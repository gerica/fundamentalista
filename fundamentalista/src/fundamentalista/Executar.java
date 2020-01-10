package fundamentalista;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import fundamentalista.view.PapelView;

@SpringBootApplication
public class Executar {

	private static final Logger logger = LoggerFactory.getLogger(Executar.class);

	public static void main(String[] args) {
		logger.info("Fundamentalista inicializar");
		// ApplicationContext context = new
		// AnnotationConfigApplicationContext(new Class[] { RootConfig.class });
		// AnnotationConfigApplicationContext context = new
		// AnnotationConfigApplicationContext(new Class[] { RootConfig.class });
		// context.scan("fundamentalista.*");
		// context.refresh();
		try {

//			ApplicationContext ctx = SpringApplication.run(Executar.class, args);
			ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Executar.class).headless(false).run(args);

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					PapelView main = (PapelView) ctx.getBean(PapelView.class);
					main.refresh();
				}
			});
			//
			// String[] beanNames = ctx.getBeanDefinitionNames();
			// Arrays.sort(beanNames);
			// for (String beanName : beanNames) {
			// System.out.println(beanName);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
