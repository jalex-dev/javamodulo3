package krakedev.com.inventario.entidades;

import java.math.BigDecimal;

public class DetallePedido {
	
	private int codigo ;
	private Pedido cabezera;
	private Producto producto;
	private int cantidadSolicitada;
	private BigDecimal subTotal;
	private int cantidadRecibida;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Pedido getCabezera() {
		return cabezera;
	}
	public void setCabezera(Pedido cabezera) {
		this.cabezera = cabezera;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidadSolicitada() {
		return cantidadSolicitada;
	}
	public void setCantidadSolicitada(int cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	public int getCantidadRecibida() {
		return cantidadRecibida;
	}
	public void setCantidadRecibida(int cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}
	public DetallePedido(int codigo, Pedido cabezera, Producto producto, int cantidadSolicitada, BigDecimal subTotal,
			int cantidadRecibida) {
		super();
		this.codigo = codigo;
		this.cabezera = cabezera;
		this.producto = producto;
		this.cantidadSolicitada = cantidadSolicitada;
		this.subTotal = subTotal;
		this.cantidadRecibida = cantidadRecibida;
	}
	public DetallePedido() {
		super();
	}
	@Override
	public String toString() {
		return "DetallePedido [codigo=" + codigo + ", cabezera=" + cabezera + ", producto=" + producto
				+ ", cantidadSolicitada=" + cantidadSolicitada + ", subTotal=" + subTotal + ", cantidadRecibida="
				+ cantidadRecibida + "]";
	}
	

}
