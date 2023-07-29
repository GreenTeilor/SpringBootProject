package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;

public interface UserRepository extends BaseRepository<User> {
    User getUserByEmail(String email) throws UnableToExecuteQueryException;

    User getUserById(int id) throws UnableToExecuteQueryException;

    User getUser(String email, String password) throws UnableToExecuteQueryException;

    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws UnableToExecuteQueryException;
}
