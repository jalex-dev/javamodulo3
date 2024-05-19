package krakedev.com.inventario.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import krakedev.com.inventario.entidades.Categoria;
import krakedev.com.inventario.entidades.CategoriaUM;
import krakedev.com.inventario.entidades.DetallePedido;
import krakedev.com.inventario.entidades.EstadoPedido;
import krakedev.com.inventario.entidades.Pedido;
import krakedev.com.inventario.entidades.Producto;
import krakedev.com.inventario.entidades.Proveedor;
import krakedev.com.inventario.entidades.TipoDocumento;
import krakedev.com.inventario.entidades.UnidadMedida;
import krakedev.com.inventario.exception.KrakeException;
import krakedev.com.inventario.utils.ConexionBDD;

public class ServiciosVariosBDD {

	public void actualizarProducto(Producto producto) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"Update productos set nombre = ?, codigo_udm = ?, precio_venta = ?, tiene_iva = ?, coste = ?, codigo_cat = ? , stock = ? "
							+ "where codigo_pro = ?");

			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCosto());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			ps.setInt(8, producto.getCodigo());

			ps.executeUpdate();

			System.out.println();
		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Actualizar producto. Detalle: " + e.getMessage());
		}
	}

	public void crearCategoria(Categoria categoria) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;
 
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("INSERT INTO public.categorias("
					+ "	 nombre, categoria_padre)"
					+ "	VALUES ( ?, ?)");

			ps.setString(1, categoria.getNombre());
			 // Validar si la categoría padre es nula
	        if (categoria.getCategoriaPadre() == null) {
	            ps.setNull(2, java.sql.Types.INTEGER);
	        } else {
	            ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
	        }


			ps.executeUpdate();

		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al insertar Categoria. Detalle: " + e.getMessage());
		}
	}

	// actualizar categoria

	public void actualizar(Categoria categoria) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("update categorias set nombre= ?, categoria_padre= ? where codigo_cat= ?");

			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			ps.setInt(3, categoria.getCodigo());

			ps.executeUpdate();

		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Actualizar Categoria. Detalle: " + e.getMessage());
		}
	}

	// buscar categoria

	public ArrayList<Categoria> recuperarTodasCategorias() throws KrakeException {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Categoria categoria;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(" select codigo_cat , nombre,categoria_padre from categorias");

			rs = ps.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");
				Categoria categoriaPadre = new Categoria();
				categoria = new Categoria(codigo, nombre, categoriaPadre);
				categorias.add(categoria);
			}
		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Buscar Categoria. Detalle: " + e.getMessage());
		}
		return categorias;
	}

	// buscar pedido por proveedor

	public ArrayList<Pedido> buscarPedidoPorProveedor(String identificador) throws KrakeException {
    ArrayList<Pedido> listPedidos = new ArrayList<>();
    try (Connection con = ConexionBDD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(
             "select pro.identificador, pro.nombre, pro.telefono, pro.correo, pro.direccion, td.codigo_td, "
             + "td.descripcion AS descipcion_doc, cp.numero, cp.fecha, cp.codigo_ep, ep.descripcion AS descripcion_estado "
             + "from proveedores pro, cabezera_pedido cp, estado_pedido ep, tipo_documento td "
             + "Where cp.identificador = pro.identificador AND ep.codigo_ep = cp.codigo_ep "
             + "AND td.codigo_td = pro.codigo_td AND cp.identificador = ?")) {

        ps.setString(1, identificador);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String codigoTd = rs.getString("codigo_td");
                String descripcionTd = rs.getString("descipcion_doc");
                TipoDocumento td = new TipoDocumento(codigoTd, descripcionTd);

                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String direccion = rs.getString("direccion");
                Proveedor pro = new Proveedor(identificador, td, nombre, telefono, correo, direccion);

                String codigoEstado = rs.getString("codigo_ep");
                String descripcionEstado = rs.getString("descripcion_estado");
                EstadoPedido ep = new EstadoPedido(codigoEstado, descripcionEstado);

                int numero = rs.getInt("numero");
                Date fecha = rs.getDate("fecha");
                Pedido p = new Pedido(numero, pro, fecha, ep);

                try (PreparedStatement psAux = con.prepareStatement(
                     "select dt.codigo_dp AS codigo_detalle , dt.numero, pro.codigo_pro, pro.nombre, "
                     + "	   udm.codigo_udm, udm.descripcion AS descripcion_udm, udm.codigo_cat_udm, cast(pro.precio_venta as decimal(6,2)), "
                     + "	   pro.tiene_iva, cast(pro.coste as decimal(5,4)), pro.codigo_cat, c.nombre As nombre_categoria, pro.stock, "
                     + "	   dt.cantidad_solicitada , cast(dt.subtotal as decimal(10,4)), dt.cantidad_recibido "
                     + "	   from detalle_pedido dt, productos pro, categorias c, unidad_medida udm "
                     + "	   Where dt.codigo_pro = pro.codigo_pro AND pro.codigo_cat = c.codigo_cat "
                     + "	   AND pro.codigo_udm = udm.codigo_udm "
                     + "	   AND dt.numero =  ?")) {

                    psAux.setInt(1, numero);
                    try (ResultSet rsAux = psAux.executeQuery()) {
                        ArrayList<DetallePedido> listDetalle = new ArrayList<>();
                        while (rsAux.next()) {
                            String codigoUdm = rsAux.getString("codigo_udm");
                            String descripcionUdm = rsAux.getString("descripcion_udm");
                            CategoriaUM categoriaUdm = new CategoriaUM(rsAux.getString("codigo_cat_udm"), null);
                            UnidadMedida udm = new UnidadMedida(codigoUdm, descripcionUdm, categoriaUdm);

                            int codigoCat = rsAux.getInt("codigo_cat");
                            String nombreCategoria = rsAux.getString("nombre_categoria");
                            Categoria cate = new Categoria(codigoCat, nombreCategoria, null);

                            int codigoProducto = rsAux.getInt("codigo_pro");
                            String nombreProducto = rsAux.getString("nombre");
                            BigDecimal precioVenta = rsAux.getBigDecimal("precio_venta");
                            boolean tieneIva = rsAux.getBoolean("tiene_iva");
                            BigDecimal coste = rsAux.getBigDecimal("coste");
                            int stock = rsAux.getInt("stock");
                            Producto producto = new Producto(codigoProducto, nombreProducto, udm, precioVenta, coste, tieneIva, cate, stock);

                            int codigoDetalle = rsAux.getInt("codigo_detalle");
                            int cantidadSolicitada = rsAux.getInt("cantidad_solicitada");
                            BigDecimal subtotal = rsAux.getBigDecimal("subtotal");
                            int cantidadRecibida = rsAux.getInt("cantidad_recibido");
                            DetallePedido detalle = new DetallePedido(codigoDetalle, null, producto, cantidadSolicitada, subtotal, cantidadRecibida);
                            listDetalle.add(detalle);
                        }
                        p.setDetallePedido(listDetalle);
                    }
                }
                listPedidos.add(p);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new KrakeException("Error al Buscar Proveedor: " + e.getMessage());
    }
    return listPedidos;
}

	// buscar por identificador provvedor

	public Proveedor buscarPorIdentificador(String cadena) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Proveedor proveedor = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"select pro.identificador, pro.codigo_td, td.descripcion, pro.nombre, pro.telefono, pro.correo, pro.direccion "
					+ "from proveedores pro, tipo_documento td "
					+ "Where pro.codigo_td = td.codigo_td "
					+ "AND identificador =  ?");
			ps.setString(1, cadena);
			rs = ps.executeQuery();
			if (rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTipoDocumento = rs.getString("codigo_td");
				String descripcionTd = rs.getString("descripcion");
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				TipoDocumento tp = new TipoDocumento(codigoTipoDocumento, descripcionTd);
				proveedor = new Proveedor(identificador, tp, nombre, telefono, correo, direccion);
			}

		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Buscar Proveedor: " + e.getMessage());
		}
		return proveedor;
	}

	// buscar Producto por id

	public Producto buscarPorId(int codigo) throws KrakeException {
	    Producto p = null;
	    try (Connection con = ConexionBDD.obtenerConexion();
	         PreparedStatement ps = con.prepareStatement(
	             "select pro.codigo_pro, pro.nombre, udm.codigo_udm, udm.descripcion AS descripcion_udm, udm.codigo_cat_udm, "
	             + "	   cast(pro.precio_venta as decimal(6,2)), pro.tiene_iva, cast(pro.coste as decimal(5,4)), pro.codigo_cat, "
	             + "	   c.nombre As nombre_categoria, pro.stock "
	             + "	   from productos pro, categorias c, unidad_medida udm "
	             + "	    Where pro.codigo_cat = c.codigo_cat AND pro.codigo_udm = udm.codigo_udm AND pro.codigo_pro =?")) {

	        ps.setInt(1, codigo);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                String codigoUdm = rs.getString("codigo_udm");
	                String descripcion = rs.getString("descripcion_udm");
	                CategoriaUM categoriaUdm = new CategoriaUM(rs.getString("codigo_cat_udm"), null);
	                UnidadMedida udm = new UnidadMedida(codigoUdm, descripcion, categoriaUdm);

	                int codigoCat = rs.getInt("codigo_cat");
	                String nombreCategoria = rs.getString("nombre_categoria");
	                Categoria cate = new Categoria(codigoCat, nombreCategoria, null);

	                int codigoProducto = rs.getInt("codigo_pro");
	                String nombreProducto = rs.getString("nombre");
	                BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
	                boolean tieneIva = rs.getBoolean("tiene_iva");
	                BigDecimal coste = rs.getBigDecimal("coste");
	                int stock = rs.getInt("stock");
	                p = new Producto(codigoProducto, nombreProducto, udm, precioVenta, coste, tieneIva, cate, stock);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeException("Error al Buscar Producto: " + e.getMessage());
	    }
	    return p;
	}


}
