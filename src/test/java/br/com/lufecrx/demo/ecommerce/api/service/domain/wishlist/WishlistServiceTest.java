// package br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;

// import br.com.lufecrx.demo.ecommerce.api.model.Product;
// import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
// import br.com.lufecrx.demo.ecommerce.api.model.dto.WishlistDTO;
// import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
// import br.com.lufecrx.demo.ecommerce.api.repository.WishlistRepository;
// import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;
// import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistAlreadyExistsException;
// import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistNotFoundException;

// @SpringBootTest
// @ExtendWith(MockitoExtension.class)
// public class WishlistServiceTest {

//     @InjectMocks
//     private WishlistService wishlistService;

//     @Mock
//     private WishlistRepository wishlistRepository;

//     @Mock
//     private ProductRepository productRepository;

//     private Authentication authentication;

//     private SecurityContext securityContext;

//     @BeforeEach
//     public void setUp() {
//         // Initialize the mocks to Authentication and the SecurityContext
//         authentication = mock(Authentication.class);
//         securityContext = mock(SecurityContext.class);

//         // Mock the SecurityContext to return the Authentication mock
//         when(securityContext.getAuthentication()).thenReturn(authentication);

//         // Configure the SecurityContextHolder to return the SecurityContext mock
//         SecurityContextHolder.setContext(securityContext);
//     }

//     @Test
//     public void testAddProductToWishlistWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist and a product
//         Wishlist wishlist = new Wishlist(); 
//         wishlist.setUser(user);
//         Product product = new Product();
        
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user
//         when(productRepository.findById(1L)).thenReturn(Optional.of(product)); // Product exists

//         // Call the method to add the product to the wishlist
//         wishlistService.addProductToWishlist(1L, 1L);

//         // Verify if the wishlist was saved and if the wishlist contains the product
//         verify(wishlistRepository, times(1)).save(wishlist);
//         assert wishlist.getProducts().contains(product);
//     }

//     @Test
//     public void testAddProductToWishlistWhenWishlistDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.empty()); // Wishlist doesn't exist

//         // Verify if the method throws an exception when the wishlist doesn't exist
//         assertThrows(WishlistNotFoundException.class, () -> {
//             wishlistService.addProductToWishlist(1L, 1L);
//         });
//     }

//     @Test
//     public void testAddProductToWishlistWhenProductDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist
//         Wishlist wishlist = new Wishlist(); 
//         wishlist.setUser(user);
        
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user
//         when(productRepository.findById(1L)).thenReturn(Optional.empty()); // Product doesn't exist

//         // Verify if the method throws an exception when the product doesn't exist
//         assertThrows(ProductNotFoundException.class, () -> {
//             wishlistService.addProductToWishlist(1L, 1L);
//         });
//     }

//     @Test
//     public void testRemoveProductFromWishlistWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist and a product
//         Wishlist wishlist = new Wishlist(); 
//         wishlist.setUser(user);
//         Product product = new Product();
        
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user
//         when(productRepository.findById(1L)).thenReturn(Optional.of(product)); // Product exists

//         // Call the method to add the product to the wishlist
//         wishlistService.removeProductFromWishlist(1L, 1L);

//         // Verify if the wishlist was saved and if the wishlist removed the product
//         verify(wishlistRepository, times(1)).save(wishlist);
//         assert wishlist.getProducts() == null || !wishlist.getProducts().contains(product);
//     }

//     @Test
//     public void testRemoveProductFromWishlistWhenWishlistDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.empty()); // Wishlist doesn't exist

//         // Verify if the method throws an exception when the wishlist doesn't exist
//         assertThrows(WishlistNotFoundException.class, () -> {
//             wishlistService.removeProductFromWishlist(1L, 1L);
//         });
//     }

//     @Test
//     public void testRemoveProductFromWishlistWhenProductDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist
//         Wishlist wishlist = new Wishlist(); 
//         wishlist.setUser(user);
        
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user
//         when(productRepository.findById(1L)).thenReturn(Optional.empty()); // Product doesn't exist

//         // Verify if the method throws an exception when the product doesn't exist
//         assertThrows(ProductNotFoundException.class, () -> {
//             wishlistService.removeProductFromWishlist(1L, 1L);
//         });
//     }

//     @Test
//     public void testDeleteWishlistWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist
//         Wishlist wishlist = new Wishlist();
//         wishlist.setUser(user);

//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user

//         // Call the method to delete the wishlist
//         wishlistService.deleteWishlist(1L);

//         // Verify if the wishlist was deleted and if the method was called correctly
//         verify(wishlistRepository, times(1)).delete(wishlist);
//     }

//     @Test
//     public void testDeleteWishlistWhenWishlistDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.empty()); // Wishlist doesn't exist

//         // Verify if the method throws an exception when the wishlist doesn't exist
//         assertThrows(WishlistNotFoundException.class, () -> {
//             wishlistService.deleteWishlist(1L);
//         });
//     }

