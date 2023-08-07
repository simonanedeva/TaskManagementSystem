package com.company.oop.taskManagementSystem.core.contracts;

import com.company.oop.taskManagementSystem.models.contracts.Member;

import java.util.List;

public interface TMSRepository {
    List<Member> getMembers();

    Member getLoggedInMember();

    void addMember(Member memberToAdd);

    Member findMemberByUsername(String username);

    boolean hasLoggedInMember();

    void login(Member member);

    void logout();

    //TODO UserImpl ++ квото друго има покрай него
}
