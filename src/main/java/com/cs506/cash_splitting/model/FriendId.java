package com.cs506.cash_splitting.model;

import java.io.Serializable;

public class FriendId implements Serializable {

    private int friend_id;

    private int uid;

    public FriendId(){
    }

    public FriendId(int friend_id, int uid) {
        this.friend_id = friend_id;
        this.uid = uid;
    }

//    public int getFriend_id() {
//        return friend_id;
//    }
//
//    public void setFriend_id(int friend_id) {
//        this.friend_id = friend_id;
//    }
//
//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }

//    @Override
//    public int hashCode()
//    {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + String.valueOf(uid).hashCode();
//        result = prime * result + String.valueOf(friend_id).hashCode();
//        return result;
//    }
//    @Override
//    public boolean equals(Object obj)
//    {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        FriendId other = (FriendId) obj;
//        if (uid != other.uid) {
//            return false;
//        }
//        return friend_id == other.friend_id;
//    }


}
