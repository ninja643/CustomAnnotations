package rs.ac.ni.pmf.annotations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import rs.ac.ni.pmf.annotations.transformer_base.StringTransformer;
import rs.ac.ni.pmf.annotations.transformers.LowerCaseStringTransformer;
import rs.ac.ni.pmf.annotations.transformers.ReversingStringTransformer;

@SpringBootApplication
public class CustomAnnotationsApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(CustomAnnotationsApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(final ApplicationContext context)
	{
		return args -> {
			runTransformation();
		};
	}

	private void runTransformation()
	{
		final String valueToTransform = "aBcDeFgHiJkLm";

		final StringTransformer transformerReverse = new ReversingStringTransformer();
		final StringTransformer transformerLowerCase = new LowerCaseStringTransformer();

		System.out.println(transformerReverse.transform(valueToTransform));
		System.out.println(transformerLowerCase.transform(valueToTransform));
	}
}
