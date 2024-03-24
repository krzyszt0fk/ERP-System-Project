package com.example.SystemBackend.repository;
import com.example.SystemBackend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ItemRepository extends JpaRepository<Item,Long> { //2 parametr okresla jakiego typu jest PrimaryKey
}
