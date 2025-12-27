package test;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Ingrediente;
import modelo.Receta;
import modelo.Unidad;
import modelo.Usuario;

public class testMateito {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebRecetario");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// 1. Usuario
		Usuario usuario = new Usuario("mateito", "simbaña", LocalDate.of(2004, 3, 8), "mateo@gmail.com", "123");

		// 2. Catálogo Ingrediente
		Ingrediente pollo = new Ingrediente("Pollo");

		em.persist(pollo);

		// 3. Crear receta
		Receta receta = new Receta("Pollo navideño", "Disfruta la navidad con un tremendo pollo", // descripcion
				120.0, // tiempo de preparacion
				"1. Sazonar pollo\n2. Colocar en el horno al pollo\n3. Cortar el pollo", 10, null, "polloNavideno.jpg", usuario);

		receta.agregarIngrediente(pollo, 2.5, Unidad.KILOGRAMOS);

		usuario.getRecetas().add(receta);

		em.persist(usuario);

		em.getTransaction().commit();

	}

}
