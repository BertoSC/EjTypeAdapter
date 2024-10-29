package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PersonaAdapter extends TypeAdapter<Persona> {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Direccion.class, new DireccionAdapter()).create();

    @Override
    public void write(JsonWriter jsonWriter, Persona persona) throws IOException {
        if (persona == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginObject();
        jsonWriter.name("name").value(persona.getNombre());
        jsonWriter.name("age").value(persona.getEdad());
        jsonWriter.name("adress");
        gson.getAdapter(Direccion.class).write(jsonWriter, persona.getDireccion());

        if (persona.getAmigos() != null) {
            jsonWriter.name("friends");
            jsonWriter.beginArray();
            for (Persona amigo : persona.getAmigos()) {
                write(jsonWriter, amigo);  // para gestionar una lista de objetos dentro de otros utilizamos llamada recursiva
            }
            jsonWriter.endArray();
        } else {
            jsonWriter.name("friends").nullValue();
        }

        if (persona.getHobbies() != null){
            jsonWriter.name("likes");
            StringBuilder sb = new StringBuilder();
            for (String s: persona.getHobbies()){
                sb.append(s).append("-");
            }
            int indice = sb.lastIndexOf("-");
            sb.deleteCharAt(indice);
            jsonWriter.value(sb.toString());
        }

        jsonWriter.endObject();
    }

    @Override
    public Persona read(JsonReader jsonReader) throws IOException {
        String nombre = null;
        String edad = null;
        Direccion direccion = null;
        List<Persona> amigos = new ArrayList<>();
        String [] aficiones = null;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String propiedad = jsonReader.nextName();
            switch (propiedad) {
                case "name":
                    nombre = jsonReader.nextString();
                    break;
                case "age":
                    edad = jsonReader.nextString();
                    break;
                case "adress":
                    direccion = gson.getAdapter(Direccion.class).read(jsonReader);
                    break;
                case "friends":
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        amigos.add(read(jsonReader));  // Llamada recursiva para leer cada amigo
                    }
                    jsonReader.endArray();
                    break;
                case "likes":
                    aficiones = jsonReader.nextString().split("-");


            }
        }
        jsonReader.endObject();

        Persona persona = new Persona(nombre, edad);
        persona.setDireccion(direccion);
        persona.setAmigos(amigos);
        persona.setHobbies(aficiones);
        return persona;
    }
}