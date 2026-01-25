package modelo.dao;

import modelo.entidades.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario, Long>{

	public Usuario autenticar(String correo, String clave);
	public boolean verificarCorreoYaExistente(String correo);

}
