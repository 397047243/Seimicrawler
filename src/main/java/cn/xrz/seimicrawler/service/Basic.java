package cn.xrz.seimicrawler.service;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.xrz.seimicrawler.bean.ArticleEntity;
import cn.xrz.seimicrawler.mapper.ArticleMapper;
import cn.xrz.seimicrawler.vo.Article;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/2 0002
 * @Description :
 *
 *          获取 https://www.dzwzzz.com/ 该网站2017年的所有文章。
 *
 */
@Crawler(name = "basic")
public class Basic extends BaseSeimiCrawler {

    @Autowired
    private ArticleMapper articleMapper;
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

//    @Override
//    public String[] startUrls() {
//        return new String[]{"https://www.dzwzzz.com"};
//    }

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


    public ArticleEntity conversion(Article article){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setDate(article.getDate());
        articleEntity.setCategory(article.getCategory());
        articleEntity.setTitle(article.getTitle());
        articleEntity.setAuthor(article.getAuthor().substring(3));
        articleEntity.setSource(article.getSource().substring(3));
        articleEntity.setContent(article.getContent());
        return articleEntity;
    }
}
