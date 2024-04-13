package kernel.jdon.modulecrawler.domain.jd.core.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WantedJobListResponse {

    private List<Data> data;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Data {
        private Long id;
    }
}
