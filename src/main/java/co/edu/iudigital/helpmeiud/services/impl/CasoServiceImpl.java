package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.dtos.casos.CasoRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.casos.CasoResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.Caso;
import co.edu.iudigital.helpmeiud.services.ifaces.ICasoService;
import co.edu.iudigital.helpmeiud.services.ifaces.IDelitoService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CasoServiceImpl implements ICasoService {

    @Override
    public List<CasoResponseDTO> consultarCasos() throws RestException {
        return List.of();
    }

    @Override
    public List<CasoResponseDTO> consultarCasosVisibles() throws RestException {
        return List.of();
    }

    @Override
    public List<CasoResponseDTO> consultarCasosPorUsuario(String username) throws RestException {
        return List.of();
    }

    @Override
    public CasoResponseDTO consultarCasoPorId(Long id) throws RestException {
        return null;
    }

    @Override
    public CasoResponseDTO guardarCaso(CasoRequestDTO caso) throws RestException {
        return null;
    }

    @Override
    public Boolean visibilizar(Boolean visible, Long id) throws RestException {
        return null;
    }
}

