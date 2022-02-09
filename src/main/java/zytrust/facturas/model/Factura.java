package zytrust.facturas.model;

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
 * Esta clase representa a un detalle de la factura y debe ser usada para almacenar
 * datos e intercambiarlos con otros objetos.
 *
 * @author Rodrigo Ticona
 * @version 1.0.0, 04/02/2022
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "FAC_FACTURAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura {

    /**  Identificador de la factura */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "FAC_ID")
    private String id; //

    /** Fecha de emision de la factura */
    @Column(name = "FAC_FEC_EMIS")
    private LocalDate fechaEmision;

    /** Fecha de vencimiento de la factura */
    @Column(name = "FAC_FEC_VENC")
    private LocalDate fechaVencimiento;

    /** Total de la factura */
    @Column(name = "FAC_TOTAL")
    private BigDecimal total ;

    /** Subtotal de la factura */
    @Column(name = "FAC_SUBTOTAL")
    private BigDecimal subtotal = new BigDecimal("0.00");

    /** Impuesto de la factura */
    @Column(name = "FAC_IMPUESTO")
    private BigDecimal impuesto;

    /** Estatus de la factura */
    @Column(name = "FAC_ESTATUS")
    private String status = "Ingresada";

    /** Cliente de la factura */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CLIE_ID", nullable = false)
    @JsonIgnore
    Cliente cliente; //

    /** Productos de la factura */
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProductoFactura> productos;
}
