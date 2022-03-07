package com.bd.tpfinal.repositories.interfaces;

import com.bd.tpfinal.model.User;

public interface IUserRepository {
	
	// Los metodos que se vayan a porponer aca, tienen que estar implementados en su implementacion (UserRepositoryImpl)
	
	User getReference(long idUser);
}
