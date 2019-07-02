package cn.xrz.seimicrawler.bean;

import lombok.Data;

/**
 * @author XRZ
 * @date 2019/7/2 0002
 * @Description :
 */
@Data
public class ArticleEntity {

    private Integer id;
    private String  date;
    private String  category;
    private String  title;
    private String  author;
    private String  source;
    private String  content;

}
