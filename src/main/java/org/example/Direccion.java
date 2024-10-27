package org.example;

public class Direccion {
    String calle;

    String ciudad;

    Direccion(){
    }

    public Direccion(String cal, String ciu){
        this.calle = cal;
        this.ciudad = ciu;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
