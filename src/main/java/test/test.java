package test;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Ingrediente;
import modelo.Receta;
import modelo.Unidad;
import modelo.Usuario;

public class test {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebRecetario");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			// 1. Usuario
			Usuario usuario = new Usuario("pablo","alba",LocalDate.of(2002, 1, 31),"pabloa@gmail.com","123");
						
			// 2. Catálogo Ingrediente
			Ingrediente papa = new Ingrediente("Papa");
			Ingrediente jamon = new Ingrediente("Jamon");
			Ingrediente lechuga = new Ingrediente("Lechuga");
			
			em.persist(papa);
			em.persist(jamon);
			em.persist(lechuga);
			
			// 3. Crear receta
			Receta receta = new Receta(
					"Ensalada de papa",
					"Ensalada muy rica", // descripcion
					25.0, // tiempo de preparacion
					"1. Cortar papas\n2. Agregar jamon\n3. Agregar lechuga",
					10,
					null,
					"ensaladaPapa.jpg",
					usuario
					);
			
			receta.agregarIngrediente(papa, 3000.0, Unidad.GRAMOS);
			receta.agregarIngrediente(jamon, 15.0, Unidad.GRAMOS);
			receta.agregarIngrediente(lechuga, 1500.0, Unidad.GRAMOS);
			
			usuario.getRecetas().add(receta);
			
			em.persist(usuario);
			
			// Commit de la transacción
            em.getTransaction().commit();
			
		}catch (Exception e) {
            System.out.print("Error!");
            System.out.print(e.getMessage());
        } finally {
            em.close();
        }
		
		
	}

}
