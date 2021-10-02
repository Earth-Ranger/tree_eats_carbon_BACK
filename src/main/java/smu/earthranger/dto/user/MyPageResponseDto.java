package smu.earthranger.dto.user;


import lombok.Builder;
import lombok.Getter;
import smu.earthranger.domain.Member;

@Getter
public class MyPageResponseDto {

    private String name;
    private String email;
    private int follower;
    private int following;

    @Builder
    public MyPageResponseDto(String name, String email, int follower, int following) {
        this.name = name;
        this.email = email;
        this.follower = follower;
        this.following = following;
    }

    public MyPageResponseDto(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.follower = member.getFollowers().size();
        this.following = member.getFollowings().size();
    }
}