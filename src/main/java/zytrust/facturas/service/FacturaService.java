package zytrust.facturas.service;

import javax.validation.Valid;

import zytrust.facturas.dto.FacturaReq;
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
 * Esta interfaz representa el servicio de Factura que extiende de CrudService .
 * @author Rodrigo Ticona
 * @version 1.0.0, 04/02/2022
 */
import zytrust.facturas.model.Factura;


public interface FacturaService extends CrudService<Factura, String> {

    Factura cambiarStatus(Factura t) throws Exception;
    Factura actualizarTotales(Factura t) throws Exception;
	
    void create(FacturaReq facturaReq);
}
