package com.example.tasks.repository;

import com.example.tasks.classes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Procedure(name="AddCategory")
    void addCategory(@Param("CategoryName") String categoryName);

    @Procedure(name="DeleteCategory")
    void deleteCategory(@Param("CategoryID") int categoryId);
}
