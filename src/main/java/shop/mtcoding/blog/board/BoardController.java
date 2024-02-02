package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.PagingUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;
    //IoC 컨테이너에 세션에 접근할 수 있는 변수가 들어가있음 DI하면 됨

    // http://localhost:8080?page=0
    @GetMapping({"/", "/board"})
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList);

        int currentPage = page;
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);

        boolean first = PagingUtil.isFirst(currentPage);
        boolean last = PagingUtil.isLast(currentPage, boardRepository.count());

        request.setAttribute("first", first);
        request.setAttribute("last", last);

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {

        BoardResponse.DetailDTO responseDTO = boardRepository.findById(id);
        request.setAttribute("board", responseDTO);

        //목적 1. 해당 페이지의 주인 여부
        boolean owner = false;

        // 2. 작성자 userId 확인하기
        int boardUserId = responseDTO.getUserId();

        // 3. 로그인 여부 체크. 로그인이 안됐으면 무조건 false
        // User 타입에서 받아와서 User?
        User sessionUser = (User) session.getAttribute("sessionUser");    //해쉬맵. 키값으로 밸류를 찾으니까
        if (sessionUser != null) {  // 로그인 했고
            if (boardUserId == sessionUser.getId()) {   
                owner = true;
            }
        }

        request.setAttribute("owner", owner);

        return "board/detail";  //forward가 발동

    }
}