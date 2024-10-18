package rs.raf.demo.services;

import rs.raf.demo.repositories.interfaces.CommentRepository;

import javax.inject.Inject;
import java.util.List;
import rs.raf.demo.entities.Comment;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.addComment(comment);
    }

    public List<Comment> findCommentsForArticle(Integer id) {
        return commentRepository.findCommentsForArticle(id);
    }

//    public List<Comment> allComments (){return commentRepository.allComments();}
}
