package zytrust.facturas.service;
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
 * Esta interfaz representa el layout de las que los demas serviocios extenderan
 * @author Rodrigo Ticona
 * @version 1.0.0, 04/02/2022
 */
import java.util.List;
import java.util.Optional;

public interface CrudService <T, ID> {

    /**
     * @param  t   El objeto de tipo T que se creará
     * @return      el repositry con la funcion save, para guardar el objeto
     * @throws Exception Excepción durante el proceso de creacion
     */
    T create (T t) throws Exception;

    /**
     * @param  t   El objeto de tipo T que se actualizará
     * @return      el repositry con la funcion save, para guardar el objeto
     * @throws Exception Excepción durante el proceso de actualizacion
     */
    T update (T t) throws Exception;

    /**
     * @return      el repositry con la funcion de encontrar todos los objetos
     * @throws Exception Excepción durante el proceso de busqueda
     */
    List<T> getAll() throws Exception;

    /**
     * @param id  el identificador del objeto
     * @return      el repositry con la funcion de encontrar por id
     * @throws Exception Excepción durante el proceso de busqueda por id
     */
    Optional<T> getById(ID id);

    /**
     * @param id   El identificador del obajeto que se eliminará
     * @return      el repositry con la funcion del eliminar por id
     * @throws Exception Excepción durante el proceso de eliminacion
     */
    void delete(ID id) throws Exception;
}
