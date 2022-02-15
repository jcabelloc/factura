package zytrust.facturas.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Esta interfaz representa la implementacion del servicio de Factura
 * que extiende de CrudServiceImpl .
 * @author Rodrigo Ticona
 * @version 1.0.0, 04/02/2022
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zytrust.facturas.controller.FacturaController;
import zytrust.facturas.dto.FacturaReq;
import zytrust.facturas.exception.ResourceNotFoundException;
import zytrust.facturas.exception.ZyTrustException;
import zytrust.facturas.model.Cliente;
import zytrust.facturas.model.Factura;
import zytrust.facturas.model.ProductoFactura;
import zytrust.facturas.repository.FacturaRepository;
import zytrust.facturas.repository.GenericRepository;
import zytrust.facturas.service.ClienteService;
import zytrust.facturas.service.FacturaService;
import zytrust.facturas.service.ProductoFacturaService;
import zytrust.facturas.service.ProductoService;
import zytrust.facturas.util.CodigoError;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl extends CrudServiceImpl<Factura, String>
        implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    ClienteService clienteService;
    
    @Autowired
    ProductoFacturaService productoFacturaService;
    
    private static final Logger logger = LoggerFactory.getLogger(FacturaServiceImpl.class);


    @Override
    protected GenericRepository<Factura, String> getRepository() {
        return facturaRepository;
    }

    @Override
    public Factura cambiarStatus(Factura t) throws Exception {
        return facturaRepository.save(t);
    }

    @Override
    public Factura actualizarTotales(Factura t) throws Exception {

        t.setSubtotal(new BigDecimal("0.00"));
        for (ProductoFactura producto: t.getProductos()) {
            t.setSubtotal(t.getSubtotal().add(producto.getProducto().getPrecio()
                    .multiply(new BigDecimal(producto.getCantidad()))));
        }

        // Se agregan los datos que no son requeridos ingresar por el cliente
        t.setImpuesto(t.getSubtotal().multiply(new BigDecimal("0.18")));
        t.setTotal(t.getSubtotal().add(t.getImpuesto()));
        // se agregan los objetos del cliente y productos

        return facturaRepository.save(t);
    }

	@Override
	public void create(FacturaReq facturaReq) {
		try {
			// Validar si el cliente existe
			Optional<Cliente> opt = clienteService.getById(facturaReq.getIdCliente());
			if (opt.isEmpty()) {
				logger.info("No se encontro el cliente con id {}", facturaReq.getIdCliente() );
				throw new ZyTrustException(CodigoError.CLIENTE_NO_EXISTE);
			}
			

			
			
			// Detalles de la factura
			List<ProductoFactura> productos = new ArrayList<>();
			// List<ProductoFactura> productos = productoFacturaService.buildProductosFacturas(facturaReq.getDetalles());
			
			Factura factura = Factura
					.builder()
					.fechaEmision(LocalDate.now())
					.fechaVencimiento(facturaReq.getFechaVencimiento())
					.productos(productos)
					.build();
			factura.setProductos(productos);
			
			
			// Almacenar el PDF de la factura en el FileSystem
			/*
			try {
				archivoService.create("F001-24", "nombrePlantilla", "ruta");	
			} catch (IOException e) {
				// logging
				
				// Tiro una nueva y diferente excepcion
				throw new ZyTrustException(CodigoError.PROBLEMAS_ALMACENAR_FACTURA);
			} */
			
			
			logger.debug("Se creo la factura {}", factura.toString());

		} catch (Exception e) {
			logger.error(e.toString());
		}
		
	}
}