//     @Test
//     public void testGetWishlistByIdWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist and a product
//         Wishlist wishlist = new Wishlist(); 
//         wishlist.setUser(user);
        
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user

//         // Call the method to get the wishlist by ID
//         Optional<WishlistDTO> result = wishlistService.getWishlistById(1L);

//         // Verify if the method is successful and returns a wishlist
//         assert result.isPresent();
//     }

//     @Test
//     public void testGetWishlistByIdWhenWishlistDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.empty()); // Wishlist doesn't exist

//         // Verify if the method throws an exception when the wishlist doesn't exist
//         assertThrows(WishlistNotFoundException.class, () -> {
//             wishlistService.getWishlistById(1L);
//         });
//     }

//     @Test
//     public void testGetWishlistByNameWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist
//         Wishlist wishlist = Wishlist.builder().name("Test Wishlist").build(); 
//         wishlist.setUser(user);

//         when(wishlistRepository.findByNameAndUser("Test Wishlist", user)).thenReturn(Optional.of(wishlist)); // Wishlist exists and belongs to the user

//         // Call the method to get the wishlist by name
//         Optional<WishlistDTO> result = wishlistService.getWishlistByName("Test Wishlist");

//         // Verify if the method returns the correct wishlist
//         assert result.isPresent();
//         assert result.get().name().equals(wishlist.getName());
//     }

//     @Test
//     public void testGetWishlistByNameWhenWishlistDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         when(wishlistRepository.findByNameAndUser("Test Wishlist", user)).thenReturn(Optional.empty()); // Wishlist doesn't exist

//         // Verify if the method throws an exception when the wishlist doesn't exist
//         assertThrows(WishlistNotFoundException.class, () -> {
//             wishlistService.getWishlistByName("Test Wishlist");
//         });
//     }

//     @Test
//     public void testCreateWishlistWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlistDTO
//         WishlistDTO wishlistDTO = new WishlistDTO("Test Wishlist", null); 

//         wishlistService.createWishlist(wishlistDTO); 

//         // Verify if the wishlist was saved and if the method was called correctly
//         verify(wishlistRepository, times(1)).save(Wishlist.builder().name("Test Wishlist").user(user).build());
//     }

//     @Test
//     public void testCreateWishlistWhenWishlistAlreadyExists() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlistDTO
//         WishlistDTO wishlistDTO = new WishlistDTO("Test Wishlist", null); 

//         when(wishlistRepository.existsByNameAndUser("Test Wishlist", user)).thenReturn(true); // Wishlist already exists

//         // Verify if the method throws an exception when the wishlist already exists
//         assertThrows(WishlistAlreadyExistsException.class, () -> {
//             wishlistService.createWishlist(wishlistDTO);
//         });
//     }

//     @Test
//     public void testRenameWishlistWhenIsSuccessful() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist
//         Wishlist wishlist = Wishlist.builder().name("Test Wishlist").build(); 
//         wishlist.setUser(user);

//         // When the wishlist exists and belongs to the user and the new name is not already in use
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist)); 
//         when(wishlistRepository.existsByNameAndUser("Updated Wishlist", user)).thenReturn(false); 

//         // Create an updated wishlist
//         WishlistDTO updatedWishlist = new WishlistDTO("Updated Wishlist", null);

//         // Call the method to rename the wishlist
//         wishlistService.renameWishlist(1L, updatedWishlist);

//         // Verify if the wishlist was saved and if the method was called correctly
//         verify(wishlistRepository, times(1)).save(Wishlist.builder().name("Updated Wishlist").user(user).build());
//     }

//     @Test
//     public void testRenameWishlistWhenWishlistDoesNotExist() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // When the wishlist doesn't exist
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.empty()); 

//         // Create an updated wishlist
//         WishlistDTO updatedWishlist = new WishlistDTO("Updated Wishlist", null); 

//         // Verify if the method throws an exception when the wishlist doesn't exist
//         assertThrows(WishlistNotFoundException.class, () -> {
//             wishlistService.renameWishlist(1L, updatedWishlist);
//         });
//     }

//     @Test
//     public void testRenameWishlistWhenWishlistAlreadyExists() {
//         User user = new User(); // create a user
//         when(authentication.getPrincipal()).thenReturn(user);

//         // Create a wishlist
//         Wishlist wishlist = Wishlist.builder().name("Test Wishlist").build(); 
//         wishlist.setUser(user);

//         WishlistDTO updatedWishlist = new WishlistDTO("Updated Wishlist", null); // create an updated wishlist

//         // When the wishlist exists and belongs to the user and the new name is already in use
//         when(wishlistRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(wishlist));
//         when(wishlistRepository.existsByNameAndUser("Updated Wishlist", user)).thenReturn(true);

//         // Verify if the method throws an exception when the wishlist already exists
//         assertThrows(WishlistAlreadyExistsException.class, () -> {
//             wishlistService.renameWishlist(1L, updatedWishlist);
//         });
//     }

// }