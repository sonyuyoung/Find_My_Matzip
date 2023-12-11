package com.matzip.controller;


import com.matzip.dto.CommentDto;
import com.matzip.service.CommentService;
import com.matzip.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UsersService usersService;

    // 댓글 작성
    @PostMapping("/comment/save")
    public ResponseEntity<?> save(@RequestBody CommentDto commentDto){
        // CommentDto를 생성하고 값 설정
//        CommentDto commentDto = new CommentDto();
//        commentDto.setCommentWriter(commentWriter);
//        commentDto.setCommentContents(commentContents);
//        commentDto.setBoardId(board);

        Long saveResult = commentService.save(commentDto);
        // 저장 결과에 따라 응답 생성
        if (saveResult != null) {
            // 저장된 댓글 ID를 사용하여 저장된 댓글 정보를 조회
            CommentDto savedComment = commentService.findById(saveResult);

            // 성공 응답
            return new ResponseEntity<>(savedComment, HttpStatus.OK);
        } else {
            // 실패 응답 (해당 게시글이 존재하지 않을 경우)
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
//// 댓글 작성rest
//@PostMapping("/save")
//public ResponseEntity<?> save(@RequestBody CommentDto commentDto) {
//    // CommentDto를 받아와서 출력
//    System.out.println("commentDto = " + commentDto);
//
//    // 댓글을 저장하고 저장된 댓글의 ID를 받아옴
//    Long saveResult = commentService.save(commentDto);
//
//    // 저장 결과에 따라 응답 생성
//    if (saveResult != null) {
//        // 저장된 댓글 ID를 사용하여 저장된 댓글 정보를 조회
//        CommentDto savedComment = commentService.findById(saveResult);
//₩
//
//
//
//// 성공 응답
//        return new ResponseEntity<>(savedComment, HttpStatus.OK);
//    } else {
//        // 실패 응답 (해당 게시글이 존재하지 않을 경우)
//        return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//    }
//}

    // 리뷰 화면
    @GetMapping("/comment/edit/{commentId}")
    public String editComment(@PathVariable Long commentId, Model model) {
        CommentDto commentDto = commentService.findById(commentId);
        model.addAttribute("comment", commentDto);

        Long boardId = commentDto.getBoardId();  // assuming CommentDTO has getItemId() method
        List<CommentDto> comments = commentService.findByBoardId(boardId);
        model.addAttribute("comments", comments);

        return "editComment";
    }
//
//
    // 리뷰 수정
    @PostMapping("/comment/update/{id}")
    public String updateComment(@PathVariable Long id, @ModelAttribute CommentDto commentDto, RedirectAttributes redirectAttributes) {
        try {
            CommentDto updatedComment = commentService.update(id, commentDto);
            redirectAttributes.addFlashAttribute("successMessage", "Comment updated successfully");
            return "redirect:/board/" + updatedComment.getBoardId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Comment could not be updated");
            return "redirect:/comment/edit/" + id;
        }
    }


    // 리뷰 삭제
    @GetMapping("/comment/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        // 댓글을 삭제하고 해당 상품 페이지로 리다이렉트합니다.
        Long boardId = commentService.deleteComment(id);
        return "redirect:/board/" + boardId;
    }

}
