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

import krakedev.com.inventario.entidades.CabeceraVentas;
import krakedev.com.inventario.entidades.DetalleVentas;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.utils.ConexionBDD;

public class VentasBDD {
	private static final String INSERT_CABECERA_VENTAS = "insert into cabezera_ventas(fecha, total_sin_iva, iva, total) values(?,?,?,?)";
	private static final String INSERT_DETALLE_VENTAS = "insert into detalles_ventas(codigo_cv, codigo_pro, cantidad, precio_venta, subtotal, subtotal_con_iva) values(?,?,?,?,?,?)";
	private static final String INSERT_HISTORIAL_STOCK = "insert into historial_stock(fecha, referencia, codigo_pro, cantidad) values(?,?,?,?)";
	private static final String UPDATE_CABECERA_VENTAS = "update cabezera_ventas set total_sin_iva = ?, iva = ?, total = ? where codigo_cv = ?";

	// Constante para el valor del IVA
    private static final BigDecimal IVA = new BigDecimal("0.15");
    
	public void crear(CabeceraVentas venta) throws KrakeException {
		
		
	    // Declaración de recursos try-with-resources para garantizar el cierre automático de los recursos
	    try (Connection con = ConexionBDD.obtenerConexion();
	         PreparedStatement psVenta = con.prepareStatement(INSERT_CABECERA_VENTAS, Statement.RETURN_GENERATED_KEYS)) {

	        int codigoCV = 0;
	        ArrayList<DetalleVentas> detallesVentas = venta.getDetalle();
	        BigDecimal sumaTotalSinIva = BigDecimal.ZERO;
	        BigDecimal sumaIva = BigDecimal.ZERO;
	        BigDecimal sumaTotalConIva = BigDecimal.ZERO;

	        Date fechaActual = new Date();
	        Timestamp fechaSQL = new Timestamp(fechaActual.getTime());

	        // CREAR CABECERA
	        psVenta.setTimestamp(1, fechaSQL);
	        psVenta.setBigDecimal(2, venta.getTotalSinIva());
	        psVenta.setBigDecimal(3, venta.getIva());
	        psVenta.setBigDecimal(4, venta.getTotal());
	        psVenta.executeUpdate();

	        // ***OBTENER CODIGO***//
	        try (ResultSet rsCV = psVenta.getGeneratedKeys()) {
	            if (rsCV.next()) {
	                codigoCV = rsCV.getInt(1);
	            }
	        }

	        // DETALLES VENTAS & HISTORIAL STOCK
	     

	        for (DetalleVentas detalleX : detallesVentas) {
	            detalleX.setPrecioVenta(detalleX.getProducto().getPrecioVenta());
	            BigDecimal subtotal = detalleX.getPrecioVenta().multiply(BigDecimal.valueOf(detalleX.getCantidad()));
	            BigDecimal totalConIva = detalleX.getProducto().isTieneIva() ? subtotal.multiply(IVA.add(BigDecimal.ONE)) : subtotal;
	            BigDecimal cantidadIva = detalleX.getProducto().isTieneIva() ? totalConIva.subtract(subtotal) : BigDecimal.ZERO;

	            try (PreparedStatement psDetalles = con.prepareStatement(INSERT_DETALLE_VENTAS);
	                 PreparedStatement psStock = con.prepareStatement(INSERT_HISTORIAL_STOCK)) {

	                psDetalles.setInt(1, codigoCV);
	                psDetalles.setInt(2, detalleX.getProducto().getCodigo());
	                psDetalles.setInt(3, detalleX.getCantidad());
	                psDetalles.setBigDecimal(4, detalleX.getPrecioVenta());
	                psDetalles.setBigDecimal(5, subtotal);
	                psDetalles.setBigDecimal(6, totalConIva);
	                psDetalles.executeUpdate();

	                sumaTotalSinIva = sumaTotalSinIva.add(subtotal);
	                sumaIva = sumaIva.add(cantidadIva);
	                sumaTotalConIva = sumaTotalConIva.add(totalConIva);

	                psStock.setTimestamp(1, fechaSQL);
	                psStock.setString(2, "Venta #" + codigoCV);
	                psStock.setInt(3, detalleX.getProducto().getCodigo());
	                psStock.setInt(4, detalleX.getCantidad() * (-1));
	                psStock.executeUpdate();
	            }
	        }
	        // ACTUALIZAR CABECERA
	        try (PreparedStatement psVentaUpdate = con.prepareStatement(UPDATE_CABECERA_VENTAS)) {
	            psVentaUpdate.setBigDecimal(1, sumaTotalSinIva);
	            psVentaUpdate.setBigDecimal(2, sumaIva);
	            psVentaUpdate.setBigDecimal(3, sumaTotalConIva);
	            psVentaUpdate.setInt(4, codigoCV);
	            psVentaUpdate.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeException("Error al insertar. Detalle: " + e.getMessage());
	    }
	}

}
