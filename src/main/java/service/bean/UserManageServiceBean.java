package service.bean;

import com.sun.mail.util.MailSSLSocketFactory;
import dao.DaoFactory;
import dao.UserDao;
import model.ResultMessage;
import model.User;
import model.VIPLevel;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserManageService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class UserManageServiceBean implements UserManageService {


    @Autowired
    UserDao userDao;

    public ResultMessage register(String account, String password) {
        User testUser=userDao.find(account,password);
        if(testUser.getId()!=-1){
            return ResultMessage.FAIL;
        }
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmailAddress(null);
        user.setConfirmed(false);
        user.setName(null);
        user.setLevel(VIPLevel.COMMON);
        user.setTotalIntegral(0);
        user.setCurrentIntegral(0);
        user.setCancelled(false);
        return userDao.save(user);
    }

    public User login(String account, String password) {
        return userDao.find(account, password);
    }

    public User getUserById(int userId) {
        return userDao.findById(userId);
    }

    public ResultMessage updateUserInfo(String userJson) {
        JSONObject jsonObject = JSONObject.fromObject(userJson);
        User user = (User) JSONObject.toBean(jsonObject, User.class);
        return userDao.update(user);
    }

    public ResultMessage emailConfirmation(int userId, String emailAddress) {
//        // 配置
//        Properties prop=new Properties();
//        // 设置邮件服务器主机名，这里是163
//        prop.put("mail.host","smtp.163.com" );
//        // 发送邮件协议名称
//        prop.put("mail.transport.protocol", "smtp");
//        // 是否认证
//        prop.put("mail.smtp.auth", true);
//
//        try {
//            // SSL加密
//            MailSSLSocketFactory sf = null;
//            sf = new MailSSLSocketFactory();
//            // 设置信任所有的主机
//            sf.setTrustAllHosts(true);
//            prop.put("mail.smtp.ssl.enable", "true");
//            prop.put("mail.smtp.ssl.socketFactory", sf);
//
//            // 创建会话对象
//            Session session = Session.getDefaultInstance(prop, new Authenticator() {
//                // 认证信息，需要提供"用户账号","授权码"
//                public PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication("用户账号", "授权码");
//                }
//            });
//            // 是否打印出debug信息
//            session.setDebug(true);
//
//            // 创建邮件
//            Message message = new MimeMessage(session);
//            // 邮件发送者
//            message.setFrom(new InternetAddress("用户账号"));
//            // 邮件接受者
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//            // 邮件主题
//            message.setSubject("激活邮件");
//            String content = "<html><head></head><body><h1>请点击连接激活</h1><h3><a href='http://localhost:8080/active?code="
//                    + code + "'>http://localhost:8080/active?code=" + code + "</href></h3></body></html>";
//            message.setContent(content, "text/html;charset=UTF-8");
//            // Transport.send(message);
//            // 邮件发送
//            Transport transport = session.getTransport();
//            transport.connect();
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return ResultMessage.SUCCESS;
    }

    public ResultMessage emailReConfirmation(String emailAddress) {
        return ResultMessage.SUCCESS;
    }

    public List<User> getAllUsers() {
        return null;
    }
}
