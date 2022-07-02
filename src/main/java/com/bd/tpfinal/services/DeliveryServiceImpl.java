package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bd.tpfinal.dto.DeliveryManDTO;
import com.bd.tpfinal.dto.FactoryDTO;
import com.bd.tpfinal.dto.ItemDTO;
import com.bd.tpfinal.dto.OrderDTO;
import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.model.Pending;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Qualification;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.AddressRepository;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import com.bd.tpfinal.utils.DeliveryException;


/**
 * @author saz20
 *
 */
@Service
public class DeliveryServiceImpl implements DeliveryService{
	
	@Autowired
    private OrderRepository orderRepository;
	
    @Autowired
    private DeliveryManRepository deliveryManRepository;

    @Autowired
    private ClientRepository clientRepository;
	
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
	private ProductRepository productRepository;
    
    @Autowired
	private SupplierRepository supplierRepository;
    
    /**
	 * Es el objeto encargado de crear los DTOs.
	 */
    @Autowired
	private FactoryDTO dtoFactory;
	
    
	
    public DeliveryServiceImpl() {/* empty for framework */}
    
    /* endpoints order */
    
   	@Override
   	@Transactional
   	
   	
   	public OrderDTO createOrderPending(Order order, ObjectId idSupplier){		
		Client client = this.readClient(order.getClient().getUsername());
		Address address = this.addressRepository.findById(order.getAddress().getId()).get();
		order.setAddress(address);
		order.setClient(client);
		this.orderRepository.save(order); 
		client.addOrder(order);
		this.clientRepository.save(client); 
		order.getItems().forEach(item ->{
		Product	prod = this.productRepository.findById(item.getProduct().getId()).get(); 
		item.setProduct(prod);
		order.setTotalPrice(order.getTotalPrice() + prod.getPrice()*item.getQuantity());
		});	
		OrderStatus status = new Pending(order, LocalDate.now());
		order.setOrderStatus(status);
		this.orderRepository.save(order);
		Collection<ItemDTO> colItemDTO = new ArrayList<ItemDTO>();
		order.getItems().forEach(item ->{
			colItemDTO.add(this.dtoFactory.createItemDTO(item));
		});
		return this.dtoFactory.createOrderDTO(order, colItemDTO );
	}
   	
	@Transactional(readOnly = true)
	private Supplier readSupplierId(ObjectId supplierId) {
		Supplier supplier = this.supplierRepository.findById(supplierId).orElseThrow(() -> new NullPointerException("Supplier not found for this id: " + supplierId));
		return supplier;
	}
   	
   	@Override
	@Transactional
	public OrderDTO addItem(ObjectId orderId, Item item, ObjectId idSupplier){		
		Order order = this.readOrder(orderId);
		if ((order!= null) && (order.canAddItem())){
			Supplier supplier = this.readSupplierId(idSupplier);
			Product	prod = this.productRepository.findById(item.getProduct().getId()).get();
			Supplier supplierOrder= order.getItems().get(0).getProduct().getSupplier();
			if (supplierOrder.getId().equals(supplier.getId())){
				item.setProduct(prod);
				order.setTotalPrice(order.getTotalPrice() + prod.getPrice()*item.getQuantity());
				order.addItem(item);
			}else throw new DeliveryException("The product does not belong to the supplier: "+ idSupplier + " !!");
		}else {throw new DeliveryException("The order cant'be add a new item");}
		this.orderRepository.save(order);
		Collection<ItemDTO> colItemDTO = new ArrayList<ItemDTO>();
		order.getItems().forEach(it ->{
			colItemDTO.add(this.dtoFactory.createItemDTO(it));
		});
		return this.dtoFactory.createOrderDTO(order, colItemDTO );
	}
   	
   	@Override
   	@Transactional(readOnly = true)
   	public Order readOrder(ObjectId number) {				
   		Order order = this.orderRepository.findById(number).orElseThrow(() -> new NullPointerException("Order not found for this id: " + number));
       	order.setStatusByName();
       	return order;
   	}

