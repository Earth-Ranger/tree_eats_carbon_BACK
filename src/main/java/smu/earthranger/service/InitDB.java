package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Info;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
        //initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit(){
            createInfo("여름철에는 25~28도, 겨울철에는 18~20도 정도만 유지하면 연간 탄소배출 166.8㎏을 줄일 수 있어요!");
            createInfo("대기전력만 최대한 차단해도 연간 81.5㎏의 탄소배출을 줄일 수 있어요!");
            createInfo("샤워 시간을 1분을 줄이면 7kg의 CO2를 줄일 수 있어요!");
            createInfo("쓰레기를 줄이고 재활용 쓰레기는 반드시 분리수거를 한다면, 탄소배출을 줄일 수 있어요!");
            createInfo("소나무 1그루는 연간 5kg의 CO2를 흡수하므로 숲을 함부로 훼손하는 일은 없도록 해요!");
            createInfo("승용차를 사용하게 될 경우에는 에코드라이빙 습관을 유지해보세요! (급가속, 급출발, 급정거 금지)");
            createInfo("음식물 쓰레기를 20% 줄이면 한 달 기준 3.02kg의 온실가스를 감축시킬 수 있어요!");
            createInfo("개인정보 보호모드(시크릿모드)를 사용하게 되면 불필요한 트래픽이 발생하지 않아 탄소발자국을 줄일 수 있어요!");
            createInfo("물을 받아놓고 양치나 세안을 하는 등 물을 절약하면 배출되는 탄소를 줄일 수 있어요!");
            createInfo("필터를 청결하게 유지하는 것만으로도 탄소 배출량을 줄일 수 있어요!");
        }

        private Info createInfo(String content) {
            Info info = Info.builder()
                    .content(content)
                    .build();

            em.persist(info);
            return info;
        }
    }

}


