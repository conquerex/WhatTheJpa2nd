package what.the.jpa2nd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import what.the.jpa2nd.domain.Address;
import what.the.jpa2nd.domain.Member;
import what.the.jpa2nd.service.MemberService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm",
                // 계속 수기 입력하는 것이 귀찮아서 샘플값 미리 입력 : new MemberForm()
                new MemberForm("NNName", "CCity", "SStreett", "zzzzzipcode")
        );
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        if (result.hasErrors())
            return "members/createMemberForm";

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // 실무라면 memberService.findMembers()를
        // 그대로 쓰기 보다는 DTO를 쓰는 것을 권장
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}
