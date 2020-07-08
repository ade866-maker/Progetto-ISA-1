package com.gb.DAO;

import com.gb.modelObject.Group;

import java.util.List;

public interface GroupDAO {

    List<Group> getAllGroups(int page);

    List<Group> getGroupById(int groupId);

    int insertGroup(Group group);

}
