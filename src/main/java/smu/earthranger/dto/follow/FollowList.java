package smu.earthranger.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FollowList {

    private List<FollowResponseDto> followList;

    public FollowList(List<FollowResponseDto> followList) {
        this.followList = followList;
    }
}
