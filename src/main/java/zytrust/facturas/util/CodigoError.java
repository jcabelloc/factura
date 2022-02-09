package zytrust.facturas.util;

public enum CodigoError {
	
	CLIENTE_NO_EXISTE("El cliente con el id indicado no existe"),
	
	PROBLEMAS_ALMACENAR_FACTURA("Se ha presentado un problema en el almance....");
	
	
	private final String descripcion;
	
	CodigoError(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	

}
