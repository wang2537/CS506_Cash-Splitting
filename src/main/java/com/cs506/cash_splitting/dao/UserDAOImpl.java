package com.cs506.cash_splitting.dao;
import com.cs506.cash_splitting.model.Group;
import com.cs506.cash_splitting.model.Password;
import com.cs506.cash_splitting.model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int hash_password = password.getPassword().hashCode();
        record.setPassword(String.valueOf(hash_password));
        record.setUid(password.getUid());
        Session currSession = entityManager.unwrap(Session.class);
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

}
