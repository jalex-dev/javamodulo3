package krakedev.com.inventario.servicio;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import krakedev.com.inventario.entidades.CabeceraVentas;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.persistencia.VentasBDD;


@Path("ventas")
public class ServiciosVentas {

	
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response crear ( CabeceraVentas venta){
		VentasBDD veBDD = new VentasBDD();
	
		try {
			veBDD.crear(venta);
			return Response.ok().build();
			
		} catch (KrakeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
