package com.bd.tpfinal.controllers;

import com.bd.tpfinal.DTOs.FinishOrderScore;
import com.bd.tpfinal.DTOs.ItemDTO;
import com.bd.tpfinal.DTOs.OrderDTO;
import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.services.DeliveryManService;
import com.bd.tpfinal.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/api/orders")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})

public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryManService deliveryManService;

    @Autowired
    private ModelMapper modelMapper;

    // Agregar un item a una orden ya creada.
    @PutMapping("/nuevo-item-Order/{order_id}")
    public Item nuevoItemForOrder(@RequestBody ItemDTO item, @PathVariable long order_id) throws Exception {
        Optional<Item> itemAgregado = orderService.agregarItemAOrdenCreada(order_id, item);
        if(itemAgregado.isPresent()) {
            return itemAgregado.get();
        }
        return null;
    }
    // Obtener las ordenes con mas productos de un proveedor especifico
    @GetMapping("/mayor-cantidad-productos-supplier/{supplier_id}")
    public List<Order> getMayorCantidadProductosPorSupplier(@PathVariable long supplier_id) {
        return orderService.getOrdersConMasProductosDeSupplier(supplier_id);
    }
    // Obtener la orden de mayor precio total de un dia dado
    @GetMapping("/mayor-precio-del-dia")
    public Order getOrderMayorPrecioDelDia(@RequestParam("fecha")
                                               @DateTimeFormat(pattern = "dd-MM-yyyy",
                                                       iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Optional<Order> order = orderService.getOrderConMayorPrecioDelDia(fecha);
        if(order.isPresent())
            return order.get();
        return null;
    }

    // Confirmar un pedido.
    @PutMapping("/confirmar-pedido/{order_id}") //las ordenes que estan pendientes
    public void updateOrdenConfirmar(@PathVariable long order_id)  throws Exception  {
        Optional<Order> order = orderService.confirmarPedido(order_id);
    }

    // cancelar un pedido por el cliente.
    @PutMapping("/cancelar-pedido/{order_id}") //las ordenes que estan pendientes y asignadas
    public void updateOrdenCancelar(@PathVariable long order_id)  throws Exception  {
        Optional<Order> order = orderService.cancelarPedido(order_id);
    }

    // rechazar un pedido por el delivery
    @PutMapping("/rechazar-pedido/{order_id}") //las ordenes que estan asignadas
    public void updateOrdenRechazar(@PathVariable long order_id)  throws Exception  {
        Optional<Order> order = orderService.rechazarPedido(order_id);
    }

    // entregar un pedido por el delivery
    @PutMapping("/entregar-pedido/{order_id}") //las ordenes que estan asignadas
    public void updateOrdenEntregar(@PathVariable long order_id)  throws Exception  {
        Optional<Order> order = orderService.entregarPedido(order_id);
    }

    // finalizar y calificar un pedido por el cliente
    @PutMapping("/finalizar-pedido/{order_id}") //las ordenes que estan asignadas
    public void updateOrdenFinalizar(@RequestBody FinishOrderScore score, @PathVariable long order_id)  throws Exception  {
        Optional<Order> order = orderService.finalizarPedido(score, order_id);
    }

    //Obtener las órdenes con más productos de un proveedor específico
    @GetMapping("/ordenes-mas-productos-supplier/{supplier_id}")
    public List<OrderDTO> getOrdenesMasProductosDeSupplier(@PathVariable long supplier_id) {
        List<Order> orders = orderService.getOrdenesConMasProductosDelSupplier(supplier_id);
        return orders.stream()
                .map(order -> convertToDTO(order))
                .collect((Collectors.toList()));
    }
    // Obtener la orden de mayor precio total de un día dado
    @GetMapping("/get-orden-de-mayor-precio-total-dia")
    public OrderDTO getOrdenMayorPrecioTotalDelDia(@RequestParam
                                                    @DateTimeFormat(pattern = "dd-MM-yyyy",
                                                            iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Optional<Order> order = orderService.getOrderConMayorPrecioTotalDelDia(fecha);
        return this.convertToDTO(order.get());
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }
}
