package br.com.caelum.brutal.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.caelum.brutal.model.Comment;
import br.com.caelum.brutal.model.Flag;
import br.com.caelum.brutal.model.FlagType;
import br.com.caelum.brutal.model.User;

public class FlagDaoTest extends DatabaseTestCase {
	
	@Test
	public void should_verify_that_user_flagged_comment() {
		FlagDao flags = new FlagDao(session);
		User author = new User("user user", "chico@brutal.com", "123456");
		User other = new User("user user", "other@brutal.com", "123456");
		session.save(author);
		session.save(other);

		Comment commentFromAuthor = createCommentWithFlag(author);
		Comment commentFromOther = createCommentWithFlag(other);
		
		assertFalse(flags.alreadyFlagged(author, commentFromOther.getId()));
		assertTrue(flags.alreadyFlagged(author, commentFromAuthor.getId()));
	}

	private Comment createCommentWithFlag(User author) {
		Comment comment = new Comment(author, "my comment my comment my comment");
		Flag flag = new Flag(FlagType.RUDE, author);
		comment.add(flag);
		session.save(flag);
		session.save(comment);
		return comment;
	}

}