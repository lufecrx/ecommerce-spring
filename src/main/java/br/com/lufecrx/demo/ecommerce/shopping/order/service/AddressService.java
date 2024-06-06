package br.com.lufecrx.demo.ecommerce.shopping.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.auth.repository.UserRepository;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.order.address.AddressListEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.order.address.AddressNotFoundException;
import br.com.lufecrx.demo.ecommerce.shopping.order.model.Address;
import br.com.lufecrx.demo.ecommerce.shopping.order.model.dto.AddressDTO;
import br.com.lufecrx.demo.ecommerce.shopping.order.repository.AddressRepository;

/**
 * This service is responsible for handling the address entity operations.
 * 
 * @see Address
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get the authenticated user.
     * 
     * @return The authenticated user.
     */
    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Add an address to the authenticated user. The method receives an AddressDTO
     * and converts it to an Address entity,
     * then sets the authenticated user to the address and saves it.
     * 
     * @param address Address DTO to be added.
     */
    public void addAddress(AddressDTO address) {
        User user = getAuthenticatedUser();

        Address addressEntity = AddressDTO.toEntity(address);

        addressEntity.setUser(user);
        user.addAddress(addressEntity);

        addressRepository.save(addressEntity);
        userRepository.save(user);
    }

    /**
     * Remove an address from the authenticated user. The method receives an
     * addressId and searches for the address in the database,
     * then removes it from the user and deletes it.
     * 
     * @param addressId Address ID to be removed.
     * @throws AddressNotFoundException if the address is not found.
     */
    public void removeAddress(Long addressId) {
        User user = getAuthenticatedUser();

        Address address = addressRepository.findByUserAndId(user, addressId)
                .orElseThrow(() -> new AddressNotFoundException());

        user.removeAddress(address);

        addressRepository.delete(address);
        userRepository.save(user);
    }

    /**
     * Update an address from the authenticated user. The method receives an
     * AddressDTO and an addressId,
     * then searches for the address in the database, updates it and saves it.
     * 
     * @param address   Address DTO to be updated.
     * @param addressId Address ID to be updated.
     * @throws AddressNotFoundException if the address is not found.
     */
    public void updateAddress(AddressDTO address, Long addressId) {
        User user = getAuthenticatedUser();

        Address addressEntity = addressRepository.findByUserAndId(user, addressId)
                .orElseThrow(() -> new AddressNotFoundException());

        addressEntity.setStreet(address.street());
        addressEntity.setCity(address.city());
        addressEntity.setState(address.state());
        addressEntity.setPostalCode(address.postalCode());

        addressRepository.save(addressEntity);
    }

    /**
     * Get an address from the authenticated user. The method receives an addressId
     * and searches for the address in the database.
     * 
     * @param addressId Address ID to be retrieved.
     * @return The address DTO.
     * @throws AddressNotFoundException if the address is not found.
     */
    public AddressDTO getAddress(Long addressId) {
        User user = getAuthenticatedUser();

        Address address = addressRepository.findByUserAndId(user, addressId)
                .orElseThrow(() -> new AddressNotFoundException());

        return AddressDTO.from(address);
    }

    /**
     * Get all addresses from the authenticated user.
     * 
     * @return The list of address DTOs.
     */
    public Iterable<AddressDTO> getAddresses() {
        User user = getAuthenticatedUser();

        if (user.getAddresses().isEmpty()) {
            throw new AddressListEmptyException();
        }

        return AddressDTO.from(user.getAddresses());
    }
}
