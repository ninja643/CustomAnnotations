package rs.ac.ni.pmf.annotations.transformer_base.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransformPart
{
	int start() default 0;
	int end() default -1;
}
