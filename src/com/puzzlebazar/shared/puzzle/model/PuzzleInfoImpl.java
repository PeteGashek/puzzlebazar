package com.puzzlebazar.shared.puzzle.model;

/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.puzzlebazar.shared.model.ActionRightsInfo;
import com.puzzlebazar.shared.model.User;

/**
 * A persistent class that is able to save all the fields required
 * to support the {@link PuzzleInfo} interface.
 * 
 * @author Philippe Beaudoin
 */
@PersistenceCapable
public class PuzzleInfoImpl implements PuzzleInfo {

  private static final int deletedFlag  = 0x0001;
  private static final int rejectedFlag = 0x0002;
  private static final int publicFlag   = 0x0004;
  private static final int validFlag    = 0x0008;
  private static final int completeFlag = 0x0010;

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
  private String key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
  private Long keyId;
  
  // Polymorphic relationship, we keep the key instead of the object
  @Persistent
  private String puzzleDetailsKey;

  @Persistent
  private String authorKey;

  @Persistent
  private String puzzleTypeKey;
  
  @Persistent
  private double difficulty;

  @Persistent
  private double quality;

  @Persistent
  private String sizeString;

  @Persistent
  private String title;

  @Persistent
  private int flags;

  @Persistent
  private Date creationDate;

  @Persistent
  private Date editionDate;

  @Persistent
  private Date publicationDate;

  @Override
  public ActionRightsInfo canUserViewPuzzleDetails(User user) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getDifficulty() {
    return difficulty;
  }

  @Override
  public PuzzleDetails getPuzzleDetails() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getQuality() {
    return quality;
  }

  @Override
  public String getSizeString() {
    return sizeString;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public boolean isComplete() {
    return (flags & completeFlag) != 0;
  }

  @Override
  public boolean isValid() {
    return (flags & validFlag) != 0;
  }

  @Override
  public long getId() {
    return keyId;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public User getAuthor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getCreationDate() {
    return creationDate;
  }

  @Override
  public Date getEditionDate() {
    return editionDate;
  }

  @Override
  public Date getPublicationDate() {
    return publicationDate;
  }

  @Override
  public boolean isDeleted() {
    return (flags & deletedFlag) != 0;
  }

  @Override
  public boolean isPublic() {
    return (flags & publicFlag) != 0;
  }

  @Override
  public boolean wasRejected() {
    return (flags & rejectedFlag) != 0;
  }

  @Override
  public PuzzleType getPuzzleType() {
    // TODO Auto-generated method stub
    return null;
  }

}