   	@Override
   	@Transactional(readOnly = true)
   	public Collection<Order> getAssignedOrders(String username) {
   		DeliveryMan dm = this.deliveryManRepository.findByUsername(username).get();
   		dm.getOrdersPending().size(); // Inicializar lista LAZY
   		return dm.getOrdersPending();
   	}

   	@Override
   	@Transactional
   	public boolean assignOrder(ObjectId number) {
   		DeliveryMan deliveryMan = this.deliveryManRepository.findByFreeTrueAndActiveTrue().stream().findAny().orElse(null);
        if (deliveryMan != null) {
            try {
                Order order = this.readOrder(number);
                deliveryMan.getOrdersPending().size(); // Inicializar lista LAZY
                order.assign(deliveryMan); 
                this.orderRepository.save(order);
                this.deliveryManRepository.save(deliveryMan); 
                return true;
            } catch (DeliveryException e) {
                return false;
            }
        }
        return false;
   	}

   	@Override
   	@Transactional
   	public void acceptOrder(ObjectId number){
   		 Order order = this.readOrder(number);
   		 order.getDeliveryMan().getOrdersPending().size(); // Inicializar lista LAZY
   		 order.deliver();
   		 this.orderRepository.save(order); // También guardamos el DeliveryMan y el Client, debido a las oper en cadena
   	}

   	@Override
   	@Transactional
   	public void refuseOrder(ObjectId number){
   		 Order order = this.readOrder(number);
   		 order.refuse();
   		 this.deliveryManRepository.save(order.getDeliveryMan());  //mongo no cascade
         this.orderRepository.save(order);
   		
   	}

   	@Override
   	@Transactional
   	public void cancelOrder(ObjectId number) {
   		 Order order = this.readOrder(number);
   		 if (order.getDeliveryMan() == null) {
   		         order.cancel();
   		      this.clientRepository.save(order.getClient()); 
   		 }else {
   			 order.cancel();
   			 this.clientRepository.save(order.getClient()); 
   			 this.deliveryManRepository.save(order.getDeliveryMan());  
   		 
   		 }
         this.orderRepository.save(order);		
   	}

   	@Override
   	@Transactional
   	public void finishOrder(ObjectId number){
   		Order order = this.readOrder(number);
   		order.finish();
   		this.deliveryManRepository.save(order.getDeliveryMan());  
   		this.clientRepository.save(order.getClient()); 
       	this.orderRepository.save(order);		
   	}

   	@Override
   	@Transactional
   	public void  addQualification(ObjectId orderId, Qualification qualification) {
   		Order order = this.readOrder(orderId);
   		if ((order!= null) &&  (order.getQualification()== null)){
			if (order.getOrderStatus().getName() == "Delivered") {
	   			order.setQualification(qualification);
	   			ObjectId idSupplier = order.getItems().get(0).getProduct().getSupplier().getId();
	   			Supplier supplier = this.supplierRepository.findById(idSupplier).get();
	   			supplier.setNumberOfQualif(supplier.getNumberOfQualif()+1);
	   			supplier.setQualification(supplier.getQualification() + qualification.getScore());
	   			this.supplierRepository.save(supplier); 
	   			this.orderRepository.save(order);
   			}else throw new DeliveryException("The order cant'be qualified because your status isn't delivered!");
   	   	}else throw new DeliveryException("The order cant'be qualified because it's already qualified!");
	}
   	
   	@Override
   	@Transactional(readOnly = true)
   	public OrderDTO getMayorPrecioTotalForDay(LocalDate fecha) {
   		Order order = this.orderRepository.findFirstByDateOfOrderEqualsOrderByTotalPriceDesc(fecha).orElseThrow(() -> new EmptyResultDataAccessException("There isn't order for this date: " + fecha, 0));
   		Collection<ItemDTO> colItemDTO = new ArrayList<ItemDTO>();
		order.getItems().forEach(item ->{
			colItemDTO.add(this.dtoFactory.createItemDTO(item));
		});
		return this.dtoFactory.createOrderDTO(order, colItemDTO );
   	}

	
	/* endpoints client */

