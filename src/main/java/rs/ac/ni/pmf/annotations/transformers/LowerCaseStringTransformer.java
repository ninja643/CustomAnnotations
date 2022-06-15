package rs.ac.ni.pmf.annotations.transformers;

import java.util.Locale;
import rs.ac.ni.pmf.annotations.transformer_base.StringTransformer;

public class LowerCaseStringTransformer extends StringTransformer
{
	@Override
	protected String doTransform(String value)
	{
		return value.toLowerCase(Locale.ROOT);
	}
}
