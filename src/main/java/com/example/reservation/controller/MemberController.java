package com.example.reservation.controller;

import com.example.reservation.dto.MemberDTO;
import com.example.reservation.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.tree.domain.SqmTreatedRoot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) { // 회원가입이 완료되면 (1)
        System.out.println("MemberController.save"); // 현재 메서드가 무엇인지 출력 (soutm)
        System.out.println("memberDTO = " + memberDTO); // (soutp)
        memberService.save(memberDTO);
        //MemberService memberService = new MemberService();
        //memberService.save();
        // 스프링에선 잘 사용하지 않음 (컨트롤 클래스에서 서비스 클래스로 뭔가를 넘길때 메서드를 호출할 때 잘 쓰지 않음)
        return "login"; // login HTML 이 띄어져야함
    }


    // 로그인 페이지를 띄어주는 내용
    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }


    @PostMapping(".member/login")
    public String lonin(@ModelAttribute MemberDTO memberDTO , HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // 로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail()); // 로그인 한 회원의 이메일 정보를 session 에 담아 준다.
            return  "main";
        } else {
            // login 실패
            return "login";  // login 페이지에 머물도
        }
    }


    @GetMapping("/member/")
    public String findAll(Model model) { // DB에서 저장 되어있는 모든 데이터를 가져온다는 개념
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 modle 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}") // 경로( {id} )에 있는 어떤 값을 취하겠다 (경로 상에 있는 값을 가져 올때는 @PathVariable 사용)
    public String findById(@PathVariable Long id, Model model) { // 그걸 받아주는 어노테이션 @PathVariable
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }


    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("longinEmail");
        // 담을 땐 set, 가져올땐 get / 강제 형 변환 (String)
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }


    // 업뎃
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }


    // 삭제
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/"; // redirect 뒤에는 반드시 주소가 온다 (파일 이름 X)
    }


    // 로그아웃
    public String logout(HttpSession session) {
        session.invalidate(); // session 을 무효화 한다 라고 본다
        return "index"; // 끝나면 index 문으로 가자
    }

}
