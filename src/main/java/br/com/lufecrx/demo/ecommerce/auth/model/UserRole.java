package br.com.lufecrx.demo.ecommerce.auth.model;

/**
 * Enum to represent the user role.
 * Contains the roles: admin and user.
 */
public enum UserRole {
    
    ADMIN("admin"),
    USER("user");

    private String role;

    /**
     * Constructor of the enum.
     * @param role the role of the user.
     */
    UserRole(String role) {
        this.role = role;
    }

    /**
     * Get the role of the user.
     * @return the role of the user.
     */
    public String getRole() {
        return role;
    }
}
