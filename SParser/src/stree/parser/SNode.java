package stree.parser;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SDefaultNode.class)
public interface SNode {

	Boolean isLeaf();
	@JsonIgnore
	int quote();
	@JsonIgnore
	void quote(int q);

	String contents();

	void setContents(String contents);

	void addToContents(Character c);

	List<SNode> children();
	
	@JsonIgnore
	void setParent(SNode parent);
	
	void addChild(SNode child);

	SNode parent();

	default Boolean isNode() {
		return !this.isLeaf();
	}

	default Boolean hasContents() {
		return this.contents() != null;
	}
	default Boolean hasChildren() {
		return this.children() != null && this.children().size() > 0;
	}
	
	default SNode get(int pos) {
		return this.children().get(pos);
	}
	default int size() {
		return this.children().size();
	}
	default void accept(SVisitor visitor) {
		if (this.isLeaf())
			visitor.visitLeaf(this);
		else
			visitor.visitNode(this);
	}
	public void setAlien(Object alien);
	public Object alien();

}
