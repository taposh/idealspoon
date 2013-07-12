package com.netshelter.ifbrands.etl.transform;

import com.netshelter.ifbrands.api.model.campaign.Notes;
import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.service.UserService;
import com.netshelter.ifbrands.data.entity.IfbNotes;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy T
 */
@Component
public class NotesTransformer
{

    @Autowired
    UserService userService = null;

    public Notes transform(IfbNotes e)
    {
        if (e == null) return null;

        UserInfo userInfo = userService.getUserInfo(e.getUserKey()+"");
        String userFullName = null;
        if (userInfo != null && StringUtils.isNotBlank(userInfo.getFullName()))
        {
            userFullName = userInfo.getFullName();
        }

        return new Notes(e.getNotesId(),
                         e.getNotesText(),
                         e.getCampaignId(),
                         e.getUserKey(),
                         new DateTime(e.getCreateTimestamp()),
                         new DateTime(e.getUpdateTimestamp()),
                         userFullName
        );
    }

    public List<Notes> transform(List<IfbNotes> list)
    {
        if (list == null) return null;

        List<Notes> result = new ArrayList<Notes>(list.size());
        for (IfbNotes e : list)
        {

            UserInfo userInfo = userService.getUserInfo(e.getUserKey()+"");
            String userFullName = null;
            if (userInfo != null && StringUtils.isNotBlank(userInfo.getFullName()))
            {
                userFullName = userInfo.getFullName();
            }

            result.add(new Notes(e.getNotesId(),
                                 e.getNotesText(),
                                 e.getCampaignId(),
                                 e.getUserKey(),
                                 new DateTime(e.getCreateTimestamp()),
                                 new DateTime(e.getUpdateTimestamp()),
                                 userFullName)
            );
        }
        return result;
    }

}
