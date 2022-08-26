package jpa.dbfunction;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomH2Function extends H2Dialect {
    public CustomH2Function() {
        registerFunction(
                "custom_string_concat",
                new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
