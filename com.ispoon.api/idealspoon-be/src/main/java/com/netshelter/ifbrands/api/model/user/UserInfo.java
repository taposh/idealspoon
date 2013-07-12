package com.netshelter.ifbrands.api.model.user;

import java.util.Comparator;

public class UserInfo
{
  private String userKey;
  private String firstName;
  private String lastName;
  private String email;
  private String fullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;

        UserInfo userInfo = (UserInfo) o;

        if (email != null ? !email.equals(userInfo.email) : userInfo.email != null) return false;
        if (firstName != null ? !firstName.equals(userInfo.firstName) : userInfo.firstName != null) return false;
        if (fullName != null ? !fullName.equals(userInfo.fullName) : userInfo.fullName != null) return false;
        if (lastName != null ? !lastName.equals(userInfo.lastName) : userInfo.lastName != null) return false;
        if (userKey != null ? !userKey.equals(userInfo.userKey) : userInfo.userKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userKey != null ? userKey.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    public String getUserKey()
  {
    return userKey;
  }

  public void setUserKey( String userKey )
  {
    this.userKey = userKey;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName( String firstName )
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName( String lastName )
  {
    this.lastName = lastName;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  public String getFullName()
  {
    return fullName != null ? fullName : String.format( "%s %s", getFirstName(), getLastName() );
  }

  public void setFullName( String fullName)
  {
    this.fullName = fullName;
  }

  @Override
  public String toString()
  {
    return "UserInfo [userKey=" + userKey + ", firstName=" + firstName + ", lastName=" + lastName
        + ", email=" + email + ", fullName=" + fullName + "]";
  }

  public static class FieldComparator implements Comparator<UserInfo>
  {
    public enum Field { USERKEY, FIRST_NAME, LAST_NAME, EMAIL, FULL_NAME };
    private Field field;
    public FieldComparator( Field field )
    {
      this.field = field;
    }
    @Override
    public int compare( UserInfo o1, UserInfo o2 )
    {
      switch( field ) {
       case USERKEY   : return o1.getUserKey().compareTo( o2.getUserKey() );
       case FIRST_NAME: return o1.getFirstName().compareTo( o2.getFirstName() );
       case LAST_NAME : return o1.getLastName().compareTo( o2.getLastName() );
       case EMAIL     : return o1.getEmail().compareTo( o2.getEmail() );
       case FULL_NAME : return o1.getFullName().compareTo( o2.getFullName() );
      }
      throw new IllegalStateException( "Comparing on field "+ field +" not yet supported" );
    }
  }

}
