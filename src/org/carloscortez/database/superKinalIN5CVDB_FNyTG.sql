use superkinalIN5CVDB;

DELIMITER $$
create function fn_verificarExistencia(prodId int) returns boolean deterministic
BEGIN
	declare flag boolean default true;
    
    if (select P.cantidadStock from Productos P where P.productoId = prodId) <= 0 then
		set flag = false;
    end if;
    
    return flag;
END $$
DELIMITER ;

DELIMITER $$
create function fn_calcularTotal(factId int) returns decimal(10, 2) deterministic
BEGIN
	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    
    declare cursorDetalleFactura cursor for 
		select DF.facturaId, DF.productoId from DetalleFactura DF
	;
    
    open cursorDetalleFactura;
    
    totalLoop : loop
    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    if factId = curFacturaId then
		if(fn_idPromocion(curProductoId) = 0) then
			set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
		else 
			set precio = (select P.precioPromocion from Promociones P where P.promocionId = fn_idPromocion(curProductoId));
        end if;
		
        set total = total + precio;
    end if;
    
    if i = (select count(*) from detalleFactura) then
		leave totalLoop;
    end if;
    
    set i = i + 1;
    
    end loop totalLoop;
    
    return total;
END $$
DELIMITER ;


DELIMITER $$
create procedure sp_asignarTotalFactura(in factId int, in totalFact decimal(10,2))
BEGIN
	update facturas
		set total = (totalFact * (1 + 0.12))
			where facturaId = factId;
END $$
DELIMITER ;

DELIMITER $$
create trigger tg_totalFactura
after insert on DetalleFactura
for each row
BEGIN
	declare total decimal(10,2);
    declare cantOriginal int;
    
    if fn_verificarExistencia(NEW.productoId) then 
		set total = fn_calcularTotal(NEW.facturaId);
        
        call sp_asignarTotalFactura(NEW.facturaId, total);
        
        
		set cantOriginal = (select P.cantidadStock from Productos P where productoId = NEW.productoId);
 
		update Productos
			set cantidadStock = (cantOriginal - 1)
				where productoId = NEW.productoId; 
                
    else
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'No hay existencias de este producto!';
    end if;
       
END $$
DELIMITER ;

DELIMITER $$
create function fn_idPromocion(prodId int) returns int deterministic
BEGIN
	declare resultado int default 0;
    declare curPromocionId, curProductoId int;
    declare curFechaFinalizacion date;
    declare i int default 1;
    
    declare cursorPromocionesId cursor for 
		select P.promocionId, P.productoId, P.fechaFinalizacion from Promociones P
	;
    
    open cursorPromocionesId;
    
    promoLoop : loop
    fetch cursorPromocionesId into curPromocionId, curProductoId, curFechaFinalizacion;
    
    if (curProductoId = prodId) then
		if(curFechaFinalizacion >= DATE(NOW())) then
			set resultado = curPromocionId;
        end if;
    end if;
    
    if i = (select count(*) from Promociones) then
		leave promoLoop;
    end if;
    
    set i = i + 1;
    
    end loop promoLoop;

	return resultado;
END $$
DELIMITER ;

