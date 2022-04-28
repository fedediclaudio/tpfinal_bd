package com.bd.tpfinal.repositories;

        import com.bd.tpfinal.model.Address;
        import com.bd.tpfinal.model.Supplier;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;

import java.util.List;
        import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> { 
        public List<Supplier> findByTypeId(Long aProductTypeId);
        public Optional<Supplier> findSupplierById(Long anId);
}
