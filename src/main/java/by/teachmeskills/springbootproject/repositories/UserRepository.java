package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;

public interface UserRepository extends BaseRepository<User> {
    User getUserByEmail(String email);

    User getUserById(int id);

    User getUser(String email, String password);

    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email);
}
