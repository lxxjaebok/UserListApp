package com.example.basiccrud.controller;

import com.example.basiccrud.dto.MemberDto;
import com.example.basiccrud.entity.Member;
import com.example.basiccrud.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@Log
public class MemberController {
    // @Autowired
    // private MemberRepository getMemberRepository;

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("show")
    public String showAll(Model model){
        List<MemberDto> memberDtoList = memberService.showAllMembers();
//        List<Member> memberList = memberRepository.findAll();
//
////        MemberDto static Method 사용
//        List<MemberDto> dtoList = new ArrayList<>();
//        for(Member member : memberList){
//            dtoList.add(MemberDto.fromMemberEntity(member));
//        }
//        log.info("dtoList = " + dtoList.toString());

//        Static Method 와 Stream 사용
//        List<MemberDto> memberDtoList = memberRepository.findAll()
//                .stream()
//                .map(x -> MemberDto.fromMemberEntity(x))
//                .toList();
//        log.info("memberDtoList = " + memberDtoList.toString());
        model.addAttribute("memberDto", memberDtoList);

//        for루프로 하나 씩 불러와서 넣어주기
//        MemberDto dto = null;
//        List<MemberDto> dtoList = new ArrayList<>();
//        for(Member member : memberList){
//            dto.setId(member.getId());
//            dto.setName(member.getName());
//            dto.setAge(member.getAge());
//            dto.setAddress(member.getAddress());
//            dtoList.add(dto);
//        }
        return "showMember";
    }

    @GetMapping("insertForm")
    public String insertForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "insertForm";
    }

    @PostMapping("insert")
    public String insertMember(@Valid @ModelAttribute("memberDto")MemberDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){log.info("ERROR !  ! ! ! ");
            return "insertForm";
        }
        memberService.insertMember(dto);

//        log.info(dto.toString());
        // 가져온 DTO를 Entity Member class로 옮겨담기
//        Member member = dto.fromMemberDto(dto);
//        log.info("Member : " + member.toString());
        //저장
//        memberRepository.save(member);
        return "redirect:/member/show";
    }

    @GetMapping("/update")
    public String updateMember(@RequestParam("updateId")Long id, Model model){
        MemberDto memberDto = memberService.getOneMember(id);
//        log.info("==== id : " + String.valueOf(id));
//        // 가져온 ID를 데이터베이스에서 검색
//        Member member = memberRepository.findById(id).orElse(null);
//        //검색한 결과를 MemberDto에 옮겨 닮기
//        MemberDto dto = MemberDto.fromMemberEntity(member);
//        //위 두가지를 한번에 처리하기
//        MemberDto memberDto = memberRepository.findById(id)
//                .map(x -> MemberDto.fromMemberEntity(x))
//                .orElse(null);
//        //dto를 Model에 담아서 updateForm 전송
        model.addAttribute("memberDto", memberDto);
        return "updateForm";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("memberDto") MemberDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "updateForm";
        }

        memberService.insert(dto);

//        //1. dto를 entity로 변경
//        Member member = dto.fromMemberDto(dto);
//
//        //2. 저장
//        memberRepository.save(member);
        return "redirect:/member/show";
    }

    // 삭제처리
    @PostMapping("/delete")
    private String delete(@RequestParam("deleteId")Long id){
        memberService.delete(id);
//        log.info("삭제할 아이디 : " + id);
//        memberRepository.deleteById(id);
        return "redirect:/member/show";
    }

    // 검색 처리
    @GetMapping("/search")
    public String searchMember(@RequestParam("type")String type, @RequestParam("keyword")String keyword, Model model){
//        log.info("type = " + type + ", keyword = " + keyword);

        List<MemberDto> memberDtoList = memberService.searchMember(type, keyword);

//        List<MemberDto> memberDtoList = new ArrayList<>();
//        switch (type){
//            case "name":
//                // 이름으로 데이터베이스 검색
//                memberDtoList = memberRepository.searchName(keyword)
//                        .stream()
//                        .map(x->MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//            case "address":
//                // 주소로 데이터베이스 검색
//                memberDtoList = memberRepository.searchAddress(keyword)
//                        .stream()
//                        .map(x->MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//            default:
//                // 전체 검색
//                memberDtoList = memberRepository.searchQuery()
//                        .stream()
//                        .map(x->MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//        }
        model.addAttribute("memberDto", memberDtoList);
        return "showMember";
    }
}
