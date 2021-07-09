package meli.bootcamp.desafio_spring.util;

import org.springframework.data.domain.Sort;

import java.util.Objects;

public class SortUtils {
    public static Sort getUserSorterOf(String paramName) {
        if (Objects.isNull(paramName))
            return null;
        if (paramName.equalsIgnoreCase("name_asc"))
            return sorterWith(Sort.Direction.ASC, "username");
        if (paramName.equalsIgnoreCase("name_desc"))
            return sorterWith(Sort.Direction.DESC, "username");
        return null;
    }

    public static Sort getPostSorterOf(String paramName) {
        if (Objects.isNull(paramName))
            return null;
        if (paramName.equalsIgnoreCase("date_asc"))
            return sorterWith(Sort.Direction.ASC, "createdAt");
        if (paramName.equalsIgnoreCase("date_desc"))
            return sorterWith(Sort.Direction.DESC, "createdAt");
        return null;
    }

    private static Sort sorterWith(Sort.Direction dir, String property) {
        return Sort.by(dir, property);
    }
}
