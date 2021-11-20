# 文献网站爬虫工具

还在为IEEE复杂的搜索条件发愁么？还在为ACM不支持自定义查询而苦恼么？那就试试这个小工具吧

### 说明

如果你是本地查看项目，请查看`README_LOCAL.md`

## 准备工作

* Chrome浏览器
* **ChromeDriver: [链接](http://chromedriver.storage.googleapis.com/index.html)**

```markdown
极其重要！！！！！！

一定要将合适的ChromeDriver放到src/main/resources里，替换掉原有的ChromeDriver!
```

驱动的版本应与Chrome的版本一致，下图将展示如何查看Chrome版本

![chrome_version](https://github.com/yagol2020/PaperWebCrawler/blob/master/images/chrome%20version.png)

## 使用说明 Usage

1. 根据pom.xml配置依赖
2. 控制台级别的工具入口是App.main，GUI级别的工具入口是`gui.designer.MainGui.main`
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

![gui main](https://github.com/yagol2020/PaperWebCrawler/blob/master/images/gui%20main.png)

4. 文献的结果会输出在`target/classes/output/IEEE XPLORE YourSerarchQuery.csv`中，GUI级别的会弹出结果展示表格

![gui paper info table](https://github.com/yagol2020/PaperWebCrawler/blob/master/images/gui%20paper%20info%20table.png)

5. 目前文献的信息包括
	1. 你查询的关键字
	2. 文章标题
	3. 作者列表
	4. 出处（期刊或会议名称）
	5. 发表日期
	6. 论文类型（期刊或会议）
	7. 对于期刊，可以获得影响因子
	8. paper的网页地址

## 文献查询结果 Csv Result

文献查询结果会以`csv`文件形式呈现，如下图所示，该样例以`NLP Model Parameter`作为关键字进行查询。

![ieee result demo](https://github.com/yagol2020/PaperWebCrawler/blob/master/images/ieee%20result%20demo.png)

## 近期目标 TODO List

| 序号        | 内容    |  开工时间  |  完工时间  | 负责人 |
| :--------:   | :-----   | :----: | :----: |:------: |
| 1        | IEEE XPLORE 爬虫框架      |   2021年11月10日    |   2021年11月12日    | @[yagol202](https://github.com/yagol2020)|
| 2        | 论文等级识别      |   2021年11月12日    |   -    |@[yagol2020](https://github.com/yagol2020) |
| 3        | ACM 爬虫框架      |   -    |   -    |- |
| 4        | IEEE XPLORE 摘要获取      |   2021年11月13日    |   -    |@[vencerk](https://github.com/vencerk) |

## 仓库
![GitHub](https://github.com/yagol2020/PaperWebCrawler)

![Gitee](https://gitee.com/yagol2020/PaperWebCrawler)

## IDEA's {File and Code Templates}
```java
/**
* @author your name
* @date ${TIME}
* @deprecated
**/
class YourCode{
    
}
```
