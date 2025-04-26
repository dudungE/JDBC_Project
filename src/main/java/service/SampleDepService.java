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


//    public int updateGift(String name, int gStart, int gEnd, int gno) {
//        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
//        // 예: 유효성 검사, 값 체크 등
//        return sampleGiftDao.updateGift(name, gStart, gEnd, gno);
//    }
//
//    /**
//     * 상품 생성을 처리
//     *
//     * @param sampleGiftDto 상품 이름
//     */
//    public int insertGift(SampleGiftDto sampleGiftDto) {
//        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
//        // 예: 유효성 검사, 값 체크 등
//        if (sampleGiftDto.getName() == null || sampleGiftDto.getName().isBlank()) {
//            System.out.println("❌ 상품 이름이 비어있습니다.");
//            return 0;
//        }
//
//        return sampleGiftDao.insertGift(sampleGiftDto);
//    }

    /**
     * 상품 조회를 처리
     *
     * @param
     */
    public List<SampleDepDto> getAllDeps() {
        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
        // 예: 유효성 검사, 값 체크 등
        return sampleDepDao.getAllDeps();
    }

    public int insertDep(SampleDepDto sampleDepDto) {
        // 비즈니스 로직을 이곳에서 추가할 수 있습니다.
        // 예: 유효성 검사, 값 체크 등
//        if (sampleDepDto.getDeptno() == 0) {
//            System.out.println("❌ 상품 이름이 비어있습니다.");
//            return 0;
//        }

        return sampleDepDao.insertDep(sampleDepDto);
    }

    public  String checkSampleDtoName(String dname) {
        String input = dname; // 테스트할 문자열

        // 정규표현식: 대문자만 5글자
        String pattern = RegexPattern.DEPT_DNAME_CHECK;

        if (input.matches(pattern)) {
            String result = input.toLowerCase(); // 소문자로 변환
            return result;
        } else {
            System.out.println("입력값이 대문자 5글자가 아닙니다.");
            return "-1";
        }
    }


}

