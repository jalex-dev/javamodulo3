package krakedev.com.inventario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import krakedev.com.inventario.entidades.Proveedor;
import krakedev.com.inventario.entidades.TipoDocumento;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.utils.ConexionBDD;



public class TipoDocumentoBDD {

	
	public ArrayList<TipoDocumento> recuperarTodo() throws KrakeException{
		ArrayList<TipoDocumento> tiposDomentos = new ArrayList<TipoDocumento>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("SELECT codigo_td, descripcion"
					+ "	FROM public.tipo_documento;");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String codigoTD = rs.getString("codigo_td");
				String descripcion = rs.getString("descripcion");
				
				TipoDocumento  tipoDocumento = new TipoDocumento(codigoTD, descripcion);
				tiposDomentos.add(tipoDocumento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al consultar. Detalle: " + e.getMessage());
		} finally {
			// Cerrar la conexión y liberar recursos
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return tiposDomentos;
	}
}
