package com.example.basiccrud.controller;

import com.example.basiccrud.dto.MemberDto;
import com.example.basiccrud.entity.Member;
import com.example.basiccrud.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> showAllMembers() {
        List<MemberDto> memberDtoList = new ArrayList<>();
        return memberRepository.findAll()
                .stream()
                .map(x->MemberDto.fromMemberEntity(x))
                .toList();
    }

    public void insertMember(MemberDto dto) {
        Member member = dto.fromMemberDto(dto);
        memberRepository.save(member);
    }

    public MemberDto getOneMember(Long id) {
        return memberRepository.findById(id)
                .map(x -> MemberDto.fromMemberEntity(x))
                .orElse(null);
    }

    public void insert(MemberDto dto) {
        //1. dto를 entity로 변경
        Member member = dto.fromMemberDto(dto);

        //2. 저장
        memberRepository.save(member);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public List<MemberDto> searchMember(String type, String keyword) {
        List<MemberDto> memberDtoList = new ArrayList<>();
        switch (type){
            case "name":
                // 이름으로 데이터베이스 검색
                memberDtoList = memberRepository.searchName(keyword)
                        .stream()
                        .map(x->MemberDto.fromMemberEntity(x))
                        .toList();
                break;
            case "address":
                // 주소로 데이터베이스 검색
                memberDtoList = memberRepository.searchAddress(keyword)
                        .stream()
                        .map(x->MemberDto.fromMemberEntity(x))
                        .toList();
                break;
            default:
                // 전체 검색
                memberDtoList = memberRepository.searchQuery()
                        .stream()
                        .map(x->MemberDto.fromMemberEntity(x))
                        .toList();
                break;
        }

        return memberDtoList;
    }
}
