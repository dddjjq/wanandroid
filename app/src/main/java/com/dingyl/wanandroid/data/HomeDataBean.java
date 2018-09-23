package com.dingyl.wanandroid.data;

import com.dingyl.wanandroid.helper.StringConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HomeDataBean{
    private String apkLink;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private boolean fresh;
    @SerializedName("id")
    private int anotherId;
    private String link;
    private String niceDate;
    private String origin;
    private String projectLink;
    private long publishTime;
    private int superChapterId;
    private String  superChapterName;
    @Convert(columnType = String.class,converter = StringConverter.class)
    private List<Tag> tags;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;

    @Generated(hash = 645888334)
    public HomeDataBean(String apkLink, String author, int chapterId,
            String chapterName, boolean collect, int courseId, String desc,
            String envelopePic, boolean fresh, int anotherId, String link,
            String niceDate, String origin, String projectLink, long publishTime,
            int superChapterId, String superChapterName, List<Tag> tags,
            String title, int type, int userId, int visible, int zan) {
        this.apkLink = apkLink;
        this.author = author;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.collect = collect;
        this.courseId = courseId;
        this.desc = desc;
        this.envelopePic = envelopePic;
        this.fresh = fresh;
        this.anotherId = anotherId;
        this.link = link;
        this.niceDate = niceDate;
        this.origin = origin;
        this.projectLink = projectLink;
        this.publishTime = publishTime;
        this.superChapterId = superChapterId;
        this.superChapterName = superChapterName;
        this.tags = tags;
        this.title = title;
        this.type = type;
        this.userId = userId;
        this.visible = visible;
        this.zan = zan;
    }

    @Generated(hash = 92414925)
    public HomeDataBean() {
    }

    public String getApkLink() {
        return this.apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return this.chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return this.chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean getCollect() {
        return this.collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return this.envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean getFresh() {
        return this.fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getAnotherId() {
        return this.anotherId;
    }

    public void setAnotherId(int anotherId) {
        this.anotherId = anotherId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return this.niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProjectLink() {
        return this.projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSuperChapterId() {
        return this.superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return this.superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return this.visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return this.zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public class Tag{
        private String name;
        private String url;
    }
}
