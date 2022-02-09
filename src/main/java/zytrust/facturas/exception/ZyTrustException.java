package zytrust.facturas.exception;

import zytrust.facturas.util.CodigoError;

public class ZyTrustException extends RuntimeException {
	
	private CodigoError codigoError;
	
	public ZyTrustException(CodigoError codigoError) {
		this.codigoError = codigoError;
	}

}
