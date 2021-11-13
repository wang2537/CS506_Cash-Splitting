package com.cs506.cash_splitting.dao;
import com.cs506.cash_splitting.model.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import javax.persistence.Query;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @ResponseBody
    public Object get() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from userdb").addEntity(User.class);
        ;
        List<User> userList = new ArrayList<>();
        List list = query.list();
        for (Object o : list) {
            User user = (User) o;
            userList.add(user);
        }
        return userList;
    }

    @Override
    @ResponseBody
    public Object get(String username1) {
        Session currSession = entityManager.unwrap(Session.class);
        int uid = get_uid(username1);
        return currSession.get(User.class, uid);
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(user);
        return true;
    }


    @Override
    public boolean addOrUpdatePassword(Password password) {
        Password record = new Password();
        Session currSession = entityManager.unwrap(Session.class);
        int encrypt_password= password.getPassword().hashCode();
        String hash_password = String.valueOf(encrypt_password);
        Query query = currSession.createSQLQuery("select pid from passworddb where uid = :userid ");
        query.setParameter("userid", password.getUid());
        if (query.getResultList().isEmpty()){
            record.setPassword(hash_password);
            record.setUid(password.getUid());
        }else{
            int pid = (int) query.getResultList().get(0);
            record.setPassword(hash_password);
            record.setUid(password.getUid());
            record.setPid(pid);
        }
        currSession.saveOrUpdate(record);

        return true;
    }


    @Override
    public String getUserName(int uid) {
        Session currSession = entityManager.unwrap(Session.class);
        return currSession.get(User.class, uid).getUsername();
    }


    @Override
    public List<?> check(String username, String password) {
        int uid = this.get_uid(username);
        Session currSession = entityManager.unwrap(Session.class);
        int encrypt_password = password.hashCode();
        String hash_password = String.valueOf(encrypt_password);
        Query query = currSession.createSQLQuery("select password from passworddb where uid = :userid and password = :code ");
        query.setParameter("userid", uid);
        query.setParameter("code", hash_password);
        return query.getResultList();
    }


    public int get_uid(String username) {
        Session currSession = entityManager.unwrap(Session.class);
        org.hibernate.Query query = currSession.createSQLQuery("select * from userdb" +
                " where username = :name").addEntity(User.class);
        query.setParameter("name", username);
        List list = query.list();
        if (list.isEmpty()) {
            return -1;
        }
        User user = (User) list.get(0);
        return user.getUid();
    }

    @Override
    public boolean createGroup(Group group){
        Session currSession = entityManager.unwrap(Session.class);
        int max_gid;
        org.hibernate.Query query_max = currSession.createSQLQuery("select max(gid) from groupdb ");
        List max = query_max.list();
        if (max == null || max.get(0) == null){
            max_gid = 1;
        }
        else{
            max_gid = (int) max.get(0) + 1;
        }
        group.setGid(max_gid);

        //sanity check
        org.hibernate.Query query = currSession.createSQLQuery("select distinct gid from groupdb " +
                "where status = 'valid'");
        List list = query.list();
        int newGid = group.getGid();
        if (!list.isEmpty() && list.contains(newGid)){
            return false; // group already exist! cannot create a new group
        }
        // if gid not exist
        currSession.saveOrUpdate(group);

        return true;
    }

    @Override
    public boolean addMember(int gid, int uid){
        Session currSession = entityManager.unwrap(Session.class);
        //sanity check
        org.hibernate.Query query = currSession.createSQLQuery("select uid from groupdb" +
                " where gid = :gid and status = 'valid'");
        query.setParameter("gid", gid);
        List list = query.list();
        if (list.isEmpty()) { // no existing group with that gid OR no valid user in that group -> 'dead group'
            return false;
        }
        if (list.contains(uid)){
            return false; // this user is already in the group
        }
        // if this user used to be in the group, status = 'invalid' now
        org.hibernate.Query query3 = currSession.createSQLQuery("select * from groupdb" +
                " where gid = :gid and uid =:uid and status = 'invalid'").addEntity(Group.class);
        query3.setParameter("gid", gid);
        query3.setParameter("uid", uid);
        List oldMemeber = query3.list();
        if (!oldMemeber.isEmpty()){
            Group entry = (Group) oldMemeber.get(0);
            entry.setStatus("valid");
            return true;
        }
        // check passed, create a new entry and add into db
        org.hibernate.Query query2 = currSession.createSQLQuery("select groupname from groupdb" +
                " where gid = :gid and status = 'valid'");
        query2.setParameter("gid", gid);
        List nameList = query2.list();
        String groupname = (String) nameList.get(0);
        Group newEntry = new Group();
        newEntry.setGid(gid);
        newEntry.setGroupname(groupname);
        newEntry.setUid(uid);
        currSession.saveOrUpdate(newEntry);
        return true;
    }

    @Override
    public boolean quitGroup(int gid, int uid){
        Session currSession = entityManager.unwrap(Session.class);
        //sanity check
        org.hibernate.Query query = currSession.createSQLQuery("select * from groupdb" +
                " where gid = :gid and uid = :uid and status = 'valid'").addEntity(Group.class);
        query.setParameter("gid", gid);
        query.setParameter("uid", uid);
        List list = query.list();
        if (list.isEmpty()) { // this user is already not in the group
            return false;
        }
        Group group = (Group) list.get(0);
        group.setStatus("invalid");
        return true;
    }

    @Override
    public boolean changeGroupname(int gid, String newGroupName){
        Session currSession = entityManager.unwrap(Session.class);
        //sanity check
        org.hibernate.Query query = currSession.createSQLQuery("select * from groupdb" +
                " where gid = :gid").addEntity(Group.class);
        query.setParameter("gid", gid);
        List list = query.list();
        if (list.isEmpty()) { // group not exist
            return false;
        }
        for (Object o : list) {
           Group group = (Group) o;
           group.setGroupname(newGroupName);
        }
        return true;
    }

    @Override
    @ResponseBody
    public Object getGroupname(int uid){
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.
                createSQLQuery("select * from groupdb where uid = :uid").addEntity(Group.class);
        query.setParameter("uid", uid);
        List list = query.list();
        List<Group> groupList = new ArrayList<>();
        for (Object o : list){
            Group gp = (Group) o;
            groupList.add(gp);
        }
        return groupList;
    }

    @Override
    public boolean sendFriendRequest(FriendApp friendApp) {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery friend_query = currSession.
                createSQLQuery("select * from frienddb where friend_id = :source and uid = :destination and status = 'valid'").
                addEntity(Friend.class);
        friend_query.setParameter("source", friendApp.getSource());
        friend_query.setParameter("destination", friendApp.getDestination());
        List friend_list = friend_query.list();
        if (friend_list.isEmpty()) {
            SQLQuery query = currSession.
                    createSQLQuery("select * from friend_appdb where source = :source and destination = :destination and status = 'pending'").
                    addEntity(FriendApp.class);
            query.setParameter("source", friendApp.getSource());
            query.setParameter("destination", friendApp.getDestination());
            List friend_app_list = query.list();
            if (friend_app_list.isEmpty()) {
                currSession.saveOrUpdate(friendApp);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    @ResponseBody
    public Object getFriendRequest(int uid) {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query_out = currSession.
                createSQLQuery("select * from friend_appdb where source = :source and status = 'pending'").
                addEntity(FriendApp.class);
        query_out.setParameter("source", uid);
        List<FriendAppWithName> friendAppList = new ArrayList<>();
        List list = query_out.list();
        for (Object o : list){
            FriendApp friendApp = (FriendApp) o;
            FriendAppWithName friendApp_with_name = new FriendAppWithName(friendApp);
            friendApp_with_name.setDestinationName(getUserName(friendApp.getDestination()));
            friendApp_with_name.setSourceName(getUserName(friendApp.getSource()));
            friendAppList.add(friendApp_with_name);
        }
        SQLQuery query_in = currSession.
                createSQLQuery("select * from friend_appdb where destination = :source and status = 'pending'").
                addEntity(FriendApp.class);
        query_in.setParameter("source", uid);
        List list2 = query_in.list();
        for (Object o : list2){
            FriendApp friendApp = (FriendApp) o;
            FriendAppWithName friendApp_with_name = new FriendAppWithName(friendApp);
            friendApp_with_name.setDestinationName(getUserName(friendApp.getDestination()));
            friendApp_with_name.setSourceName(getUserName(friendApp.getSource()));
            friendAppList.add(friendApp_with_name);
        }
        return friendAppList;
    }

    @Override
    public Object updateFriendApp(FriendApp friendApp) {
        Session currSession = entityManager.unwrap(Session.class);
        FriendApp originApp = currSession.get(FriendApp.class, friendApp.getAid());
        if (originApp.getStatus().equals("denied") || originApp.getStatus().equals("approved")) {
            return false;
        }
        if (originApp.getStatus().equals("pending")) {
           originApp.setStatus(friendApp.getStatus());
           currSession.saveOrUpdate(originApp);
            if (originApp.getStatus().equals("denied")) {
                SQLQuery friendApp_query = currSession.
                        createSQLQuery("select * from friend_appdb where source = :destination and destination = :source and status = 'pending'").
                        addEntity(FriendApp.class);
                friendApp_query.setParameter("source", friendApp.getSource());
                friendApp_query.setParameter("destination", friendApp.getDestination());
                List friendApp_list = friendApp_query.list();
                if (!friendApp_list.isEmpty()) {
                    FriendApp friend_application = (FriendApp) friendApp_list.get(0);
                    friend_application.setStatus("denied");
                    currSession.saveOrUpdate(friend_application);
                }
                return true;
            }
            if (originApp.getStatus().equals("approved")) {
                SQLQuery friendApp_query = currSession.
                        createSQLQuery("select * from friend_appdb where source = :destination and destination = :source and status = 'pending'").
                        addEntity(FriendApp.class);
                friendApp_query.setParameter("source", friendApp.getSource());
                friendApp_query.setParameter("destination", friendApp.getDestination());
                List friendApp_list = friendApp_query.list();
                if (!friendApp_list.isEmpty()) {
                    FriendApp friend_application = (FriendApp) friendApp_list.get(0);
                    friend_application.setStatus("approved");
                    currSession.saveOrUpdate(friend_application);
                }
                SQLQuery query = currSession.
                        createSQLQuery("select * from frienddb where friend_id = :friend_id and uid = :uid").
                        addEntity(Friend.class);
                query.setParameter("uid", friendApp.getSource());
                query.setParameter("friend_id", friendApp.getDestination());
                List list = query.list();
                Friend newFriend = new Friend(originApp.getDestination(), originApp.getSource());
                Friend newFriend2 = new Friend(originApp.getSource(), originApp.getDestination());
                if (list.isEmpty()) {
                    currSession.saveOrUpdate(newFriend);
                    currSession.saveOrUpdate(newFriend2);
                    return true;
                } else {
                    newFriend.setStatus("valid");
                    updateFriend(newFriend);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Object updateFriend(Friend friend) {
        HashMap<Object,Object> result = new HashMap<>();
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.
                createSQLQuery("select * from frienddb where friend_id = :friend_id and uid = :uid").
                addEntity(Friend.class);
        query.setParameter("uid", friend.getUid());
        query.setParameter("friend_id", friend.getFriend_id());
        List<Friend> friendList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Friend tmp = (Friend) o;
            friendList.add(tmp);
        }
        Friend originFriend = friendList.get(0);
        SQLQuery _query = currSession.
                createSQLQuery("select * from frienddb where friend_id = :friend_id and uid = :uid").
                addEntity(Friend.class);
        _query.setParameter("uid", friend.getFriend_id());
        _query.setParameter("friend_id", friend.getUid());
        List<Friend> _friendList = new ArrayList<>();
        List _list = _query.list();
        for (Object o : _list){
            Friend tmp = (Friend) o;
            _friendList.add(tmp);
        }
        Friend _originFriend = _friendList.get(0);
        if (friend.getStatus().equals("invalid") && originFriend.getStatus().equals("valid")) {
            originFriend.setStatus(friend.getStatus()); // delete friend
            _originFriend.setStatus(friend.getStatus());
            // TODO: settle up all related balance bill to paid
            currSession.saveOrUpdate(originFriend);
            currSession.saveOrUpdate(_originFriend);
            result.put("result", "Succeed in deleting " + getUserName(originFriend.getFriend_id()));
            return result;
        }
        if (friend.getStatus().equals("valid") && originFriend.getStatus().equals("invalid")) {
            originFriend.setStatus(friend.getStatus()); // refriend
            _originFriend.setStatus(friend.getStatus());
            currSession.saveOrUpdate(originFriend);
            currSession.saveOrUpdate(_originFriend);
            result.put("result", "succeed in adding old friend" + getUserName(originFriend.getFriend_id()));
            return result;
        }
        result.put("result", "error, nothing changed");
        return result;
    }

    @Override
    @ResponseBody
    public Object getFriend(int uid) {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.
                createSQLQuery("select friend_id from frienddb where uid = :uid and status = 'valid'");
        query.setParameter("uid", uid);
        List list = query.list();
        List<User> friendList = new ArrayList<>();
        for (Object o : list){
            int friend_id = (Integer) o;
            friendList.add(currSession.get(User.class, friend_id));
        }
        User user = currSession.get(User.class, uid);
        return new FriendList(user, friendList);

    }
}
