package model;
//모델
//모델 클래스들은 데이터의 틀을 담당하는 클래스이다.
//모델 클래스 안에는 어떤 데이터가 저장이 될지(=필드),
//겟터/셋터
//그리고 java.lang.Object의 오버라이드 할 메소드만 들어가게 된다.

//DTO VS VO
//Data Transfer Object:데이터 전송 객체-> 데이터베이스로부터 받은 데이터를
//객체에 담아서 사용자에게 보내줄 때 쓰는 틀=사용자가 입력한 데이터를
//객체에 담아서 데이터베이스로 보내줄 때 쓰는 틀
//Value Object:값 객체->데이터베이스로부터 받은 데이터를 객체에 담아서 사용자에게 보내줄 떄 쓰는 틀
public class BoardDTO {
	private int id;
	private String title;
	private String content;
	private int writerId;
	public int getId() {
		return id;
	}
	
	public BoardDTO(){}
	public BoardDTO(int id){
		this.id=id;
	}
	public BoardDTO(BoardDTO b){
		this.id=b.id;
		this.title=b.title;
		this.content=b.content;
		this.writerId=b.writerId;
	}
	public boolean equals(Object o) {
		if(o instanceof BoardDTO) {
			return this.id==((BoardDTO)o).id;
		} else {
			return false;
		}
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public int getWriterId() {
		return writerId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}
}
