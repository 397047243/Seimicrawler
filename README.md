# 用Seimicrawler+springboot爬取文章并存入MySql
## 目标 https://www.dzwzzz.com/ 
> 站长如果看到了请立即通知我。。需求:2017年的所有文章
## 其他简单的不管，看重点
根据需要自定义这个bean
``` java
@Data
public class Article {

    @Xpath("//div[@class='top-nav-info']/a[2]/text()") //Xpath
    private String date;

    @Xpath("//div[@class='showList']/h2/text()")
    private String category;

    @Xpath("//div[@class='blkContainer']/div/h1/text()")
    private String title;

    @Xpath("//span[@id='pub_date']/text()")
    private String author;

    @Xpath("//span[@id='media_name']/text()")
    private String source;

    @Xpath("//div[@class='blkContainerSblkCon']/p/text()")
    private String content;
    
}
```
## 获取所有2017的所有url
时间紧迫，这段写的有点丑，其实可以筛选出来的。

``` java
    private static final String URL = "https://www.dzwzzz.com/";
    private static String[] urls = new String[24]; //请求的URL数组

    @Override
    public String[] startUrls() {
        String str = "";
        for (int i = 1; i <= urls.length; i++) {
            str = i < 10 ? "0"+i:i+"";
            urls[i-1] = URL + "2017_"+ str +"/index.html";
        }
        return urls;
    }
```
## 获取本日中所有文章的url
注意上面的那个方法，返回的是一个String数组，每一个URL都会调用该方法。也就是说这个方法会被调用24次。
调用` push(Request.build(url,Basic::getDetails)); ` 这个方法到具体的文章中获取内容
``` java
    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            String url = "";
            //获取当日所有文章的请求
            List<Object> urls = doc.sel("//tr/td[@class='title']/a/@href");
            for (Object u : urls) {
                //获取当前请求
                url = response.getUrl();
                url = url.substring(0,url.length() - 10) + u; //拼接URL
                push(Request.build(url,Basic::getDetails));
            }
            System.out.println("URL:"+response.getUrl()+"\t 爬取结束！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
## Basic::getDetails
``` java
    /**
     * 获取文章详细信息
     * @param response
     */
    public void getDetails(Response response){
        JXDocument doc = response.document();
        try {
            Article article = response.render(Article.class);
//            logger.info("文章实体对象：{}",article);
            //持久化
            articleMapper.insert(conversion(article));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
