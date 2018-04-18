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
import co.edu.usbcali.banco.modelo.TipoDocumento;

class TestCliente {
	
	private final static Logger log = LoggerFactory.getLogger(TestCliente.class);

	static EntityManagerFactory entityManagerFactory =null;
	static EntityManager entityManager =null;
	
	BigDecimal clieId = new BigDecimal(142020);
	
	
	@Test
	@DisplayName("ConsultarCliente")
	void etest() {
		
		assertNotNull(entityManager, "El entityManagger es nulo");
		String jpql = "SELECT cli FROM Cliente cli";
		List<Cliente> losclientes=entityManager.createQuery(jpql).getResultList();
		
		/*ForEach Java 8*/
		losclientes.forEach(cliente->{
			log.info("Id: "+cliente.getClieId());
			log.info("Nombre: "+cliente.getNombre());
		});
		
		/* tradicional
		for (Cliente cliente : losclientes) {
			log.info("Id: "+cliente.getClieId());
			log.info("Nombre: "+cliente.getNombre());
		}*/
		
		
	}
	
	@Test
	@DisplayName("BorrarCliente")
	void dtest() {
		
		assertNotNull(entityManager, "El entityManagger es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente ya existe");
				
		
		
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ModificarCliente")
	void ctest() {
		
		assertNotNull(entityManager, "El entityManagger es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente ya existe");
				
		cliente.setActivo('N');
		cliente.setClieId(clieId);
		cliente.setDireccion("avenida siempre viva 123 C");
		cliente.setEmail("hsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 5555");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, new Long(2));
		assertNotNull(tipoDocumento, "El tipo documento no existe");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test	
	@DisplayName("ConsultarClienteporId")
	void btest() {
		assertNotNull(entityManager, "El entityManagger es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente ya existe");
		
		log.info("Id: "+cliente.getClieId());
		log.info("Nombre: "+cliente.getNombre());
	}
	
	
	@Test
	@DisplayName("CrearCliente")
	void atest() {
		assertNotNull(entityManager, "El entityManagger es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "El cliente ya existe");
		cliente= new Cliente();
		
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("avenida siempre viva 123");
		cliente.setEmail("hsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 5555");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, new Long(2));
		assertNotNull(tipoDocumento, "El tipo documento no existe");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
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
