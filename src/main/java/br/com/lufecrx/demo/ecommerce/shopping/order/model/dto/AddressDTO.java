package br.com.lufecrx.demo.ecommerce.shopping.order.model.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.lufecrx.demo.ecommerce.shopping.order.model.Address;

/**
 * This class represents an address in the system.
 * An address is associated with an order and represents the address where the
 * products will be delivered.
 * 
 * An address contains the street, number, neighborhood, city, state, and
 * country.
 * 
 * @param street The street of the address.
 * @param city The city of the address.
 * @param state The state of the address.
 * @param postalCode The postal code of the address.
 * @see Address
 */
public record AddressDTO(
    String street,
    String city,
    String state,
    String postalCode
) {

    /**
     * This method creates an address DTO from the given parameters.
     * 
     * @param street The street of the address.
     * @param city The city of the address.
     * @param state The state of the address.
     * @param postalCode The postal code of the address.
     * @return An address DTO with the given parameters.
     */
    public static AddressDTO from(String street, String city, String state, String postalCode) {
        return new AddressDTO(street, city, state, postalCode);
    }

    /**
     * This method creates an address DTO from the given Address entity.
     * 
     * @param address An Address entity.
     * @return An address DTO with the given Address entity.
     */
    public static AddressDTO from(Address address) {
        return new AddressDTO(address.getStreet(), address.getCity(), address.getState(), address.getPostalCode());
    }

    /**
     * This method creates a set of address DTOs from the given set of Address entities.
     * 
     * @param addresses A set of Address entities.
     * @return A set of address DTOs with the given set of Address entities.
     */
    public static Set<AddressDTO> from(Set<Address> addresses) {
        return addresses.stream().map(AddressDTO::from).collect(Collectors.toSet());
    }

    /**
     * This method creates an Address entity from the given address DTO.
     * 
     * @return An Address entity with the given address DTO.
     */
    public static Address toEntity(AddressDTO addressDTO) {
        return new Address(null, addressDTO.street(), addressDTO.city(), addressDTO.state(), addressDTO.postalCode(), null);
    }
}
