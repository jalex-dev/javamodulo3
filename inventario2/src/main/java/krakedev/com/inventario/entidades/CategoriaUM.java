package krakedev.com.inventario.entidades;

public class CategoriaUM {
	
	public String codigoUDM;
	public String nombre;
	
	public CategoriaUM(String codigoUDM, String nombre) {
		super();
		this.codigoUDM = codigoUDM;
		this.nombre = nombre;
	}
	
	
	public CategoriaUM() {
		super();
	}


	public String getCodigoUDM() {
		return codigoUDM;
	}
	public void setCodigoUDM(String codigoUDM) {
		this.codigoUDM = codigoUDM;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "CategoriaUM [codigoUDM=" + codigoUDM + ", nombre=" + nombre + "]";
	}
	
	

}
