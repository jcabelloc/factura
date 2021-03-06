package zytrust.facturas.util;

/*
 * @(#)Cliente.java
 *
 * Copyright 2022 ZyTrust SA, Todos los derechos reservados.
 * ZT PROPRIETARIO/CONFIDENTIALIDAD. Su uso está sujeto a los
 * términos de la licencia adquirida a ZyTrust SA.
 * No se permite modificar, copiar ni difundir sin autorización
 * expresa de ZyTrust SA.
 */
/**
 * Esta clase representa la clase que convierte el objeto Factura.
 * @author Rodrigo Ticona
 * @version 1.0.0, 04/02/2022
 */
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zytrust.facturas.dto.FacturaDetailsResponse;
import zytrust.facturas.dto.FacturaRequest;
import zytrust.facturas.dto.FacturaResponse;
import zytrust.facturas.model.Factura;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacturaConverter {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param request El objeto factura de tipo Request
     * @return La factura como entity
     */
    public Factura convertFacturaToEntity(FacturaRequest request){
        return modelMapper.map(request, Factura.class);
    }

    /**
     * @param factura objeto factura de tipo Factura
     * @return El factura como Respuesta
     */
    public FacturaResponse convertFacturaToResponse(Factura factura){
        return modelMapper.map(factura, FacturaResponse.class);
    }

    /**
     * @param factura objeto factura de tipo Factura
     * @return El factura con detalles como Respuesta
     */
    public FacturaDetailsResponse convertFacturaDetailsToResponse(Factura factura){
        return modelMapper.map(factura, FacturaDetailsResponse.class);
    }

    /**
     * @param facturas Lista de objetos factura de tipo Factura
     * @return La lista de facturas como Respuesta
     */
    public List<FacturaResponse> convertFacturaToResponse(List<Factura> facturas) {
        return facturas.stream().map(factura -> modelMapper.map(factura, FacturaResponse.class))
                .collect(Collectors.toList());
    }
}
