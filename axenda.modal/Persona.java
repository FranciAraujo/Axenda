/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package axenda.modal; // Usando tu convención 'modal'

import java.util.Objects;
/**
 *
 * @author Francinelle
 */
public class Persona {
   // Datos privados (no se pueden cambiar después)
    private String nif;
    private String apellidos;
    private String nombre;

    // Constructor: se necesita el NIF, los apellidos y el nombre
    public Persona(String nif, String apellidos, String nombre) {
        // Si el NIF no es válido, se lanza un error
        if (!nifValido(nif)) {
            throw new IllegalArgumentException("NIF incorrecto");
        }
        this.nif = nif;
        this.apellidos = apellidos;
        this.nombre = nombre;
    }

    // Método para comprobar si el NIF tiene 8 números y 1 letra mayúscula
    private boolean nifValido(String nif) {
        if (nif == null || nif.length() != 9) return false;
        String numeros = nif.substring(0, 8);
        char letra = nif.charAt(8);
        return numeros.matches("\\d{8}") && Character.isUpperCase(letra);
    }

    // Métodos para leer los datos (no se pueden modificar)
    public String getNif() { return nif; }
    public String getApellidos() { return apellidos; }
    public String getNombre() { return nombre; }

    // Dos personas son iguales si tienen el mismo NIF
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Persona otra = (Persona) obj;
        return Objects.equals(this.nif, otra.nif);
    }
    
    // Implementación necesaria para ser consistente con equals
    @Override
    public int hashCode() {
        return Objects.hash(this.nif);
    }

    // Representación como String: [nif] apelidos, nome
    @Override
    public String toString() {
        return String.format("[%s] %s, %s", nif, apellidos, nombre);
    }
}
