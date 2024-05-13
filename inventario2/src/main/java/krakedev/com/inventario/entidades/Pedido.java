package krakedev.com.inventario.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {

	private int codigo;
	private Proveedor proveedor;
	private Date fecha;
	private EstadoPedido estado;
	
	private ArrayList<DetallePedido> detallePedido ;
	
	public ArrayList<DetallePedido> getDetallePedido() {
		return detallePedido;
	}
	public void setDetallePedido(ArrayList<DetallePedido> detallePedido) {
		this.detallePedido = detallePedido;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
	public Pedido() {
		super();
	}
	public Pedido(int codigo, Proveedor proveedor, Date fecha, EstadoPedido estado,
			ArrayList<DetallePedido> detallePedido) {
		super();
		this.codigo = codigo;
		this.proveedor = proveedor;
		this.fecha = fecha;
		this.estado = estado;
		this.detallePedido = detallePedido;
	}
	@Override
	public String toString() {
		return "Pedido [codigo=" + codigo + ", proveedor=" + proveedor + ", fecha=" + fecha + ", estado=" + estado
				+ ", detallePedido=" + detallePedido + "]";
	}
	
	
	
	
	
}
