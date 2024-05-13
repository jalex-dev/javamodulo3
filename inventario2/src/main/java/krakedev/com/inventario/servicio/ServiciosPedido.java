package krakedev.com.inventario.servicio;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import krakedev.com.inventario.entidades.Pedido;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.persistencia.PedidoBDD;

@Path("pedidos")
public class ServiciosPedido {
	@Path("registrar")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response crearProduct(Pedido pedido) {
	 PedidoBDD provBDD = new PedidoBDD();
        try {
            provBDD.insertarPedido(pedido);
            return Response.ok(pedido).build();
        } catch (KrakeException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
	@Path("recibir")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPedido(Pedido pedido) {
        PedidoBDD pedidoBDD = new PedidoBDD();
        try {
            pedidoBDD.actualizarDetallePedido(pedido);
            return Response.ok(pedido).build();
        } catch (KrakeException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
