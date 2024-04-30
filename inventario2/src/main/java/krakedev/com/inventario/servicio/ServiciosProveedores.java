package krakedev.com.inventario.servicio;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import krakedev.com.inventario.entidades.Proveedor;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.persistencia.ProveedoresBDD;


@Path("proveedore")
public class ServiciosProveedores {
	
	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarProveedor (@PathParam("sub")String subcadena){
		ProveedoresBDD provBDD = new ProveedoresBDD();
		ArrayList<Proveedor> proveedores = null;
		try {
			proveedores= provBDD.buscar(subcadena);
			return Response.ok(proveedores).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

}
