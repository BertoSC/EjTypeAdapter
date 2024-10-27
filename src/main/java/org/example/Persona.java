package org.example;


import java.util.List;

public class Persona {
    String nombre;
    String edad;
    Direccion direccion;
    List<Persona> amigos;

    public Persona(String n, String e) {
        this.nombre = n;
        this.edad = e;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Persona> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Persona> amigos) {
        this.amigos = amigos;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad='" + edad + '\'' +
                ", direccion=" + direccion +
                ", amigos=" + amigos +
                '}';
    }
}
