package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ej4Main {
    public static void main(String[] args) {
        Producto pro = new Producto("Cerveza", 25.998);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Producto.class, new TypeAdapter<Producto>() {
                    @Override
                    public void write(JsonWriter jsonWriter, Producto producto) throws IOException {
                        if (producto == null) {
                            jsonWriter.nullValue();
                        } else {
                            jsonWriter.beginObject();
                            jsonWriter.name("product").value(producto.getNombre());
                            String precioS= producto.getPrecio().toString();
                            String precioF= precioS.substring(0, precioS.indexOf(".")+1)+precioS.substring(precioS.indexOf(".")+1, precioS.indexOf(".")+3);

                            jsonWriter.name("prize").value(precioF);
                            jsonWriter.endObject();
                        }
                    }

                    @Override
                    public Producto read(JsonReader jsonReader) throws IOException {
                        String nombre = null;
                        Double precio = null;
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String nombrePropiedad = jsonReader.nextName();
                            switch (nombrePropiedad) {
                                case "product":
                                    nombre = jsonReader.nextString();
                                    break;
                                case "prize":
                                    precio = Double.parseDouble(jsonReader.nextString());
                                    break;
                            }
                        }
                        jsonReader.endObject();
                        return new Producto(nombre, precio);
                    }
                }).create();

        // Serializando
        String json = gson.toJson(pro);
        System.out.println(json);

        // Escribiendo en el archivo
        try (var out = Files.newBufferedWriter(Path.of("prueba4.txt"))) {
            out.write(json);
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir traza de error para mejor depuración
        }

        // Leyendo del archivo
        Path pat = Path.of("prueba4.txt");
        String s;
        try {
            s = Files.readString(pat);
            Producto prueba = gson.fromJson(s, Producto.class);
            System.out.println(prueba); // Asegúrate de que toString esté implementado
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir traza de error para mejor depuración
        }
    }
}
