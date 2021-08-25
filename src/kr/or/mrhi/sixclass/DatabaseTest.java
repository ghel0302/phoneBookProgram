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
//         System.out.println("DB���� ����");
//      }
		boolean flag = false;
		int selectNumber = 0;

		while (!flag) {
			// �޴�����
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
				System.out.println("���ڹ����ʰ�");
				break;
			}

		} // end of while
		System.out.println("���α׷� ����");

	}
	
	// ��ȭ��ȣ�� ����ϱ�
	private static void phoneBookSelect() {
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		
		list = DBController.phoneBookSelectTBL();
		
		if(list.size()<=0) {
			System.out.println("����� �����Ͱ� �����ϴ�.");
			return;
		}
		
		for(PhoneBook pb : list) {
			System.out.println(pb.toString());
		}
		
	}

	// ��ȭ��ȣ�� �����ϱ�
	private static void phoneBookUpdate() {
		// ��ȭ��ȣ�θ� ������� �ش�� ���ڵ带 �����ش�.
		System.out.println("������ ��� ��ȭ��ȣ�� �Է�");
		String phoneNumber = scan.nextLine();
		
		System.out.println("������ �̸� �Է�");
		String name = scan.nextLine();

		int count = DBController.phoneBookUpdateTBL(phoneNumber,name);
		
		if (count != 0) {
			System.out.println(phoneNumber + "�� �̸� ���� ����.");
		} else {
			System.out.println(phoneNumber + "�� �̸� ���� ����.");
		}

	}
	
	// ��ȭ��ȣ�� �����ϱ�
	private static void phoneBookDelete() {
		System.out.println("������ �̸� �Է� >> ");
		String name = scan.nextLine();

		int count = DBController.phoneBookDeleteTBL(name);
		
		if (count == 1) {
			System.out.println(name + "�� ���� ����.");
		} else {
			System.out.println(name + "�� ���� ����.");
		}
		
	}

	//��ȭ��ȣ�� �˻��ϱ�
	private static void phoneBookSearch() {
		// �˻��� ���� �Է¹ޱ� (�˻��� ������ �����ؼ� �� �� �ִ�.)
		final int PHONE = 1, NAME = 2, GENDER = 3, EXIT =4;
		
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		boolean flag = false;
		String searchData = null;
		int searchNumber = 0;
		

		while (!flag) {
			int selectNumber = displaySearchMenu();

			switch (selectNumber) {
			case PHONE:
				System.out.println("��ȣ �Է� >> ");
				searchData = scan.nextLine();
				searchNumber = PHONE;
				break;
			case NAME:
				System.out.println("�̸� �Է� >> ");
				searchData = scan.nextLine();
				searchNumber = NAME;
				break;
			case GENDER :
				System.out.println("���� �Է� >> ");
				searchData = scan.nextLine();
				searchNumber = GENDER;
				break;
			case EXIT :
				//�Լ� ����
				return;
			default:
				System.out.println("�ٽ� �Է��ϼ���.");
				continue;
			}
			flag = true;
		}
		list = DBController.phoneBookSearchTBL(searchData, searchNumber);
		
		if(list.size() <= 0) {
			System.out.println("�˻� �����ų� ã�� �����Ͱ� �����ϴ�.");
			return;
		}
		
		for(PhoneBook pb : list) {
			System.out.println(pb);
		}
	}
	
	//��ȭ��ȣ�� �����ϱ�
	private static void phoneBookInsert() {
		String phoneNumber = null;
		String name = null;
		String gender = null;
		String job = null;
		String birthDate = null;
		int age = 0;

		while (true) {
			System.out.println("������ �Է��ϼ���(000-0000-0000) >>");
			phoneNumber = scan.nextLine();
			if (phoneNumber.length() != 13) {
				System.out.println("���Ŀ� �°� �Է��ϼ���");
				continue;
			} 
			
			boolean checkPhoneNumber = duplicatePhoneNumberCheck(phoneNumber);
			if(checkPhoneNumber == true) {
				System.out.println("�����ϴ� �ڵ��� ��ȣ�Դϴ�. �ٽ� �Է����ּ���.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.println("�̸��� �Է��ϼ���(������� �Է�)");
			name = scan.nextLine();
			if (name.length() < 2 || name.length() > 7) {
				System.out.println("���Ŀ� �°� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("������ �Է��ϼ���(����/����)");
			gender = scan.nextLine();
			if (!(gender.equals("����") || gender.equals("����"))) {
				System.out.println("���Ŀ� �°� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("������ �Է��ϼ���(20���� �̸�)");
			job = scan.nextLine();
			if (job.length() > 20 || job.length() < 1) {
				System.out.println("���Ŀ� �°� �Է��ϼ���");
				continue;
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("��������� �Է��ϼ���(YYYYMMDD)");
			birthDate = scan.nextLine();
			if (birthDate.length() != 8) {
				System.out.println("���Ŀ� �°� �Է��ϼ���");
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
			System.out.println("�Է¼���");
		} else {
			System.out.println("�Է½���");
		}
		
		// DB�� ����
		
	}
	
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("===================================================");
			System.out.println("�˻� ���� >>> 1. ��ȣ �˻� 2. �̸� �˻� 3. ���� �˻� 4. �˻� ������");
			System.out.println("===================================================");
			System.out.print("��ȣ���� >>  ");

			// ��ȣ�Է�
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("�Է� ����: ���ڸ� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("�Է� ����: �Է� ���� ����");
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
			System.out.println("=> 1.�Է� 2.��ȸ 3. ���� 4. ���� 5. ��� 6. ����");
			System.out.println("==========================================");
			System.out.print("��ȣ���� >>  ");

			// ��ȣ�Է�
			try {
				selectNumber = Integer.parseInt(scan.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("�Է� ����: ���ڸ� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("�Է� ����: �Է� ���� ����");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	//��ȭ��ȣ�� �˻��ϱ�
		private static boolean duplicatePhoneNumberCheck(String phoneNumber) {
			// �˻��� ���� �Է¹ޱ� (�˻��� ������ �����ؼ� �� �� �ִ�.)
			
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