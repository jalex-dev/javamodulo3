package krakedev.com.inventario.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import krakedev.com.inventario.entidades.DetallePedido;
import krakedev.com.inventario.entidades.Pedido;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.utils.ConexionBDD;

public class PedidoBDD {

	public void insertarPedido(Pedido pedido) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet generateKey = null;
		int codigoCabezera = 0;

		try {
			con = ConexionBDD.obtenerConexion();
			String sqlCabezera = "INSERT INTO public.cabezera_pedido(identificador, fecha, codigo_ep) VALUES (?, ?, ?)";
			ps = con.prepareStatement(sqlCabezera, Statement.RETURN_GENERATED_KEYS);

			Date fechaActual = new Date();
			java.sql.Date fechaSql = new java.sql.Date(fechaActual.getTime());

			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSql);
			ps.setString(3, "S");

			// Ejecutar la consulta para insertar la cabecera del pedido
			ps.executeUpdate();

			generateKey = ps.getGeneratedKeys();
			if (generateKey.next()) {
				codigoCabezera = generateKey.getInt(1);
			}

			System.out.println("Codigo Cabezera >>>> " + codigoCabezera);

			// Insertar los detalles del pedido
			String sqlDetalle = "INSERT INTO public.detalle_pedido(numero, codigo_pro, cantidad_solicitada, subtotal , cantidad_recibido) VALUES (?, ?, ?, ?, ?)";
			psDet = con.prepareStatement(sqlDetalle);

			ArrayList<DetallePedido> detallesPedido = pedido.getDetallePedido();
			for (DetallePedido detalle : detallesPedido) {
				psDet.setInt(1, codigoCabezera);
				psDet.setInt(2, detalle.getProducto().getCodigo()); // Suponiendo que getCodigo() devuelve el código del
																	// producto
				psDet.setInt(3, detalle.getCantidadSolicitada());
				BigDecimal pv = detalle.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(detalle.getCantidadSolicitada());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(4, subtotal);
				psDet.setInt(5, 0);

				// Ejecutar la consulta para insertar el detalle del pedido
				psDet.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al insertar el pedido. Detalle: " + e.getMessage());
		} finally {
			// Cerrar la conexión y liberar recursos
			try {
				if (ps != null) {
					ps.close();
				}
				if (psDet != null) {
					psDet.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void actualizarDetallePedido(Pedido pedido) throws KrakeException {
    Connection con = null;
    PreparedStatement psDetalle = null;
    PreparedStatement psCabecera = null;
    
    PreparedStatement pshist= null;
	
	Date fechaActual= new Date();
	Timestamp fechaHoraActual= new Timestamp(fechaActual.getTime());


    
    try {
        con = ConexionBDD.obtenerConexion();
        con.setAutoCommit(false); // Establecer el modo de autocommit en falso
        
        
     // Actualizar cabecera del pedido
        String sqlCabecera = "UPDATE public.cabezera_pedido SET  codigo_ep = ? WHERE numero = ?";
        psCabecera = con.prepareStatement(sqlCabecera);
        
        
        psCabecera.setString(1, "R");
        psCabecera.setInt(2, pedido.getCodigo()); // Suponiendo que tienes un método getNumero en la clase Pedido
        
        // Ejecutar la actualización de la cabecera del pedido
        psCabecera.executeUpdate();
        
        // Actualizar detalles del pedido
        String sqlDetalle = "UPDATE public.detalle_pedido SET cantidad_recibido = ?, subtotal = ? WHERE codigo_dp = ?";
        psDetalle = con.prepareStatement(sqlDetalle);
        
        ArrayList<DetallePedido> detallesPedido = pedido.getDetallePedido();
        for (DetallePedido detalle : detallesPedido) {
            int codigoDetalle = detalle.getCodigo(); // Obtener el código del detalle de pedido
            int cantidadRecibida = detalle.getCantidadRecibida(); // Obtener la cantidad recibida
            BigDecimal pv = detalle.getProducto().getPrecioVenta();
            BigDecimal cantidad = new BigDecimal(detalle.getCantidadRecibida());
            BigDecimal subtotal = pv.multiply(cantidad);
            
            psDetalle.setInt(1, cantidadRecibida);
            psDetalle.setBigDecimal(2, subtotal);
            psDetalle.setInt(3, codigoDetalle);
            
            // Ejecutar la actualización para cada detalle de pedido
            psDetalle.executeUpdate();
            
            
            pshist= con.prepareStatement("insert into historial_stock(fecha, referencia, codigo_pro, cantidad) "
					+ "values(?,?,?,?);");
            pshist.setTimestamp(1, fechaHoraActual);
            pshist.setString(2, "pedido" + pedido.getCodigo() );
            pshist.setInt(3, detalle.getProducto().getCodigo());
            pshist.setInt(4, detalle.getCantidadRecibida());
		
            pshist.executeUpdate();
        }
        
        
        
        // Confirmar la transacción
        con.commit();
    } catch (SQLException e) {
        e.printStackTrace();
        try {
            if (con != null) {
                con.rollback(); // Revertir la transacción en caso de error
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        throw new KrakeException("Error al actualizar el detalle y la cabecera del pedido. Detalle: " + e.getMessage());
    } finally {
        // Cerrar la conexión y liberar recursos
        try {
            if (psDetalle != null) {
                psDetalle.close();
            }
            if (psCabecera != null) {
                psCabecera.close();
            }
            if (con != null) {
                con.setAutoCommit(true); // Restaurar el modo de autocommit a true antes de cerrar la conexión
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

}
