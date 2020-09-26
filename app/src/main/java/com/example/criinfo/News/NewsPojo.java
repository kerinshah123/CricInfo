package com.example.criinfo.News;

public class NewsPojo
{


    public String imgurl;
    public String authorname;
    public String newsurl;
    public String newstitle;
    public String newsdate;

    public NewsPojo(String imgurl, String authorname, String newsurl, String newstitle, String newsdate) {
        this.imgurl = imgurl;
        this.authorname = authorname;
        this.newsurl = newsurl;
        this.newstitle = newstitle;
        this.newsdate = newsdate;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getNewsurl() {
        return newsurl;
    }

    public void setNewsurl(String newsurl) {
        this.newsurl = newsurl;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsdate() {
        return newsdate;
    }

    public void setNewsdate(String newsdate) {
        this.newsdate = newsdate;
    }
}
