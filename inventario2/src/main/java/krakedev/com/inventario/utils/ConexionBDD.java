package krakedev.com.inventario.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import krakedev.com.inventario.exception.KrakeException;


public class ConexionBDD {
	public static Connection obtenerConexion() throws KrakeException {
		Context ctx = null;
		DataSource ds = null;
		Connection con = null;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ConexionInventario");
			con = ds.getConnection();

		} catch (NamingException | SQLException e) {
			throw new KrakeException("Error al obtener conexion");

		} 
		
		return con;
	}
}
