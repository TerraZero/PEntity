package tz.pentity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface REntity {

	public String name() default "";
	
	public String table() default "";
	
	public String built() default "";
	
}
