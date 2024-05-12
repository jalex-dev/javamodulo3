package krakedev.com.inventario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import krakedev.com.inventario.entidades.Categoria;
import krakedev.com.inventario.entidades.Producto;
import krakedev.com.inventario.entidades.UnidadMedida;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.utils.ConexionBDD;

public class ProductosBDD {
	
	 public List<Producto> obtenerProductos(String subcadena) throws KrakeException {
	        List<Producto> productos = new ArrayList<>();
	        Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs =null;
			try {
				con = ConexionBDD.obtenerConexion();
	             ps = con.prepareStatement("SELECT prod.codigo_pro, prod.nombre as nombre_producto, um.codigo_udm as nombre_udm, um.descripcion as descripcion_udm, "
	             		+ "cast(prod.precio_venta as decimal(6,2)),  cast(prod.coste as Decimal(5,4)), prod.tiene_iva, prod.codigo_cat, ca.nombre as nombre_categoria, stock FROM productos prod, unidad_medida um, "
	             		+ "categorias ca WHERE prod.codigo_udm = um.codigo_udm AND ca.codigo_cat = prod.codigo_cat "
	             		+ "AND upper(prod.nombre) LIKE ? ");
				
				ps.setString(1, "%"+subcadena.toUpperCase()+"%");
				rs = ps.executeQuery();
				
				while (rs.next()) {
					UnidadMedida unidadMedida = new UnidadMedida(rs.getString("nombre_udm"),rs.getString("descripcion_udm"));
					Categoria categoria = new Categoria(rs.getString("codigo_cat"),rs.getString("nombre_categoria"));
					 Producto producto = new Producto();
		                producto.setCodigo(rs.getInt("codigo_pro"));
		                producto.setNombre(rs.getString("nombre_producto"));
		                producto.setUnidadMedida(unidadMedida);
		                producto.setMonto(rs.getBigDecimal("precio_venta"));
		                producto.setCosto(rs.getBigDecimal("coste"));
		                producto.setTieneIva(rs.getBoolean("tiene_iva"));
		                producto.setCategoria(categoria);
		                producto.setStock(rs.getInt("stock"));
		                productos.add(producto);
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
			

	        return productos;
	    }

}
