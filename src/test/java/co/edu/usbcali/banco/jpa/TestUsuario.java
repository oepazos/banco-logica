package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.modelo.Cliente;
import co.edu.usbcali.banco.modelo.TipoUsuario;
import co.edu.usbcali.banco.modelo.Transaccion;
import co.edu.usbcali.banco.modelo.Usuario;

class TestUsuario {
	private final static Logger log = LoggerFactory.getLogger(TestCliente.class);
	
	static EntityManagerFactory entityManagerFactory =null;
	static EntityManager entityManager =null;
	String usu_usuario = "oepazos";
	
	@Test
	@DisplayName("ConsultarUsuario")
	void etest() {
		
		assertNotNull(entityManager, "El entityManagger es nulo");
		String jpql = "SELECT usu FROM Usuario usu";
		List<Usuario> losusuarios=entityManager.createQuery(jpql).getResultList();
		
		/*ForEach Java 8*/
		losusuarios.forEach(usuario->{
			log.info("Usuario: "+usuario.getUsuUsuario());
			log.info("Nombre: "+usuario.getNombre());
			log.info("Identificacion: "+ usuario.getIdentificacion());
			log.info("Estado: "+usuario.getActivo());
		});
		
		/* tradicional
		for (Usuario usuario : losusuarios) {
			log.info("Usuario: "+usuario.getUsuUsuario());
			log.info("Nombre: "+usuario.getNombre());
			log.info("Identificacion: "+ usuario.getIdentificacion());
			log.info("Estado: "+usuario.getActivo());
		}*/
		
		
	}
	
	
	@Test
	@DisplayName("BorrarCliente")
	void dtest() {
		
		assertNotNull(entityManager, "El entityManagger es nulo");
		Usuario usuario = entityManager.find(Usuario.class, usu_usuario);
		assertNotNull(usuario, "El usuario ya existe");
				
				
		entityManager.getTransaction().begin();
		entityManager.remove(usuario);
		entityManager.getTransaction().commit();
	}
	
	
	@Test
	@DisplayName("ModificarUsuario")
	void ctest() {
		assertNotNull(entityManager, "El entityManagger es nulo");
		Usuario usuario = entityManager.find(Usuario.class,usu_usuario); 
		assertNotNull(usuario, "El usuario ya existe");
		
		
		usuario.setActivo('N');
		usuario.setClave("940698765");
		usuario.setIdentificacion(new BigDecimal(94063159));
		usuario.setNombre("Eduardo");
		TipoUsuario tipousuario = entityManager.find(TipoUsuario.class, new Long(1));
		usuario.setTipoUsuario(tipousuario);
		usuario.setUsuUsuario("oepazos");
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		
	}
	
	@Test	
	@DisplayName("ConsultarUsuarioporId")
	void btest() {
		assertNotNull(entityManager, "El entityManagger es nulo");
		Usuario usuario = entityManager.find(Usuario.class, usu_usuario);
		assertNotNull(usuario, "El usuario ya existe");
		
		log.info("Usuario: "+usuario.getUsuUsuario());
		log.info("Nombre: "+usuario.getNombre());
		log.info("Identificacion: "+ usuario.getIdentificacion());
		log.info("Estado: "+usuario.getActivo());
		
		
	}
	
	
	@Test
	@DisplayName("CrearUsuario")
	void atest() {
		assertNotNull(entityManager, "El entityManagger es nulo");
		Usuario usuario = entityManager.find(Usuario.class,usu_usuario); 
		assertNull(usuario, "El usuario ya existe");
		usuario=new Usuario();
		
		usuario.setActivo('S');
		usuario.setClave("940698765");
		usuario.setIdentificacion(new BigDecimal(94063159));
		usuario.setNombre("Oscar");
		TipoUsuario tipousuario = entityManager.find(TipoUsuario.class, new Long(1));
		usuario.setTipoUsuario(tipousuario);
		usuario.setUsuUsuario("oepazos");
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		
	}
	
	@BeforeAll
	public static void iniciar() {
		log.info("Ejecuto el @BeforeAll");
		entityManagerFactory = Persistence.createEntityManagerFactory("banco-logica");
		entityManager=entityManagerFactory.createEntityManager();
	} 
	
	
	
	@BeforeEach
	public void antes() {
		log.info("Ejecuto el @BeforeEach");
	}
	
	@AfterEach
	public void despues() {
		log.info("Ejecuto el @AfterEach");
	}
	
		
	@AfterAll
	public static void finalizar() {
		log.info("Ejecuto el @AfterAll");
		entityManager.close();
		entityManagerFactory.close();
	}

}
