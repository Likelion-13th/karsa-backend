package likelion13th.shop.repository;


import likelion13th.shop.domain.Item;
import likelion13th.shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long itemId);
}

//ItemRepository.java
//Order API 패턴을 최대한 따라하려고 했습니다.
//JpaRepository를 상속받아 기본 CRUD 메서드를 자동 제공받을 수 있었습니다.


