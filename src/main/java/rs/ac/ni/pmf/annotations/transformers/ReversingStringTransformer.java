package rs.ac.ni.pmf.annotations.transformers;

import rs.ac.ni.pmf.annotations.transformer_base.*;
import rs.ac.ni.pmf.annotations.transformer_base.annotation.*;

@TransformPart(start = 0, end = 5)
public class ReversingStringTransformer extends StringTransformer
{
	@TransformPrefix
	private final String prefixToUse = "Tralala_";

	@Override
	@UpperCaseFirst
	protected String doTransform(String value)
	{
		return new StringBuilder(value).reverse().toString();
	}

	@TransformAfter
	protected String additionalTransform(final String value)
	{
		return value + "_TRANSFORMED_AFTER";
	}

	// Just as example
	@TransformAfter
	protected int count(final String value)
	{
		return value.length();
	}

	// Just as example
	@TransformAfter
	protected String concat(final String value1, final String value2)
	{
		return value1 + value2;
	}
}
