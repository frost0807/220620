package controller;

import java.util.ArrayList;
import model.ReplyDTO;

public class ReplyController {
	private ArrayList<ReplyDTO> list;
	private int nextId;
	
	public ReplyController() {
		list=new ArrayList<>();
		nextId=1;
	}
	public ArrayList<ReplyDTO> selectAll() {
		ArrayList<ReplyDTO> temp=new ArrayList<>();
		for(ReplyDTO r:list) {
			temp.add(new ReplyDTO(r));
		}
		return temp;
	}
	public ReplyDTO selectOne(int id) {
		ReplyDTO r=new ReplyDTO(id);
		if(list.contains(r)) {
			return new ReplyDTO(list.get(list.indexOf(r)));
		} else {
			return null;
		}
	}
	public void insert(ReplyDTO r) {
		r.setId(nextId++);
		list.add(r);
	}
	public void delete(int id) {
		list.remove(new ReplyDTO(id));
	}
	public void update(ReplyDTO r) {
		list.set(list.indexOf(r), r);
	}
	public boolean validOnThisBoard(int id,int boardId) {
		if(list.contains(new ReplyDTO(id))) {
			return selectOne(id).getBoardId()==boardId;
		} else {
			return false;
		}
	}
	public void deleteByWriterId(int writerId) {
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getWriterId()==writerId) {
				list.remove(i);
				i--;
			}
		}
	}
}