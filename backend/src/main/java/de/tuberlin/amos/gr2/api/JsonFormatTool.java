package main.java.de.tuberlin.amos.gr2.api;

import com.atlassian.jira.issue.Issue;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatTool {

    public List<Map> getHashMap(List<Issue> result){

        List<Map> IssueInfoList = new ArrayList<>();
        for(Issue i:result){
            Map<String, String> issueMap = new HashMap<>();
            issueMap.put("id",i.getId().toString());
            issueMap.put("name", i.getKey());
            issueMap.put("start", i.getCreated().toString());
            if(i.getDueDate()!=null){
                issueMap.put("end", i.getDueDate().toString());
            }else{
                issueMap.put("end", null);
            }
            issueMap.put("progress", "10");
            issueMap.put("dependencies", "");
            IssueInfoList.add(issueMap);
        }
        Gson gson = new Gson();
        gson.toJson(IssueInfoList);

        return IssueInfoList;
    }
}
