package krakedev.com.inventario.servicio;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import krakedev.com.inventario.entidades.Categoria;
import krakedev.com.inventario.entidades.Pedido;
import krakedev.com.inventario.entidades.Producto;
import krakedev.com.inventario.entidades.Proveedor;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.persistencia.ServiciosVariosBDD;

@Path("producto")
public class ServiciosVarios {

	@Path("actualizar")
	@PUT
	@Consumes("application/json")
	public Response actualizarProducto(Producto producto) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		try {
			servBDD.actualizarProducto(producto);
			;
			return Response.ok().build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("crearCategoria")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crarCategoria(Categoria categoria) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		try {
			servBDD.crearCategoria(categoria);
			return Response.ok().build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("actualizarCategoria")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarCategoria(Categoria categoria) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		try {
			servBDD.actualizar(categoria);
			return Response.ok().build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("buscarCategoria")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCategorias() {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		ArrayList<Categoria> categorias = null;
		try {
			categorias = servBDD.recuperarTodasCategorias();
			return Response.ok(categorias).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("buscarPedido/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPedido(@PathParam("sub") String proveedor) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		ArrayList<Pedido> listaPedidos = null;
		try {
			listaPedidos = servBDD.buscarPedidoPorProveedor(proveedor);
			return Response.ok(listaPedidos).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("buscarProveedor/{cadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarProveedor(@PathParam("cadena") String identificador) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		Proveedor proveedor = null;
		try {
			proveedor = servBDD.buscarPorIdentificador(identificador);
			return Response.ok(proveedor).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("buscarProducto/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarProducto(@PathParam("sub") int cadena) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		Producto producto = null;
		try {
			producto = servBDD.buscarPorId(cadena);
			return Response.ok(producto).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
