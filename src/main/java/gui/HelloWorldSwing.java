package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Java Swing练习demo
 *
 * @author yagol
 */
public class HelloWorldSwing extends JFrame {
    private void createAndShowGui() {
        setTitle("Paper Web Crawler 文献网站爬虫工具");
        setSize(600, 500);
        setBounds(300, 200, 600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel searchQueryLabel = new JLabel("关键字查询");
        JTextField searchQueryInputTextField = new JTextField();
        JButton searchButton = new JButton("开始");

        JFrame frame = new JFrame("Java第五个程序");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel cards = new JPanel(new CardLayout());
        p1.add(new JButton("登录按钮"));
        p1.add(new JButton("注册按钮"));
        p1.add(new JButton("找回密码按钮"));
        p2.add(new JTextField("用户名文本框", 20));
        p2.add(new JTextField("密码文本框", 20));
        p2.add(new JTextField("验证码文本框", 20));
        cards.add(p1, "card1");
        cards.add(p2, "card2");
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "card1");
        frame.add(cards);
        frame.setBounds(300, 200, 400, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new HelloWorldSwing().createAndShowGui();
    }
}
