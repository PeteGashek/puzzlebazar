package com.puzzlebazar.shared.puzzle.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.puzzlebazar.shared.model.ActionRightsInfo;
import com.puzzlebazar.shared.model.Comment;
import com.puzzlebazar.shared.model.PrivacySettings;
import com.puzzlebazar.shared.model.Tag;
import com.puzzlebazar.shared.model.TagInstance;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;

public abstract class PuzzleDetailsImpl<T extends PuzzleDetailsImpl<?>> implements PuzzleDetails<T> {

  private static final long serialVersionUID = -1095496912986893668L;

  @Id Long id;

  private Key<PuzzleInfoImpl> puzzleInfoKey;
  @Transient transient private PuzzleInfoImpl puzzleInfo = null;


  @Override
  public void addComment(Comment comment) {
    // TODO Auto-generated method stub

  }

  @Override
  public ActionRightsInfo canUserComment(User user) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ActionRightsInfo canUserSetTitle(User user) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ActionRightsInfo canUserTag(User user, Tag tag) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ActionRightsInfo canUserViewPuzzle(User user) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Comment> getComments() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PuzzleHistory getHistory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Puzzle getPuzzle() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<PuzzleSolve> getSolves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TagInstance> getTagInstances() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTitle(User user, String title) {
    // TODO Auto-generated method stub

  }

  @Override
  public void tag(User user, Tag tag) {
    // TODO Auto-generated method stub

  }

  @Override
  public void untag(User user, Tag tag) {
    // TODO Auto-generated method stub

  }

  @Override
  public ActionRightsInfo canUserViewPuzzleDetails(User user) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getDifficulty() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Key<? extends PuzzleDetails<?>> getPuzzleDetailsKey() {
    return createKey();
  }

  @Override
  public Key<PuzzleTypeImpl> getPuzzleTypeKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getQuality() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getSizeString() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isComplete() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Key<UserImpl> getAuthorKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getCreationDate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getEditionDate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getPublicationDate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isDeleted() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPublic() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean wasRejected() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public PrivacySettings getPrivacySettings() {
    // TODO Auto-generated method stub
    return null;
  }


}
