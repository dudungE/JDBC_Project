package view;

import controller.SampleDepController;
import model.dao.SampleDepDao;
import model.dto.SampleDepDto;
import java.util.Scanner;

public class Main {

    public  static  void menu() {
        System.out.println("1:C 2:R 3:U 4:D 0:exit");
    }
    public static void main(String[] args) {

        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        SampleDepController controller = new SampleDepController();

        // table initialize
        controller.initDep();

        while (flag) {
            menu();
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.println("deptno, dname, loc 입력");
                    int deptno = sc.nextInt();
                    String dname = sc.next();
                    String loc = sc.next();

                    SampleDepDto sampleDepDto = new SampleDepDto(deptno, dname, loc);
                    controller.insertDep(sampleDepDto);
                }
                case 2 -> {
                    controller.getAllDep();
                    System.out.println("전체 Dept 출력 완료 ");
                }
                case 3 -> {
                    System.out.println("[수정] deptno, dname, loc 입력 ");
                    int deptno = sc.nextInt();
                    String dname = sc.next();
                    String loc = sc.next();
                    controller.updateDep(deptno, dname, loc);

                }
                case 4 -> {
                    System.out.println("[삭제] deptno 입력");
                    int deptno = sc.nextInt();
                    controller.deleteDep(deptno);
                }
                case 0 -> {System.exit(0);}
                default -> System.out.println("번호 잘못 입력 ");
            }


        }



//        // 컨트롤러 객체 생성
//        SampleDepController controller = new SampleDepController();
//
//        // init 메서드 활용(초기화)
//        controller.initDep();
//
//        // Dto클래스 데이터 담아서 객체 생성
//        SampleDepDto sampleDepDto = new SampleDepDto(111, "AAA", "KOR");
//        SampleDepDto sampleDepDto1 = new SampleDepDto(112, "bbb", "KOR");
//        SampleDepDto sampleDepDto2 = new SampleDepDto(113, "ccc", "KOR");
//
//        // Dto객체 controller의 메서드에 담아서 실행
//        controller.insertDep(sampleDepDto);
//        controller.insertDep(sampleDepDto1);
//        controller.insertDep(sampleDepDto2);
////
//
////        System.out.println("hsdfgsdfgi");
//        controller.updateDep(111, "test", "seoul");
//
//        controller.deleteDep(111);
//
//
//        controller.getAllDep();

    }
}
