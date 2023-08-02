package com.genebank.genedatabank.entity;

import com.google.common.base.Strings;
import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

@DatatypeDef(
        id="geocoordinate",
        javaClass = Double.class
)
@Ddl("double precision")
public class GeoCoordinateDatatype implements Datatype<Double> {
    private static final String PATTERN = "#0.000000";
    @Override
    public String format(@Nullable Object value) {
        if (value == null) {
            return "";
        }
        DecimalFormat format = new DecimalFormat(PATTERN);
        return format.format(value);
    }
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }
    @Nullable
    @Override
    public Double parse(@Nullable String value) throws ParseException {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        DecimalFormat format = new DecimalFormat(PATTERN);
        return format.parse(value).doubleValue();
    }
    @Nullable
    @Override
    public Double parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }
}
