/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package axenda; 

import axenda.modal.Contacto;
import axenda.modal.forms.Forms;
import axenda.modal.dao.ContactosDAO;
import axenda.modal.dao.DuplicateElement;
import axenda.modal.dao.StorageFull;
import com.iesrodeira.utils.Input;
import com.iesrodeira.utils.CancelException;

/**
 * Clase principal de la aplicación Axenda.
 * Se encarga de la interfaz de usuario, gestión del menú y flujo de trabajo.
 */
public class Axenda { 

    public static void main(String[] args) { 
        ContactosDAO dao = new ContactosDAO(); // Nuestra agenda (capacidad por defecto 100)
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1.- Lista de contactos");
            System.out.println("2.- Registro de Contactos");
            System.out.println("3.- Buscar contactos");
            System.out.println("4.- Vete.");

            try {
                char opcion = Input.option("Elixe unha opción", "1234");

                switch (opcion) {
                    case '1': listarContactos(dao); break;
                    case '2': registrarContacto(dao); break;
                    case '3': buscarContactos(dao); break;
                    case '4': 
                        salir = true;
                        System.out.println("Apertando o botón de saída...");
                        break;
                }
            } catch (CancelException e) {
                // No se hace nada, simplemente vuelve al menú principal
            } catch (Exception e) {
                System.out.println("\t[ERRO FATAL]: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra todos los contactos y permite borrar todos.
     */
    private static void listarContactos(ContactosDAO dao) {
        Contacto[] contactos = dao.listarTodos();

        if (contactos.length == 0) {
            System.out.println("\nNon hai contactos na axenda.");
            Input.waitEnter();
            return;
        }

        System.out.println("\n--- LISTA DE CONTACTOS (" + contactos.length + ") ---");
        for (Contacto c : contactos) {
            System.out.println(c);
        }
        
        try {
            if (Input.areYouSure()) {
                dao.eliminarTodos();
                System.out.println("\nTodos os contactos foron eliminados.");
            }
        } catch (CancelException e) {
            System.out.println("Operación de eliminación cancelada.");
        }
        Input.waitEnter();
    }

    /**
     * Pide los datos de un nuevo contacto e intenta almacenarlo.
     */
    private static void registrarContacto(ContactosDAO dao) {
        try {
            Contacto nuevo = Forms.contactForm();
            dao.addContacto(nuevo);
            System.out.println("\nContacto gardado correctamente co número: " + nuevo.getNumero());
        } catch (CancelException e) {
            System.out.println("\nOperación de rexistro cancelada.");
        } catch (DuplicateElement e) {
            System.out.println("\n[ERRO]: Este contacto xa existe:");
            System.out.println(e.getMessage());
        } catch (StorageFull e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\t[ERRO]: Ocurriu un erro ao rexistrar: " + e.getMessage());
        }
        Input.waitEnter();
    }

    /**
     * Permite buscar contactos por criterio y, si se encuentran, modificarlos o eliminarlos.
     */
    private static void buscarContactos(ContactosDAO dao) {
        try {
            System.out.println("\n--- BUSCA DE CONTACTOS ---");
            char tipo = Input.option("Buscar por (N)IF, (T)eléfono, (E)mail ou (D)escrición", "NCTD");
            String criterio = Input.readText("Introduce o criterio de busca");

            // La clave 'C' es para 'Correo' pero la he mapeado a 'E' (Email) en ContactosDAO.java.
            // Uso 'E' aquí para ser coherente con la implementación de búsqueda de ContactosDAO.
            if (tipo == 'C') tipo = 'E'; 
            
            Contacto[] resultados = dao.search(tipo, criterio);

            if (resultados.length == 0) {
                System.out.println("\nNon se atoparon contactos que coincidan co criterio.");
            } else {
                System.out.println("\n--- CONTACTOS ATOPADOS (" + resultados.length + ") ---");
                for (Contacto c : resultados) {
                    System.out.println(c);
                }

                char accion = Input.option("\nQueres (M)odificar, (B)orrar ou (S)air?", "MBS");

                if (accion == 'M') {
                    // El número de contacto es el índice en el array del DAO.
                    int num = Integer.parseInt(Input.readText("Número do contacto a modificar (0-99)"));
                    Contacto c = dao.get(num);
                    
                    if (c != null) {
                        dao.update(Forms.contactForm(c));
                        System.out.println("Contacto modificado correctamente.");
                    } else {
                        System.out.println("[ERRO]: O número de contacto introducido non é válido.");
                    }
                } else if (accion == 'B') {
                    int num = Integer.parseInt(Input.readText("Número do contacto a borrar (0-99)"));
                    if (dao.delete(num) != null) {
                        System.out.println("Contacto borrado correctamente.");
                    } else {
                        System.out.println("[ERRO]: O número de contacto introducido non é válido.");
                    }
                }
            }

        } catch (CancelException e) {
            System.out.println("Busca cancelada.");
        } catch (NumberFormatException e) {
             System.out.println("[ERRO]: Debes introducir un número válido.");
        } catch (Exception e) {
            System.out.println("Erro inesperado durante a busca/modificación: " + e.getMessage());
        }
        Input.waitEnter();
    }
}
