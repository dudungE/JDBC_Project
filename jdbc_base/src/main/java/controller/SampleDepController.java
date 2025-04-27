package controller;

import model.dto.SampleDepDto;
import service.SampleDepService;

import java.util.List;

public class SampleDepController {

    private final SampleDepService sampleDepService;


    // Controller 생성자로 Service 참조변수에 객체 참조
    public SampleDepController() {
        this.sampleDepService = new SampleDepService();
    }

    // 데이터 init
    public void initDep() {
        sampleDepService.initDep();
    }



    // C
    public void insertDep(SampleDepDto sampleDepDto) {

        String check = sampleDepService.checkSampleDtoName(sampleDepDto.getDname());
        if (check.equals("-1")) {
            System.out.println("다시 입력하세요");
        } else {
            int result = sampleDepService.insertDep(sampleDepDto);
            if (result == 1) {
                System.out.println(sampleDepDto.getDeptno()+ ": 🎉 성공적으로 등록되었습니다!");
            } else if (result == 0) {
                System.out.println("⚠️ 등록에 실패했습니다. 다시 시도해보세요.");
            } else if (result == -1) {
                System.out.println("❌ 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        }
    }

    // R
    public void getAllDep() {
        List<SampleDepDto> deptList = sampleDepService.getAllDeps();
        deptList.forEach(g -> System.out.printf("%d %s %s\n",
                g.getDeptno(), g.getDname(), g.getLoc()));

    }

    // U
    public void updateDep(int deptno, String dname, String loc) {
        int result = sampleDepService.updateDep(deptno, dname, loc);
        if (result == 1) {
            System.out.println(deptno+ ": 성공적으로 수정!");
        } else if (result == 0) {
            System.out.println("수정 실패!");
        } else if (result == -1) {
            System.out.println("수정실패 - 오류 발생!");
        }

    }

    // D
    public void deleteDep(int deptno) {
        int result = sampleDepService.deleteDep(deptno);
        if (result == 1) {
            System.out.println(deptno + "번 삭제성공!");
        } else if (result == 0) {
            System.out.println("삭제 실패!");
        } else if (result == -1) {
            System.out.println("삭제실패 - 오류 발생!");
        }
    }




}
