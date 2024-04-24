use superkinalIN5CVDB;

call sp_listarProductos();

call sp_agregarFactura(1, 1);

call sp_agregarDetalleFactura(6,1);

call sp_listarFacturas();

call sp_listarPromociones();

select * from DetalleFactura;

select * from Productos;

select fn_calcularTotal(1);

call sp_asignarTotalFactura(1, 30);

select fn_verificarExistencia(1);

select fn_idPromocion(1);

select * from Promociones;