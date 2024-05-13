package br.com.lufecrx.demo.ecommerce.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class CategoryTest {

    private List<Category> categories;

    private Faker faker;

    @BeforeEach
    public void init() {
        faker = new Faker();
        categories = fillCategories();
    }

    public List<Category> fillCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setName(faker.commerce().department());

            categories.add(category);
        }

        return categories;
    }

    @Test
    public void testCategoryNotNull() {
        for (Category category : categories) {
            assertNotNull(category);
        }
    }

    @Test
    public void testGetAndSetName() {
        for (Category category : categories) {
            String name = faker.commerce().department();
            category.setName(name);
            assertEquals(name, category.getName());
        }
    }
}