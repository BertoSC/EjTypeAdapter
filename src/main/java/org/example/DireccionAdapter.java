package org.example;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DireccionAdapter extends TypeAdapter<Direccion> {
    @Override
    public void write(JsonWriter jsonWriter, Direccion direccion) throws IOException {
        if (direccion == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginObject();
        jsonWriter.name("street").value(direccion.getCalle());
        jsonWriter.name("city").value(direccion.getCiudad());
        jsonWriter.endObject();
    }

    @Override
    public Direccion read(JsonReader jsonReader) throws IOException {
        String calle = null;
        String ciudad = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String propiedad = jsonReader.nextName();
            switch (propiedad) {
                case "street":
                    calle = jsonReader.nextString();
                    break;
                case "city":
                    ciudad = jsonReader.nextString();
                    break;
            }
        }
        jsonReader.endObject();
        return new Direccion(calle, ciudad);
    }
}