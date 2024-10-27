package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ej2Main {
    public static void main(String[] args) {
        Persona p2 = new Persona("María", "32");
        Direccion dir = new Direccion("Abalorios", "Fene");
        p2.setDireccion(dir);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Persona.class, new TypeAdapter<Persona>() {
                    @Override
                    public void write(JsonWriter jsonWriter, Persona persona) throws IOException {
                        if (persona == null) {
                            jsonWriter.nullValue();
                        } else {

                            jsonWriter.beginObject();
                            jsonWriter.name("name").value(persona.getNombre());
                            jsonWriter.name("age").value(persona.getEdad());
                            if (persona.getDireccion() != null) {
                                jsonWriter.name("adress");
                                jsonWriter.beginObject();
                                jsonWriter.name("street").value(persona.getDireccion().getCalle());
                                jsonWriter.name("city").value(persona.getDireccion().getCiudad());
                                jsonWriter.endObject();
                            } else {
                                jsonWriter.name("adress").nullValue();
                            }
                            jsonWriter.endObject();
                        }
                    }

                    @Override
                    public Persona read(JsonReader jsonReader) throws IOException {
                        String n = null;
                        String e = null;
                        Direccion d = null;
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String nombrePro = jsonReader.nextName();
                            switch (nombrePro) {
                                case "name":
                                    n = jsonReader.nextString();
                                    break;
                                case "age":
                                    e = jsonReader.nextString();
                                    break;
                                case "adress":
                                    jsonReader.beginObject();
                                    String calle = null;
                                    String ciudad = null;
                                    while (jsonReader.hasNext()) {
                                        String propiedad = jsonReader.nextName();
                                        if (propiedad.equals("street")) {
                                            calle = jsonReader.nextString();
                                        } else if (propiedad.equals("city")) {
                                            ciudad = jsonReader.nextString();
                                        }
                                    }
                                    d = new Direccion(calle, ciudad);
                                    jsonReader.endObject();
                                    break;
                            }

                        }
                        jsonReader.endObject();
                        Persona temp = new Persona(n, e);
                        temp.setDireccion(d);
                        return temp;

                    }
                }).create();

        System.out.println(gson.toJson(p2));

        try (var in = Files.newBufferedWriter(Path.of("prueba2.txt"))) {
            in.write(gson.toJson(p2));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path pat = Path.of("prueba2.txt");

        String s = null;
        try {
            s = Files.readString(pat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Persona prueba = gson.fromJson(s, Persona.class);
        System.out.println(prueba);

    }
}
