package shared.annotations;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ServerEndpoint {
    String value();

    boolean gameSpecific() default true;

    boolean requiresAuth() default true;

    boolean isPost() default true;

    String returnsCookie() default "";
}
