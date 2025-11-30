/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package axenda.modal;

import java.util.Objects;
import com.iesrodeira.utils.Input; // Se añade el import para las validaciones

/**
 *
 * @author Francinelle
 */
public class Contacto {
  // Datos privados
    private int numero;          // empieza en -1
    private String descripcion;  // puede estar vacía
    private Persona persona;     // persona asociada
    private String direccion;    // puede estar vacía
    private String telefono;     // debe ser válido
    private String correo;       // debe ser válido

    // Constructor con todos los datos
    public Contacto(Persona persona, String telefono, String correo, String descripcion) {
        this.numero = -1;
        this.persona = persona;
        // Llama a los setters que ahora usan la clase Input para la validación
        setTelefono(telefono); 
        setCorreo(correo);     
        this.descripcion = descripcion;
        this.direccion = "";
    }

    // Constructor sin descripción
    public Contacto(Persona persona, String telefono, String correo) {
        this(persona, telefono, correo, "");
    }

    // Constructor solo con teléfono
    public Contacto(Persona persona, String telefono) {
        this(persona, telefono, "", "");
    }

    // Métodos para leer los datos
    public int getNumero() { return numero; }
    public String getDescripcion() { return descripcion; }
    public Persona getPersona() { return persona; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }

    // Métodos para modificar los datos permitidos
    public void setNumero(int numero) { this.numero = numero; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    // Validar teléfono usando Input.testPhone
    public void setTelefono(String telefono) {
        try {
            if (!telefono.isEmpty()) {
                Input.testPhone(telefono);
            }
            this.telefono = telefono;
        } catch(IllegalArgumentException e) {
            // Se propaga el error de validación
            throw new IllegalArgumentException("Teléfono incorrecto: " + e.getMessage());
        }
    }

    // Validar correo usando Input.testEmail
    public void setCorreo(String correo) {
        try {
            if (!correo.isEmpty()) {
                Input.testEmail(correo);
            }
            this.correo = correo;
        } catch(IllegalArgumentException e) {
            // Se propaga el error de validación
            throw new IllegalArgumentException("Correo incorrecto: " + e.getMessage());
        }
    }

    // Dos contactos son iguales si tienen la misma persona y la misma descripción
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contacto otro = (Contacto) obj;
        return this.persona.equals(otro.persona) && this.descripcion.equals(otro.descripcion);
    }

    // Consistente con equals
    @Override
    public int hashCode() {
        return Objects.hash(persona, descripcion);
    }

    // Cómo se muestra el contacto en texto
    @Override
    public String toString() {
        return String.format("%d) %s: %s (%s) - %s",
                numero, persona.toString(), telefono, correo.isEmpty() ? "sin email" : correo, descripcion);
    }
}
