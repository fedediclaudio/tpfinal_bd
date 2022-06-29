package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Supplier> getTop10SupplierConMasOrdenesDespachadas() {
        return supplierRepository.getTop10SupplierConMasOrdenesDespachadas();
    }

    @Override
    public List<Supplier> getAllProveedoresByTipo(String tipoId) {
        return supplierRepository.findAllByType(tipoId);
    }

    @Override
    public List<Supplier> getProveedoresWithProductosDeTodosLosTipos() {
        return null;
    }

    @Override
    @Transactional
    public List<SupplierDTO> getAllProveedoresPorCantEstrellas(int cant_estrellas) {
        List<SupplierDTO> suppliersDTO = new ArrayList(); // para devolver los prov con X cant de estrellas

        Iterable<Supplier> supplierAll = supplierRepository.findAll();
        List<Supplier> supplier = new ArrayList<>();
        supplierAll.forEach(supplier::add);

        for (int j = 0; j < supplier.size(); j++) //// recorro todos los suppliers
        {
            Supplier s = supplier.get(j);
            List<Order> ordersSupplier = orderRepository.findOrdersDeSupplier(s.getId());// busco las ordenes del supplier
            int cantQualification = 0;
            int i = 0;
            for (i = 0; i < ordersSupplier.size(); i++) {
                Order o = ordersSupplier.get(i);
                if (o.getStatus().getName().equals("Delivered"))
                    if (o.getQualification().getScore() == cant_estrellas) // la orden tiene las estrellas que busco
                        cantQualification = cantQualification + (int) (o.getQualification().getScore());
            }
            if (cantQualification > 0) // tiene calificaciones con la estrella indicada, agregarlo
            {
                SupplierDTO sDTO = new SupplierDTO();
                sDTO.setName(s.getName());
                sDTO.setStarQualification(cant_estrellas);
                sDTO.setCantQualifications(cantQualification);
                suppliersDTO.add(sDTO);
            }
        }
        return suppliersDTO;
    }
}
