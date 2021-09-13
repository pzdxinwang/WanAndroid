package com.example.wanandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Tree extends LitePalSupport implements Parcelable {
    private String name;
    private String id;
    private List<Tree> children;

    public Tree(Parcel pSource) {
        name = pSource.readString();
        id = pSource.readString();
        children = new ArrayList<>();
        pSource.readTypedList(children, Tree.CREATOR);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将外部数据写入到Parcel中(封装起来)
     *
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeParcelableArray(children.toArray(new Tree[0]), i);
    }

    /**
     * 静态的Parcelable.Creator<>  CREATOR 接口 注意必须是 CREATOR
     * 前边修饰关键字只能是 public static （可以有 final），这个接口是用来反序列化的
     */
    public static final Creator<Tree> CREATOR = new Creator<Tree>() {
        @Override
        public Tree createFromParcel(Parcel PSource) {
            return new Tree(PSource);
        }

        @Override
        public Tree[] newArray(int i) {
            return new Tree[i];
        }
    };

    public static class ChildrenBean {
        /**
         * "children": [],
         * "courseId": 13,
         * "id": 60, // id会在查看该目录下所有文章时有用
         * "name": "Android Studio相关", // 子名称
         * "order": 1000,
         * "parentChapterId": 150,
         * "visible": 1
         */
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
