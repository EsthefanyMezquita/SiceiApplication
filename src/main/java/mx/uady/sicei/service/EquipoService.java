package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.model.Equipo;
import mx.uady.sicei.repository.EquipoRepository;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    /**
    * Este es el metodo que devuelve los equipos
    * @return equipos
    *
    */
    public List<Equipo> getEquipos() {

        List<Equipo> equipos = new LinkedList<>();
        equipoRepository.findAll().iterator().forEachRemaining(equipos::add);

        return equipos;
    }

    /**
    * Este es el metodo que devuelve el equpo por alumno
    * @param request Intger, id del alumno 
    * @return void
    *
    */
    public Equipo getEquipo(Integer id) {
        return equipoRepository.findById(id).get();
    }
}
