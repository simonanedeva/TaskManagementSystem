package com.company.oop.taskManagementSystemTests.core;

import com.company.oop.taskManagementSystem.core.TMSRepositoryImpl;
import com.company.oop.taskManagementSystem.models.BoardImpl;
import com.company.oop.taskManagementSystem.models.BugImpl;
import com.company.oop.taskManagementSystem.models.MemberImpl;
import com.company.oop.taskManagementSystem.models.TeamImpl;
import com.company.oop.taskManagementSystem.models.contracts.Board;
import com.company.oop.taskManagementSystem.models.contracts.Bug;
import com.company.oop.taskManagementSystem.models.contracts.Member;
import com.company.oop.taskManagementSystem.models.contracts.Team;
import com.company.oop.taskManagementSystemTests.utils.TaskConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TMSRepositoryImplTests {
    TMSRepositoryImpl tmsRepository;
    Member member;

    @BeforeEach
    public void beforeEach() {
        tmsRepository = new TMSRepositoryImpl();
        member = new MemberImpl(TaskConstants.VALID_MEMBER_NAME);
        tmsRepository.addMember(member);
    }

    @Test
    public void constructor_Should_initializeAllCollections() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(tmsRepository.getMembers()),
                () -> Assertions.assertNotNull(tmsRepository.getTeams())
        );
    }

    @Test
    public void should_AddMember_When_ValidInput() {
        Assertions.assertTrue(tmsRepository.memberExists(TaskConstants.VALID_MEMBER_NAME));
        Assertions.assertEquals(1, tmsRepository.getMembers().size());
    }
    @Test
    public void should_ThrowException_When_MemberAlreadyExists() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> tmsRepository.addMember(member));
    }

    @Test
    public void getMembers_Should_ReturnCopyOfCollection() {
        tmsRepository.getMembers().clear();
        Assertions.assertEquals(1, tmsRepository.getMembers().size());
    }

    @Test
    public void getTeams_Should_ReturnCopyOfCollection() {
        Team team = new TeamImpl("JavaHolics");
        tmsRepository.addTeam(team);
        tmsRepository.getTeams().clear();
        Assertions.assertEquals(1, tmsRepository.getMembers().size());
    }

    @Test
    public void findMemberByUsername_ShouldReturnUsername_When_UsernameExists() {
        Assertions.assertEquals(TaskConstants.VALID_MEMBER_NAME,
                tmsRepository.findMemberByUsername(TaskConstants.VALID_MEMBER_NAME).getUsername());
    }

    @Test
    public void findMemberByUsername_ShouldThrow_When_UsernameNotExist() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> tmsRepository.findMemberByUsername("Simona"));
    }

    @Test
    public void findTeamOfMember_ShouldReturnName_When_MemberIsPartOfTeam(){
        tmsRepository.login(this.member);
        Team team = new TeamImpl("Javaholics");
        tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        Assertions.assertEquals(team.getName(), tmsRepository.findTeamOfMember(member.getUsername()).getName());
    }

    @Test
    public void findTeamOfMember_ShouldThrow_When_MemberNotPartOfTeam() {
        tmsRepository.login(this.member);
        Team team = new TeamImpl("Javaholics");
        tmsRepository.addTeam(team);
        team.addMember(tmsRepository.getLoggedInMember());
        Member member2 = new MemberImpl("Simona");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> tmsRepository.findTeamOfMember(member2.getUsername()).getName());
    }

    @Test
    public void getLoggedInMember_shouldReturnTrue_When_MemberLogged() {
        tmsRepository.login(member);
        Assertions.assertEquals(member, tmsRepository.getLoggedInMember());
    }

    @Test
    public void getLoggedInMember_shouldThrow_When_NoMemberLogged() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> tmsRepository.getLoggedInMember());
    }

    @Test
    public void logout_ShouldLogoutMember_When_Prompted() {
        tmsRepository.login(member);
        Assertions.assertTrue(tmsRepository.hasLoggedInMember());
        tmsRepository.logout();
        Assertions.assertFalse(tmsRepository.hasLoggedInMember());
    }

}
