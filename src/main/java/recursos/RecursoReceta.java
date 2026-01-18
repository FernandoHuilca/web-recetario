package recursos;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.dao.FactoryDAO;
import modelo.entidades.Receta;

@Path("/Receta")
public class RecursoReceta {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Receta> getRecetas(){
		return FactoryDAO.getFactory().getRecetaDAO().getAll();
	}
	
	
	@GET
	@Path("getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Receta getById(@PathParam("id") Long id) {
		return FactoryDAO.getFactory().getRecetaDAO().getById(id);
	}
	
	
	@DELETE
	@Path("/deleteById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteById(@PathParam("id") Long id) {
		return FactoryDAO.getFactory().getRecetaDAO().eliminarReceta(id);
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean create(Receta receta) {
		return FactoryDAO.getFactory().getRecetaDAO().create(receta);
	}
	
	
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean update(Receta receta) {
		return FactoryDAO.getFactory().getRecetaDAO().update(receta);
	}
	
}
