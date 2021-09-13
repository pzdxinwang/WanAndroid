package com.example.wanandroid.utils;

import android.util.Log;

import com.example.wanandroid.model.Banner;
import com.example.wanandroid.model.Location;
import com.example.wanandroid.model.Project;
import com.example.wanandroid.model.ProjectPresent;
import com.example.wanandroid.model.Tree;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.example.wanandroid.model.Article;

import java.util.Collection;
import java.util.List;

public class JsonUtil {
    /***
     * 得到错误码
     * @param response
     * @return
     */
    public static int getErrorCode(String response) {
        int errorCode = -1;
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            errorCode = jsonObj.get("errorCode").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorCode;
    }

    public static String getErrorMsg(String response) {
        String errorMsg = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            errorMsg = jsonObj.get("errorMsg").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMsg;
    }

    public static List<Article> getArticles(String response) {
        List<Article> articles = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            JsonObject data = jsonObj.getAsJsonObject("data");
            if (data.get("datas").getClass() == JsonArray.class) {
                JsonArray datas = data.getAsJsonArray("datas");
                //将Json数据转换为对象
                articles = new Gson().fromJson(datas, new TypeToken<List<Article>>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    public static List<Location> getLocation(JsonObject jsonObj) {
        List<Location> oas = null;
        if (jsonObj.get("data").getClass() == JsonArray.class) {
            JsonArray data = jsonObj.get("data").getAsJsonArray();
            oas = new Gson().fromJson(data, new TypeToken<List<Location>>() {
            }.getType());
        }
        return oas;
    }

    public static List<Tree> getTrees(JsonObject jsonObj) {
        List<Tree> trees = null;
        if (jsonObj.get("data").getClass() == JsonArray.class) {
            JsonArray datas = jsonObj.getAsJsonArray("data");
            trees = new Gson().fromJson(datas, new TypeToken<List<Tree>>() {
            }.getType());
        }
        return trees;
    }

    public static List<Banner> getBanners(String response) {
        List<Banner> banners = null;
        try {
            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            if (jsonObject.get("data").getClass() == JsonArray.class) {
                JsonArray data = jsonObject.getAsJsonArray("data");
                banners = new Gson().fromJson(data, new TypeToken<List<Banner>>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("banners", "getBanners: " + banners);
        return banners;
    }

    public static List<Project> getProjects(JsonObject jsonObject) {
        List<Project> projects = null;
        JsonObject data = jsonObject.getAsJsonObject("data");
        if (data.get("datas").getClass() == JsonArray.class) {
            JsonArray datas = data.getAsJsonArray("datas");
            projects = new Gson().fromJson(datas, new TypeToken<List<Project>>() {
            }.getType());
        }

        Log.e("projects", String.valueOf(projects));
        return projects;
    }

    public static List<ProjectPresent> getProjectPresents(JsonObject jsonObject) {
        List<ProjectPresent> projectPresents = null;
        if (jsonObject.get("data").getClass()==JsonArray.class){
            JsonArray data = jsonObject.getAsJsonArray("data");

            projectPresents = new Gson().fromJson(data,new TypeToken<List<ProjectPresent>>(){}.getType());
        }

        return projectPresents;
    }
}
