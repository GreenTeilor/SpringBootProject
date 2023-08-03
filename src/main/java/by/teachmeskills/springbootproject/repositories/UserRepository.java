package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;

public interface UserRepository extends BaseRepository<User> {
    User getUserByEmail(String email) throws EntityOperationException;

    User getUserById(int id) throws EntityOperationException;

    User getUser(String email, String password) throws EntityOperationException;

    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws EntityOperationException;
}
