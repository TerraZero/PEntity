package tz.pentity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface REField {
	
	public String type();

	public String name() default "";
	
	public int size() default 0;
	
	public String[] additionals() default {};
	
	public boolean primary() default false;
	
}
