package cn.xrz.seimicrawler.vo;

import cn.wanghaomiao.seimi.annotation.Xpath;
import lombok.Data;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/2 0002
 * @Description :
 */
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
