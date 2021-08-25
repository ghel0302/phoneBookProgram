package kr.or.mrhi.sixclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 데이터베이스에 관련된 내용을 여기에서 주관한다. (입력) -> (실행결과) ->(실행결과를 리턴받아서 UI에서 보여준다.)
public class DBController {

	// 1.전화번호부 입력하기
	public static int phoneBookInsertTBL(PhoneBook phoneBook) {
		Connection con = DBUtility.getConnection();
		String insertQuery = "insert into phonebooktbl values(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, phoneBook.getPhoneNumber());
			preparedStatement.setString(2, phoneBook.getName());
			preparedStatement.setString(3, phoneBook.getGender());
			preparedStatement.setString(4, phoneBook.getJob());
			preparedStatement.setString(5, phoneBook.getBirthDate());
			preparedStatement.setInt(6, phoneBook.getAge());

			// 테이블의 값을 줄땐 excuteUpdate
			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// 2.전화번호부 검색하기
	public static List<PhoneBook> phoneBookSearchTBL(String searchData, int searchNumber) {

		List<PhoneBook> list = new ArrayList<PhoneBook>();

		// 데이터베이스에 연결
		Connection con = DBUtility.getConnection();
		String searchQuery = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			switch (searchNumber) {
			case 1:
				searchQuery = "select * from phonebooktbl where phoneNumber like ?";
				break;
			case 2:
				searchQuery = "select * from phonebooktbl where name like ?";
				break;
			case 3:
				searchQuery = "select * from phonebooktbl where gender like ?";
				break;
			default:
				System.out.println("다시 입력하세요.");
				return list;
			}

			preparedStatement = con.prepareStatement(searchQuery);
			searchData = "%" + searchData + "%";
			preparedStatement.setString(1, searchData);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				return list;
			}

			while (resultSet.next()) {
				String phoneNumber = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String job = resultSet.getString(4);
				String birthday = resultSet.getString(5);
				int age = resultSet.getInt(6);

				PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
				list.add(phoneBook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}

		return list;
	}
	
	// 3.전화번호부 삭제하기
	public static int phoneBookDeleteTBL(String name) {
		// 데이터베이스에 연결
				Connection con = DBUtility.getConnection();
				String deletetQuery = "delete from phoneBookTBL where name like ?";
				PreparedStatement preparedStatement = null;
				int count = 0;
				
				try {
					preparedStatement = con.prepareStatement(deletetQuery);
					String strName = "%" + name + "%";
					preparedStatement.setString(1, strName);
					count = preparedStatement.executeUpdate();

					

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (preparedStatement != null && !preparedStatement.isClosed()) {
							preparedStatement.close();
						}
						if (con != null && !con.isClosed()) {
							con.close();
						}
					} catch (SQLException e) {
					}
				}
		return count;
	}

	// 4.전화번호부 수정하기
	public static int phoneBookUpdateTBL(String phoneNumber, String name) {
		// 데이터베이스에 연결
				Connection con = DBUtility.getConnection();
				String updatetQuery = "update phoneBookTBL set name = ? where phoneNumber = ?";
				PreparedStatement preparedStatement = null;
				int count = 0;
				

				try {
					preparedStatement = con.prepareStatement(updatetQuery);
					String strName = "%" + name + "%";
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, phoneNumber);
					count = preparedStatement.executeUpdate();

					

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (preparedStatement != null && !preparedStatement.isClosed()) {
							preparedStatement.close();
						}
						if (con != null && !con.isClosed()) {
							con.close();
						}
					} catch (SQLException e) {
					}
				}
		return count;
	}
	
	// 5.전화번호부 출력하기
	public static List<PhoneBook> phoneBookSelectTBL() {
		
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		
		//DB연결
		Connection con = DBUtility.getConnection();
		String selectQuery = "select * from phoneBookTBL";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = con.prepareStatement(selectQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String phoneNumber = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String job = resultSet.getString(4);
				String birthday = resultSet.getString(5);
				int age = resultSet.getInt(6);

				PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
				list.add(phoneBook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return list;
	}

}
