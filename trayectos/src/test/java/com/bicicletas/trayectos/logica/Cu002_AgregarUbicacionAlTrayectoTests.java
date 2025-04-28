package com.bicicletas.trayectos.logica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bicicletas.trayectos.dataAccess.TrayectosRepository;
import com.bicicletas.trayectos.dataAccess.UbicacionesRepository;
import com.bicicletas.trayectos.modelo.Trayecto;
import com.bicicletas.trayectos.modelo.Ubicacion;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Cu002_AgregarUbicacionAlTrayectoTests {

    @Autowired
    TrayectosService servicio;

    @Autowired
    TrayectosRepository trayectos;

    @Autowired
    UbicacionesRepository ubicaciones;

    @Test
    @Transactional
    public void agreagarUbicacionAlTrayecto_OK(){



        try{
            //Arrange - Preparar la prueba
            UUID idTrayecto = servicio.iniciarTrayecto(27.0, 14.0);
            
            //Act - Realizar la accion
            UUID idUbicacion = servicio.agregarUbicacionAlTrayecto(idTrayecto, 45.8, 12.5);
            
            //Assert - Revisar
            Trayecto trayecto = trayectos.findById(idTrayecto).get();
            assertEquals(2,trayecto.getUbicaciones().size(),"Debío traer 2 ubicaciones");
            
            Ubicacion ubicacion = ubicaciones.findById(idUbicacion).get();
            assertEquals(ubicacion.getTrayecto().getId(), idTrayecto,"No se asocio");
            
        }catch(Exception e){
            fail("Generó una excepcion cuando no debía");
        }
    }

    @Test
    @Transactional
    public void agregarUbicacionAlTrayectoNoExiste_FALLA(){
        try{

            //Arrange
            UUID idTrayecto = UUID.randomUUID();

            //Act
            UUID idUbicacion = servicio.agregarUbicacionAlTrayecto(idTrayecto, 2.4, 3.5);
            
            //Assert
            fail("Debio fallar con una excepcion");


        }catch(Exception e){

        }
    }

    @Test
    @Transactional
    public void agregarUbicacionAlTrayectoNoInactivo_FALLA(){
        try{

            //Arrange
            UUID idTrayecto = servicio.iniciarTrayecto(23.4, 2.5);
            Trayecto trayecto = trayectos.findById(idTrayecto).get();
            trayecto.setEnProceso(false);

            //Act
            UUID idUbicacion = servicio.agregarUbicacionAlTrayecto(idTrayecto, 2.4, 3.5);
            
            //Assert
            fail("Debio fallar con una excepcion");


        }catch(Exception e){

        }
    }
}
