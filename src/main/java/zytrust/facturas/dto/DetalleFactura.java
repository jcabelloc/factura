package zytrust.facturas.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class DetalleFactura implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer cantidad;
	
	private String idProducto;

}
