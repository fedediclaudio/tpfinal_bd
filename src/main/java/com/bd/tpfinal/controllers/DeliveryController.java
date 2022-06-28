package com.bd.tpfinal.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.dto.OrderDTO;
import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Qualification;
import com.bd.tpfinal.services.DeliveryService;

/**
 * @author Sandra Zocchi
 *
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Validated
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	/* endpoints order */
    
    /**Crea una orden para un proveedor
     * @param order
     * @return una orden
     */
	@PostMapping("/suppliers/{id}/order")
    public OrderDTO createOrder(@RequestBody Order order, @PathVariable(value = "id") Long supplierId ){
        return this.deliveryService.createOrderPending(order, supplierId);
    }

    /** Recupera una orden
     * @param number de la orden
     * @return una orden
     */
    @GetMapping("/orders/{number}")
    public Order readOrder(@PathVariable Long number){
        return this.deliveryService.readOrder(number);
    }

    /**Recupera las órdenes asignadas a un deliveryMan
     * @param username del deliveryMan
     * @return
     */
    @GetMapping("/deliveryMan/{username}/orders")
    public Collection<Order> getAssignedOrders(@PathVariable String username){
    	return this.deliveryService.getAssignedOrders(username);
    }

    /**Asigna una orden a un deliveryMan
     * @param number de orden
     * @return
     */
    @PutMapping("/orders/{number}/assign")
    public boolean assignOrder(@PathVariable Long number){
        return this.deliveryService.assignOrder(number);
    }

    /**DeliveryMan acepta una orden asignada
     * @param number de orden
     * @throws Exception
     */
    @PutMapping("/orders/{number}/accept")
    public void acceptOrder(@PathVariable Long number){
        this.deliveryService.acceptOrder(number);
    }

    /**DeliveryMan rechaza la orden
     * @param number de orden
     */
    @PutMapping("/orders/{number}/refuse")
    public void refuseOrder(@PathVariable Long number){
        this.deliveryService.refuseOrder(number);
    }

    /** Cliente cancela la orden
     * @param number de orden
     */
    @PutMapping("/orders/{number}/cancel")
    public void cancelOrder(@PathVariable Long number){
        this.deliveryService.cancelOrder(number);
    }

    /**Orden es entregada exitosamente
     * @param number de orden
     * @throws Exception
     */
    @PutMapping("/orders/{number}/finish")
    public void finishOrder(@PathVariable Long number){
        this.deliveryService.finishOrder(number);
    }
    
	/**Agrega un ítem a una orden
	 * @param item
	 * @param orderId
	 * @return una orden 
	 * @throws Exception
	 */
	@PostMapping ("/suppliers/{id}/order/{number}/newitem" )
	public OrderDTO addItem(@RequestBody Item item, @PathVariable(value = "number") Long orderId, @PathVariable(value = "id") Long idSupplier){
		return this.deliveryService.addItem(orderId, item, idSupplier);
	}
	
	/**Agrego una calificación a una orden ya entregada
	 * @param q: la calificación
	 * @param orderId
	 * @return una orden calificada
	 */
	@PostMapping ("/orders/{id}/qualification")
	public void addQualification(@RequestBody Qualification qualification, @PathVariable(value = "id") Long orderId) {
		this.deliveryService.addQualification(orderId, qualification);
	}

	/**Obtengo la orden de mayor precio total de un día dado
	 * @param fecha
	 * @return
	 */
	@GetMapping("/orders/mayorpreciototalenundia")
	public OrderDTO getMayorPrecioTotalForDay(@RequestParam (name = "fecha") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) java.time.LocalDate fecha){
		return this.deliveryService.getMayorPrecioTotalForDay(fecha);		
	}
			
	/* endpoints client */
	
	/**Crea un cliente
	 * @param client
	 * @return un cliente
	 */
	@PostMapping(path = "/clients")
    public Client createClient(@RequestBody Client client){
        return this.deliveryService.createClient(client);        
    }
	
	/**Recupera un cliente
	 * @param username del cliente
	 * @return un cliente
	 */
	@GetMapping(path = "/clients/{username}")
    public Client readClient(@PathVariable String username){
        return this.deliveryService.readClient(username);
    }	

    /**Actualiza la información de un cliente. Pudiéndole sumar nueva/s dirección/es
     * @param client
     * @param username del cliente
     * @return un cliente actualizado
     * 
     */
    @PutMapping(path = "/clients/{username}")
    public Client updateClient(@RequestBody Client clientDetails, @PathVariable String username){
        return this.deliveryService.updateClient(username, clientDetails);
    }

    /**Desactiva un cliente
     * @param username del cliente
     */
    @DeleteMapping(path = "/clients/{username}")
    public void desactiveClient(@PathVariable String username){
        this.deliveryService.desactiveClient(username);
    }    

    /**Recupera las órdenes de un cliente
     * @param username del cliente
     * @return
     */
    @GetMapping(path = "/clients/{username}/orders")
    public Collection<Order> getClientOrders(@PathVariable String username){
        return this.deliveryService.getClientOrders(username);
    }
    
   	/**Agrega a un cliente una dirección. Un cliente puede tener muchas direcciones asociadas: personal, 
   	 * laboral, familiar, por ejemplo la dirección de los padres. 
   	 * La dirección puede ser que sea una ya persistida o una nueva.
   	 * @param address
   	 * @param username del cliente
   	 * @return el cliente con la nueva dirección
   	 */
   	@PostMapping ("/clients/{username}/address" )
  	public Client addAddress(@RequestBody Address address, @PathVariable String username){
  		return this.deliveryService.addAddress(address, username);
  	}
    

   	/* endpoints delivery man */
    
    /**Crea un deliveryMan
     * @param deliveryMan
     * @return un deliveryMan
     */
    @PostMapping("/deliverymen")
    public DeliveryMan createDeliveryMan(@RequestBody DeliveryMan deliveryMan){
        return this.deliveryService.createDeliveryMan(deliveryMan);        
    }
    
    /**Recupera un deliveryMan
     * @param username
     * @return un deliveryMan
     */
    @GetMapping("/deliverymen/{username}")
    public DeliveryMan readDeliveryMan(@PathVariable String username){
        return this.deliveryService.readDeliveryMan(username);
    }

    /**Actualiza un deliveryMan
     * @param deliveryMan 
     * @param username del deliveryMan
     * @return
     * @throws Exception
     */
    @PutMapping("/deliverymen/{username}")
    public DeliveryMan updateDeliveryMan(@RequestBody DeliveryMan deliveryMan, @PathVariable String username) {
        return this.deliveryService.updateDeliveryMan(username, deliveryMan);
    }

    /**Desactiva un deliveryMan
     * @param username del deliveryMan
     */
    @DeleteMapping("/deliverymen/{username}")
    public void desactiveDeliveryMan(@PathVariable String username){
        this.deliveryService.desactiveDeliveryMan(username);
    }
    
  	/**Obtengo los diez repartidores con mayor puntaje
  	 * @return 
  	 */
  	@GetMapping("/deliverymen/diezmayorescore")
  	public Collection<DeliveryMan> getDiezMayorScore() {
  		return this.deliveryService.getDiezMayorScore();		
  	}
      
	
}
