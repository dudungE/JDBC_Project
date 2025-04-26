package controller;

import model.dto.SampleDepDto;
import service.SampleDepService;

import java.util.List;

public class SampleDepController {

    private final SampleDepService sampleDepService;

    public SampleDepController() {
        this.sampleDepService = new SampleDepService();
    }


    // 전체 상품 조회
    public void getAllDep() {
        List<SampleDepDto> giftList = sampleDepService.getAllDeps();
        giftList.forEach(g -> System.out.printf("%d %s %s\n",
                g.getDeptno(), g.getDname(), g.getLoc()));

    }

    // 상품 생성 처리
    public void insertDep(SampleDepDto sampleDepDto) {

        String check = sampleDepService.checkSampleDtoName(sampleDepDto.getDname());
        if (check.equals("-1")) {
            System.out.println("다시 입력하세요");
        } else {
            int result = sampleDepService.insertDep(sampleDepDto);
            if (result == 1) {
                System.out.println("🎉 성공적으로 등록되었습니다!");
            } else if (result == 0) {
                System.out.println("⚠️ 등록에 실패했습니다. 다시 시도해보세요.");
            } else if (result == -1) {
                System.out.println("❌ 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        }
    }


}
