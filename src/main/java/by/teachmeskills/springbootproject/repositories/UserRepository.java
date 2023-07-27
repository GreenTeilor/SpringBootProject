package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;

public interface UserRepository extends BaseRepository<User> {
    User getUserByEmail(String email) throws BadConnectionException;

    User getUserById(int id) throws BadConnectionException;

    User getUser(String email, String password) throws BadConnectionException;

    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws BadConnectionException;
}
