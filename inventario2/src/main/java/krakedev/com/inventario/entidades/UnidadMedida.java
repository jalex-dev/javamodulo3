package krakedev.com.inventario.entidades;

public class UnidadMedida {

	private String codigo;
	private String descripcion;
	private CategoriaUM categoriaUM;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUM getCategoriaUM() {
		return categoriaUM;
	}
	public void setCategoriaUM(CategoriaUM categoriaUM) {
		this.categoriaUM = categoriaUM;
	}
	public UnidadMedida(String codigo, String descripcion, CategoriaUM categoriaUM) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriaUM = categoriaUM;
	}
	public UnidadMedida() {
		super();
	}
	@Override
	public String toString() {
		return "UnidadMedida [codigo=" + codigo + ", descripcion=" + descripcion + ", categoriaUM=" + categoriaUM + "]";
	}
	public UnidadMedida(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	
	
	
}
