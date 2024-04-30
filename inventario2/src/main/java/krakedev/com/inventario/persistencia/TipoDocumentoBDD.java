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

	public ArrayList<Proveedor> buscar(String subcadena) throws KrakeException{
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("SELECT identificador, codigo_td, nombre, telefono, correo, direccion"
					+ "	FROM proveedores"
					+ "	where upper(nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTD = rs.getString("codigo_td");
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				Proveedor  proveedor = new Proveedor(identificador,codigoTD, nombre,telefono,correo,direccion);
				proveedores.add(proveedor);
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
		return proveedores;
	}
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
