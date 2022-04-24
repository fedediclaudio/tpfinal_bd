package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.request.items.CreateItemRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.dtos.response.orders.SingleOrderResponse;
import com.bd.tpfinal.enums.OrderStatusAction;
import com.bd.tpfinal.services.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public ResponseEntity<BaseResponse> retrieve(@RequestParam(value = "status", required = false) String status,
                                                 @RequestParam(value = "number", required = false) Integer number){
        BaseResponse response = ordersService.retrieve(status, number);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<BaseResponse> retrieve(@PathVariable(name = "order_id", required = true) String id){
        BaseResponse response = ordersService.retrieve(id);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @PostMapping("/{client_id}/address/{address_id}")
    public ResponseEntity<BaseResponse> create(@PathVariable("client_id") String clientId,
                                               @PathVariable(name = "address_id") String addressId,
                                               @RequestParam(name = "comments") String comments){
        BaseResponse response = ordersService.create(clientId, addressId, comments);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @PutMapping("/{order_id}")
    public ResponseEntity<BaseResponse> addItem(@PathVariable("order_id") String orderId, @RequestBody CreateItemRequest createItemRequest){
        BaseResponse response = ordersService.addItemToOrder(orderId, createItemRequest);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @PatchMapping("/{order_id}/{order_status}")
    public ResponseEntity<BaseResponse> changeOrderStatus(@PathVariable("order_id") String orderId,
                                                          @PathVariable("order_status") String orderStatus,
                                                          @RequestParam(value = "cancelled_by_client", required = false) Boolean canceledByClient){
        ChangeOrderStatusDto request = ChangeOrderStatusDto.builder()
                .orderId(orderId)
                .status(OrderStatusAction.valueOf(orderStatus.toUpperCase()))
                .build();
        request.setCanceledByClient(canceledByClient);

        BaseResponse response = ordersService.changeOrderStatus(request);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<BaseResponse> qualify(@PathVariable("order_id") String orderId,
                                                @RequestParam(name = "qualification", required = true) Float qualification,
                                                @RequestParam(name = "qualification_message", required = false) String qualificationMessage) {
        BaseResponse response = new SingleOrderResponse();
        response = ordersService.qualifyOrder(orderId, qualification, qualificationMessage);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @GetMapping("/max_price")
    public ResponseEntity<BaseResponse> maxPriceOrder(@RequestParam(name = "date", required = true) String stringDate){
        BaseResponse response = new SingleOrderResponse();
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
    public ResponseEntity<BaseResponse> maxSupplierProducts(@PathVariable(name = "supplier_id") String supplierId){
        BaseResponse response = ordersService.getOrdersWithMaximumProductsBySupplier(supplierId);
        return new ResponseEntity<>(response,responseStatus(response));
    }
}
