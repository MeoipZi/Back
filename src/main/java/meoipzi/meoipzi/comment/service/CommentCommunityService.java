package meoipzi.meoipzi.comment.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.comment.dto.CommentCommunityRequestDTO;
import meoipzi.meoipzi.comment.repository.CommentCommunityRepository;
import meoipzi.meoipzi.domain.Community;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentCommunityService {
    private final CommentCommunityRepository commentCommunityRepository;
    //private final UserRepository userRepository;
    //private final CommunityRepository communityRepository;

    @Transactional
    public CommentCommunity addComment(Long communityId, CommentCommunityRequestDTO commentCommunityRequestDTO){
        //User user = userRepository.findById(commentCommunityRequestDTO.getUserId());
        //Community community =communityRepository.findById(communityId);
        CommentCommunity commentCommunity = commentCommunityRequestDTO.toEntity();

        Optional<CommentCommunity> parent;
        if(commentCommunityRequestDTO.getParentId() != null){
            parent = commentCommunityRepository.findById(commentCommunityRequestDTO.getParentId());
            commentCommunity.setParent(parent.get());
        }

        //commentCommunity.setUser(user);
        //commentCommunity.setCommunity(community);

        return commentCommunityRepository.save(commentCommunity);
    }

    @Transactional
    public void delete (Long commentCommunityId){
        //CommentCommunity commentCommunity = communityRepository.findCommentByParent(commentCommunityId);
        //if(commentCommunity.getChildren().size()!=0){
        //}
    }

}
