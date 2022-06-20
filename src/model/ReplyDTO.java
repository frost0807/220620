package model;

public class ReplyDTO {
	private int id;
	private int writerId;
	private int boardId;
	private String content;
	
	public ReplyDTO(){}
	public ReplyDTO(int id){
		this.id=id;
	}
	public ReplyDTO(ReplyDTO r) {
		this.id=r.id;
		this.boardId=r.boardId;
		this.writerId=r.writerId;
		this.content=r.content;
	}
	public boolean equals(Object o) {
		if(o instanceof ReplyDTO) {
			return this.id==((ReplyDTO)o).id;
		} else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public int getWriterId() {
		return writerId;
	}
	public int getBoardId() {
		return boardId;
	}
	public String getContent() {
		return content;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
