package cat.mood.koryto.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Set;

public class ObjectMapperDialect implements IExpressionObjectDialect {
    private final ObjectMapper objectMapper;

    public ObjectMapperDialect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getName() {
        return "ObjectMapperDialect";
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            private final String OBJECT_NAME = "json";
            private final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Set.of(OBJECT_NAME);

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return ALL_EXPRESSION_OBJECT_NAMES;
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                if (OBJECT_NAME.equals(expressionObjectName)) {
                    return new ThymeleafExpressionObject(objectMapper);
                }
                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }
}

