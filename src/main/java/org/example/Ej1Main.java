package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ej1Main {
    public static void main(String[] args) {
        Persona p1 = new Persona("Paco", "25");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Persona.class, new TypeAdapter<Persona>() {
                    @Override
                    public void write(JsonWriter jsonWriter, Persona persona) throws IOException {

                        if (persona == null) {
                            jsonWriter.nullValue();

                        }else{
                                jsonWriter.beginObject();
                                jsonWriter.name("name").value(persona.getNombre());
                                jsonWriter.name("age").value(persona.getEdad());
                                jsonWriter.endObject();
                            }
                        }

                    @Override
                    public Persona read(JsonReader jsonReader) throws IOException {
                        String nom="";
                        String ed= "";
                        Persona r = new Persona(nom, ed);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()){
                            String propertyName = jsonReader.nextName();
                            if (propertyName.equals("name")){
                                nom = jsonReader.nextString();
                            } else if (propertyName.equals("age")) {
                                ed = jsonReader.nextString();

                            }
                            r = new Persona(nom, ed);
                        }
                        jsonReader.endObject();
                        return r;
                }}).create();

        System.out.println(gson.toJson(p1));

        try(var in = Files.newBufferedWriter(Path.of("prueba.txt"))){
            in.write(gson.toJson(p1));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path pat = Path.of("prueba.txt");

        String s = null;
        try {
            s = Files.readString(pat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Persona prueba= gson.fromJson(s, Persona.class);
        System.out.println(prueba);



    }
    }
