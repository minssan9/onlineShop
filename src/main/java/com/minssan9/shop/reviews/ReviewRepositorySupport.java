//package com.minssan9.shop.reviews;
//
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ReviewRepositorySupport extends QuerydslRepositorySupport {
//
//    private final JPAQueryFactory queryFactory = new JPAQueryFactory();
//
//    private Review review= new Review();
//    public ReviewRepositorySupport (JPAQueryFactory queryFactory){
//        super(Review.class);
//        this.queryFactory = queryFactory;
//    }
//
//    public List<Review> findByName (String name ){
//        return queryFactory
//            .selectFrom(review)
//            .where(review.user.name.eq(name))
//            .fetch();
//    }
//}
