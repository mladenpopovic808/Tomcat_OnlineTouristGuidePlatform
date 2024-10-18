package rs.raf.demo.repositories.interfaces;

import rs.raf.demo.entities.Comment;

import java.util.List;

public interface CommentRepository {

    public Comment addComment(Comment comment);
    public List<Comment> findCommentsForArticle(Integer id);
}
