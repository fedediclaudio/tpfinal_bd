package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.request.ItemRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.orders.SingleOrderResponseDto;
import com.bd.tpfinal.enums.OrderStatusAction;
import com.bd.tpfinal.services.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE, RequestMethod.PATCH})
public class OrderController extends BaseController {

    //TODO implementar las siguientes acciones:
    //Agregar un item a una orden ya creada *
    //Confirmar un pedido *
    //Agregar una calificacion a una orden ya completada (tambien actualizar la calif. del proveedor)
    //Obtener las ordenes con mas productos de un proveedor especifico *
    //Obtener la orden de mayor precio total de un dia dado *

    private final OrdersService ordersService;

    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("")
    public ResponseEntity<BaseResponseDto> retrieve(@RequestParam(value = "status", required = false) String status,
                                                    @RequestParam(value = "number", required = false) Integer number){
        BaseResponseDto response = ordersService.retrieve(status, number);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<BaseResponseDto> retrieve(@PathVariable(name = "order_id", required = true) String id){
        BaseResponseDto response = ordersService.retrieve(id);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @PostMapping("/{client_id}/address/{address_id}")
    public ResponseEntity<BaseResponseDto> create(@PathVariable("client_id") String clientId,
                                                  @PathVariable(name = "address_id") String addressId,
                                                  @RequestParam(name = "comments") String comments){
        BaseResponseDto response = ordersService.create(clientId, addressId, comments);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @PutMapping("/{order_id}")
    public ResponseEntity<BaseResponseDto> addItem(@PathVariable("order_id") String orderId, @RequestBody ItemRequestDto itemRequestDto){
        BaseResponseDto response = ordersService.addItemToOrder(orderId, itemRequestDto);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @PatchMapping("/{order_id}/{order_status}")
    public ResponseEntity<BaseResponseDto> changeOrderStatus(@PathVariable("order_id") String orderId,
                                                             @PathVariable("order_status") String orderStatus,
                                                             @RequestParam(value = "cancelled_by_client", required = false) Boolean canceledByClient){
        ChangeOrderStatusDto request = ChangeOrderStatusDto.builder()
                .orderId(orderId)
                .status(OrderStatusAction.valueOf(orderStatus.toUpperCase()))
                .build();
        request.setCanceledByClient(canceledByClient);

        BaseResponseDto response = ordersService.changeOrderStatus(request);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<BaseResponseDto> qualify(@PathVariable("order_id") String orderId,
                                                   @RequestParam(name = "qualification", required = true) Float qualification,
                                                   @RequestParam(name = "qualification_message", required = false) String qualificationMessage) {
        BaseResponseDto response = new SingleOrderResponseDto();
        response = ordersService.qualifyOrder(orderId, qualification, qualificationMessage);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/max_price")
    public ResponseEntity<BaseResponseDto> maxPriceOrder(@RequestParam(name = "date", required = true) String stringDate){
        BaseResponseDto response = new SingleOrderResponseDto();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            response = ordersService.getMaximumPriceOrderByDate(date);
        } catch (ParseException e) {
            response.setMessage(e.getMessage());
            response.setStatus(com.bd.tpfinal.dtos.response.ResponseStatus.ERROR);
        }
        return new ResponseEntity<>(response,responseStatus(response));
    }

    @GetMapping("/{supplier_id}/max_supplier_products")
    public ResponseEntity<BaseResponseDto> maxSupplierProducts(@PathVariable(name = "supplier_id") String supplierId){
        BaseResponseDto response = ordersService.getOrdersWithMaximumProductsBySupplier(supplierId);
        return new ResponseEntity<>(response,responseStatus(response));
    }
}
