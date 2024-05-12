package krakedev.com.inventario.servicio;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import krakedev.com.inventario.entidades.Producto;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.persistencia.ProductosBDD;


@Path("productos")
public class ServiciosProductos {
	
	 @GET
		@Path("buscar/{sub}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response buscarProductosPorNombre(@PathParam("sub")String nombre) {
		 ProductosBDD prodBdd = new ProductosBDD();
	        List<Producto> productos = null;
			try {
				productos = prodBdd.obtenerProductos(nombre);
			} catch (KrakeException e) {
				
	            return Response.serverError().build();
			}

	        return Response.ok(productos).build();
	    }


}
