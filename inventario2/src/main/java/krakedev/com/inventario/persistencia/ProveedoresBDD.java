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



public class ProveedoresBDD {

	public ArrayList<Proveedor> buscar(String subcadena) throws KrakeException{
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("SELECT prov.identificador, prov.codigo_td, td.descripcion, prov.nombre, prov.telefono, prov.correo, prov.direccion "
					+ "FROM proveedores prov , tipo_documento td "
					+ "where "
					+ "prov.codigo_td = td.codigo_td "
					+ "and upper(nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTD = rs.getString("codigo_td");
				String descripcion = rs.getString("descripcion");
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				TipoDocumento tipoDocumento = new TipoDocumento(codigoTD,descripcion);
				Proveedor  proveedor = new Proveedor(identificador,tipoDocumento, nombre,correo,direccion,telefono);
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

	public void crearProveedor(Proveedor proveedor) throws KrakeException {
	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = ConexionBDD.obtenerConexion();
	        String query = "INSERT INTO proveedores (identificador, codigo_td, nombre, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?, ?)";
	        ps = con.prepareStatement(query);
	        ps.setString(1, proveedor.getIdentificador());
	        ps.setString(2, proveedor.getTipoDocumento().getCodigoTD());
	        ps.setString(3, proveedor.getNombre());
	        ps.setString(4, proveedor.getTelefono());
	        ps.setString(5, proveedor.getCorreo());
	        ps.setString(6, proveedor.getDireccion());
	        
	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected == 0) {
	            throw new KrakeException("No se pudo insertar el proveedor en la base de datos");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeException("Error al insertar el proveedor. Detalle: " + e.getMessage());
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
	}

}
