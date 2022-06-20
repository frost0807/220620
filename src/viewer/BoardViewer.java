package viewer;

import controller.BoardController;
import model.UserDTO;
import model.BoardDTO;
import util.UtilScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
//뷰어
//뷰어는 사용자가 직접적으로 보게 되는 화면단, 즉 프론트엔드가 된다.
//단, 지금 현재 우리는 console에서 프로그래밍을 하므로 뷰어가 java클래스가 된다.

//또한, 원칙적으로는 데이터베이스가 존재하기 때문에 우리가 필요하면 그 즉시 해당 Controller 객체를 생성해서
//해당 객체의 필요한 메소드를 실행시키는게 맞지만
//없다...
//그래서 어쩔 수 없이, BoardController
public class BoardViewer {
	private BoardController controller;
	private UserViewer userViewer; 
	private ReplyViewer replyViewer;
	private Scanner sc;
	private UserDTO logIn;
	
	public BoardViewer() {
		controller=new BoardController();
		sc=new Scanner(System.in);
	}
	public BoardViewer(Scanner sc) {
		controller=new BoardController();
		this.sc=sc;
	}
	public void setLogIn(UserDTO logIn) {
		this.logIn=logIn;
	}
	public void setUserViewer(UserViewer userViewer) {
		this.userViewer=userViewer;
	}
	public void setReplyViewer(ReplyViewer replyViewer) {
		this.replyViewer=replyViewer;
	}
	//게시글 관련 메뉴 메소드
	public void showMenu() {
		while(true) {
			System.out.println("1.새 글 작성 2.글 목록 보기 3.종료");
			int userChoice=UtilScanner.nextInt(sc);
			
			if(userChoice==1) {
				//새 글 작성 메소드 호출
				writeBoard();
			} else if(userChoice==2) {
				//글 목록 출력 메소드 호출
				printList();
			} else if(userChoice==3) {
				//메시지 출력 후 while 종료
				System.out.println("사용해주셔서 감사합니다.");
				break;
			}
		}
	}
	public void writeBoard() {
		BoardDTO b=new BoardDTO();
		
		System.out.println("글의 제목을 입력해주세요");
		b.setTitle(UtilScanner.nextLine(sc));
		
		b.setWriterId(logIn.getId());
		
		System.out.println("글의 내용을 입력해주세요");
		b.setContent(UtilScanner.nextLine(sc));
		
		controller.insert(b);
	}
	private void printList() {
		ArrayList<BoardDTO> list=controller.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("아직 등록된 글이 존재하지 않습니다.");
		} else {
			Collections.reverse(list);
			
			for(BoardDTO b:list) {
				System.out.printf("%d. %s\n",b.getId(),b.getTitle());
			}
			System.out.println("상세보기 할 글의 번호나 뒤로 가시려면 0을 입력해주세요");
			int userChoice=UtilScanner.nextInt(sc);
			
			while(userChoice!=0&&controller.selectOne(userChoice)==null) {
				System.out.println("잘못 입력하셨습니다.");
				userChoice=UtilScanner.nextInt(sc);
			}
			
			if(userChoice!=0) {
				printOne(userChoice);
			}
		}
	}
	private void printOne(int id) {
		BoardDTO b=controller.selectOne(id);

		System.out.println("============================================");
		System.out.println(b.getTitle());
		System.out.println("--------------------------------------------");
		System.out.println("글번호:"+b.getId());
		System.out.print("작성자:");
		userViewer.printNickname(b.getWriterId());
		System.out.println("--------------------------------------------");
		System.out.println(b.getContent());
		System.out.println("============================================");
		
		replyViewer.setLogin(logIn);
		replyViewer.printReplyList(id);
		
		if(b.getId()==logIn.getId()) {
			System.out.println("1.글수정 2.글삭제 3.댓글쓰기 4.댓글삭제 5.댓글수정 6.뒤로가기");
			int userChoice=UtilScanner.nextInt(sc);
		
			if(userChoice==1) {
				updateBoard(id);
			} else if(userChoice==2) {
				deleteBoard(id);
			} else if(userChoice==3) {
				replyViewer.writeReply(id);
			} else if(userChoice==4) {
				replyViewer.deleteReply(id);
			} else if(userChoice==5) {
				replyViewer.updateReply(id);
			} else if(userChoice==6) {
				printList();
			}
		} else {
			System.out.println("1.댓글쓰기 2.댓글삭제 3.댓글수정 4.뒤로가기");
			int userChoice=UtilScanner.nextInt(sc);
			if(userChoice==1) {
				replyViewer.setLogin(logIn);
				replyViewer.writeReply(id);
			} else if(userChoice==2) {
				replyViewer.setLogin(logIn);
				replyViewer.deleteReply(id);
			} else if(userChoice==3) {
				replyViewer.setLogin(logIn);
				replyViewer.updateReply(id);
			}
			printList();
		}
	}
	private void deleteBoard(int id) {
		System.out.println("정말로 삭제하시겠습니까?");
		String yOrN=UtilScanner.nextLine(sc);
		
		if(yOrN.equalsIgnoreCase("y")) {
			controller.delete(id);
			printList();
		} else {
			printOne(id);
		}
	}
	private void updateBoard(int id) {
		BoardDTO b=controller.selectOne(id);
		
		System.out.println("새로운 제목을 입력해주세요");
		b.setTitle(UtilScanner.nextLine(sc));
		
		System.out.println("새로운 내용을 입력해주세요");
		b.setContent(UtilScanner.nextLine(sc));
		
		controller.update(b);
		
		printOne(id);
	}
	public void deleteByWriterId(int writerId) {
		controller.deleteByWriterId(writerId);
	}
}