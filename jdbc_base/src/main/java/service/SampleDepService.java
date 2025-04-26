package service;

import model.dao.SampleDepDao;
import model.dto.SampleDepDto;
import util.RegexPattern;

import java.util.List;

public class SampleDepService {
    private final SampleDepDao sampleDepDao;

    public SampleDepService() {
        this.sampleDepDao = new SampleDepDao();
    }


    // data init method
    public void initDep() {
        sampleDepDao.initDep();
    }

    // C
    public int insertDep(SampleDepDto sampleDepDto) {
        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
        // 예: 유효성 검사, 값 체크 등
//        if (sampleDepDto.getDeptno() == 0) {
//            System.out.println("❌ 상품 이름이 비어있습니다.");
//            return 0;
//        }

        return sampleDepDao.insertDep(sampleDepDto);
    }

    // R
    public List<SampleDepDto> getAllDeps() {
        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
        // 예: 유효성 검사, 값 체크 등
        return sampleDepDao.getAllDeps();
    }

    // U
    public int updateDep(int updateDeptno, String updateDname, String updateLoc) {
        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
        // 예: 유효성 검사, 값 체크 등
        return sampleDepDao.updateDep(updateDeptno, updateDname, updateLoc);
    }


    // D
    public int deleteDep(int deleteDeptno) {
        return sampleDepDao.deleteDep(deleteDeptno);
    }





    // util method
    public  String checkSampleDtoName(String dname) {
        String input = dname; // 테스트할 문자열

        // 정규표현식: 대문자만 5글자
        String pattern = RegexPattern.DEPT_DNAME_CHECK;

        if (input.matches(pattern)) {
            String result = input.toLowerCase(); // 소문자로 변환
            return result;
        } else {
            System.out.println("입력형식이 잘못됐습니다(대소문자 10자리)");
            return "-1";
        }
    }


}

