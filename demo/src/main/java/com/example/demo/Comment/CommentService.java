package com.example.demo.Comment;

import java.sql.SQLException;
import java.util.List;

public class CommentService {
    private final CommentRepository commentRepository = new CommentRepository();

    public CommentEntity addComment(CommentEntity commentEntity) throws SQLException {
        return commentRepository.create(commentEntity);
    }
    public List<CommentEntity> getAllComments() throws SQLException {
        return commentRepository.findAll();
    }
    public CommentEntity getCommentById(Long id) throws SQLException {
        var comment = commentRepository.findByID(id);
        if(comment == null){
            throw new IllegalArgumentException("Comment does not exist in database");
        }else{
            return comment;
        }
    }
    public CommentEntity updateComment(Long id, CommentEntity commentEntity) throws SQLException {
        CommentEntity comment = commentRepository.findByID(id);
        if(comment == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        comment = commentEntity;
        return commentRepository.updateById(id, comment);
    }
    public void deleteComment(Long id) throws SQLException {
        CommentEntity comment = commentRepository.findByID(id);
        if(comment == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        commentRepository.deleteByID(id);
    }
}
