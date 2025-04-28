package com.bicicletas.trayectos.logica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bicicletas.trayectos.dataAccess.TrayectosRepository;
import com.bicicletas.trayectos.dataAccess.UbicacionesRepository;
import com.bicicletas.trayectos.modelo.Trayecto;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Cu004_ConsultarTrayectoTests {

	@Autowired
	TrayectosService servicio;

	@Autowired
	TrayectosRepository trayectos;

	@Autowired
	UbicacionesRepository ubicaciones;

    @Test
    @Transactional
    void consultarTrayecto_retornaTrayecto() {

		try {

			UUID idTrayecto = servicio.iniciarTrayecto(27.0, 42.0);


			Trayecto trayecto = servicio.consultarEstadisticasDelTrayecto(idTrayecto);

			assertEquals(1, trayecto.getUbicaciones().size(), "El trayecto no se actualiza y queda con dos ubicaciones");

			assertTrue(trayecto.isEnProceso(), "El trayecto aparece como no activo");


		} catch (Exception e) {
			fail();
		}        

    }

    @Test
    @Transactional
    void consultarTrayectoFinalizado_retornaTrayecto() {

		try {


			UUID idTrayecto = servicio.iniciarTrayecto(27.0, 42.0);
            servicio.finalizarTrayecto(idTrayecto, 28.0, 43.0);

			Trayecto trayecto = servicio.consultarEstadisticasDelTrayecto(idTrayecto);

			assertEquals(2, trayecto.getUbicaciones().size(), "El trayecto no se actualiza y queda con dos ubicaciones");

			assertFalse(trayecto.isEnProceso(), "El trayecto aparece como no activo");

			assertNotNull(trayecto.getDuracion(), "El trayecto aparece con duración en null");

		} catch (Exception e) {
			fail();
		}        

    }

    @Test
    @Transactional
    void consultarTrayectoInexistente_Falla() {

		try {
                UUID idTrayecto = UUID.randomUUID();
                Trayecto trayecto = servicio.consultarEstadisticasDelTrayecto(idTrayecto);
    
                assertNull(trayecto, "Se buscó un ID vacio y no lo está");
                fail();
    
            } catch (Exception e) {

            }

    }

}