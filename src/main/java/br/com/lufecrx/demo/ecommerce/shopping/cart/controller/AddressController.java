package br.com.lufecrx.demo.ecommerce.shopping.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.shopping.order.model.dto.AddressDTO;
import br.com.lufecrx.demo.ecommerce.shopping.order.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller class for address endpoints. It handles the HTTP requests related to addresses.
 * It uses the AddressService to perform operations on the database.
 */
@RestController
@ApiResponse(responseCode = "403", description = "You are not authorized to access this resource")
@RequestMapping("/user/address")
public class AddressController {
    
    @Autowired
    private AddressService addressService;

    /**
     * Get all addresses of the user authenticated.
     * @return The list of addresses of the user.
     */
    @Operation(summary = "Get all addresses of the user authenticated")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "Addresses retrieved successfully"),
        @ApiResponse (responseCode = "404", description = "No addresses found for the user")
    })
    @GetMapping("/all")
    public ResponseEntity<Iterable<AddressDTO>> getAllAddressesOfUser() {
        return ResponseEntity.ok(addressService.getAddresses());
    }   
    
    /**
     * Get an address of the user authenticated.
     * @param addressId The id of the address to be retrieved.
     * @return The address with the given id.
     */
    @Operation(summary = "Get an address of the user authenticated")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "Address retrieved successfully"),
        @ApiResponse (responseCode = "404", description = "Address for the user not found")
    })
    @GetMapping("/get")
    public ResponseEntity<AddressDTO> getAddress(
            @RequestParam("address") Long addressId) {
        
        return ResponseEntity.ok(addressService.getAddress(addressId));
    }

    /**
     * Add a new address to the user authenticated.
     * @param address The address to be added, passed as a request body.
     * @return The response containing a message indicating that the address was added successfully.
     */
    @Operation(summary = "Add a new address to the user authenticated")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "Address added successfully")
    })
    @PostMapping("/add")
    public ResponseEntity<String> addAddress(
            @RequestBody AddressDTO address) {
        
        addressService.addAddress(address);
        return ResponseEntity.ok("Address added successfully");
    }

    /**
     * Update an address of the user authenticated.
     * @param addressId The id of the address to be updated.
     * @param address The new data of the address, passed as a request body.
     * @return The response containing a message indicating that the address was updated successfully.
     */
    @Operation(summary = "Update an address of the user authenticated")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "Address updated successfully"),
        @ApiResponse (responseCode = "404", description = "Address for the user not found")
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateAddress(
            @RequestParam("address") Long addressId,
            @RequestBody AddressDTO address) {
        
        addressService.updateAddress(address, addressId);
        return ResponseEntity.ok("Address updated successfully");
    }

    /**
     * Delete an address of the user authenticated.
     * @param addressId The id of the address to be deleted.
     * @return The response containing a message indicating that the address was deleted successfully.
     */
    @Operation(summary = "Delete an address of the user authenticated")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "Address deleted successfully"),
        @ApiResponse (responseCode = "404", description = "Address for the user not found")
    })
    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteAddress(
            @RequestParam("address") Long addressId) {
        
        addressService.removeAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }
}
