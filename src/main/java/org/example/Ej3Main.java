package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Ej3Main {
    public static void main(String[] args) {

        List<Persona> nombres = new ArrayList<>();
        nombres.add(new Persona("Chris Cornell", "35"));
        nombres.add(new Persona("Wayne Stanley", "25"));
        nombres.add(new Persona("Peter Steele", "46"));
        nombres.add(new Persona("Peter Murphy", "60"));
        nombres.add(new Persona("Jimmy Bower", "57"));
        Direccion dir = new Direccion("Abalorios", "Fene");

        Persona p3 = new Persona("Paco", "35");
        p3.setDireccion(dir);
        p3.setAmigos(nombres);

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

                            if (persona.getAmigos() != null) {
                                jsonWriter.name("friends");
                                jsonWriter.beginArray();
                                for (Persona p : persona.getAmigos()) {
                                    jsonWriter.beginObject();
                                    jsonWriter.name("name").value(p.getNombre());
                                    jsonWriter.name("age").value(p.getEdad());
                                    jsonWriter.name("adress").nullValue();
                                    jsonWriter.name("friends").nullValue();
                                    jsonWriter.endObject();
                                }
                                jsonWriter.endArray();
                            }
                            jsonWriter.endObject();
                        }
                    }

                    @Override
                    public Persona read(JsonReader jsonReader) throws IOException {
                        String nombre = null;
                        String edad = null;
                        Direccion dir = null;
                        List<Persona> amigos = new ArrayList<>();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String propiedadPer = jsonReader.nextName();
                            switch (propiedadPer) {
                                case "name":
                                    nombre = jsonReader.nextString();
                                    break;
                                case "age":
                                    edad = jsonReader.nextString();
                                    break;
                                case "adress":
                                    jsonReader.beginObject();
                                    String calle = null;
                                    String ciudad = null;
                                    while (jsonReader.hasNext()) {
                                        String propiedadDir = jsonReader.nextName();
                                        switch (propiedadDir) {
                                            case "street":
                                                calle = jsonReader.nextString();
                                                break;
                                            case "city":
                                                ciudad = jsonReader.nextString();
                                                break;
                                        }


                                    }
                                    dir = new Direccion(calle, ciudad);
                                    jsonReader.endObject();
                                    break;

                                case "friends":
                                    jsonReader.beginArray();

                                    while (jsonReader.hasNext()) {
                                        String nombreF = null;
                                        String edadF = null;
                                        Direccion dirF = null;
                                        List<Persona> friF = null;
                                        jsonReader.beginObject();
                                        while (jsonReader.hasNext()) {
                                            String propiedadF = jsonReader.nextName();
                                            switch (propiedadF) {
                                                case "name":
                                                    nombreF = jsonReader.nextString();
                                                    break;
                                                case "age":
                                                    edadF = jsonReader.nextString();
                                                    break;
                                            }
                                        }
                                        amigos.add(new Persona(nombreF, edadF));
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                    break;
                            }
                        }

                        jsonReader.endObject();
                        Persona persona = new Persona(nombre, edad);
                        persona.setDireccion(dir);
                        persona.setAmigos(amigos);
                        return persona;
                    }
                }).create();

        System.out.println(gson.toJson(p3));

        try (var in = Files.newBufferedWriter(Path.of("prueba3.txt"))) {
            in.write(gson.toJson(p3));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path pat = Path.of("prueba3.txt");

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
