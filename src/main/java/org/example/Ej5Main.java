package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Ej5Main {
    public static void main(String[] args) {
        List<Persona> nombres = new ArrayList<>();
        nombres.add(new Persona("Chris Cornell", "35"));
        nombres.add(new Persona("Wayne Stanley", "25"));
        nombres.add(new Persona("Peter Steele", "46"));
        nombres.add(new Persona("Peter Murphy", "60"));
        nombres.add(new Persona("Jimmy Bower", "57"));

        Direccion dir = new Direccion("Abalorios", "Fene");

        String [] aficiones = {"viciar", "jitarrear", "cocinar koreanadas", "darlle jas"};

        Persona p5 = new Persona("Paco", "35");
        p5.setDireccion(dir);
        p5.setAmigos(nombres);
        p5.setHobbies(aficiones);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Persona.class, new PersonaAdapter()).create();

        System.out.println(gson.toJson(p5));


        try (var out = Files.newBufferedWriter(Path.of("prueba5.txt"))) {
            out.write(gson.toJson(p5));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Path pat = Path.of("prueba5.txt");
        String s;
        try {
            s = Files.readString(pat);
            Persona prueba = gson.fromJson(s, Persona.class);
            System.out.println(prueba);
        } catch (IOException e) {
            e.printStackTrace();
        }










    }
}
