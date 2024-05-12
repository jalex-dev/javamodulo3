package krakedev.com.inventario.entidades;

import java.math.BigDecimal;

public class Producto {
	
	private int codigo;
	private String nombre;
	private UnidadMedida unidadMedida;
	private BigDecimal monto;
	private BigDecimal costo;
	private boolean tieneIva;
	private Categoria categoria;
	private int stock;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public BigDecimal getCosto() {
		return costo;
	}
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	public boolean isTieneIva() {
		return tieneIva;
	}
	public void setTieneIva(boolean tieneIva) {
		this.tieneIva = tieneIva;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Producto(int codigo, String nombre, UnidadMedida unidadMedida, BigDecimal monto, BigDecimal costo,
			boolean tieneIva, Categoria categoria, int stock) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.unidadMedida = unidadMedida;
		this.monto = monto;
		this.costo = costo;
		this.tieneIva = tieneIva;
		this.categoria = categoria;
		this.stock = stock;
	}
	public Producto() {
		super();
	}
	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", unidadMedida=" + unidadMedida + ", monto="
				+ monto + ", costo=" + costo + ", tieneIva=" + tieneIva + ", categoria=" + categoria + ", stock="
				+ stock + "]";
	}
	
	

}
