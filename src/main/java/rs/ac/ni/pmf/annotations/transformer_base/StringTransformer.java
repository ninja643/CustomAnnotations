package rs.ac.ni.pmf.annotations.transformer_base;

import java.lang.reflect.*;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.annotations.transformer_base.annotation.*;

@Slf4j
public abstract class StringTransformer
{
	public final String transform(final String value)
	{
		final Class<? extends StringTransformer> clazz = this.getClass();
		boolean shouldUpperCaseFirst = false;
		String valueToTransform;
		int start = 0;
		int end = -1;

		try
		{
			// Access to non-public methods needs to be explicitly given
			clazz.getDeclaredMethod("doTransform", String.class).setAccessible(true);
			shouldUpperCaseFirst = clazz.getDeclaredMethod("doTransform", String.class)
				.isAnnotationPresent(UpperCaseFirst.class);
		}
		catch (NoSuchMethodException e)
		{
			// Won't happen
		}

		if (clazz.isAnnotationPresent(TransformPart.class))
		{
			start = clazz.getAnnotation(TransformPart.class).start();
			end = clazz.getAnnotation(TransformPart.class).end();

			if (start < 0)
			{
				start = 0;
			}
			if (end == -1)
			{
				end = value.length();
			}

			valueToTransform = value.substring(start, end);
		}
		else
		{
			valueToTransform = value;
		}

		for (final Field field : clazz.getDeclaredFields())
		{
			if (field.isAnnotationPresent(TransformPrefix.class))
			{
				// Again, access to non-public members need to be explicitly allowed
				field.setAccessible(true);

				try
				{
					final Object fieldValue = field.get(this);

					// Prior to Java 16
					//					if (fieldValue instanceof String)
					//					{
					//						final String prefix = (String)fieldValue;
					//						valueToTransform = prefix + valueToTransform;
					//					}

					if (fieldValue instanceof String prefix)
					{
						valueToTransform = prefix + valueToTransform;
					}
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}

		if (shouldUpperCaseFirst)
		{
			valueToTransform = valueToTransform.toUpperCase(Locale.ROOT);
		}

		String transformedValue = doTransform(valueToTransform);

		for (final Method method : clazz.getDeclaredMethods())
		{
			method.setAccessible(true);

			if (canApply(method))
			{
				try
				{
					transformedValue = (String)method.invoke(this, transformedValue);
				}
				catch (IllegalAccessException | InvocationTargetException e)
				{
					e.printStackTrace();
				}
			}
		}

		return transformedValue;
	}

	private boolean canApply(final Method method)
	{
		final String methodName = method.getName();

		if (!method.isAnnotationPresent(TransformAfter.class))
		{
			return false;
		}

		if (method.getParameterCount() != 1)
		{
			log.warn("Method {} should have only one parameter", methodName);
			return false;
		}

		if (!method.getParameterTypes()[0].equals(String.class))
		{
			log.warn("Method {} does not have a single String parameter", methodName);
			return false;
		}

		if (!method.getReturnType().equals(String.class))
		{
			log.warn("Method {} does not return String", methodName);
			return false;
		}

		return true;
	}

	protected abstract String doTransform(final String value);
}
