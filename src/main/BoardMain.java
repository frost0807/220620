package main;

import java.util.Scanner;
import viewer.UserViewer;
import viewer.BoardViewer;
import viewer.ReplyViewer;
//1. 학생관리 시스템을 MVC패턴으로 구축하시오
//2. 사용자가 관리 시스템을 만드시오
//단, 회원가입시 동일한 아이디는 만들 수 없습니다.
//3. 사원 관리 시스템을 MVC패턴으로 구축하시오
public class BoardMain {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		BoardViewer boardViewer=new BoardViewer(sc);
		UserViewer userViewer=new UserViewer(sc);
		ReplyViewer replyViewer=new ReplyViewer(sc);
		
		boardViewer.setUserViewer(userViewer);
		boardViewer.setReplyViewer(replyViewer);
		userViewer.setBoardViewer(boardViewer);
		replyViewer.setUserViewer(userViewer);
		userViewer.showIndex();
	}
}