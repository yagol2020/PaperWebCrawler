# 文献网站爬虫工具

还在为IEEE复杂的搜索条件发愁么？还在为ACM不支持自定义查询而苦恼么？那就试试这个小工具吧

## 准备工作

* Chrome浏览器
* ChromeDriver: [链接](http://chromedriver.storage.googleapis.com/index.html)

驱动的版本应与Chrome的版本一致，下图将展示如何查看Chrome版本

![chrome_version](https://github.com/yagol2020/PaperWebCrawler/blob/master/images/chrome%20version.png)

## 使用说明 Usage

1. 根据pom.xml配置依赖
2. 工具的入口是App.main
3. 输入想要查询的关键字
```java
public class App {
    public static void main(String[] args) {
        IeeeSearchQuery ieeeSearchQuery = new IeeeSearchQuery("put your search query in there");
        IeeeResultProcessor processor = new IeeeResultProcessor();
        processor.run(ieeeSearchQuery);
    }
}
```
4. 文献的结果会输出在`target/classes/output/IEEE XPLORE YourSerarchQuery.csv`中
5. 目前文献的信息包括
    1. 你查询的关键字
    2. 文章标题
    3. 作者列表
    4. 出处（期刊或会议名称）
    5. 发表日期
    6. 论文类型（期刊或会议）

## 文献查询结果 Csv Result
文献查询结果会以`csv`文件形式呈现，如下图所示，该样例以`NLP Model Parameter`作为关键字进行查询。
![ieee result demo](https://github.com/yagol2020/PaperWebCrawler/blob/master/images/ieee%20result%20demo.png)
