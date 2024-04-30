package krakedev.com.inventario.entidades;

public class Proveedor {

	private String identificador;
	private String tipoDocumento;
	private String nombre;
	private String correo;
	private String direccion;
	private String telefono;
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Proveedor(String identificador, String tipoDocumento, String nombre, String correo, String direccion,
			String telefono) {
		super();
		this.identificador = identificador;
		this.tipoDocumento = tipoDocumento;
		this.nombre = nombre;
		this.correo = correo;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	public Proveedor() {
		super();
	}
	@Override
	public String toString() {
		return "Proveedor [identificador=" + identificador + ", tipoDocumento=" + tipoDocumento + ", nombre=" + nombre
				+ ", correo=" + correo + ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}
	
}
