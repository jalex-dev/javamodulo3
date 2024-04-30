package krakedev.com.inventario.entidades;

public class TipoDocumento {
	private String codigoTD;
	private String descripcion;
	
	
	@Override
	public String toString() {
		return "TipoDocumento [codigoTD=" + codigoTD + ", descripcion=" + descripcion + "]";
	}
	
	public TipoDocumento() {
		super();
	}
	
	public TipoDocumento(String codigoTD, String descripcion) {
		super();
		this.codigoTD = codigoTD;
		this.descripcion = descripcion;
	}
	
	public String getCodigoTD() {
		return codigoTD;
	}
	public void setCodigoTD(String codigoTD) {
		this.codigoTD = codigoTD;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
