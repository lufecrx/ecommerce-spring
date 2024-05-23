package br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.model.dto.WishlistDTO;
import br.com.lufecrx.demo.ecommerce.api.repository.WishlistRepository;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination.InvalidArgumentsToPaginationException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination.InvalidSortDirectionException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistsEmptyException;

public class WishlistServicePaginableTest {

    @InjectMocks
    private WishlistServicePaginable wishlistServicePaginable;

    @Mock
    private WishlistRepository wishlistRepository;

    private Authentication authentication;

    private SecurityContext securityContext;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the mocks to Authentication and the SecurityContext
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);

        // Mock the SecurityContext to return the Authentication mock
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Configure the SecurityContextHolder to return the SecurityContext mock
        SecurityContextHolder.setContext(securityContext);

        // Mock the User object to be returned by the Authentication mock
        user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
    }

    @Test
    public void testGetWithPaginationReturnsNonEmptyPage() {
        Wishlist wishlist = new Wishlist();
        Page<Wishlist> page = new PageImpl<>(Arrays.asList(wishlist));

        // Mocking the behavior of the methods to simulate a non-empty page
        when(wishlistRepository.findAllByUser(eq(user), any(Pageable.class))).thenReturn(page);

        // Getting the wishlists
        Iterable<WishlistDTO> wishlists = wishlistServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });

        // Verifying if the methods were called correctly
        assertTrue(wishlists.iterator().hasNext());
        assertEquals(WishlistDTO.from(wishlist), wishlists.iterator().next());
    }

    @Test
    public void testGetWithPaginationReturnsEmptyPage() {
        Page<Wishlist> page = Page.empty();

        // mock the behavior of the methods to simulate an empty page
        when(wishlistRepository.findAllByUser(eq(user), any(Pageable.class))).thenReturn(page);

        // Getting the wishlists
        assertThrows(WishlistsEmptyException.class, () -> {
            wishlistServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });
        });
    }

    @Test
    public void testGetWithPaginationThrowsException() {
        // Mocking the behavior of the methods to simulate when an exception is thrown
        when(wishlistRepository.findAllByUser(eq(user), any(Pageable.class))).thenThrow(new RuntimeException());

        // Getting the wishlists and expecting an exception
        assertThrows(RuntimeException.class, () -> {
            wishlistServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });
        });
    }

    @Test
    public void testGetWithPaginationThrowsExceptionWhenPageIsNegative() {
        // Getting the wishlists and expecting an exception
        assertThrows(InvalidArgumentsToPaginationException.class, () -> {
            wishlistServicePaginable.getWithPagination(-1, 1, new String[] { "id", "asc" });
        });
    }

    @Test
    public void testGetWithPaginationThrowsExceptionWhenSizeIsNegative() {
        // Getting the wishlists and expecting an exception
        assertThrows(InvalidArgumentsToPaginationException.class, () -> {
            wishlistServicePaginable.getWithPagination(0, -1, new String[] { "id", "asc" });
        });
    }

    @Test
    public void testGetWithPaginationThrowsExceptionWhenSortDirectionIsInvalid() {
        // Getting the wishlists and expecting an exception
        assertThrows(InvalidSortDirectionException.class, () -> {
            wishlistServicePaginable.getWithPagination(0, 1, new String[] { "id", "invalid" });
        });
    }
}
