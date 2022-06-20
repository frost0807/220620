package viewer;

import java.util.Scanner;
import model.UserDTO;
import controller.UserController;
import util.UtilScanner;

public class UserViewer {
	private Scanner sc;
	private UserController userController;
	private UserDTO logIn;
	private BoardViewer boardViewer;
	
	
	public UserViewer() {
		sc=new Scanner(System.in);
		userController=new UserController();
	}
	public UserViewer(Scanner sc) {
		userController=new UserController();
		this.sc=sc;
	}
	public void setBoardViewer(BoardViewer boardViewer) {
		this.boardViewer=boardViewer;
	}
	public void printNickname(int id) {
		UserDTO u=userController.selectOne(id);
		System.out.println(u.getNickname());
	}
	public void showIndex() {
		while(true) {
			System.out.println("1.로그인 2.회원가입 3.종료");
			int userChoice=UtilScanner.nextInt(sc, 1, 3);
			
			if(userChoice==1) {
				logIn();
				if(logIn!=null) {
					boardViewer.setLogIn(logIn);
					showMenu();
				}
			} else if(userChoice==2) {
				register();
			} else if(userChoice==3) {
				System.out.println("사용해주셔서 감사합니다.");
				break;
			}
		}
	}
	private void showMenu() {
		while(logIn!=null) {
			System.out.println("1.게시판 2.회원정보 상세보기 3.로그아웃");
			int userChoice=UtilScanner.nextInt(sc, 1, 3);
			if(userChoice==1) {
				boardViewer.showMenu();
			} else if(userChoice==2) {
				printOne();
			} else if(userChoice==3) {
				System.out.println("로그아웃 되셨습니다.");
				logIn=null;
			}
		}
	}
	private void printOne() {
		System.out.println("회원 username:"+logIn.getUsername());
		System.out.println("회원 nickname:"+logIn.getNickname());
		System.out.println("1.정보 수정 2.회원 탈퇴 3.뒤로 가기");
		int userChoice=UtilScanner.nextInt(sc, 1, 3);
		if(userChoice==1) {
			update();
		} else if(userChoice==2) {
			delete();
		}
	}
	private void update() {
		System.out.println("새로운 비밀번호를 입력해주세요");
		String password=UtilScanner.nextLine(sc);
		System.out.println("새로운 닉네임을 입력해주세요");
		String nickname=UtilScanner.nextLine(sc);
		
		System.out.println("기존 비밀번호를 입력해주세요");
		String oldpassword=UtilScanner.nextLine(sc);
		if(logIn.getPassword().equals(oldpassword)) {
			logIn.setPassword(password);
			logIn.setNickname(nickname);
			userController.update(logIn);
		}
		printOne();
	}
	private void delete() {
		System.out.println("정말로 탈퇴하시겠습니까? Y/N");
		String yOrN=UtilScanner.nextLine(sc);
		if(yOrN.equalsIgnoreCase("y")) {
			System.out.println("비밀번호를 입력해주세요");
			String oldPassword=UtilScanner.nextLine(sc);
			if(oldPassword.equals(logIn.getPassword())) {
				userController.delete(logIn.getId());
				boardViewer.deleteByWriterId(logIn.getId());
				logIn=null;
			}
		}
		if(logIn!=null) {
			printOne();
		}
	}
	private void logIn() {
		System.out.println("아이디를 입력해주세요");
		String username=UtilScanner.nextLine(sc);
		System.out.println("비밀번호를 입력해주세요");
		String password=UtilScanner.nextLine(sc);
		
		while(userController.auth(username, password)==null) {
			System.out.println("잘못 입력하셨습니다.");
			System.out.println("로그인을 그만 하시겠습니까? Y/N");
			String yOrN=UtilScanner.nextLine(sc);
			if(yOrN.equalsIgnoreCase("y")) {
				password=null;
				break;
			} else {
				System.out.println("아이디를 입력해주세요");
				username=UtilScanner.nextLine(sc);
				System.out.println("비밀번호를 입력해주세요");
				password=UtilScanner.nextLine(sc);
			}
		}
		logIn=userController.auth(username, password);
	}
	private void register() {
		System.out.println("사용하실 아이디를 입력해주시거나 뒤로가시려면 X를 입력해주세요");
		String username=UtilScanner.nextLine(sc);
		while(!username.equalsIgnoreCase("X")&&userController.validateUsername(username)) {
			System.out.println("잘못 입력하셨습니다.");
			System.out.println("사용하실 아이디를 입력해주시거나 뒤로가시려면 X를 입력해주세요");
			username=UtilScanner.nextLine(sc);
		}
		if(!username.equalsIgnoreCase("X")) {
			System.out.println("사용하실 비밀번호를 입력해주세요");
			String password=UtilScanner.nextLine(sc);
			System.out.println("사용하실 닉네임을 입력해주세요");
			String nickname=UtilScanner.nextLine(sc);
			UserDTO u=new UserDTO();
			u.setUsername(username);
			u.setPassword(password);
			u.setNickname(nickname);
			
			userController.insert(u);
		}
	}
}