	@Override
	@Transactional
	public Client createClient(Client client) {
		Set<Address> addresses = new HashSet<>();
		client.getAddresses().forEach(address->{
			if ( address.getId()!= null ) {
				Address adPersist = this.addressRepository.findById(address.getId()).get();
				addresses.add(adPersist);
			}else {
				addresses.add(address); /* dirección aún No persistida */
				this.addressRepository.save(address); 
			}
		}); 
		
		client.setAddresses(addresses);		
	return this.clientRepository.save(client);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Client readClient(String username) {				
		return this.clientRepository.findByUsername(username).orElseThrow(() -> new NullPointerException("Cient not found for this username: " + username));
	}	
	
	@Override
	@Transactional
	public Client updateClient(String username, Client clientDetails){		
		Client client = this.readClient(username);
		this.updateUser(client, clientDetails);
		Set<Address> addresses = new HashSet<>(); //Actualizo la/las direcciones del cliente.	
		clientDetails.getAddresses().forEach(address->{
			if ( address.getId() != null ){
				Address adPersist = this.addressRepository.findById(address.getId()).get();
				addresses.add(adPersist);
			}else addresses.add(address);
		});
		client.addAddresses(addresses); 		
		return this.clientRepository.save(client);
	}
	
	private User updateUser(User realUser, User user){
        if (user != null) {
            realUser.setName(user.getName());
            realUser.setEmail(user.getEmail());
            realUser.setDateOfBirth(user.getDateOfBirth());
            realUser.setPassword(user.getPassword());           
        } else {
            throw new DeliveryException("User not found");
        }
        return realUser;
    }
	
	@Override
	@Transactional
	public void desactiveClient(String username) {
		Client client = this.readClient(username);
		if (client.isActive())
			client.setActive(false);
        this.clientRepository.save(client);
	}	

	@Override
	@Transactional(readOnly = true)
	public Collection<Order> getClientOrders(String username) {
		Client client = this.readClient(username);
		return client.getOrders();
	}
	
	@Override
   	@Transactional
   	public Client addAddress(Address address, String username) {
		Client client = this.readClient(username);
		if ( address.getId() != null ){
			Address adPersist = this.addressRepository.findById(address.getId()).get();
			client.addAddress(adPersist);
		}else client.addAddress(address);
		return this.clientRepository.save(client); 
  	}
	
	/* endpoints delivery man */
	
	@Override
	@Transactional
	public DeliveryMan createDeliveryMan(DeliveryMan deliveryMan) {
		return this.deliveryManRepository.save(deliveryMan);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DeliveryMan readDeliveryMan(String username) {		 
		DeliveryMan delMan = this.deliveryManRepository.findByUsername(username).orElseThrow(() -> new NullPointerException("Delivery Man not found for this username: " + username));
		return delMan;
	}

	@Override
	@Transactional
	public DeliveryMan updateDeliveryMan(String username, DeliveryMan deliveryMan){		
		 DeliveryMan delMan = this.readDeliveryMan(username);
		 this.updateUser(delMan, deliveryMan);
		 return this.deliveryManRepository.save(delMan);
	}

	@Override
	@Transactional
	public void desactiveDeliveryMan(String username) {
		DeliveryMan delMan = this.readDeliveryMan(username);
        if (delMan.isActive()) delMan.setActive(false);              
        this.deliveryManRepository.save(delMan);
	}
	
	@Override
   	@Transactional(readOnly = true)
   	public Collection<DeliveryManDTO> getDiezMayorScore() {
   		 List<DeliveryMan> listDM = this.deliveryManRepository.findFirst10ByActiveTrueOrderByScoreDesc();
   		 Collection<DeliveryManDTO> colDmDTO = new ArrayList<DeliveryManDTO>();
   		
   		listDM.forEach(dm ->{
			colDmDTO.add(this.dtoFactory.createDeliveryManDTO(dm));
		});
		return colDmDTO;
   	}

	
}

