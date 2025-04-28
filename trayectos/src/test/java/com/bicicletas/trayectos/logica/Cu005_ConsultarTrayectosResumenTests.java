package com.bicicletas.trayectos.logica;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bicicletas.trayectos.dataAccess.TrayectosRepository;
import com.bicicletas.trayectos.dataAccess.UbicacionesRepository;
import com.bicicletas.trayectos.modelo.Trayecto;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Cu005_ConsultarTrayectosResumenTests {

	@Autowired
	TrayectosService servicio;

	@Autowired
	TrayectosRepository trayectos;

	@Autowired
	UbicacionesRepository ubicaciones;

    @Test
    @Transactional
    void consultarTrayectosEnRangoFechas_retornaListaTrayectos() {

		try {

			servicio.iniciarTrayecto(27.0, 42.0);


            LocalDateTime fechaAyer = LocalDateTime.now().minusDays(1);
            LocalDateTime fechaMañana = LocalDateTime.now().plusDays(1);

			List<Trayecto> listaTrayectos = servicio.consultarResumenDeTodosLosTrayectos(
                                            fechaAyer,
                                            fechaMañana
                                        );

			assertEquals(1, listaTrayectos.size(), "La lista trae más de un trayecto");

		} catch (Exception e) {
			fail();
		}        

    }

    @Test
    @Transactional
    void consultarTrayectosFechaInicioMayorFechaFin_falla() {

		try {


			servicio.iniciarTrayecto(27.0, 42.0);


            LocalDateTime fechaAyer =  LocalDateTime.now().minusDays(1);
            LocalDateTime fechaMañana = LocalDateTime.now().plusDays(1);


			List<Trayecto> listaTrayectos = servicio.consultarResumenDeTodosLosTrayectos(
                                            fechaMañana,
                                            fechaAyer
                                        );

            assertEquals(0, listaTrayectos.size(), "La consulta trajo más de un dato y debió fallar");

            fail();
			
		} catch (Exception e) {
		}        

    }

}