package cn.xrz.seimicrawler;

import cn.xrz.seimicrawler.vo.Article;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.xrz.seimicrawler.mapper")
public class SeimicrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeimicrawlerApplication.class, args);
    }

//
//    create database seimicrawler character set utf8;
//    use seimicrawler;
//
//
//    create table Article(
//      id int primary key auto_increment,
//      date varchar(50),
//      category varchar(25),
//      title varchar(100),
//      author varchar(100),
//      source varchar(100),
//      content text
//    );
//
//    delete FROM Article;

}
