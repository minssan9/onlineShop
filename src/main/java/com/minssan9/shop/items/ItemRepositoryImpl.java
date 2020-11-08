package com.minssan9.shop.items;

import com.minssan9.shop.items.ItemRepository;
import com.minssan9.shop.items.ItemSearch;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ItemRepositoryImpl {
    public static EntityManager em ;
//    public static Querydsl;

    public List<Item> findAll(ItemSearch itemSearch){
        QItem item = QItem.item;
        return null;
    }
}
