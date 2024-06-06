package br.com.lufecrx.demo.ecommerce.shopping.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.shopping.order.model.Address;

/**
 * This interface represents the repository of the Address entity.
 * The repository is responsible for managing the addresses in the database.
 * @see Address
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Get all addresses of a user by the user id.
     * 
     * @param userId The id of the user.
     * @return The list of addresses of the user.
     */
    Optional<List<Address>> findByUserId(Long userId);

    /**
     * Get an address by the user and the address id.
     * 
     * @param user The user that owns the address.
     * @param addressId The id of the address to be retrieved.
     * @return An Optional containing the address if it exists, or an empty Optional otherwise.
     */
    Optional<Address> findByUserAndId(User user, Long addressId);
}