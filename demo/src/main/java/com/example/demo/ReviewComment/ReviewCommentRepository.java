package com.example.demo.ReviewComment;

import com.example.demo.DataBase;
import com.example.demo.Repository;

import java.util.List;

public class ReviewCommentRepository implements Repository<ReviewCommentEntity, Long> {
    public ReviewCommentEntity findByUserId(Long aLong) {
        return DataBase.getInstance().createNamedQuery("reviewComments.findByUserId", ReviewCommentEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }
    public ReviewCommentEntity findByReviewId(Long aLong) {
        return DataBase.getInstance().createNamedQuery("reviewComments.findByReviewId", ReviewCommentEntity.class)
                .setParameter(1, aLong)
                .getSingleResult();
    }

    @Override
    public ReviewCommentEntity findByID(Long aLong) {
        return null;
    }

    @Override
    public List<ReviewCommentEntity> findAll() {
        var query = DataBase.getInstance().createNamedQuery("reviewComments.findAll", ReviewCommentEntity.class);
        return query.getResultList();
    }

    @Override
    public void deleteByID(Long aLong) {

    }

//    public ReviewCommentEntity findByIDS(ReviewCommentEntityPK r) {
//        return DataBase.getInstance().find(ReviewCommentEntity.class, r);
//    }

//    public void deleteByIdS(ReviewCommentEntityPK r) {
//        DataBase.getInstance().getTransaction().begin();
//        ReviewCommentEntity r1 = findByIDS(r);
//        DataBase.getInstance().remove(r1);
//        DataBase.getInstance().getTransaction().commit();
//
//    }
}
