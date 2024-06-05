package krakedev.com.inventario.servicio;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import krakedev.com.inventario.entidades.TipoDocumento;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.persistencia.TipoDocumentoBDD;

@Path("tipodocumento")
public class ServiciosTipoDocumentos {

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperarTodo() {
		TipoDocumentoBDD tipoDocumentoBDD = new TipoDocumentoBDD();
		ArrayList<TipoDocumento> tipoDocumentos = null;
		try {
			tipoDocumentos = tipoDocumentoBDD.recuperarTodo();
			return Response.ok(tipoDocumentos).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

	@Path("insertar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertarTipoDocumento(TipoDocumento tipoDocumento) {
		TipoDocumentoBDD tipoDocumentoBDD = new TipoDocumentoBDD();
		try {
			tipoDocumentoBDD.insertaTipoDocumento(tipoDocumento);
			return Response.status(Response.Status.CREATED).entity(tipoDocumento).build();
		} catch (KrakeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
