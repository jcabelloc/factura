package zytrust.facturas.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class FacturaReq implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private LocalDate fechaVencimiento;
	
	private String idCliente;
	
	private List<DetalleFactura> detalles;
}
