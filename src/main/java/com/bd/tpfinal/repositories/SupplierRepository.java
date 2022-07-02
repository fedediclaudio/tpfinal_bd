package com.bd.tpfinal.repositories;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.dto.SupplierOrderDTO;
import com.bd.tpfinal.dto.SupplierProductsTypesDTO;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, ObjectId>{
	
	public Collection<Supplier> findByTypesContains(SupplierType st); // for readAllSuppliersForSupplierType
	

	public Collection<Supplier> findByNumberOfQualifGreaterThanEqual(float qualification);
	
	
	@Aggregation(pipeline = {"{"
			+ "$lookup: {"
			+ "    from: 'product',"
			+ "    localField: 'products.$id',"
			+ "    foreignField: '_id',"
			+ "    pipeline: ["
			+ "      { \"$project\": { \"types.$id\": 1 }}"
			+ "    ],"
			+ "    as: 'output'"
			+ "},"
			+ "}",
            "  {'$unwind': '$output'}",
            " {$unwind: '$output.types'}",
            " {$group: {_id:'$_id', name: { $first: '$name' }, cuil: { $first: '$cuil' }, address: { $first: '$address' }, productTypes: { $addToSet: '$output.types.$id' } }}",
            "  {"
            + "        \"$project\": {"
            + "            \"_id\": 1,"
            + "            \"name\": 1,"
            + "            \"cuil\": 1,"
            + "            \"address\": 1,"
            + "            \"productTypes\": 1,"
            + "            \"countTypes\": { \"$size\": \"$productTypes\" }"
            + "        }"
            + "    }",
            "{ $match : { countTypes : {$eq: ?0 }} }"
})
	public Collection<SupplierProductsTypesDTO> getSuppliersWithProductsAllTypes(Long cantProductsType); // for getSuppliersWithProductsAllTypes
	
	//No llegué a corregirla!
	@Aggregation(pipeline = {"{ $match : {'orderStatus.name' : {$eq: 'Delivered' }} }",
			" {$lookup:{from: 'product', localField: 'items.product.$id', foreignField: '_id', as: 'output'}}",
			" {$unwind: '$output'}",
			" {$group:{_id:'$output.supplier.$id', numberOfOrdersDelivered: {$count:{}},}}",
			" {$sort:{numberOfOrdersDelivered : -1}}",
			" {$limit : 10}"})
	public Collection<SupplierOrderDTO> getDiezMayorOrderSent();
	
}
