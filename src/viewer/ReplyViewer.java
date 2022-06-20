package viewer;

import model.ReplyDTO;
import controller.ReplyController;
import java.util.Scanner;
import model.UserDTO;
import util.UtilScanner;

public class ReplyViewer {
	private Scanner sc;
	private ReplyController controller;
	private UserViewer userViewer;
	private int nextId;
	private UserDTO logIn;
	public ReplyViewer(Scanner sc) {
		nextId=1;
		this.sc=sc;
		controller=new ReplyController();
	}
	public void setLogin(UserDTO logIn) {
		this.logIn=logIn;
	}
	public void setUserViewer(UserViewer userViewer) {
		this.userViewer=userViewer;
	}
	public void printReplyList(int boardId) {
		for(ReplyDTO r:controller.selectAll()) {
			if(r.getBoardId()==boardId) {
				System.out.print("댓글작성자:");
				userViewer.printNickname(r.getWriterId());
				System.out.println("댓글코드:"+r.getId());
				
				System.out.println("댓글내용:"+r.getContent());
				if(logIn.getId()==r.getWriterId()) {
					System.out.printf("%40s","[삭제&수정가능]\n");
				}
				System.out.println("--------------------------------------------");
			}
		}
	}
	public void writeReply(int boardId) {
		ReplyDTO r=new ReplyDTO();
		r.setBoardId(boardId);
		r.setWriterId(logIn.getId());
		System.out.println("댓글내용을 입력해주세요");
		r.setContent(UtilScanner.nextLine(sc));
		controller.insert(r);
	}
	public void deleteReply(int boardId) {
		System.out.println("삭제할 댓글의코드를 입력해주시거나 0을 입력해 뒤로가기");
		int userChoice=UtilScanner.nextInt(sc);
		if(userChoice!=0&&!controller.validOnThisBoard(userChoice,boardId)) {
			System.out.println("잘못된 입력입니다.");
		} else if(userChoice!=0) {
			System.out.println("정말로 삭제하시겠습니까? Y/N");
			String deleteChoice=UtilScanner.nextLine(sc);
			if(deleteChoice.equalsIgnoreCase("y")&&logIn.getId()==controller.selectOne(userChoice).getWriterId()) {
				controller.delete(userChoice);
				System.out.println("삭제가 완료되었습니다.");
			} else if(deleteChoice.equalsIgnoreCase("y")) {
				System.out.println("권한이 없습니다.");
			}
		}
	}
	public void updateReply(int boardId) {
		System.out.println("수정할 댓글의코드를 입력해주시거나 0을 입력해 뒤로가기");
		int userChoice=UtilScanner.nextInt(sc);
		if(userChoice!=0&&!controller.validOnThisBoard(userChoice,boardId)) {
			System.out.println("잘못된 입력입니다.");
		} else if(userChoice!=0) {
			if(logIn.getId()==controller.selectOne(userChoice).getWriterId()) {
				System.out.println("새로운 내용을 입력해주세요");
				ReplyDTO temp=controller.selectOne(userChoice);
				temp.setContent(UtilScanner.nextLine(sc));
				controller.update(temp);
			} else {
				System.out.println("권한이 없습니다.");
			}
		}
	}
}
