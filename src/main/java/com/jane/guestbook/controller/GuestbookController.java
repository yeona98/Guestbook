package com.jane.guestbook.controller;

import com.jane.guestbook.dto.GuestbookRequestDto;
import com.jane.guestbook.dto.PageMapper;
import com.jane.guestbook.dto.PageRequestDto;
import com.jane.guestbook.entity.Guestbook;
import com.jane.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/guestbook")
@Controller
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) { // 파라미터로 PageRequestDto(page & size) 를 받음
        log.info("show list page" + pageRequestDto);

//        PageMapper<GuestbookRequestDto, Guestbook> dto = service.getList(pageRequestDto);

        model.addAttribute("result", service.getList(pageRequestDto));
    }

    @GetMapping("/register")
    public void register() {
        log.info("show Register get page");
    }


    @PostMapping("/register")
    public String registerPost(GuestbookRequestDto payload, RedirectAttributes redirectAttributes) {


        return "redirect:/guestbook/list";
    }
}
