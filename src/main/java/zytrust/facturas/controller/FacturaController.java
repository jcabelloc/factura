package zytrust.facturas.controller;

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
 * Esta clase representa el controllador Factura que interactua con el cliente .
 * @author Rodrigo Ticona
 * @version 1.0.0, 04/02/2022
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import zytrust.facturas.dto.FacturaDetailsResponse;
import zytrust.facturas.dto.FacturaReq;
import zytrust.facturas.dto.FacturaRequest;
import zytrust.facturas.dto.FacturaResponse;
import zytrust.facturas.exception.ResourceNotFoundException;
import zytrust.facturas.model.Cliente;
import zytrust.facturas.model.Factura;
import zytrust.facturas.model.ProductoFactura;
import zytrust.facturas.service.ClienteService;
import zytrust.facturas.service.FacturaService;
import zytrust.facturas.service.ProductoFacturaService;
import zytrust.facturas.service.ProductoService;
import zytrust.facturas.util.FacturaConverter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoFacturaService productoFacturaService;

    @Autowired
    private FacturaConverter converter;

    private static final Logger logger = LoggerFactory.getLogger(FacturaController.class);
    
    /**
     * @return      el objeto factura con el status Http200
     * @throws Exception Excepción durante el proceso de busqueda
     * */
    @GetMapping
    public ResponseEntity<List<FacturaResponse>> getAll() throws Exception{
        var facturas = facturaService.getAll();
        return new ResponseEntity<>(
                converter.convertFacturaToResponse(facturas), HttpStatus.OK);
    }

    /**
     * @param id facturaId que se extrae del path
     * @return      el objeto factura con el status Http200
     * @throws Exception Excepción durante el proceso de busqueda
     * */
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDetailsResponse> getById(
            @Valid @PathVariable(name = "id") String id) throws Exception{
        Factura factura;
        factura= facturaService.getById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado"));
        return new ResponseEntity<>(
                converter.convertFacturaDetailsToResponse(
                        factura), HttpStatus.OK);
    }
    
    @PostMapping()
    public void crearFactura(@Valid @RequestBody FacturaReq facturaReq) {
    	logger.info("Creando la factura con los siguientes datos {}", facturaReq.toString());
    	facturaService.create(facturaReq);
    }
    
    /**@param  request  un obejto json con los parametros de una factura
    * @param   clienteId que se extrae del path
    * @return      el objeto factura con el status Http200
     *@throws Exception Excepción durante el proceso de creacion
    * */
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<FacturaResponse> crearProducto(
            @Valid @RequestBody FacturaRequest request,
            @Valid @PathVariable(name = "clienteId")
                    String clienteId) throws Exception{

        // se crea el cliente de la factura
        Cliente cliente;
        var tem = new Factura();  // se crea auxiliar de la factura
        // se crea lista de productos
        List<ProductoFactura> productos = new ArrayList<>();

        cliente = clienteService.getById(clienteId).orElseThrow(
                // se instancia el cliente de la factura
                () -> new ResourceNotFoundException("Cliente no encontrado"));

        // se instancia auxiliar de la factura
        tem= converter.convertFacturaToEntity(request);

        // bucle para agregar todos los productos
        for (ProductoFactura producto: request.getProductos()) {
            // Buscar todos los productos requeridos
            var productoTem = (
                    productoService.getById(producto.getId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Producto no encontrado")));

            // Crear un nuevo ProductoFactura
            var prodFacTem = new ProductoFactura();

            prodFacTem.setCantidad(producto.getCantidad());
            prodFacTem.setProducto(productoTem);
            // Añadit el ProductoFactura a la lista de pproductos
            productos.add(prodFacTem);
            // Crear el productoFactura
            productoFacturaService.create(prodFacTem);
            // Agregar el precio del producto al subtotal de la factura
            tem.setSubtotal(tem.getSubtotal()
                    .add(productoTem.getPrecio().multiply(
                            new BigDecimal(prodFacTem.getCantidad()))));
        }

        // Se agregan los datos que no son requeridos ingresar por el cliente
        tem.setFechaEmision(LocalDate.now());
        tem.setFechaVencimiento(tem.getFechaEmision().plusMonths(1));
        tem.setImpuesto(tem.getSubtotal().multiply(new BigDecimal("0.18")));
        tem.setTotal(tem.getSubtotal().add(tem.getImpuesto()));
        // se agregan los objetos del cliente y productos
        tem.setCliente(cliente);
        tem.setProductos(productos);

        var factura = facturaService.create(tem); // se crea la factura

        return new ResponseEntity<>(
                converter.convertFacturaToResponse(
                        factura), HttpStatus.ACCEPTED);
    }

    /**@param  id  El identificador de la fatura a editar
     * @param  idProducto  El identificador del producto a agregar
     * @return      el objeto factura con el status Http200
     * @throws Exception Excepción durante el proceso de actualizacion
     * */
    @PutMapping("/{id}/producto/{idProducto}")
    public ResponseEntity<FacturaResponse> agregarProducto(
            @Valid @PathVariable(name = "id") String id,
            @Valid @PathVariable(name = "idProducto") String idProducto
    ) throws Exception{

        var factura = facturaService.getById(id).orElseThrow(
                () -> new ResourceNotFoundException("Factura no encontrada"));
        List<ProductoFactura> productos;
        boolean productoAgregado;

        productoAgregado = false;
        productos = factura.getProductos();

        for (ProductoFactura producto: productos) {

            if (producto.getProducto().getId().equals(idProducto)) {

                producto.setCantidad(producto.getCantidad() + 1);
                productoAgregado = true;
            }
        }

        if(!productoAgregado) {

            var temProdFac = new ProductoFactura();
            var temProd = productoService.getById(
                    idProducto).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Producto no encontrada"));

            temProdFac.setFactura(factura);
            temProdFac.setCantidad(1);
            temProdFac.setProducto(temProd);

            productos.add(temProdFac);
        }

        factura.setProductos(productos);

        facturaService.actualizarTotales(factura);

        return new ResponseEntity<>(
                converter.convertFacturaToResponse(factura), HttpStatus.OK);
    }



    /** @param   id     el identificador de la facrtura
     * @param  status el nuevo status de la factura
     * @return        el objeto factura con el status Http200
     * @throws Exception Excepción durante el proceso de actualizcion
     * */
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<FacturaResponse> cambiarStatus(
            @Valid @PathVariable(name = "id") String id,
            @Valid @PathVariable(name = "status") String status
            ) throws Exception{

        var factura = facturaService.getById(id).orElseThrow(
                () -> new ResourceNotFoundException("Factura no encontrada"));

        factura.setStatus(status);

        /*if(status.equals("Confirmada")) {
            for(ProductoFactura producto: factura.getProductos()) {

            }
        }*/

        facturaService.update(factura);

        return new ResponseEntity<>(
                converter.convertFacturaToResponse(factura), HttpStatus.OK);
    }

}
