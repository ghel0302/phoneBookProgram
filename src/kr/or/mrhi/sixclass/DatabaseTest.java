package kr.or.mrhi.sixclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DatabaseTest {
	// MYSQL DRIVER && URL definition

	public static final Scanner scan = new Scanner(System.in);
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, SHOWTB = 5, FINISH = 6;

	public static void main(String[] args) throws Exception {

//      Connection con = null;
//      con = DButil.getConnection();
//      if(con != null) {
//         System.out.println("DB연결 성공");
//      }
		boolean flag = false;
		int selectNumber = 0;

		while (!flag) {
			// 메뉴선택
			selectNumber = displayMenu();

			switch (selectNumber) {
			case INSERT:
				phoneBookInsert(); // insert information
				break;
			case SEARCH:
				phoneBookSearch(); // search information
				break;
			case DELETE:
				phoneBookDelete(); // delete information
				break;
			case UPDATE:
				phoneBookUpdate(); // update information
				break;
			case SHOWTB:
				phoneBookSelect(); // update information
				break;
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("숫자범위초과");
				break;
			}

		} // end of while
		System.out.println("프로그램 종료");

	}
	
	// 전화번호부 출력하기
	private static void phoneBookSelect() {
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		
		list = DBController.phoneBookSelectTBL();
		
		if(list.size()<=0) {
			System.out.println("출력할 데이터가 없습니다.");
			return;
		}
		
		for(PhoneBook pb : list) {
			System.out.println(pb.toString());
		}
		
	}

	// 전화번호부 수정하기
	private static void phoneBookUpdate() {
		// 전화번호부를 물어봐서 해당된 레코드를 보여준다.
		System.out.println("수정할 사람 전화번호부 입력");
		String phoneNumber = scan.nextLine();
		
		System.out.println("수정할 이름 입력");
		String name = scan.nextLine();

		int count = DBController.phoneBookUpdateTBL(phoneNumber,name);
		
		if (count != 0) {
			System.out.println(phoneNumber + "의 이름 수정 성공.");
		} else {
			System.out.println(phoneNumber + "의 이름 수정 실패.");
		}

	}
	
	// 전화번호부 삭제하기
	private static void phoneBookDelete() {
		System.out.println("삭제할 이름 입력 >> ");
		String name = scan.nextLine();

		int count = DBController.phoneBookDeleteTBL(name);
		
		if (count == 1) {
			System.out.println(name + "님 삭제 성공.");
		} else {
			System.out.println(name + "님 삭제 실패.");
		}
		
	}

	//전화번호부 검색하기
	private static void phoneBookSearch() {
		// 검색할 내용 입력받기 (검색할 조건을 선택해서 줄 수 있다.)
		final int PHONE = 1, NAME = 2, GENDER = 3, EXIT =4;
		
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		boolean flag = false;
		String searchData = null;
		int searchNumber = 0;
		

		while (!flag) {
			int selectNumber = displaySearchMenu();

			switch (selectNumber) {
			case PHONE:
				System.out.println("번호 입력 >> ");
				searchData = scan.nextLine();
				searchNumber = PHONE;
				break;
			case NAME:
				System.out.println("이름 입력 >> ");
				searchData = scan.nextLine();
				searchNumber = NAME;
				break;
			case GENDER :
				System.out.println("성별 입력 >> ");
				searchData = scan.nextLine();
				searchNumber = GENDER;
				break;
			case EXIT :
				//함수 종료
				return;
			default:
				System.out.println("다시 입력하세요.");
				continue;
			}
			flag = true;
		}
		list = DBController.phoneBookSearchTBL(searchData, searchNumber);
		
		if(list.size() <= 0) {
			System.out.println("검색 오류거나 찾을 데이터가 없습니다.");
			return;
		}
		
		for(PhoneBook pb : list) {
			System.out.println(pb);
		}
	}
	
	//전화번호부 삽입하기
	private static void phoneBookInsert() {
		String phoneNumber = null;
		String name = null;
		String gender = null;
		String job = null;
		String birthDate = null;
		int age = 0;

		while (true) {
			System.out.println("내용을 입력하세요(000-0000-0000) >>");
			phoneNumber = scan.nextLine();
			if (phoneNumber.length() != 13) {
				System.out.println("형식에 맞게 입력하세요");
				continue;
			} 
			
			boolean checkPhoneNumber = duplicatePhoneNumberCheck(phoneNumber);
			if(checkPhoneNumber == true) {
				System.out.println("존재하는 핸드폰 번호입니다. 다시 입력해주세요.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.println("이름을 입력하세요(공백없이 입력)");
			name = scan.nextLine();
			if (name.length() < 2 || name.length() > 7) {
				System.out.println("형식에 맞게 입력하세요");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("성별을 입력하세요(남자/여자)");
			gender = scan.nextLine();
			if (!(gender.equals("남자") || gender.equals("여자"))) {
				System.out.println("형식에 맞게 입력하세요");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("직업을 입력하세요(20글자 미만)");
			job = scan.nextLine();
			if (job.length() > 20 || job.length() < 1) {
				System.out.println("형식에 맞게 입력하세요");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("생년월일을 입력하세요(YYYYMMDD)");
			birthDate = scan.nextLine();
			if (birthDate.length() != 8) {
				System.out.println("형식에 맞게 입력하세요");
				continue;
			} else {
				int year = Integer.parseInt(birthDate.substring(0, 4));
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				age = currentYear - year + 1;
				break;
			}
		}

		PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthDate, age);
		
		int count = DBController.phoneBookInsertTBL(phoneBook);
		
		if (count == 1) {
			System.out.println("입력성공");
		} else {
			System.out.println("입력실패");
		}
		
		// DB에 연결
		
	}
	
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("===================================================");
			System.out.println("검색 선택 >>> 1. 번호 검색 2. 이름 검색 3. 성별 검색 4. 검색 나가기");
			System.out.println("===================================================");
			System.out.print("번호선택 >>  ");

			// 번호입력
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("입력 오류: 숫자만 입력 요망");
				continue;
			} catch (Exception e) {
				System.out.println("입력 오류: 입력 과정 오류");
				continue;
			}
			break;
		}
		return selectNumber;
	}

	private static int displayMenu() {

		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("==========================================");
			System.out.println("=> 1.입력 2.조회 3. 삭제 4. 수정 5. 출력 6. 종료");
			System.out.println("==========================================");
			System.out.print("번호선택 >>  ");

			// 번호입력
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("입력 오류: 숫자만 입력 요망");
				continue;
			} catch (Exception e) {
				System.out.println("입력 오류: 입력 과정 오류");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	//전화번호부 검색하기
		private static boolean duplicatePhoneNumberCheck(String phoneNumber) {
			// 검색할 내용 입력받기 (검색할 조건을 선택해서 줄 수 있다.)
			
			final int PHONE = 1;
			List<PhoneBook> list = new ArrayList<PhoneBook>();
			int searchNumber = PHONE;
			
			list = DBController.phoneBookSearchTBL(phoneNumber, searchNumber);
			
			if(list.size() >= 1 ) {
				return true;
			}
			return false;
		}
}