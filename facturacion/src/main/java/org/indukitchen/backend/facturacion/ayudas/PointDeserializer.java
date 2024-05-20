package org.indukitchen.backend.facturacion.ayudas;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.data.geo.Point;

import java.io.IOException;

public class PointDeserializer extends JsonDeserializer<Point> {

    @Override
    public Point deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String[] pointParts = p.getText().split(",");
        double latitude = Double.parseDouble(pointParts[0].trim());
        double longitude = Double.parseDouble(pointParts[1].trim());
        return new Point(latitude, longitude);
    }
}
