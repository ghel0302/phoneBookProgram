package kr.or.mrhi.sixclass;

import java.util.Objects;

public class PhoneBook {
	private String phoneNumber;
	   private String name;
	   private String gender;
	   private String job;
	   private String birthDate;
	   private int age;
	   
	   public PhoneBook(String phoneNumber, String name, String gender, String job, String birthDate, int age) {
	      this.phoneNumber = phoneNumber;
	      this.name = name;
	      this.gender = gender;
	      this.job = job;
	      this.birthDate = birthDate;
	      this.age = age;
	   }
	   
	   
	   
	   public String getPhoneNumber() {
	      return phoneNumber;
	   }



	   public void setPhoneNumber(String phoneNumber) {
	      this.phoneNumber = phoneNumber;
	   }



	   public String getName() {
	      return name;
	   }



	   public void setName(String name) {
	      this.name = name;
	   }



	   public String getGender() {
	      return gender;
	   }



	   public void setGender(String gender) {
	      this.gender = gender;
	   }



	   public String getJob() {
	      return job;
	   }



	   public void setJob(String job) {
	      this.job = job;
	   }



	   public String getBirthDate() {
	      return birthDate;
	   }



	   public void setBirthDate(String birthDate) {
	      this.birthDate = birthDate;
	   }



	   public int getAge() {
	      return age;
	   }



	   public void setAge(int age) {
	      this.age = age;
	   }



	   @Override
	   public boolean equals(Object obj) {
	      if(obj instanceof PhoneBook) {
	         PhoneBook phoneBook = (PhoneBook)obj;
	         return this.getPhoneNumber().equals(phoneBook.getPhoneNumber());
	      }
	      return false;
	   }
	   
	   @Override
	   public int hashCode() {
	      return Objects.hash(phoneNumber);
	   }



	   @Override
	   public String toString() {
	      String year = this.birthDate.substring(0,4);
	      String month = this.birthDate.substring(4,6);
	      String day = this.birthDate.substring(6);
	      String strbirthDate = year + "³â\t" + month + "¿ù\t" + day + "ÀÏ";
	      return  phoneNumber + "\t" + name + "\t" + gender + "\t" + job +  "\t" + strbirthDate + "\t" + age;
	   }	  
	   }