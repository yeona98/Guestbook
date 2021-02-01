package com.jane.guestbook.controller;

import com.jane.guestbook.dto.GuestbookRegisterRequestDto;
import com.jane.guestbook.dto.PageRequestDto;
import com.jane.guestbook.entity.Guestbook;
import com.jane.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor
@Controller
public class GuestbookController {

    // https://mangkyu.tistory.com/49
    // https://goddaehee.tistory.com/203
    // https://shinsunyoung.tistory.com/28

    private final GuestbookService guestbookService;

    @GetMapping({"/", "/guestbook"})
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/guestbook/list")
    public void list(@ModelAttribute("pageRequestDto") PageRequestDto pageRequestDto, Model model) { // 파라미터로 PageRequestDto(page & size) 를 받음
        log.info("show list page" + pageRequestDto);

//        PageMapper<GuestbookRequestDto, Guestbook> dto = guestbookService.getList(pageRequestDto);

        model.addAttribute("result", guestbookService.getList(pageRequestDto));
    }

    @GetMapping("/guestbook/register")
    public void register(Model model, GuestbookRegisterRequestDto registerForm) {
        model.addAttribute("registerForm", registerForm);
        log.info("show Register Get page");
    }


    @PostMapping("/guestbook/register")
    public String registerPost(@ModelAttribute("registerRequestDto") GuestbookRegisterRequestDto registerRequestDto,
                               RedirectAttributes redirectAttributes) {
        log.info("GuestbookRequestDto: " + registerRequestDto);
        Long gno = guestbookService.register(registerRequestDto); // 새로 만들어진 엔티티 인덱스
        redirectAttributes.addFlashAttribute("message", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping("/guestbook/read")
    public void read(long gno, @ModelAttribute("readForm") PageRequestDto requestDto, Model model) {
        log.info("show read page of gno " + gno);
        GuestbookRegisterRequestDto payload = guestbookService.read(gno);
        model.addAttribute("dto", payload);
    }

    @GetMapping("/guestbook/modify")
    public void modify(long gno, @ModelAttribute("modifyForm") PageRequestDto requestDto, Model model) {
        log.info("show read page of gno " + gno);
        GuestbookRegisterRequestDto payload = guestbookService.read(gno);
        model.addAttribute("dto", payload);
    }

    @PostMapping("/guestbook/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {
        log.info("remove gno: " + gno);
        guestbookService.remove(gno);
        redirectAttributes.addFlashAttribute("message", gno);

        return "redirect:/guestbook/list";
    }
}
